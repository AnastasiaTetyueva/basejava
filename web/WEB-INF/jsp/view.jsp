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
            <jsp:useBean id="sectionType" type="com.urise.webapp.model.SectionType"/>
        <c:if test="${sections.get(sectionType) != null}">
            <h2><%=sectionType.getTitle()%></h2>
            <c:choose>
                <c:when test="${sectionType.equals(SectionType.PERSONAL)}">
                    <p>
                        <%=resume.getSection(SectionType.PERSONAL)%>
                    </p>
                    <br/>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.OBJECTIVE)}">
                    <p>
                        <%=resume.getSection(SectionType.OBJECTIVE)%>
                    </p>
                    <br/>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.ACHIEVEMENT)}">
                    <ul>
                    <%ListSection listSection = (ListSection) resume.getSection(SectionType.ACHIEVEMENT);
                        for (String row : listSection.getList()) {
                    %>
                        <li>
                            <%=row%>
                        </li>
                    <%
                        }
                    %>
                    </ul>
                    <br/>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.QUALIFICATIONS)}">
                    <ul>
                        <%ListSection listSection = (ListSection) resume.getSection(SectionType.QUALIFICATIONS);
                            for (String row : listSection.getList()) {
                        %>
                        <li>
                            <%=row%>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                    <br/>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.EXPERIENCE)}">
                    <table>
                        <%
                            OrganizationSection organizationSection = (OrganizationSection) resume.getSection(SectionType.EXPERIENCE);
                            List<Organization> organizationList = organizationSection.getOrganizations();
                            for (Organization organization : organizationList) {
                        %>
                        <tr>
                            <td>
                                <%
                                    List<Period> periods = organization.getPeriods();
                                    for (Period period : periods) {
                                %>
                                <p class="org">
                                    <%=period.getStart() + " -- " + period.getEnd()%><br/>
                                </p>
                                <p>
                                    <%=period.getTitle()%><br/>
                                </p>
                                <p>
                                    <%=period.getDescription()%><br/>
                                </p>
                                <%
                                    }
                                %>
                            </td>
                            <td>
                                <p class="org">
                                    <%=organization.getName()%>
                                </p>
                                <p>
                                    <a href="<%=organization.getWebsite()%>>"><%=organization.getWebsite()%></a>
                                </p>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </c:when>
                <c:when test="${sectionType.equals(SectionType.EDUCATION)}">
                    <table>
                        <%
                            OrganizationSection organizationSection = (OrganizationSection) resume.getSection(SectionType.EDUCATION);
                            List<Organization> organizationList = organizationSection.getOrganizations();
                            for (Organization organization : organizationList) {
                        %>
                        <tr>
                            <td>
                                <%
                                    List<Period> periods = organization.getPeriods();
                                    for (Period period : periods) {
                                %>
                                <p class="org">
                                    <%=period.getStart() + " -- " + period.getEnd()%><br/>
                                </p>
                                <p>
                                    <%=period.getTitle()%><br/>
                                </p>
                                <p>
                                    <%=period.getDescription()%><br/>
                                </p>
                                <%
                                    }
                                %>
                            </td>
                            <td>
                                <p class="org">
                                    <%=organization.getName()%>
                                </p>
                                <p>
                                    <a href="<%=organization.getWebsite()%>>"><%=organization.getWebsite()%></a>
                                </p>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </c:when>

            </c:choose>
        </c:if>
    </c:forEach>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>