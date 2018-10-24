
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
            <c:if test="${role == 'GUEST'}">
                <form action="${pageContext.request.contextPath}/main" method="post">
                    <input type="hidden" name="command" value="sign_in">
                    <label for="login"><fmt:message key="startpage.label.login"/> </label><br>
                    <input type="text" id="login" name="login"><br>
                    <small class="small"><c:if test="${requestScope.loginError != null && requestScope.loginError != ''}">
                        <c:out value="${requestScope.loginError}"/>
                    </c:if></small><br>
                    <label for="password"><fmt:message key="startpage.label.password"/> </label><br>
                    <input type="password" id="password" name="password"><br>
                    <small class="small"><c:if test="${requestScope.passwordError != null && requestScope.passwordError != ''}">
                        <c:out value="${requestScope.passwordError}"/>
                    </c:if></small><br>
                    <label for="role"><fmt:message key="startpage.label.role"/></label><br>
                    <select id="role" name="role">
                        <option name="client" value="client"><fmt:message key="startpage.label.role.client"/> </option>
                        <option name="doctor" value="doctor" disabled><fmt:message key="startpage.label.role.doctor" /></option>
                        <option name="pharmacist" value="pharmacist" disabled><fmt:message key="startpage.label.role.pharmacist"/> </option>
                    </select><br>
                    <input type="submit">
                </form>
                <form action="${pageContext.request.contextPath}/main" method="get">
                    <input type="hidden" name="command" value="registration">
                    <label for="roleReg"><fmt:message key="startpage.label.register"/></label><br>
                    <select id="roleReg" name="role">
                        <option name="client" value="client"><fmt:message key="startpage.label.role.client"/> </option>
                        <option name="doctor" disabled value="doctor"><fmt:message key="startpage.label.role.doctor" /></option>
                        <option name="pharmacist" disabled value="pharmacist"><fmt:message key="startpage.label.role.pharmacist"/> </option>
                    </select><br>
                    <input type="submit">
                </form>
            </c:if>
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
