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

    <c:if test="${empty requestScope.bookings}">
        <h2 class="d-flex justify-content-center"> <fmt:message key="booking.error.cannotFind" /></h2>
        <div class="d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
        </div>
    </c:if>

    <c:if test="${!empty requestScope.bookings}">

        <h2 class="col-lg-8"><fmt:message key="booking.list.all" /></h2>

        <div class="container">
            <div class="row ">
                <table class="table table-bordered table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><fmt:message key="booking.status" /></th>
                        <th scope="col"><fmt:message key="booking.makePaymentForBooking" /></th>
                        <th scope="col"><fmt:message key="booking.apartment" /></th>
                        <th scope="col"><fmt:message key="booking.guestsNumber" /></th>
                        <th scope="col"><fmt:message key="booking.checkInDate" /></th>
                        <th scope="col"><fmt:message key="booking.checkOutDate" /></th>
                        <th scope="col"><fmt:message key="booking.dateOfCreation" /></th>
                        <th scope="col"><fmt:message key="booking.userAccount" /></th>
                        <th scope="col"><fmt:message key="booking.actions" /></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.bookings}" var="booking" >

                        <tr>
                            <th scope="row">1</th>

                            <c:choose>
                                <c:when test="${booking.getIsPaidForReservation() == true}">
                                    <td class="table-success">
                                        <fmt:message key="booking.paid" />
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class="table-warning"><fmt:message key="booking.notPaid" /></td>
                                </c:otherwise>
                            </c:choose>

                            <td>
                                <c:if test="${booking.getIsPaidForReservation() == false}">
                                    <form id="paymentForm" action="${pageContext.request.contextPath}/front?controller=makePaymentForBooking&bookingId=${booking.getId()}" method="POST">
                                        <button type="submit" class="btn btn-info"><i class="bi bi-currency-dollar"></i></button>
                                    </form>
                                </c:if>
                            </td>

                            <td>
                                <form id="apartmentForm" action="${pageContext.request.contextPath}/front?controller=getApartmentDetails&apartmentId=${booking.getApartment().getId()}" method="POST">
                                    <button type="submit" class="btn btn-primary"><i class="bi bi-info-circle"></i></button>
                                </form>
                            </td>
                            <td><c:out value="${booking.getGuestsNumber()}" /></td>
                            <td><c:out value="${booking.getCheckInDate()}" /></td>
                            <td><c:out value="${ booking.getCheckOutDate()}" /></td>
                            <td><c:out value="${ booking.getReservationData()}" /></td>
                            <td><c:out value="${ booking.getAccount().getId()}" /></td>

                            <td>
                                <a class="btn btn-danger" href="${pageContext.request.contextPath}/front?controller=deleteBooking&bookingId=${booking.getId()}" >
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