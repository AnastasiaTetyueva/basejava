package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        if (value != null && value.trim().length() != 0) {
                            r.setSection(type, new TextSection(value));
                        } else {
                            r.getSections().remove(type);
                        }
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        if (value != null && value.trim().length() != 0) {
                            r.setSection(type, new ListSection(Arrays.asList(value.split("\n"))));
                        } else {
                            r.getSections().remove(type);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> orgs = new ArrayList<>();
                        String[] names = request.getParameterValues(type.name());
                        for (int i = 0; i < names.length; i++) {
                            List<Period> periodList = new ArrayList<>();
                            String name = names[i];
                            String website = request.getParameterValues(type.name() + "website")[i];
                            String[] startDates = request.getParameterValues(type.name() + i + "start");
                            String[] endDates = request.getParameterValues(type.name() + i + "end");
                            String[] titles = request.getParameterValues(type.name() + i + "title");
                            String[] descriptions = request.getParameterValues(type.name() + i + "description");
                            for (int j = 0; j < titles.length; j++) {
                                if (titles[j] != null) {
                                    periodList.add(new Period(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                }
                            }
                            if (titles.length > 0 && !name.isEmpty()) {
                                orgs.add(new Organization(periodList, name, website));
                            }
                        }
                        if (orgs.stream().count() > 0) {
                            r.setSection(type, new OrganizationSection(orgs));
                        } else {
                            r.getSections().remove(type);
                        }
                    default: break;
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
                            case OBJECTIVE:
                                if (section == null) {
                                    r.setSection(type, TextSection.EMPTY);
                                }
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                if (section == null) {
                                    r.setSection(type, ListSection.EMPTY);
                                }
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                if (section == null) {
                                    r.setSection(type, OrganizationSection.EMPTY);
                                } else {
                                    OrganizationSection organizationSection = (OrganizationSection) section;
                                    List<Organization> orgs = new ArrayList<>();
                                    orgs.add(Organization.EMPTY);
                                    for (Organization org : organizationSection.getOrganizations()) {
                                        List<Period> periodList = new ArrayList<>();
                                        periodList.add(Period.EMPTY);
                                        periodList.addAll(org.getPeriods());
                                        orgs.add(new Organization(org.getPeriods(), org.getName(), org.getWebsite()));
                                    }
                                    r.setSection(type, new OrganizationSection(orgs));
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
                r.setSection(SectionType.PERSONAL, TextSection.EMPTY);
                r.setSection(SectionType.OBJECTIVE, TextSection.EMPTY);
                r.setSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
                r.setSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
                r.setSection(SectionType.EXPERIENCE, OrganizationSection.EMPTY);
                r.setSection(SectionType.EDUCATION, OrganizationSection.EMPTY);
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
