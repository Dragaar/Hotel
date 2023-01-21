<%@ attribute name="messageKey" required="true" rtexprvalue="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<c:if test="${!empty messageKey}">
    <h3><fmt:message key="${messageKey}" /></h3>
</c:if>