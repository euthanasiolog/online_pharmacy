<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 04.10.18
  Time: 00:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%--<jsp:directive.page pageEncoding="UTF-8" contentType="text/html; UTF-8"/>--%>
<%@include file="../jspf/import.jspf"%>
<fmt:requestEncoding value="utf-8"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <c:set var="pagePass" value="pages/client/clientRegistrationPage.jsp" scope="request"/>
    <title><fmt:message key="registration.client.title"/></title>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <label for="registrationclient"><fmt:message key="clientregistrationpage.registrationform.label"/> </label>
    <form action="${pageContext.request.contextPath}/main" name="registration_client" accept-charset="UTF-8" id="registrationclient" method="post">
        <input type="hidden" name="role" value="client">
    <%@include file="../jspf/registratinUserForm.jspf"%>
        <button type="submit" class="btn btn-primary"><fmt:message key="registrationuserform.submit"/></button>
    </form>
</div>
</body>
</html>

