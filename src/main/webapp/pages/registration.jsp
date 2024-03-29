<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />


<c:choose>
    <c:when test="${!empty sessionScope.lang}">
        <fmt:setLocale value="${sessionScope.lang}"/>
    </c:when>
    <c:otherwise>
        <c:set var="lang" scope="session" value="en"/>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="language"/>

<section class="vh-100">
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-6 text-black">

            <div class="row" >
                <img class="col-ml-6 offset-md-3" style="max-height: 180px; max-width: 180px;" src="${pageContext.request.contextPath}/images/site-logo.png" >
            </div>

            <div class="px-5 ms-xl-4">
                <tags:showMessage messageKey="${param.message}"/>
            </div>

            <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 pt-xl-0 mt-xl-n5">

                <form action="${pageContext.request.contextPath}/front?controller=registration" method="post" style="width: 23rem;">

                    <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;"><fmt:message key="registration.singUpNow" /></h3>

                    <!-- 2 column grid layout with text inputs for the first and last names -->
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <input name="firstName" type="text" id="form3Example1" class="form-control" />
                                <label class="form-label" for="form3Example1"><fmt:message key="registration.firstName" /></label>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <input name="lastName" type="text" id="form3Example2" class="form-control" />
                                <label class="form-label" for="form3Example2"><fmt:message key="registration.lastName" /></label>
                            </div>
                        </div>
                    </div>

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <input name="email" type="email" id="form3Example3" class="form-control" />
                        <label class="form-label" for="form3Example3"><fmt:message key="registration.email" /></label>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-2">
                        <input name="password" type="password" id="form3Example4" class="form-control" />
                        <label class="form-label" for="form3Example4"><fmt:message key="registration.password" /></label>
                    </div>

                    <div class="pt-1 mb-2">
                        <button class="col-12 btn btn-primary btn-lg btn-block" type="submit"><fmt:message key="registration.signUp" /></button>
                    </div>

                    <p><fmt:message key="registration.haveAccount" /><a href="login.jsp" class="link-info"><fmt:message key="registration.loginIn" /></a></p>

                </form>

            </div>

        </div>
        <div class="col-sm-6 px-0 d-none d-sm-block">
            <img src="${pageContext.request.contextPath}/images/fon-1.jpg"
                 alt="Sign up image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
        </div>
    </div>
</div>
</section>


<jsp:include page="/pages/common/pagePatternPart2.jsp" />