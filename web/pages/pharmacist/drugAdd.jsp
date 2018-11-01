<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 28.10.18
  Time: 00:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/pharmacist/drugAdd.jsp"/>
    <title><fmt:message key="drug.add"/> </title>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <div class="col-md-auto">
        <div class="row">
            <form action="${pageContext.request.contextPath}/main" method="post">
                <input type="hidden" name="page" value="${pagePass}">
                <input type="hidden" name="command" value="add_drug">
                <%@include file="../jspf/drugForm.jspf"%>
                <button type="submit" class="btn btn-primary"><fmt:message key="drug.add"/> </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
