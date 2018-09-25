<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 13.09.18
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello!</title>
</head>
<body>
<form action="/main" method="post">
<input type="hidden" name="command" value="signIn">
    <label for="login">Login</label><br>
    <input type="text" id="login" name="login"><br>
    <label for="password">Password</label><br>
    <input type="password" id="password" name="password"><br>
    <input type="submit">
</form>
</body>
</html>
