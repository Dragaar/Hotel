<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<div class="container mt-5">

    <c:if test="${empty requestScope.email}">
        <br><fmt:message key="account.error.cannotFind" /></br>
    </c:if>

    <div class="container">
        <div class="row">

            <h3 class="fw-normal fs-4 mb-3 pb-3" style="letter-spacing: 1px;"><fmt:message key="account.actualInformation" /></h3>
            <div class="row">
                <div class="col-md-3 mb-4 form-outline">
                    <h6 class="text-left"> <fmt:message key="account.email" />:</h6>
                </div>
                <div class="col-md-3 mb-4 form-outline">
                    <h6>
                        <c:out value="${requestScope.email}" />
                    </h6>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3 mb-4 form-outline">
                    <h6 class="text-left"> <fmt:message key="account.firstName" />:</h6>
                </div>
                <div class="col-md-3 mb-4 form-outline">
                    <h6>
                        <c:out value="${requestScope.firstName}" />
                    </h6>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3 mb-4 form-outline">
                    <h6 class="text-left"> <fmt:message key="account.lastName" />:</h6>
                </div>
                <div class="col-md-3 mb-4 form-outline">
                    <h6>
                        <c:out value="${requestScope.lastName}" />
                    </h6>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/front?controller=updateAccount" method="post" style="width: 30rem;">

                <h3 class="fw-normal fs-4 mb-3 pb-3" style="letter-spacing: 1px;"><fmt:message key="account.changeFirstAndLastName" /></h3>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <input name="firstName" type="text" id="form2" class="form-control" />
                            <label class="form-label" for="form2"><fmt:message key="account.firstName" /></label>
                        </div>
                    </div>
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <input name="lastName" type="text" id="form3" class="form-control" />
                            <label class="form-label" for="form3"><fmt:message key="account.lastName" /></label>
                        </div>
                    </div>
                </div>

                <h3 class="fw-normal fs-4 mb-3 pb-3" style="letter-spacing: 1px;"><fmt:message key="account.changePassword" /></h3>
                <div class="row">
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <input name="oldPassword" type="text" id="form4" class="form-control" />
                            <label class="form-label" for="form4"><fmt:message key="account.oldPassword" /></label>
                        </div>
                    </div>
                    <div class="col-md-6 mb-4">
                        <div class="form-outline">
                            <input name="newPassword" type="text" id="form5" class="form-control" />
                            <label class="form-label" for="form5"><fmt:message key="account.newPassword" /></label>
                        </div>
                    </div>
                </div>


                <div class="pt-1 mb-2">
                    <button class="col-12 btn btn-primary btn-lg btn-block" type="submit"><fmt:message key="account.saveChanges" /></button>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="/pages/common/pagePatternPart2.jsp" />
