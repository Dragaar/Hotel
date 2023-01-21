<%@ attribute name="messageKey" required="true" rtexprvalue="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${!empty param.message}">
    <h3><fmt:message key="${param.message}" /></h3>
</c:if>