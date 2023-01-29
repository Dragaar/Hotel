<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<div class="container mt-5">

    <div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
        <h3><c:out value = "${param.message}" /></h3>
    </div>

    <c:if test="${empty requestScope.apartments}">
        <h2 class="d-flex justify-content-center"> <fmt:message key="apartment.error.cannotFind" /></h2>
        <div class="d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
        </div>
    </c:if>

<c:if test="${!empty requestScope.apartments}">



        <%-- Фільтри апартаментів--%>

<div class="container">
    <form action="${pageContext.request.contextPath}/front?controller=createResponseToOrder" method="POST" class="float-end">

        <%-- Додаткові поля респонсу, order id, опис--%>
        <input type="hidden" name="orderId" value="${requestScope.orderId}">
        <div class="row form-outline mb-4">
            <textarea name="description" class="form-control" id="form8" rows="3"></textarea>
            <label class="form-label" for="form8"><fmt:message key="responseToOrder.description" /></label>
        </div>
        <div class="row mb-4 mt-4">
            <h2 class="col-md-4"><fmt:message key="apartment.submitApartmentsMessage" /></h2>
        </div>

    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4 g-2 g-lg-3 ">
            <c:forEach items="${requestScope.apartments}" var="apartment" >
                <div class="col">
                    <div class="card">
                        <img src="${pageContext.request.contextPath}/images/${apartment.getImageURL()}/1.jpg" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title"><c:out value="${apartment.getTitle()}" /></h5>
                            <h6 class="card-subtitle mb-2 text-muted"><c:out value="${apartment.getAddress()}" /></h6>
                            <p class="card-text line-clamp" style="-webkit-line-clamp: 3"><c:out value="${apartment.getDescription()}" /></p>
                            <h6 class="card-subtitle mb-2 text-muted text-end"><c:out value="${apartment.getPrice()}" /> &#8372</h6>
                            <div class="row ml-2">

                            <a class="col btn btn-primary align-self-center my-1" href="${pageContext.request.contextPath}/front?controller=getApartmentDetails&apartmentId=${apartment.getId()}" ><fmt:message key="apartment.details" /></a>
                            <h6 class="col align-self-center ml-2"><fmt:message key="apartment.check" /></h6>
                            <input class="col align-self-center" type = "checkbox"  name = "apartmentId" value = "${apartment.getId()}" style="width: 30px; height: 30px;"><br>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <button class="btn btn-primary my-1" type="submit" ><fmt:message key="apartment.submitApartments" /></button>
</form>
</c:if>
    <%--
 <jsp:include page="/pages/common/pagination.jsp" /> --%>

</div>
<jsp:include page="/pages/common/footer.jsp" />
<jsp:include page="/pages/common/pagePatternPart2.jsp" />