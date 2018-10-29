<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 04.10.18
  Time: 00:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/pharmacist/pharmacistCabinetPage.jsp" scope="request"/>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">
            <%@include file="../jspf/userTable.jspf"%>
                </tbody>
            </table>
        </div>
        <div class="col-md-auto">
            <form action="${pageContext.request.contextPath}/main" method="get">
                <input type="hidden" name="command" value="add_drug_page">
                <button type="submit" class="btn btn-primary"><fmt:message key="drug.add"/></button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <%@include file="../jspf/drugTable.jspf"%>
        </div>
    </div>
</div></body>
</html>
