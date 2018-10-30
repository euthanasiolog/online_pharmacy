<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 24.10.18
  Time: 01:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="jspf/import.jspf"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-lg-auto">
            <h3><fmt:message key="registration.succesfull.confirm"/> </h3>
            <form action="${pageContext.request.contextPath}/main" method="post">
                <input type="hidden" name="command" value="sign_in">
                <label for="login"><fmt:message key="startpage.label.login"/> </label><br>
                <input type="text" id="login" name="login"><br>
                <label for="password"><fmt:message key="startpage.label.password"/> </label><br>
                <input type="password" id="password" name="password"><br>
                <input type="submit">
            </form>
        </div>
    </div>
</div>

</body>
</html>
