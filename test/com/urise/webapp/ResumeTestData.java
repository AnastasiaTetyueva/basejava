package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume RESUME = new Resume();
        RESUME.setFullName("Григорий Кислин");

        RESUME.setContact(ContactType.PHONE, "+7(921) 855-0482");
        RESUME.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        RESUME.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        RESUME.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        RESUME.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        RESUME.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        RESUME.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        RESUME.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям."));

        RESUME.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        List<String> achievementList = new ArrayList<>();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков.");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
        RESUME.setSection(SectionType.ACHIEVEMENT, new ListSection(achievementList));

        List<String> qualificationList = new ArrayList<>();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        RESUME.setSection(SectionType.QUALIFICATIONS, new ListSection(qualificationList));

        List<Period> periodListEx1 = new ArrayList<>();
        Period periodEx1 = new Period(LocalDate.of(2013, 10, 01), LocalDate.now(), "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        periodListEx1.add(periodEx1);

        List<Period> periodListEx2 = new ArrayList<>();
        Period periodEx2 = new Period(LocalDate.of(2014, 10, 01), LocalDate.of(2016, 01, 01 ),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).");
        periodListEx2.add(periodEx2);

        List<Organization> organizationListEx = new ArrayList<>();
        Organization organizationEx1 = new Organization("Java Online Projects", "http://javaops.ru/", periodListEx1);
        Organization organizationEx2 = new Organization("Wrike", "https://www.wrike.com/", periodListEx2);
        organizationListEx.add(organizationEx1);
        organizationListEx.add(organizationEx2);

        RESUME.setSection(SectionType.EXPERIENCE, new OrganizationSection(organizationListEx));

        List<Period> periodListEd1 = new ArrayList<>();
        Period periodEd1 = new Period(LocalDate.of(2013, 03, 01), LocalDate.of(2013, 05, 01), "",
                        "'Functional Programming Principles in Scala' by Martin Odersky");
        periodListEd1.add(periodEd1);

        List<Period> periodListEd2 = new ArrayList<>();
        Period periodEd2 = new Period(LocalDate.of(2011, 03, 01), LocalDate.of(2011, 04, 01), "",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML'");
        periodListEd1.add(periodEd2);

        List<Organization> organizationListEd = new ArrayList<>();
        Organization organizationEd1 = new Organization("Coursera", "https://www.coursera.org/course/progfun", periodListEd1);
        Organization organizationEd2 = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", periodListEd2);
        organizationListEd.add(organizationEd1);
        organizationListEd.add(organizationEd2);

        RESUME.setSection(SectionType.EDUCATION, new OrganizationSection(organizationListEd));

        System.out.println(RESUME.toString());
    }
}
