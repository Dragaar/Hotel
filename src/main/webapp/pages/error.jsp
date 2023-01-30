<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />

<c:choose>
    <c:when test="${empty sessionScope.lang}">
      <fmt:setLocale value="en"/>
    </c:when>
    <c:otherwise>
      <fmt:setLocale value="${sessionScope.lang}"/>
    </c:otherwise>
</c:choose>
      <fmt:setBundle basename="language"/>

<div class="container mt-5">


    <h2 class="d-flex justify-content-center"> <fmt:message key="app.error" /></h2>
    <div class="d-flex justify-content-center">
        <img src="${pageContext.request.contextPath}/images/icons/sad-cat.jpg" class="img">
    </div>
        <c:if test="${!empty requestScope.statusCode}">
            <div class="d-flex justify-content-center"> <strong><fmt:message key="app.error.statusCode" /></strong>:${requestScope.statusCode} </div>
        </c:if>
        <c:if test="${!empty requestScope.requestUri}">
            <div class="d-flex justify-content-center"> <strong><fmt:message key="app.error.requestURI" /></strong>:${requestScope.requestUri} </div>
        </c:if>
        <c:if test="${!empty requestScope.message}">
            <div class="d-flex justify-content-center"> <strong><fmt:message key="app.error.message" /></strong>:${requestScope.message} </div>
        </c:if>
      </div>



</div>

<jsp:include page="/pages/common/pagePatternPart2.jsp" />
