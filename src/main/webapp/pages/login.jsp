<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tagsl" uri ="commonTags" %>
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

                    <form action="../front?controller=login" method="post" style="width: 23rem;">

                        <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Log in</h3>

                        <div class="form-outline mb-4">
                            <input name="email" type="email" id="form2Example18" class="form-control form-control-lg" />
                            <label class="form-label" for="form2Example18">Email address</label>
                        </div>

                        <div class="form-outline mb-4">
                            <input name="password" type="password" id="form2Example28" class="form-control form-control-lg" />
                            <label class="form-label" for="form2Example28">Password</label>
                        </div>

                        <div class="pt-1 mb-4">
                            <button type="submit" name="Login" value="Login" title="Login" class="col-12 btn btn-primary btn-lg btn-block">Login in</button>
                        </div>

                        <p class="small mb-2 pb-lg-2"><a class="text-muted" href="#!">Forgot password?</a></p>
                        <p>Don't have an account? <a href="registration.jsp" class="link-info">Register here</a></p>

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