<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 27.09.18
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <c:set var="pagePass" value="pages/client/clientCabinetPage.jsp" scope="request"/>
    <title><fmt:message key="registration.client.title"/></title>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<div class="container">
    <div class="row">
        <div class="col-md">
            <%@include file="../jspf/userTable.jspf"%>
            <c:if test="${user.discount != 0}">
                <tr>
                    <td>
                        discount
                    </td>
                    <td>
                        ${user.discount}
                    </td>
                </tr>
            </c:if>
            </tbody>
            </table>
        </div>
        <div class="col-md-auto">
            <c:choose>
                <c:when test="${orders == null}">
                    <%--<fmt:message key=""/>--%>
                    Orders:
                </c:when>
                <c:otherwise>
                    <%@include file="../jspf/cartTable.jspf"%>
                </c:otherwise>
            </c:choose>
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

