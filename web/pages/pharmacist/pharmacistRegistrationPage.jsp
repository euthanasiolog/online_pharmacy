<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 04.10.18
  Time: 00:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/pharmacist/pharmacistRegistrationPage.jsp"/>
    <title><fmt:message key="registration.pharmacist.title"/> </title>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md-auto">
            <h3><label for="registration_pharmacist"><fmt:message key="registration.pharmacist.title"/></label></h3>
            <form action="${pageContext.request.contextPath}/main" method="post" id="registration_pharmacist">
                <input type="hidden" name="role" value="pharmacist">
                <%@include file="../jspf/registratinUserForm.jspf"%>
                <button type="submit" class="btn btn-primary"><fmt:message key="registrationuserform.submit"/> </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
