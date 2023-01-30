<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<div class="container mt-5">

  <div class="container">
    <div class="row">
      <div class="col-md-12 text-left">

        <div class="row">
          <h6 class="col-md-3 text-left"> <fmt:message key="booking.bookingIn" /></h6>
          <h6 class="col-md-3 d-flex flex-row-reverse align-items-center">
            <c:out value="${param.apartmentTitle}" />
          </h6>
        </div>

        <h6 class="col-md-6 mt-2 text-muted d-flex flex-row-reverse align-items-center">
          <c:out value="${param.apartmentAddress}" />
        </h6>

        <div class="row mt-2">
          <h6 class="col-md-2 text-left"> <fmt:message key="booking.apartmentType" />: </h6>
          <h6 class="col-md-4 text-muted d-flex flex-row-reverse align-items-center">
            <c:out value="${param.apartmentClass}" />
          </h6>
        </div>

        <div class="row mt-2">
          <h6 class="col-md-4 text-left"> <fmt:message key="booking.price.details" />:</h6>
          <h6 class="col-md-2 text-muted d-flex flex-row-reverse align-items-center">
            <c:out value="${param.apartmentPrice}" /> &#8372
          </h6>
        </div>

        <form action="${pageContext.request.contextPath}/front?controller=createBooking" method="post" style="width: 23rem;">

          <h4 class="fw-normal mt-2 mb-3 pb-3" style="letter-spacing: 1px;"> <fmt:message key="booking.details" /></h4>

          <div class="form-outline mb-4">
            <input name="guestsNumber" type="number" id="form2Example18" class="form-control form-control-lg" />
            <label class="form-label" for="form2Example18"> <fmt:message key="booking.guestsNumber" /></label>
          </div>

          <div class="form-outline mb-4">
            <input name="checkInDate" type="date" id="form2Example28" class="form-control form-control-lg" />
            <label class="form-label" for="form2Example28"> <fmt:message key="booking.checkInDate" /></label>
          </div>
          <div class="form-outline mb-4">
            <input name="checkOutDate" type="date" id="form2Example29" class="form-control form-control-lg" />
            <label class="form-label" for="form2Example29"> <fmt:message key="booking.checkOutDate" /></label>
          </div>

          <div class="pt-1 mb-4">
            <input type="hidden" name="apartmentId" value="${param.apartmentId}">
            <button type="submit" name="Book" class="col-12 btn btn-primary btn-lg btn-block"> <fmt:message key="booking.makeABooking" /></button>
          </div>

        </form>
      </div>
  </div>
</div>
<jsp:include page="/pages/common/pagePatternPart2.jsp" />
