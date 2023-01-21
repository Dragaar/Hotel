
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="language"/>

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
      integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
      crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>

<nav class="navbar navbar-expand-lg navbar-light bg-light">


    <a href="${pageContext.request.contextPath}">
        <img style="max-height: 90px; max-width: 90px;" src="${pageContext.request.contextPath}/images/site-logo.png" >
    </a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/front?controller=getApartments">Catalog <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/front?controller=getBookings">Bookings</a>
            </li>
            <c:if test="${sessionScope.role == sessionScope.userRoleName}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/front?controller=getOrders">My applications</a>
            </li>
            </c:if>
            <c:if test="${sessionScope.role == sessionScope.managerRoleName}">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/front?controller=getAllOrders">Users applications</a>
            </li>
            </c:if>
            <li class="nav-item ">
                <a class="nav-link" href="${pageContext.request.contextPath}/front?controller=getAccount">Account</a>
            </li>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>


        </ul>
        <ul class="navbar-nav navbar-right">
            <div class="nav-item dropdown">
                <button class="btn btn-outline-secondary" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
                    <fmt:message key="menu.languages.${sessionScope.lang}" />
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <c:forEach items="${sessionScope.languages}" var="language" >
                    <li><a class="dropdown-item" href="front?controller=changeLanguage&language=${language}">
                        <fmt:message key="menu.languages.${language}" />
                    </a></li>
                </c:forEach>
                </div>
            </div>
        </ul>
        <ul class="navbar-nav navbar-right">
            <li class="nav-item float-right">
                <a class="nav-link" href="front?controller=logout">Logout</a>
            </li>
        </ul>
    </div>
</nav>