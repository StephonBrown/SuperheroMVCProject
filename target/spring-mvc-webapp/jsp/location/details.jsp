<%-- 
    Document   : dvdDetails
    Created on : Jul 8, 2018, 3:37:20 PM
    Author     : Stephon
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Location Details</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>
        <div class="container">
            <h1><c:out value="Location Details"/></h1>
            <hr/>
           <sec:authorize access="hasRole('ROLE_USER')">
            <h2><c:out value="Location Name: ${viewModel.locationName}"/></h2>
            <h2><c:out value="Description: ${viewModel.locationDescription}"/></h2>
            <h2><c:out value="Address: ${viewModel.street} ${viewModel.city}, ${viewModel.state} ${viewModel.zipCode}"/></h2>
            <h2><c:out value="Coordinates: ${viewModel.longitude}, ${viewModel.latitude} "/></h2>
            <hr/>
           </sec:authorize>
            <a href="${pageContext.request.contextPath}/location/home"><button class="btn btn-primary" type="button" >Back</button></a>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>