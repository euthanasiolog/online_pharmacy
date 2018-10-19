<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
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
            <form action="${pageContext.request.contextPath}/main" method="post">
                <input type="hidden" name="command" value="signIn">
                <label for="login"><fmt:message key="startpage.label.login"/> </label><br>
                <input type="text" id="login" name="login"><br>
                <label for="password"><fmt:message key="startpage.label.password"/> </label><br>
                <input type="password" id="password" name="password"><br>
                <label for="role"><fmt:message key="startpage.label.role"/></label><br>
                <select id="role" name="role">
                    <option name="client" value="client"><fmt:message key="startpage.label.role.client"/> </option>
                    <option name="doctor" value="doctor"><fmt:message key="startpage.label.role.doctor" /></option>
                    <option name="pharmacist" value="pharmacist"><fmt:message key="startpage.label.role.pharmacist"/> </option>
                </select><br>
                <input type="submit">
            </form>
            <form action="${pageContext.request.contextPath}/main" method="get">
                <input type="hidden" name="command" value="registration">
                <label for="roleReg"><fmt:message key="startpage.label.register"/></label><br>
                <select id="roleReg" name="role">
                    <option name="client" value="client"><fmt:message key="startpage.label.role.client"/> </option>
                    <option name="doctor" value="doctor"><fmt:message key="startpage.label.role.doctor" /></option>
                    <option name="pharmacist" value="pharmacist"><fmt:message key="startpage.label.role.pharmacist"/> </option>
                </select><br>
                <input type="submit">
            </form>
        </div>
        <div class="col-md-auto>
            <form action="${pageContext.request.contextPath}/main" class="form-inline my-2 my-lg-0">
                <input type="hidden" name="command" value="searchdrug">
                <input type="hidden" name="page" value="${pagePass}">
                <input class="form-control mr-sm-2" type="text" name="drugsearch" id="drugsearch" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="drug.search"/></button>
            </form>
            <form action="${pageContext.request.contextPath}/main" name="showalldrugs">
                <input type="hidden" name="command" value="showalldrugs">
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
