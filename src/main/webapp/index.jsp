<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />

<c:choose>
    <c:when test="${!empty sessionScope.id}">
        <jsp:include page="/pages/common/navbar.jsp" />
    </c:when>
    <c:otherwise>
        <style> .masthead { padding-bottom: 12rem !important; } </style>
    </c:otherwise>
</c:choose>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

    <header class="masthead">
        <div class="container" style="width: 100%; max-width: 1320px;">
            <div class="row justify-content-center ">
                <div class="col-xl-7 col-lg-11">
                    <div class="section-tittle pt-10">
                        <h1>Rest Journey in Single step, in the breathtaking</h1>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <main>
        <div class="row ps-4 mt-4" style="width: 100%">
            <div class="col-xl-8 col-lg-8 col-md-8">
                <div class="right-caption">
                    <div class="section-tittle">
                        <h2>Luxury &amp; Comfort Our services and wonders of Prague</h2>
                    </div>
                    <div class="support-caption">
                        <p class="mb-10">The concept and service of the best luxury hotels in Asturias in our sophisticated Urban Double and Unique Junior Suite rooms, with the possibility of enjoying a furnished terrace in our Double Urban Loft and Unique Junior Loft Suite.</p>
                    </div>
                </div>
            </div>
            <div class="col-xl-4 col-lg-4 col-md-4">
                <div class="col-md-12 mt-2 text-center">
                 <a class="btn btn-primary index-btn" href="${pageContext.request.contextPath}/pages/registration.jsp"><fmt:message key="index.getStarted" /></a>
                </div>
                <div class="col-md-12 mt-2 text-center">
                    <a class="btn btn-primary index-btn" href="${pageContext.request.contextPath}/pages/login.jsp"><fmt:message key="index.loginIn" /></a>
                </div>
            </div>
        </div>
    </main>

    <div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
        <h3><c:out value = "${param.message}" /></h3>
    </div>

<jsp:include page="/pages/common/pagePatternPart2.jsp" />