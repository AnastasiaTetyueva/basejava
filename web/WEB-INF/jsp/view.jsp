<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/styleView.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h1>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h1>
    <p class="con">
        <c:set var="contacts" value="${resume.contacts}"/>
        <c:forEach var="contactType" items="${ContactType.values()}">
                <jsp:useBean id="contactType" type="com.urise.webapp.model.ContactType"/>
            <c:if test="${contacts.get(contactType) != null}">
                <c:if test="${contactType.equals(ContactType.PHONE)}">
                    <c:set var="img" value="<img src='img/phone.png' height='16' width='16'>"/>
                </c:if>
                <c:if test="${contactType.equals(ContactType.SKYPE)}">
                    <c:set var="img" value="<img src='img/skype.png'>"/>
                </c:if>
                <c:if test="${contactType.equals(ContactType.EMAIL)}">
                    <c:set var="img" value="<img src='img/email.png'>"/>
                </c:if>
                <c:if test="${contactType.equals(ContactType.LINKEDIN)}">
                    <c:set var="img" value="<img src='img/lin.png'>"/>
                </c:if>
                <c:if test="${contactType.equals(ContactType.GITHUB)}">
                    <c:set var="img" value="<img src='img/gh.png'>"/>
                </c:if>
                <c:if test="${contactType.equals(ContactType.STACKOVERFLOW)}">
                    <c:set var="img" value="<img src='img/so.png'>"/>
                </c:if>
                <c:if test="${contactType.equals(ContactType.HOMEPAGE)}">
                    <c:set var="img" value="<img src='img/homepage.png' height='16' width='16'>"/>
                </c:if>
                    ${img}<a href="${contactType.prefix}${contacts.get(contactType)}">${contacts.get(contactType)}</a><br/>
            </c:if>
        </c:forEach>
    <p>
    <br/>
    <c:set var="sections" value="${resume.sections}"/>
    <c:forEach var="sectionType" items="${SectionType.values()}">
        <c:if test="${sections.get(sectionType) != null}">
            <c:set var="section" value="${sections.get(sectionType)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <c:if test="<%=!section.isEmpty()%>">
                <h2>${sectionType.title}</h2>
                <c:choose>
                    <c:when test="${sectionType=='PERSONAL' || sectionType=='OBJECTIVE'}">
                        <p>
                            <%=((TextSection) section).getText()%>
                        </p>
                        <br/>
                    </c:when>
                    <c:when test="${sectionType=='ACHIEVEMENT' || sectionType=='QUALIFICATIONS'}">
                        <ul>
                            <c:forEach var="row" items="<%=((ListSection) section).getList().stream().map(String::trim).filter(x->!x.isEmpty()).toArray()%>">
                                <li>
                                        ${row}
                                </li>
                            </c:forEach>
                        </ul>
                        <br/>
                    </c:when>
                    <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION'}">
                        <table>
                            <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>">
                                <tr>
                                    <td>
                                        <c:forEach var="period" items="${organization.periods}">
                                            <p class="org">
                                                    ${period.start} + " -- " + ${period.end}<br/>
                                            </p>
                                            <p>
                                                    ${period.title}<br/>
                                            </p>
                                            <p>
                                                    ${period.description}<br/>
                                            </p>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <p class="org">
                                                ${organization.name}
                                        </p>
                                        <p>
                                                <a href="${organization.website}>">${organization.website}</a>
                                        </p>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                </c:choose>
            </c:if>
        </c:if>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>