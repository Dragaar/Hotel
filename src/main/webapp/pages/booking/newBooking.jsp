<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />
<div class="container mt-5">

  <div class="container">
    <div class="row">
      <div class="col-md-12 text-left">

        <div class="row">
          <h6 class="col-md-3 text-left">Бронювання в </h6>
          <h6 class="col-md-3 d-flex flex-row-reverse align-items-center">
            <c:out value="${param.apartmentTitle}" />
          </h6>
        </div>

        <h6 class="col-md-6 mt-2 text-muted d-flex flex-row-reverse align-items-center">
          <c:out value="${param.apartmentAddress}" />
        </h6>

        <div class="row mt-2">
          <h6 class="col-md-2 text-left">Тип житла: </h6>
          <h6 class="col-md-4 text-muted d-flex flex-row-reverse align-items-center">
            <c:out value="${param.apartmentClass}" />
          </h6>
        </div>

        <div class="row mt-2">
          <h6 class="col-md-4 text-left">Вартість проживання за одну ніч:</h6>
          <h6 class="col-md-2 text-muted d-flex flex-row-reverse align-items-center">
            <c:out value="${param.apartmentPrice}" /> &#8372
          </h6>
        </div>

        <form action="${pageContext.request.contextPath}/front?controller=createBooking" method="post" style="width: 23rem;">

          <h4 class="fw-normal mt-2 mb-3 pb-3" style="letter-spacing: 1px;">Booking details</h4>

          <div class="form-outline mb-4">
            <input name="guestsNumber" type="number" id="form2Example18" class="form-control form-control-lg" />
            <label class="form-label" for="form2Example18">Guests number</label>
          </div>

          <div class="form-outline mb-4">
            <input name="checkInDate" type="date" id="form2Example28" class="form-control form-control-lg" />
            <label class="form-label" for="form2Example28">checkInDate</label>
          </div>
          <div class="form-outline mb-4">
            <input name="checkOutDate" type="date" id="form2Example29" class="form-control form-control-lg" />
            <label class="form-label" for="form2Example29">checkOutDate</label>
          </div>

          <div class="pt-1 mb-4">
            <input type="hidden" name="apartmentId" value="${param.apartmentId}">
            <button type="submit" name="Book" class="col-12 btn btn-primary btn-lg btn-block">Make a booking</button>
          </div>

          <!--<p class="small mb-5 pb-lg-2"><a class="text-muted" href="#!">Forgot password?</a></p>
          <p>Don't have an account? <a href="registration.jsp" class="link-info">Register here</a></p>
          -->
        </form>
      </div>
  </div>
</div>
<jsp:include page="/pages/common/pagePatternPart2.jsp" />
