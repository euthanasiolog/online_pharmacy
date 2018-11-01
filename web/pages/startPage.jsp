
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@include  file="jspf/import.jspf"%>
    <title><fmt:message key="startpage.title"/></title>
    <c:set var="pagePass" value="pages/startPage.jsp" scope="request"/>
</head>
<body>
<%@ include file="jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">

        </div>
        <div class="col-md-auto">
            <%@include file="jspf/drugTable.jspf"%>
        </div>
    </div>
</div>
<%@include file="jspf/footer.jspf"%>
</body>
</html>
