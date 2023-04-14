package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;

public class ResumeServlet extends HttpServlet {
    private SqlStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isCreating = (uuid == null || uuid.length() == 0);
        Resume r;
        if (isCreating) {
            r = new Resume();
        } else {
            r = storage.get(uuid);
        }
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.setSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.setSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        r.setSection(type, new OrganizationSection());
                    default: break;
                }
            } else {
                r.getSections().remove(type);
            }
        }
        if (isCreating) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                        switch (type) {
                            case PERSONAL:
                                if (section == null) {
                                    r.setSection(SectionType.PERSONAL, new TextSection());
                                }
                                break;
                            case OBJECTIVE:
                                if (section == null) {
                                    r.setSection(SectionType.OBJECTIVE, new TextSection());
                                }
                                break;
                            case ACHIEVEMENT:
                                if (section == null) {
                                    r.setSection(SectionType.ACHIEVEMENT, new ListSection());
                                }
                                break;
                            case QUALIFICATIONS:
                                if (section == null) {
                                    r.setSection(SectionType.QUALIFICATIONS, new ListSection());
                                }
                                break;
                            case EXPERIENCE:
                                if (section == null) {
                                    r.setSection(SectionType.EXPERIENCE, new OrganizationSection());
                                }
                                break;
                            case EDUCATION:
                                if (section == null) {
                                    r.setSection(SectionType.EDUCATION, new OrganizationSection());
                                }
                                break;
                            default:
                                break;
                        }

                }
                break;
            case "create":
                r = new Resume();
                r.setUuid(null);
                r.setSection(SectionType.PERSONAL, new TextSection());
                r.setSection(SectionType.OBJECTIVE, new TextSection());
                r.setSection(SectionType.ACHIEVEMENT, new ListSection());
                r.setSection(SectionType.QUALIFICATIONS, new ListSection());
                r.setSection(SectionType.EXPERIENCE, new OrganizationSection());
                r.setSection(SectionType.EDUCATION, new OrganizationSection());
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        if (action.equals("create")) {
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher(
                    ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
            ).forward(request, response);
        }
    }

}
