<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 01.11.18
  Time: 23:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/client/recipes.jsp"/>
    <title><fmt:message key="client.recipes.page"/> </title>
</head>
<body>
<%@ include file="../jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">
            <%@include file="../jspf/recipeClientTable.jspf"%>
        </div>
        <div class="col-md">
            <%@include file="../jspf/usedRecipeClientTable.jspf"%>
        </div>
    </div>
    <div class="row">
        <div class="col-md">
            requests
            <%@include file="../jspf/recipeRequestClientTable.jspf"%>
        </div>
    </div>
</div>
</body>
</html>
