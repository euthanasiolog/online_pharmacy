<%@ page import="java.util.ResourceBundle" %><%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 13.09.18
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="prop.pagecontent"/>
<html>
<head>
    <title><fmt:message key="startpage.title"/></title>
</head>
<body>
<form action="/main" method="post">
<input type="hidden" name="command" value="signIn">
    <label for="login">Login</label><br>
    <input type="text" id="login" name="login"><br>
    <label for="password">Password</label><br>
    <input type="password" id="password" name="password"><br>
    <label for="role">Role</label><br>
    <select id="role" name="role">
        <option name="client">client</option>
        <option name="doctor">doctor</option>
        <option name="pharmacist">pharmacist</option>
    </select><br>
    <input type="submit">
</form>
</body>
</html>
