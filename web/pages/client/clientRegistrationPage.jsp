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
    <form name="client" id="client" method="post">
        <input type="hidden" name="command" value="registerclient">
        <div class="form-group">
            <label for="usernickname"></label>
            <input class="form-control" name="nickname" type="text" id="nickname">
            <small id="nickname-helper" class="small"></small>
        </div>
        <div class="form-group">
            <label for="email"></label>
            <input type="email" class="form-control" name="email" id="email">
        </div>
        <div class="form-group">
            <label for="firstname"></label>
            <input class="form-control" type="text" name="firstname" id="firstname">
        </div>
        <div class="form-group">
            <label for="lastname"></label>
            <input class="form-control" type="text" name="lastname" id="lastname">
        </div>
        <div class="form-group">
            <label for="patronymic"></label>
            <input type="text" class="form-control" name="patronymic" id="patronymic">
        </div>
        <div class="form-group">
            <label for="day"></label>
            <input class="form-control" type="text" name="day" id="day">
            <input class="form-control" type="text" name="month" id="month">
            <input class="form-control" type="text" name="year" id="year">
        </div>
        <button type="submit" class="button"></button>
    </form>
</div>
</body>
</html>

