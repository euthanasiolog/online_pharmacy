<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 29.10.18
  Time: 18:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/admin/adminCabinet.jsp"/>
    <title>Title</title>
</head>
<body>
<%@include file="../jspf/adminHeader.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">
            <%@include file="../jspf/doctorQueriesTable.jspf"%>
        </div>
        <div class="col-md">
            <%@include file="../jspf/pharmacistQueriesTable.jspf"%>
        </div>
    </div>
</div>
</body>
</html>
