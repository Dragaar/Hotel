<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<jsp:include page="/pages/common/navbar.jsp" />

    <div class="px-5 ms-xl-4 d-flex d-flex justify-content-center">
        <h3><c:out value = "${param.message}" /></h3>
    </div>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<a href="pages/registration.jsp">Registration</a>
<a href="pages/login.jsp">Login</a>
<a href="front?controller=getApartments">Get Apartments</a>


</body>
</html>