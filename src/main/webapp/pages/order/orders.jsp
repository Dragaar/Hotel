<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<div class="container mt-5">

    <div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
        <tags:showMessage messageKey="${param.message}"/>
    </div>

    <c:if test="${empty requestScope.orders}">
        <h2 class="d-flex justify-content-center"> <fmt:message key="order.error.cannotFind" /></h2>
        <div class="d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
        </div>
    </c:if>

    <c:if test="${page == 1}">
        <div class="row">
            <h4 class="col-lg-8 pt-1"> <fmt:message key="apartment.createApplicationMessage" /></h4>
            <div class="col-lg-2 text-center">
                <a class="order-btn btn btn-primary" href="${pageContext.request.contextPath}/front?controller=newOrder" >
                    <fmt:message key="apartment.createApplication" />
                </a>
            </div>
        </div>
    </c:if>

    <c:if test="${!empty requestScope.orders}">

        <div class="row" style="margin-top: 15px;">
            <h2 class="col-lg-4"><fmt:message key="order.list" /></h2>
        </div>

    <div class="container">
        <div class="row ">
            <table class="table table-bordered table-responsive">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="order.status" /></th>
                    <th scope="col"><fmt:message key="order.apartmentType" /></th>
                    <th scope="col"><fmt:message key="order.roomsCount" /></th>
                    <th scope="col"><fmt:message key="order.guestsNumber" /></th>
                    <th scope="col"><fmt:message key="order.price" /></th>
                    <th scope="col"><fmt:message key="order.checkInDate" /></th>
                    <th scope="col"><fmt:message key="order.checkOutDate" /></th>
                    <th scope="col"><fmt:message key="order.details" /></th>
                    <th scope="col"><fmt:message key="order.actions" /></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.orders}" var="order" >

                    <tr>
                        <th scope="row">1</th>

                        <c:choose>
                            <c:when test="${!empty order.getResponseToOrder()}">
                                <td class="table-success">
                                    <fmt:message key="order.status.processed" />
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td class="table-warning">
                                    <fmt:message key="order.status.inProcessing" />
                                </td>
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
                        <td>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/front?controller=deleteOrder&orderId=${order.getId()}" >
                                <i class="bi bi-trash3"></i>
                            </a>
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