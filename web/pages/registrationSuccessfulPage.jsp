<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 04.10.18
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="jspf/import.jspf"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>
    <fmt:message key="registration.succesfull"/> ${e:forHtml(user.nickName)}!
</h1>
<form action="${pageContext.request.contextPath}/main">
    <input type="hidden" name="command" value="go_to_start">
    <button type="submit"><fmt:message key="go.to.start"/> </button>
</form>
<form action="${pageContext.request.contextPath}/main">
    <input type="hidden" name="command" value="go_to_cabinet">
    <button type="submit"><fmt:message key="go.to.cabinet"/> </button>
</form>
</body>
</html>
