<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/pages/common/pagePatternPart1.jsp" />

<section class="vh-100">
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-6 text-black">

            <div class="px-5 ms-xl-4">
                <i class="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style="color: #709085;"></i>
                <span class="h1 fw-bold mb-0">Hotel</span>
            </div>

            <div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
                <h3><c:out value = "${param.message}" /></h3>
            </div>

            <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-4 pt-5 pt-xl-0 mt-xl-n5">

                <form action="../front?controller=registration" method="post" style="width: 23rem;">

                    <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Sign up now!</h3>

                    <!-- 2 column grid layout with text inputs for the first and last names -->
                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <input name="firstName" type="text" id="form3Example1" class="form-control" />
                                <label class="form-label" for="form3Example1">First name</label>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-outline">
                                <input name="lastName" type="text" id="form3Example2" class="form-control" />
                                <label class="form-label" for="form3Example2">Last name</label>
                            </div>
                        </div>
                    </div>

                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <input name="email" type="email" id="form3Example3" class="form-control" />
                        <label class="form-label" for="form3Example3">Email address</label>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                        <input name="password" type="password" id="form3Example4" class="form-control" />
                        <label class="form-label" for="form3Example4">Password</label>
                    </div>

                    <div class="pt-1 mb-4">
                        <button class="col-12 btn btn-primary btn-lg btn-block" type="submit">Sign up</button>
                    </div>

                    <p>You already have an existing account? <a href="login.jsp" class="link-info">Login in here</a></p>

                </form>

            </div>

        </div>
        <div class="col-sm-6 px-0 d-none d-sm-block">
            <img src="/hotel/images/fon-1.jpg"
                 alt="Sign up image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
        </div>
    </div>
</div>
</section>


<jsp:include page="/pages/common/pagePatternPart2.jsp" />