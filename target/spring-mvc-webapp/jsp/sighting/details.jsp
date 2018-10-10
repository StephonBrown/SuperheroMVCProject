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
        <title>Sighting Details</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>
        <sec:authorize access="hasRole('ROLE_USER')">
        <div class="container">
            <h1><c:out value="Sighting Details"/></h1>
            <hr/>
            <h2><c:out value="Sighting Date:"/>                                 
                <fmt:parseDate value="${viewModel.sightingDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="M/dd/yyyy" />
                <c:out value="${newParsedDate}"/>
            </h2>
            <h2><c:out value="Location: ${viewModel.location.street} ${viewModel.location.city}, ${viewModel.location.state} ${viewModel.location.zipCode}"/></h2>
            <h2><c:out value="Hero/Villain at Sighting: ${viewModel.hero.heroName}"/></h2>
            <hr/>
            <a href="${pageContext.request.contextPath}/sighting/home"><button class="btn btn-primary" type="button" >Back</button></a>
        </div>
        </sec:authorize>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>