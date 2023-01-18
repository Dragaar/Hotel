<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<div class="container mt-5">

    <div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
        <h3><c:out value = "${param.message}" /></h3>
    </div>

    <c:if test="${empty requestScope.orders}">
        <h2 class="d-flex justify-content-center"> Ops! Cannot find your applications!</h2>
        <div class="d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
        </div>
    </c:if>


    <c:if test="${!empty requestScope.orders}">

        <h2 class="col-md-4">Applications List</h2>

        <div class="container">
            <div class="row ">
                <table class="table table-bordered table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Статус заявки</th>
                        <th scope="col">Тип Апартаментів</th>
                        <th scope="col">Кількість кімнат</th>
                        <th scope="col">Кількість гостей</th>
                        <th scope="col">Вартість</th>
                        <th scope="col">День заїзду</th>
                        <th scope="col">День виїзду</th>
                        <th scope="col">Деталі заявки</th>
                        <th scope="col">Дії</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.orders}" var="order" >

                        <tr>
                            <th scope="row">1</th>

                            <c:choose>
                                <c:when test="${!empty order.getResponseToOrder()}">
                                    <td class="table-success">
                                        Заявку оброблено!
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class="table-warning">Оброблюється</td>
                                </c:otherwise>
                            </c:choose>

                            <td><c:out value="${order.getApartmentClass()}" /></td>
                            <td><c:out value="${order.getRoomsNumber()}" /></td>
                            <td><c:out value="${order.getGuestsNumber()}" /></td>
                            <td><c:out value="${order.getPrice()}" /></td>
                            <td><c:out value="${order.getCheckInDate()}" /></td>
                            <td><c:out value="${order.getCheckOutDate()}" /></td>
                            <td><c:out value="${order.getDescription()}" /></td>

                            <td>
                                <c:if test="${!empty order.getResponseToOrder()}">
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/front?controller=getResponseToOrder&responseToOrderId=${order.getResponseToOrder().getId()}" >
                                        <i class="bi bi-eye"></i>
                                    </a>
                                </c:if>
                            </td>
                            <c:if test="${empty order.getResponseToOrder() && sessionScope.role == sessionScope.managerRoleName}">
                                <td>
                                    <a class="btn btn-warning" href="${pageContext.request.contextPath}/front?controller=newResponseToOrder&orderId=${order.getId()}" >
                                        <i class="bi bi-chat-left-text"></i>
                                    </a>
                                </td>
                            </c:if>
                            <td>
                                <a class="btn btn-danger" href="${pageContext.request.contextPath}/front?controller=deleteOrder&orderId=${order.getId()}" ><i class="bi bi-trash3"></i></a>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>

    <jsp:include page="/pages/common/pagination.jsp" />

</div>
<jsp:include page="/pages/common/footer.jsp" />
<jsp:include page="/pages/common/pagePatternPart2.jsp" />