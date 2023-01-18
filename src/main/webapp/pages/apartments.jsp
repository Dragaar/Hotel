<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />
<div class="container mt-5">

<div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
    <h3><c:out value = "${param.message}" /></h3>
</div>

<c:if test="${empty requestScope.apartments}">
    <h2 class="d-flex justify-content-center"> Ops! Cannot find available apartments!</h2>
    <div class="d-flex justify-content-center">
        <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
    </div>
</c:if>


<c:if test="${!empty requestScope.apartments}">

    <c:if test="${page == 2}">
    <div class="row img-thumbnail">
        <h3 class="col-md-8"> Не можете знайти потрібне житло? Залиште заявку!</h3>
        <a href="${pageContext.request.contextPath}/front?controller=newOrder" class="col-md-2 btn btn-primary">Оформити</a>
    </div>
    </c:if>

    <div class="row mb-4 mt-4">

        <h2 class="col-md-6">Apartment List</h2>

        <div class="col-md-6 input-group">
            <select class="col form-select" id="inputGroupSelect04" aria-label="Example select with button addon">
                <option selected>Choose...</option>
                <option value="1">One</option>
                <option value="2">Two</option>
                <option value="3">Three</option>
            </select>
            <select class="col form-select" id="inputGroupSelect05" aria-label="Example select with button addon">
                <option selected>Choose...</option>
                <option value="1">One</option>
                <option value="2">Two</option>
                <option value="3">Three</option>
            </select>
            <button class="col-md-2 btn btn-outline-secondary" type="button">Sort</button>
        </div>
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
                        <a href="/notes/edit">Редагувати</a>
                        </c:if>
                        <form action="${pageContext.request.contextPath}/front?controller=getApartmentDetails" method="POST" class="float-end">
                            <input type="hidden" name="apartmentId" value="${apartment.getId()}">
                            <button class="btn btn-primary my-1" type="submit" >Details</button>
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