<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="e" uri="https://www.owasp.org/index.php/OWASP_Java_Encoder_Project" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<div>
    <script>
        $( function() {
            var dateFormat = "dd-mm-yy",
                from = $( "#from" )
                    .datepicker({
                        defaultDate: "+1w",
                        changeMonth: true,
                        numberOfMonths: 3
                    })
                    .on( "change", function() {
                        to.datepicker( "option", "minDate", getDate( this ) );
                    }),
                to = $( "#to" ).datepicker({
                    defaultDate: "+1w",
                    changeMonth: true,
                    numberOfMonths: 3
                })
                    .on( "change", function() {
                        from.datepicker( "option", "maxDate", getDate( this ) );
                    });

            function getDate( element ) {
                var date;
                try {
                    date = $.datepicker.parseDate( dateFormat, element.value );
                } catch( error ) {
                    date = null;
                }

                return date;
            }
        } );
    </script>

    <table class="table-sm table-hover">
        <c:set var="totalCountRecipe" value="${fn:length(recipeRequests)}"/>
        <c:set var="stepRecipeRequests" value="${20}" scope="page"/>
        <c:set var="startRecipeRequests" value="${0}" scope="page"/>
        <c:if test="${param.startRecipeRequests > 0}">
            <c:set var="startRecipe" scope="page" value="${param.startRecipeRequests}"/>
        </c:if>
        <thead>
        <tr>
            <th scope="col">N%</th>
            <th scope="col"><fmt:message key="drug.name"/> </th>
            <th scope="col"><fmt:message key="drug.dose"/> </th>
            <th scope="col"><fmt:message key="drug.form"/> </th>
            <th scope="col"><fmt:message key="action"/> </th>
        </tr>
        </thead>
        <c:forEach  var="recipeRequest" items="${recipeRequests}" varStatus="recipeRequestsCount" begin="${startRecipeRequests}" end="${startRecipeRequests + stepRecipeRequests - 1}">
            <c:if test="${recipeRequests.used == 0 || recipeRequests.to > now}">
                <tr>
                    <th scope="row">${recipeRequestsCount.index+1}</th>
                    <td>${e:forHtml(recipeRequest.drug.name)}</td>
                    <td><fmt:formatNumber value="${e:forHtml(recipeRequest.drug.dose)}" type="number"/> </td>
                    <td>
                        <c:choose>
                            <c:when test="${e:forHtml(recipeRequest.drug.form).equalsIgnoreCase('pill')}" >
                                <fmt:message key="drug.form.pill"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.form).equalsIgnoreCase('solution')}" >
                                <fmt:message key="drug.form.solution"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.form).equalsIgnoreCase('powder')}" >
                                <fmt:message key="drug.form.powder"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.form).equalsIgnoreCase('cream')}" >
                                <fmt:message key="drug.form.cream"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.form).equalsIgnoreCase('gel')}" >
                                <fmt:message key="drug.form.gel"/>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/main" method="post">
                            <input type="hidden" name="page" value="${pagePass}">
                            From <input type="text" id="from" name="from"> To <input type="text" name="to" id="to">
                            <input type="hidden" name="command" value="confirm_recipe">
                            <input type="hidden" name="id" value="${recipeRequest.id}">
                            <button type="submit" class="btn btn-primary"><fmt:message key="accept"/></button>
                        </form>
                        <form action="${pageContext.request.contextPath}/main" method="post">
                            <input type="hidden" name="page" value="${pagePass}">
                            <input type="hidden" name="command" value="delete_recipe">
                            <input type="hidden" name="id" value="${recipeRequest.id}">
                            <button type="submit" class="btn btn-primary"><fmt:message key="deny"/></button>
                        </form>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
    <div>
        <c:if test="${totalCountRecipe > step}">
            <c:forEach begin="0" step="${stepRecipe}" end="${totalCountRecipe - 1}" varStatus="page">
                <c:choose>
                    <c:when test="${startRecipe==(page.count*stepRecipe - stepRecipe)}">
                        <a class="btn btn-dark"
                           href="${pageContext.request.contextPath}/main?command=refresh&startRecipe=${page.count*stepRecipe - stepRecipe}&page=${pagePass}"
                           role="button">
                                ${page.count}</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary"
                           href="${pageContext.request.contextPath}/main?command=refresh&startRecipe=${page.count*stepRecipe - stepRecipe}&page=${pagePass}"
                           role="button">
                                ${page.count}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>
    </div>
</div>

