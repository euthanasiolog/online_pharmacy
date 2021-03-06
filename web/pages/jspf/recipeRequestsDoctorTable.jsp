<div>
    <script>
        $(function(){
            $( "#dateOfBirth" ).datepicker({
                dateFormat: "yy-mm-dd",
                changeMonth: true,
                minDate: "0"
            });
        });
    </script>

    <table class="table-dark table-hover">
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
                <tr>
                    <th scope="row">${recipeRequestsCount.index+1}</th>
                    <td>${e:forHtml(recipeRequest.drug.name)}</td>
                    <td><fmt:formatNumber value="${e:forHtml(recipeRequest.drug.dose)}" type="number"/> </td>
                    <td>
                        <c:choose>
                            <c:when test="${e:forHtml(recipeRequest.drug.drugForm).equalsIgnoreCase('pill')}" >
                                <fmt:message key="drug.form.pill"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.drugForm).equalsIgnoreCase('solution')}" >
                                <fmt:message key="drug.form.solution"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.drugForm).equalsIgnoreCase('powder')}" >
                                <fmt:message key="drug.form.powder"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.drugForm).equalsIgnoreCase('cream')}" >
                                <fmt:message key="drug.form.cream"/>
                            </c:when>
                            <c:when test="${e:forHtml(recipeRequest.drug.drugForm).equalsIgnoreCase('gel')}" >
                                <fmt:message key="drug.form.gel"/>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                            <fmt:message key="accept"/>
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
                                        <form action="${pageContext.request.contextPath}/main" method="post">
                                            <script>
                                                $(function(){
                                                    $( "#to" ).datepicker({
                                                        dateFormat: "yy-mm-dd",
                                                        changeMonth: true,
                                                        changeYear: true,
                                                        minDate: "0"
                                                    });
                                                });
                                            </script>
                                            <input type="hidden" name="page" value="${pagePass}">
                                            <label for="to"><fmt:message key="recipe.to"/> </label>
                                            <input type="text" name="to" id="to" autocomplete="off" required>
                                            <input type="hidden" name="command" value="confirm_recipe">
                                            <input type="hidden" name="id" value="${recipeRequest.id}">
                                            <button type="submit" class="btn btn-primary"><fmt:message key="accept"/></button>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="cancel"/> </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <form action="${pageContext.request.contextPath}/main" method="post">
                            <input type="hidden" name="page" value="${pagePass}">
                            <input type="hidden" name="command" value="delete_recipe">
                            <input type="hidden" name="id" value="${recipeRequest.id}">
                            <button type="submit" class="btn btn-primary"><fmt:message key="deny"/></button>
                        </form>
                    </td>
                </tr>
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

