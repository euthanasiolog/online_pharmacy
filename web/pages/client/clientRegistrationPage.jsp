<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 04.10.18
  Time: 00:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/client/clientRegistrationPage.jsp" scope="request"/>
    <title><fmt:message key="registration.client.title"/></title>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <label for="registrationclient"><fmt:message key="clientregistrationpage.registrationform.label"/> </label>
    <form name="registrationclient" id="registrationclient" method="get">
        <input type="hidden" name="role" value="client">
    <%@include file="../jspf/registratinUserForm.jspf"%>
        <button type="submit" class="btn btn-primary"><fmt:message key="registrationuserform.submit"/></button>
    </form>
</div>
</body>
</html>

