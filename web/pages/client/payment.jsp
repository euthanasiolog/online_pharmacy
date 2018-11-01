<%--
  Created by IntelliJ IDEA.
  User: piatr
  Date: 26.10.18
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../jspf/import.jspf"%>
    <title><fmt:message key="payment.title"/> </title>
</head>
<body>
<%@include file="../jspf/header.jspf"%>
<c:set var="pagePass" value="pages/client/payment.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-md">
            <form action="${pageContext.request.contextPath}/main" method="post">
                <input type="hidden" name="command" value="do_payment">
                <table class="table-sm table-hover">
                    <c:set var="totalCountOrder" value="${fn:length(cart.orders)}"/>
                    <c:set var="stepOrder" value="${20}" scope="page"/>
                    <c:set var="startOrder" value="${0}" scope="page"/>
                    <c:if test="${param.startOrder > 0}">
                        <c:set var="startOrder" scope="page" value="${param.startOrder}"/>
                    </c:if>
                    <thead>
                    <tr>
                        <th scope="col">N%</th>
                        <th scope="col"><fmt:message key="drug.name"/> </th>
                        <th scope="col"><fmt:message key="drug.inn"/> </th>
                        <th scope="col"><fmt:message key="drug.dose"/> </th>
                        <th scope="col"><fmt:message key="drug.form"/> </th>
                        <th scope="col"><fmt:message key="drug.composite"/> </th>
                        <th scope="col"><fmt:message key="drug.number"/> </th>
                        <th scope="col"><fmt:message key="drug.price"/> </th>
                        <th scope="col"><fmt:message key="drug.recipe"/> </th>
                        <th scope="col"><fmt:message key="drug.availability"/> </th>
                        <th scope="col"><fmt:message key="drug.ordertime"/> </th>
                        <th scope="col"><fmt:message key="cart.table.number"/> </th>
                        <th scope="col"><fmt:message key="cart.table.payment"/> </th>
                        <th scope="col">Add to payment</th>
                    </tr>
                    </thead>

                    <c:forEach  var="order" items="${cart.orders}" varStatus="orderCount" begin="${startOrder}" end="${startOrder + stepOrder - 1}">
                        <tr>
                            <th scope="row">${orderCount.index+1}</th>
                            <td>${e:forHtml(order.drug.name)}</td>
                            <td>${e:forHtml(order.drug.inn)}</td>
                            <td><fmt:formatNumber value="${e:forHtml(order.drug.dose)}" type="number"/> </td>
                            <td>
                                <c:choose>
                                    <c:when test="${e:forHtml(order.drug.form).equalsIgnoreCase('pill')}" >
                                        <fmt:message key="drug.form.pill"/>
                                    </c:when>
                                    <c:when test="${e:forHtml(order.drug.form).equalsIgnoreCase('solution')}" >
                                        <fmt:message key="drug.form.solution"/>
                                    </c:when>
                                    <c:when test="${e:forHtml(order.drug.form).equalsIgnoreCase('powder')}" >
                                        <fmt:message key="drug.form.powder"/>
                                    </c:when>
                                    <c:when test="${e:forHtml(order.drug.form).equalsIgnoreCase('cream')}" >
                                        <fmt:message key="drug.form.cream"/>
                                    </c:when>
                                    <c:when test="${e:forHtml(order.drug.form).equalsIgnoreCase('gel')}" >
                                        <fmt:message key="drug.form.gel"/>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>${e:forHtml(order.drug.composite)}</td>
                            <td><fmt:formatNumber value="${e:forHtml(order.drug.number)}" type="number"/></td>
                            <td><fmt:formatNumber value="${e:forHtml(order.drug.price)}" type="currency"/></td>
                            <td>
                                <c:choose>
                                <c:when test="${e:forHtml(order.drug.recipeType).equalsIgnoreCase('without')}" >
                                    <fmt:message key="drug.recipe.without"/>
                                </c:when>
                                <c:when test="${e:forHtml(order.drug.recipeType).equalsIgnoreCase('regular')}" >
                                    <fmt:message key="drug.recipe.regular"/>
                                </c:when>
                                <c:when test="$${e:forHtml(order.drug.recipeType).equalsIgnoreCase('narcotic')}" >
                                <strong><fmt:message key="drug.recipe.narcotic"/></strong>
                                </c:when>
                                </c:choose>
                            <td>
                                <c:choose>
                                <c:when test="${e:forHtml(order.drug.availability).equalsIgnoreCase('stock')}" >
                                    <fmt:message key="drug.availability.stock"/>
                                </c:when>
                                <c:when test="${e:forHtml(order.drug.availability).equalsIgnoreCase('order')}" >
                                    <fmt:message key="drug.availability.order"/>
                                </c:when>
                                <c:when test="${e:forHtml(order.drug.availability).equalsIgnoreCase('not')}" >
                                    <fmt:message key="drug.availability.not"/>
                                </c:when>
                                </c:choose>
                            <td>${e:forHtml(order.drug.orderTime)} <fmt:message key="drug.availability.order.time"/> </td>
                            <td>
                                <fmt:formatNumber type="number" value="${e:forHtml(order.number)}"/>
                            </td>
                            <td>

                            </td>
                            <td>
                                <c:set var="isPayed" value="${0}" scope="page"/>
                                <c:choose>
                                    <c:when test="${!order.payment}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" name="id"
                                                   onclick="payList.push(${order.id})"
                                                   value="${order.id}" id="add-to-payment">
                                            <label class="form-check-label" for="add-to-payment">
                                                Add to payment
                                            </label>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="form-check">
                                            <input class="form-check-input" type="checkbox" value="" id="defaultCheck2" disabled>
                                            <label class="form-check-label" for="defaultCheck2">
                                                Add to payment
                                            </label>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div>
                    <c:if test="${totalCountOrder > step}">
                        <c:forEach begin="0" step="${stepOrder}" end="${totalCountOrder - 1}" varStatus="page">
                            <c:choose>
                                <c:when test="${startOrder==(page.count*stepOrder - stepOrder)}">
                                    <a class="btn btn-dark"
                                       href="${pageContext.request.contextPath}/main?command=refresh&startOrder=${page.count*stepOrder - stepOrder}&page=${pagePass}"
                                       role="button">
                                            ${page.count}</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-primary"
                                       href="${pageContext.request.contextPath}/main?command=refresh&startOrder=${page.count*stepOrder - stepOrder}&page=${pagePass}"
                                       role="button">
                                            ${page.count}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                </div>
                <!-- Button trigger modal -->
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                    Do payment
                </button>

                <!-- Modal -->
                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <%--<div class="container-fluid py-3">--%>
                                <%--<div class="row">--%>
                                <%--<div class="col-12 col-sm-8 col-md-6 col-lg-4 mx-auto">--%>
                                <%--<div id="pay-invoice" class="card">--%>
                                <%--<div class="card-body">--%>
                                <%--<div class="card-title">--%>
                                <%--<h3 class="text-center">Pay Invoice</h3>--%>
                                <%--</div>--%>
                                <%--<hr>--%>
                                <%--<form action="/echo" method="post" novalidate="novalidate" class="needs-validation">--%>
                                <%--<div class="form-group text-center">--%>
                                <%--<ul class="list-inline">--%>
                                <%--<li class="list-inline-item"><i class="text-muted fa fa-cc-visa fa-2x"></i></li>--%>
                                <%--<li class="list-inline-item"><i class="fa fa-cc-mastercard fa-2x"></i></li>--%>
                                <%--<li class="list-inline-item"><i class="fa fa-cc-amex fa-2x"></i></li>--%>
                                <%--<li class="list-inline-item"><i class="fa fa-cc-discover fa-2x"></i></li>--%>
                                <%--</ul>--%>
                                <%--</div>--%>
                                <%--<div class="form-group has-success">--%>
                                <%--<label for="cc-name" class="control-label mb-1">Name on card</label>--%>
                                <%--<input id="cc-name" name="cc-name" type="text" class="form-control cc-name" required autocomplete="cc-name" aria-required="true" aria-invalid="false" aria-describedby="cc-name-error">--%>
                                <%--<span class="invalid-feedback">Enter the name as shown on credit card</span>--%>
                                <%--</div>--%>
                                <%--<div class="form-group">--%>
                                <%--<label for="cc-number" class="control-label mb-1">Card number</label>--%>
                                <%--<input id="cc-number" name="cc-number" type="tel" class="form-control cc-number identified visa" required="" pattern="[0-9]{16}">--%>
                                <%--<span class="invalid-feedback">Enter a valid 16 digit card number</span>--%>
                                <%--</div>--%>
                                <%--<div class="row">--%>
                                <%--<div class="col-6">--%>
                                <%--<div class="form-group">--%>
                                <%--<label for="cc-exp" class="control-label mb-1">Expiration</label>--%>
                                <%--<input id="cc-exp" name="cc-exp" type="tel" class="form-control cc-exp" required placeholder="MM / YY" autocomplete="cc-exp">--%>
                                <%--<span class="invalid-feedback">Enter the expiration date</span>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="col-6">--%>
                                <%--<label for="x_card_code" class="control-label mb-1">Security code</label>--%>
                                <%--<div class="input-group">--%>
                                <%--<input id="x_card_code" name="x_card_code" type="tel" class="form-control cc-cvc" required autocomplete="off">--%>
                                <%--<span class="invalid-feedback order-last">Enter the 3-digit code on back</span>--%>
                                <%--<div class="input-group-append">--%>
                                <%--<div class="input-group-text">--%>
                                <%--<span class="fa fa-question-circle fa-lg" data-toggle="popover" data-container="body" data-html="true" data-title="Security Code"--%>
                                <%--data-content="<div class='text-center one-card'>The 3 digit code on back of the card..<div class='visa-mc-cvc-preview'></div></div>"--%>
                                <%--data-trigger="hover"></span>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                <%--<i class="fa fa-lock fa-lg"></i>&nbsp;--%>
                                <%--<span id="payment-button-amount">Pay $100.00</span>--%>
                                <%--</button>--%>
                                <%--</div>--%>
                                <%--</form>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary">Do payment</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
