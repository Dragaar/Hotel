<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<div class="container mt-5">

    <c:if test="${empty requestScope.apartment}">
        <br><fmt:message key="apartment.error.cannotFind" /></br>
    </c:if>

    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <img class="w-100" src="/hotel/images/${apartment.getImageURL()}/1.jpg">
            </div>
            <div class="col-md-8 text-left">
                <div class="row">
                    <h2 class="col-md-10">${apartment.getTitle()}</h2>
                    <h6 class="col-md-2 text-muted d-flex flex-row-reverse align-items-center">
                        <c:out value="${apartment.getApartmentClass()}"/>
                    </h6>
                </div>

                <h6 class="mb-2 text-muted"><c:out value="${apartment.getAddress()}" /></h6>
                <p>${apartment.getDescription()}</p>

                <div class="row">
                        <h6 class="col-md-5 text-left"><fmt:message key="apartment.price" /></h6>
                        <h6 class="col-md-2 text-muted">
                            <c:out value="${apartment.getPrice()}" /> &#8372
                        </h6>
                </div>

                <div class="row">
                    <h6 class="col-md-3 text-left"><fmt:message key="apartment.roomsCount" /></h6>
                    <h6 class="col-md-2 text-muted">
                        <c:out value="${apartment.getRoomsNumber()}" />
                    </h6>
                </div>
                <div class="row">
                    <h6 class="col-md-3 text-left"><fmt:message key="apartment.maxGuestsNumber" /></h6>
                    <h6 class="col-md-2 text-muted">
                        <c:out value="${apartment.getMaxGuestsNumber()}" />
                    </h6>
                </div>

                <script id="datepickerScript" src="${pageContext.request.contextPath}/assets/js/bookingDatepickerConfig.js"
                        data-pickerLang = "${sessionScope.lang}" data-currentDate = "${requestScope.currentDate}"
                        data-disabledDatesListExist = "${!empty requestScope.disabledDatesList}"
                        data-disabledDatesList = "${requestScope.disabledDatesList}">
                </script>

                <h6 class="text-left"><fmt:message key="apartment.availableDates"/></h6>
                <div class="row">
                    <div class="datepicker"> </div>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/front?controller=newBooking" method="POST">
                    <input type="hidden" name="apartmentTitle" value="${apartment.getTitle()}">
                    <input type="hidden" name="apartmentAddress" value="${apartment.getAddress()}">
                    <input type="hidden" name="apartmentClass" value="${apartment.getApartmentClass()}">
                    <input type="hidden" name="apartmentPrice" value="${apartment.getPrice()}">
                    <input type="hidden" name="apartmentId" value="${apartment.getId()}">
                <div class="row d-flex flex-row-reverse">
                <button class="col-2 btn btn-primary my-1" type="submit" ><fmt:message key="apartment.makeBooking" /></button>
            </div>
            </form>

        </div>
    </div>
</div>
<jsp:include page="/pages/common/pagePatternPart2.jsp" />
