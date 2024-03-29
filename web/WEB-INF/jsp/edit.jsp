<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <h3>Имя:</h3>
            <dd><input type="text" name="fullName" required="required" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:set var="contacts" value="${resume.contacts}"/>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <jsp:useBean id="contactType" type="com.urise.webapp.model.ContactType"/>
                <dl>
                    <dt>${contactType.title}</dt>
                    <dd><input type="text" name="${contactType.name()}" size=30 value="${resume.getContact(contactType)}"></dd>
                </dl>
        </c:forEach>
        <hr>
        <c:set var="sections" value="${resume.sections}"/>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:if test="${sections.get(sectionType) != null}">
                <c:set var="section" value="${sections.get(sectionType)}"/>
                <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
                <h3>${sectionType.title}</h3>
                <c:choose>
                    <c:when test="${sectionType=='PERSONAL' || sectionType=='OBJECTIVE'}">
                        <textarea name="${sectionType}" cols=70 rows=5><%=section%></textarea>
                    </c:when>
                    <c:when test="${sectionType=='ACHIEVEMENT' || sectionType=='QUALIFICATIONS'}">
                        <textarea name="${sectionType}" cols=70
                                  rows=5><%=((ListSection) section).getList().stream().map(String::trim).filter(x -> !x.isEmpty()).collect(Collectors.joining("\n"))%></textarea>
                    </c:when>
                    <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>" varStatus="counter">
                            <dl>
                                <dt style="font-weight: bold">Наименование организации</dt>
                                <dd><input type="text" name="${sectionType}" size=80 value="${org.name}"></dd>
                            </dl>
                            <dl>
                                <dt>Сайт организации</dt>
                                <dd><input type="text" name="${sectionType}website" size=80 value="${org.website}"></dd>
                            </dl>
                            <c:forEach var="period" items="${org.periods}">
                                <c:if test="${org.name.trim() != ''}">
                                    <dl>
                                        <dt>Начало работы/учебы</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}start" size=20 value="${period.start}" placeholder="ГГГГ-ММ-ДД"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Конец работы/учебы</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}end" size=20 value="${period.end}" placeholder="ГГГГ-ММ-ДД"></dd>
                                    </dl>
                                </c:if>
                                <c:if test="${org.name.trim() == ''}">
                                    <dl>
                                        <dt>Начало работы/учебы</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}start" size=20 value="" placeholder="ГГГГ-ММ-ДД"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Конец работы/учебы</dt>
                                        <dd><input type="text" name="${sectionType}${counter.index}end" size=20 value="" placeholder="ГГГГ-ММ-ДД"></dd>
                                    </dl>
                                </c:if>
                                <dl>
                                    <dt>Должность/специальность</dt>
                                    <dd><input type="text" name="${sectionType}${counter.index}title" size=80 value="${period.title}"></dd>
                                </dl>
                                <dl>
                                    <dt>Описание</dt>
                                    <textarea name="${sectionType}${counter.index}description" cols=70 rows=5>${period.description}</textarea>
                                </dl>
                                <div style="margin: 50px"></div>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="submit" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>