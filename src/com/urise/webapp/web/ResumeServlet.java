package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private SqlStorage storage = Config.get().getStorage();

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
                for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                    ContactType type = entry.getKey();
                    switch (type) {
                        case PHONE:
                            out.printf("<a href='tel:+7(921) 855-0482'>Tелефон</a><br>");
                            break;
                        case EMAIL:
                            out.printf("<a href='mailto:gkislin@yandex.ru'>E-mail</a><br>");
                            break;
                        case SKYPE:
                            out.printf("<a href='skype:grigory.kislin'>Skype</a><br>");
                            break;
                        case GITHUB:
                            out.printf("<a href='https://github.com/gkislin'>Профиль Github</a><br>");
                            break;
                        case HOMEPAGE:
                            out.printf("<a href='http://gkislin.ru/'>Домашняя страница</a><br>");
                            break;
                        case LINKEDIN:
                            out.printf("<a href='https://www.linkedin.com/in/gkislin'>Профиль Linkedin</a><br>");
                            break;
                        case STACKOVERFLOW:
                            out.printf("<a href='https://stackoverflow.com/users/548473'>Профиль Stackoverflow</a><br>");
                            break;
                    }
                }
                out.println("<br>");

                Map<SectionType, AbstractSection> sections = resume.getSections();
                for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                    SectionType type = entry.getKey();
                    switch (type) {
                        case PERSONAL: {
                            TextSection section = (TextSection) entry.getValue();
                            out.printf("<h2>Личные качества</h2>");
                            out.printf("<p>%s</p><br>", section.getText());
                        }
                        break;
                        case OBJECTIVE: {
                            TextSection section = (TextSection) entry.getValue();
                            out.printf("<h2>Позиция</h2>");
                            out.printf("<p>%s</p><br>", section.getText());
                        }
                        break;
                        case ACHIEVEMENT: {
                            ListSection section = (ListSection) entry.getValue();
                            out.printf("<h2>Достижения</h2>");
                            listToHtml(out, section);
                        }
                        break;
                        case QUALIFICATIONS: {
                            ListSection section = (ListSection) entry.getValue();
                            out.printf("<h2>Квалификация</h2>");
                            listToHtml(out, section);
                        }
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
