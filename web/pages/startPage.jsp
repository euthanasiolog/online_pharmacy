
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@include  file="jspf/import.jspf"%>
    <title><fmt:message key="startpage.title"/></title>
    <c:set var="pagePass" value="pages/startPage.jsp"/>
</head>
<body>
<%@ include file="jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">

        </div>
        <div class="col-md-auto">
            <form action="${pageContext.request.contextPath}/main" name="showalldrugs">
                <input type="hidden" name="command" value="show_all_drugs">
                <input type="hidden" name="page" value="${pagePass}">
                <button type="submit" class="btn btn-primary"><fmt:message key="drug.showalldrugs"/></button>
            </form>
            <%@include file="jspf/drugTable.jspf"%>
        </div>
    </div>
</div>
<%@include file="jspf/footer.jspf"%>
</body>
</html>
