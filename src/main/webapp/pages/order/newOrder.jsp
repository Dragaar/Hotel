<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />
<div class="container mt-5">

    <div class="container">
        <div class="row">
            <div class="col-md-12 text-left">

                <form action="${pageContext.request.contextPath}/front?controller=createOrder" method="post" style="width: 23rem;">

                    <h4 class="fw-normal mt-2 mb-3 pb-3" style="letter-spacing: 1px;">Application details</h4>

                    <div class="form-outline mb-4">
                        <input name="apartmentClass" type="text" id="form1" class="form-control form-control-lg" />
                        <label class="form-label" for="form1">Тип житла</label>
                    </div>

                    <div class="form-outline mb-4">
                        <input name="roomsNumber" type="number" id="form2" class="form-control form-control-lg" />
                        <label class="form-label" for="form2">Кількість кімнат</label>
                    </div>

                    <div class="form-outline mb-4">
                        <input name="guestsNumber" type="number" id="form3" class="form-control form-control-lg" />
                        <label class="form-label" for="form3">Guests number</label>
                    </div>

                    <div class="form-outline mb-4">
                        <input name="price" type="number" id="form4" class="form-control form-control-lg" />
                        <label class="form-label" for="form4">Вартість проживання за одну ніч (в гривнях &#8372)</label>
                    </div>

                    <div class="form-outline mb-4">
                        <input name="checkInDate" type="date" id="form5" class="form-control form-control-lg" />
                        <label class="form-label" for="form5">checkInDate</label>
                    </div>
                    <div class="form-outline mb-4">
                        <input name="checkOutDate" type="date" id="form6" class="form-control form-control-lg" />
                        <label class="form-label" for="form6">checkOutDate</label>
                    </div>

                    <div class="form-outline mb-4">
                        <textarea name="description" class="form-control" id="form8" rows="3"></textarea>
                        <label class="form-label" for="form8">Description</label>
                    </div>

                    <div class="pt-1 mb-4">
                        <input type="hidden" name="apartmentId" value="${param.apartmentId}">
                        <button type="submit" name="Book" class="col-12 btn btn-primary btn-lg btn-block">Create application</button>
                    </div>

                </form>
            </div>
        </div>
    </div>
    <jsp:include page="/pages/common/pagePatternPart2.jsp" />
