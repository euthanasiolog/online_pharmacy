<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 29.10.18
  Time: 12:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/client/recipeRequestConfirm.jsp"/>
    <title>Title</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg">
            <form action="${pageContext.request.contextPath}/main" method="post">
                <input type="hidden" name="command" value="confirm_recipe_request">
                <input type="hidden" name="drug_id" value="${requestScope.recipeRequest.drugId}">
                <input type="hidden" name="requestRecipeType" value="${requestScope.recipeRequest.recipeType}">
                <input type="hidden" name="clientId" value="${user.id}">
                <%@include file="../jspf/doctorsTable.jspf"%>
                <button type="submit" class="btn btn-primary"><fmt:message key="send.rec"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
