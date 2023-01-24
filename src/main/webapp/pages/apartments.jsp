<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<!-- Bootstrap-Select plugin -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<div class="container mt-5">

<div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
    <tags:showMessage messageKey="${param.message}"/>
</div>

<c:if test="${empty requestScope.apartments}">
    <h2 class="d-flex justify-content-center"> <fmt:message key="apartment.error.cannotFind" /></h2>
    <div class="d-flex justify-content-center">
        <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
    </div>
</c:if>


<c:if test="${!empty requestScope.apartments}">

    <c:if test="${page == 2}">
    <div class="row img-thumbnail">
        <h4 class="col-md-8"> <fmt:message key="apartment.createApplicationMessage" /></h4>
        <a href="${pageContext.request.contextPath}/front?controller=newOrder" class="col-md-2 btn btn-primary"><fmt:message key="apartment.createApplication" /></a>
    </div>
    </c:if>

    <div class="row mb-4 mt-4">

        <h2 class="col-md-6"><fmt:message key="apartment.list" /></h2>


        <form class="col-md-6 input-group" action="${pageContext.request.contextPath}/front?controller=getApartments" method="POST">
            <select class="selectpicker" multiple  name="price" data-selected-text-format="static" title="<fmt:message key="apartment.sort.price" />">
                <optgroup label="<fmt:message key="apartment.sort.price" />" data-max-options="1">
                    <option name="price" value="Asc" ><fmt:message key="apartment.sort.ascOrder" /></option>
                    <option name="price" value="Desc" ><fmt:message key="apartment.sort.descOrder" /></option>
                </optgroup>
            </select>
            <select class="selectpicker" multiple name="maxGuestsNumber" data-selected-text-format="static" title="<fmt:message key="apartment.sort.maxGuestsNumber" />">
                <optgroup label="<fmt:message key="apartment.sort.maxGuestsNumber" />" data-max-options="1">
                    <option name="maxGuestsNumber" value="Asc" ><fmt:message key="apartment.sort.ascOrder" /></option>
                    <option name="maxGuestsNumber" value="Desc" ><fmt:message key="apartment.sort.descOrder" /></option>
                </optgroup>
            </select>
            <select class="selectpicker" multiple name="class" data-selected-text-format="static" title="<fmt:message key="apartment.sort.class" />">
                <optgroup label="<fmt:message key="apartment.sort.class" />" data-max-options="1">
                    <option name="class" value="Asc" ><fmt:message key="apartment.sort.ascOrder" /></option>
                    <option name="class" value="Desc" ><fmt:message key="apartment.sort.descOrder" /></option>
                </optgroup>
            </select>
            <select class="selectpicker" multiple name="status" data-selected-text-format="static" title="<fmt:message key="apartment.sort.status" />">
                <optgroup label="<fmt:message key="apartment.sort.status" />" data-max-options="1">
                    <option name="status" value="Free" ><fmt:message key="apartment.sort.status.free" /></option>
                    <option name="status" value="Booked" ><fmt:message key="apartment.sort.status.booked" /></option>
                    <option name="status" value="Busy" ><fmt:message key="apartment.sort.status.busy" /></option>
                    <option name="status" value="Unavailable" ><fmt:message key="apartment.sort.status.unavailable" /></option>
                </optgroup>
            </select>

            <input type="hidden" name="newSortingOrder" value="exist">
            <button class="col-md-2 btn btn-outline-secondary" type="submit"><fmt:message key="apartment.sort" /></button>
            </form>

    </div>


    <div class="container">
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

                        <c:if test="${sessionScope.role == sessionScope.managerRoleName}">
                        <a href="/notes/edit"><fmt:message key="apartment.edit" /></a>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/front?controller=getApartmentDetails" method="POST" class="float-end">
                            <input type="hidden" name="apartmentId" value="${apartment.getId()}">
                            <button class="btn btn-primary my-1" type="submit" ><fmt:message key="apartment.details" /></button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
    </div>
</c:if>

    <jsp:include page="/pages/common/pagination.jsp" />

</div>
<jsp:include page="/pages/common/footer.jsp" />
<jsp:include page="/pages/common/pagePatternPart2.jsp" />