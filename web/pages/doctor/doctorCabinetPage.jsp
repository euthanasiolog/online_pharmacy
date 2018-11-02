<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 27.09.18
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <title><fmt:message key="doctor.cabinet.title"/> </title>
    <c:set var="pagePass" value="pages/doctor/doctorCabinetPage.jsp" scope="request"/>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">
            <%@include file="../jspf/userTable.jspf"%>
            <tr>
                <td>

                </td>
                <td>
                    ${e:forHtml(user.workplace)}
                </td>
            </tr>
            <tr>
                <td>

                </td>
                <td>
                    ${e:forHtml(user.specialization)}
                </td>
            </tr>
            </tbody>
            </table>
        </div>
        <div class="col-md-auto">
            <%@include file="../jspf/recipeRequestsDoctorTable.jsp"%>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <%@include file="../jspf/drugTable.jspf"%>
        </div>
    </div>
</div>
</body>
</html>
