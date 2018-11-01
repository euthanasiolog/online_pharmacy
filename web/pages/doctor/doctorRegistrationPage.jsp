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
    <div class="row">
        <div class="col-md">
            <c:set var="type" value="doctor" scope="page"/>
            <form action="${pageContext.request.contextPath}/main" class="form-group" method="post">
                <%@include file="../jspf/registratinUserForm.jspf"%>
            </form>
        </div>
    </div>
</div>
</body>
</html>
