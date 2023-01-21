
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
    <c:when test="${!empty sessionScope.lang}">
        <fmt:setLocale value="${sessionScope.lang}"/>
    </c:when>
    <c:otherwise>
        <c:set var="lang" value="en" scope="session"  />
        <fmt:setLocale value="${sessionScope.lang}"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="language"/>