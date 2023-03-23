package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
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
/*
        RESUME.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям."));

        RESUME.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        // Achievements
        ListSection achievments = new ListSection();
        List<String> achievementList = achievments.getList();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков.");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\".");
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike.");
        RESUME.setSection(SectionType.ACHIEVEMENT, achievments);

        // Qualification
        ListSection qualifications = new ListSection();
        List<String> qualificationList = qualifications.getList();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2.");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce.");
        qualificationList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy.");
        RESUME.setSection(SectionType.QUALIFICATIONS, qualifications);

        // Experience
        OrganizationSection experience = new OrganizationSection();
        List<Organization> experienceList = experience.getOrganizations();

        Organization organizationEx1 = new Organization();
        organizationEx1.setName("Java Online Projects");
        organizationEx1.setWebsite("http://javaops.ru/");
        List<Period> periodListEx1 = organizationEx1.getPeriods();
        Period periodEx1 = new Period(LocalDate.of(2013, 10, 01), LocalDate.now(), "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        periodListEx1.add(periodEx1);
        experienceList.add(organizationEx1);

        Organization organizationEx2 = new Organization();
        organizationEx2.setName("Wrike");
        organizationEx2.setWebsite("https://www.wrike.com/");
        List<Period> periodListEx2 = organizationEx2.getPeriods();
        Period periodEx2 = new Period(LocalDate.of(2014, 10, 01), LocalDate.of(2016, 01, 01 ),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis).");
        periodListEx2.add(periodEx2);
        experienceList.add(organizationEx2);

        RESUME.setSection(SectionType.EXPERIENCE, experience);

        // Education
        OrganizationSection education = new OrganizationSection();
        List<Organization> educationList = education.getOrganizations();

        Organization organizationEd1 = new Organization();
        organizationEd1.setName("Coursera");
        organizationEd1.setWebsite("https://www.coursera.org/course/progfun");
        List<Period> periodListEd1 = organizationEd1.getPeriods();
        Period periodEd1 = new Period(LocalDate.of(2013, 03, 01), LocalDate.of(2013, 05, 01), "",
                "'Functional Programming Principles in Scala' by Martin Odersky");
        periodListEd1.add(periodEd1);
        educationList.add(organizationEd1);

        Organization organizationEd2 = new Organization();
        organizationEd2.setName("Luxoft");
        organizationEd2.setWebsite("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        List<Period> periodListEd2 = organizationEd2.getPeriods();
        Period periodEd2 = new Period(LocalDate.of(2011, 03, 01), LocalDate.of(2011, 04, 01), "",
                "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML'");
        periodListEd2.add(periodEd2);
        educationList.add(organizationEd2);

        Organization organizationEd3 = new Organization();
        organizationEd3.setName("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
        organizationEd3.setWebsite("http://www.ifmo.ru/");
        List<Period> periodListEd3 = organizationEd3.getPeriods();
        Period periodEd3Second = new Period(LocalDate.of(1993, 9, 01), LocalDate.of(1996, 07, 01), "",
                "Аспирантура (программист С, С++)");
        periodListEd3.add(periodEd3Second);
        Period periodEd3First = new Period(LocalDate.of(1987, 9, 01), LocalDate.of(1993, 07, 01), "",
                "Инженер (программист Fortran, C)");
        periodListEd3.add(periodEd3First);
        educationList.add(organizationEd3);

        RESUME.setSection(SectionType.EDUCATION, education);

        System.out.println(RESUME.toString()); */
    }

     public static Resume createResume(String uuid, String fulLName) {
        Resume RESUME = new Resume(uuid, fulLName);

        RESUME.setContact(ContactType.PHONE, "+7(921) 855-0482");
        RESUME.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        RESUME.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        RESUME.setContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        RESUME.setContact(ContactType.GITHUB, "https://github.com/gkislin");
        RESUME.setContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        RESUME.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
/*
        RESUME.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям."));

        RESUME.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        // Achievements
        ListSection achievments = new ListSection();
        List<String> achievementList = achievments.getList();
        achievementList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков.");
        achievementList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\".");
        RESUME.setSection(SectionType.ACHIEVEMENT, achievments);

        // Qualification
        ListSection qualifications = new ListSection();
        List<String> qualificationList = qualifications.getList();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2.");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce.");
        RESUME.setSection(SectionType.QUALIFICATIONS, qualifications);

        // Experience
        OrganizationSection experience = new OrganizationSection();
        List<Organization> experienceList = experience.getOrganizations();

        Organization organizationEx1 = new Organization();
        organizationEx1.setName("Java Online Projects");
        organizationEx1.setWebsite("http://javaops.ru/");
        List<Period> periodListEx1 = organizationEx1.getPeriods();
        Period periodEx1 = new Period(LocalDate.of(2013, 10, 01), LocalDate.now(), "Автор проекта",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        periodListEx1.add(periodEx1);
        experienceList.add(organizationEx1);
        RESUME.setSection(SectionType.EXPERIENCE, experience);

        // Education
        OrganizationSection education = new OrganizationSection();
        List<Organization> educationList = education.getOrganizations();
        Organization organizationEd1 = new Organization();
        organizationEd1.setName("Coursera");
        organizationEd1.setWebsite("https://www.coursera.org/course/progfun");
        List<Period> periodListEd1 = organizationEd1.getPeriods();
        Period periodEd1 = new Period(LocalDate.of(2013, 03, 01), LocalDate.of(2013, 05, 01), "",
                "'Functional Programming Principles in Scala' by Martin Odersky");
        periodListEd1.add(periodEd1);
        educationList.add(organizationEd1);

        Organization organizationEd3 = new Organization();
        organizationEd3.setName("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
        organizationEd3.setWebsite("http://www.ifmo.ru/");
        List<Period> periodListEd3 = organizationEd3.getPeriods();
        Period periodEd3Second = new Period(LocalDate.of(1993, 9, 01), LocalDate.of(1996, 07, 01), "",
                "Аспирантура (программист С, С++)");
        periodListEd3.add(periodEd3Second);
        Period periodEd3First = new Period(LocalDate.of(1987, 9, 01), LocalDate.of(1993, 07, 01), "",
                "Инженер (программист Fortran, C)");
        periodListEd3.add(periodEd3First);
        educationList.add(organizationEd3);
        RESUME.setSection(SectionType.EDUCATION, education);
*/
        return RESUME;


    }
}
