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
        <h2 class="d-flex justify-content-center"> Ops! Cannot find your bookings!</h2>
        <div class="d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
        </div>
    </c:if>


    <c:if test="${!empty requestScope.bookings}">

        <h2 class="col-md-4">All booking List</h2>

        <div class="container">
            <div class="row ">
                <table class="table table-bordered table-responsive">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Статус бронювання</th>
                        <th scope="col">Апартаменти</th>
                        <th scope="col">Кількість гостей</th>
                        <th scope="col">День заїзду</th>
                        <th scope="col">День виїзду</th>
                        <th scope="col">Час створення</th>
                        <th scope="col">Користувач</th>
                        <th scope="col">Дії</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.bookings}" var="booking" >

                        <tr>
                            <th scope="row">1</th>

                            <c:choose>
                                <c:when test="${booking.getIsPaidForReservation() == true}">
                                    <td class="table-success">
                                        Оплачено!
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td class="table-warning">Не оплачено</td>
                                </c:otherwise>
                            </c:choose>

                            <td>
                                <a class="btn btn-danger" href="${pageContext.request.contextPath}/front?controller=getApartmentDetails&apartmentId=${booking.getApartment().getId()}" >
                                    <i class="bi bi-info-circle"></i>
                                </a>

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