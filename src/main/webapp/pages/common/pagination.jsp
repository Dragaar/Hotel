<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="mt-4">
    <nav aria-label="Page navigation">
        <ul class="pagination">

            <%--For displaying Previous link except for the 1st page --%>
            <c:choose>
                <c:when test="${page != 1}">
                    <li class="page-item">
                        <a class="page-link" href="?controller=${requestScope.currentController}&page=${page - 1}">Previous</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                        <a class="page-link">Previous</a>
                    </li>
                </c:otherwise>
            </c:choose>

            <%--For displaying Page numbers.
            The when condition does not display a link for the current page--%>

            <c:forEach begin="${page > 5 ? page-3 : '1'}" end="${page+3}" var="i">
                <c:choose>
                    <c:when test="${page == i}">
                        <li class="page-item active">
                            <a class="page-link">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="?controller=${requestScope.currentController}&page=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <%--For displaying Next link --%>
            <li class="page-item">
                <a class="page-link" href="?controller=${requestScope.currentController}&page=${page + 1}">Next</a>
            </li>
            <%-- if totalPagesCount exist
            <c:choose>
            <c:when test="${page < totalPagesCount}">
                <li class="page-item">
                    <a class="page-link" href="?controller=${requestScope.currentController}&page=${page + 1}">Next</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <a class="page-link">Next</a>
                </li>
            </c:otherwise>
            </c:choose>
            --%>
    </ul>
    </nav>
</div>