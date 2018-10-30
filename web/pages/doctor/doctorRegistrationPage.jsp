<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 04.10.18
  Time: 00:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <title><fmt:message key="registration.doctor.title"/> </title>
    <c:set var="pagePass" value="pages/doctor/doctorRegistrationPage.jsp" scope="request"/>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <label for="registration_doctor"><fmt:message key="registration.doctor.title"/> </label>
    <form action="${pageContext.request.contextPath}/main" name="registration_doctor" id="registration_doctor" method="post">
        <input type="hidden" name="role" value="doctor">
        <%@include file="../jspf/registratinUserForm.jspf"%>
        <div class="form-group">
            <label for="workplace"><fmt:message key="doctor.registration.workplace"/></label>
            <input class="form-control" type="text" name="workplace" id="workplace">
            <small class="small"><c:if test="${requestScope.workplaceError != null && requestScope.workplaceError != ''}">
                <c:out value="${requestScope.workplaceError}"/>
            </c:if></small>
        </div>
        <div class="form-group">
            <label for="specialization"><fmt:message key="doctor.registration.specialization"/></label>
            <input class="form-control" type="text" name="specialization" id="specialization">
            <small class="small"><c:if test="${requestScope.specializationError != null && requestScope.specializationError != ''}">
                <c:out value="${requestScope.specializationError}"/>
            </c:if></small>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="send.request"/></button>
    </form>
</div>
</body>
</html>
