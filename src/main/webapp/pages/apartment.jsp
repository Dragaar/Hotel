<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />
<div class="container mt-5">

    <c:if test="${empty requestScope.apartment}">
        <br>Ops! Cannot find available apartments!</br>
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
                        <h6 class="col-md-5 text-left">Вартість проживання за одну ніч:</h6>
                        <h6 class="col-md-2 text-muted">
                            <c:out value="${apartment.getPrice()}" /> &#8372
                        </h6>
                </div>
            </div>
            <div class="row">
                <h6 class="col-md-3 text-left">Кількість кімнат:</h6>
                <h6 class="col-md-2 text-muted">
                    <c:out value="${apartment.getRoomsNumber()}" />
                </h6>
            </div>
            <div class="row">
                <h6 class="col-md-3 text-left">Максимальна кількість гостей:</h6>
                <h6 class="col-md-2 text-muted">
                    <c:out value="${apartment.getMaxGuestsNumber()}" />
                </h6>
            </div>

            <form action="${pageContext.request.contextPath}/front?controller=newBooking" method="POST">
                    <input type="hidden" name="apartmentTitle" value="${apartment.getTitle()}">
                    <input type="hidden" name="apartmentAddress" value="${apartment.getAddress()}">
                    <input type="hidden" name="apartmentClass" value="${apartment.getApartmentClass()}">
                    <input type="hidden" name="apartmentPrice" value="${apartment.getPrice()}">
                    <input type="hidden" name="apartmentId" value="${apartment.getId()}">
                <div class="row d-flex flex-row-reverse">
                <button class="col-2 btn btn-primary my-1" type="submit" >Make a booking</button>
            </div>
            </form>

        </div>
    </div>
</div>
<jsp:include page="/pages/common/pagePatternPart2.jsp" />
