<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 04.10.18
  Time: 00:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../jspf/import.jspf"%>
<html>
<head>
    <c:set var="pagePass" value="pages/client/clientRegistrationPage.jsp"/>
    <title><fmt:message key="registration.client.title"/></title>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">
            <c:set var="type" value="client" scope="page"/>
            <form action="${pageContext.request.contextPath}/main" class="form-group" method="post">
                <%@include file="../jspf/registratinUserForm.jspf"%>
            </form>
        </div>
    </div>
</div>
</body>
</html>

