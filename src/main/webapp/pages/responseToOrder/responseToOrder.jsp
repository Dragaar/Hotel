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

    <c:if test="${empty requestScope.apartments}">
        <h2 class="d-flex justify-content-center"> Ops! Cannot find available apartments!</h2>
        <div class="d-flex justify-content-center">
            <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
        </div>
    </c:if>


    <c:if test="${!empty requestScope.apartments}">

        <div class="row mb-4 mt-4">

            <h4 >Коментар модератора: <c:out value="${description}" /></h4>
            <h2 >Submitted apartment List</h2>

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
                                <!-- /hotel/front?controller=deleteApartment
                                /hotel/pages/test11.jsp
                                -->
                                <a href="/notes/edit">Редагувати</a>
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

</div>
<jsp:include page="/pages/common/footer.jsp" />
<jsp:include page="/pages/common/pagePatternPart2.jsp" />