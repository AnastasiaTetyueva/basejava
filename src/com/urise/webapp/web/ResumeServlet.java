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
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private SqlStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String uuid = request.getParameter("uuid");
            if (uuid != null) {
                Resume resume = storage.get(request.getParameter("uuid"));
                out.println("<html><head><title>Resume</title></head>");
                out.println("<link rel=\"stylesheet\" href=\"css/style.css\">");
                out.println("<body>");
                out.printf("<h1>%s</h1><br>", resume.getFullName());

                Map<ContactType, String> contacts = resume.getContacts();
                for (ContactType contact : ContactType.values()) {
                    out.printf("<a href='%s%s'>%s</a><br>", contact.getPrefix(), contacts.get(contact), contact.getTitle());
                }
                out.println("<br>");

                Map<SectionType, AbstractSection> sections = resume.getSections();
                for (SectionType section : SectionType.values()) {
                    out.printf("<h2>%s</h2>", section.getTitle());
                    switch (section) {
                        case PERSONAL:
                        case OBJECTIVE: {
                            TextSection sec = (TextSection) sections.get(section);
                            out.printf("<p>%s</p><br>", sec);
                        }
                        break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS: {
                            ListSection sec = (ListSection) sections.get(section);
                            listToHtml(out, sec);
                        }
                        break;
                        default:
                            break;
                    }
                }
            } else {
                List<Resume> list = storage.getAllSorted();
                out.println("<html><head><title>AllResumes</title></head>");
                out.println("<link rel=\"stylesheet\" href=\"css/style.css\">");
                out.println("<body>");
                out.println("<h1>Резюме</h1><br>");
                out.println("<table>");
                out.println("<tr><td>UUID</td><td>Ф.И.О. соискателя</td></tr>");
                for (Resume r : list) {
                    out.printf("<tr><td>%s</td>", r.getUuid());
                    out.printf("<td><a href='/resumes/resume?uuid=%s'>%s</a></td></tr>", r.getUuid(), r.getFullName());
                }
                out.println("</table>");
            }
            out.println("</body></html>");
        }
    }

    private void listToHtml(PrintWriter printWriter, ListSection section) {
        printWriter.printf("<ul>");
        for (String row : section.getList()) {
            printWriter.printf("<li>%s</li>", row);
        }
        printWriter.printf("</ul><br>");
    }


}
