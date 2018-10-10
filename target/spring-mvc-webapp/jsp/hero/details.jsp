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
        <title>Hero/Villain Details</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>
        <div class="container">
            <h1><c:out value="Hero/Villain Details"/></h1>
            <hr/>
            <sec:authorize access="hasRole('ROLE_USER')">
            <h2><c:out value="Name: ${viewModel.heroName}"/></h2>
            <h2><c:out value="Description: ${viewModel.heroDescription}"/></h2>
            <h2><c:out value="Organizations:"/>
                    <c:if test="${empty orgs}">
                        <c:out value="Unknown Organization"/>
                    </c:if>
                    <c:if test="${!empty orgs}">
                        <c:forEach var ="name" items="${viewModel.orgNames}" varStatus="stat">
                                ${name}
                                <c:if test="${!stat.last}">,</c:if>
                        </c:forEach>
                    </c:if>
            </h2>
            <h2><c:out value="Super Powers:"/>
                <c:forEach var ="name" items="${viewModel.spNames}" varStatus="stat">
                     ${name}
                     <c:if test="${!stat.last}">,</c:if>
                </c:forEach>
            </h2>
            <h2><c:out value="Locations Sighted:"/>
                <c:forEach var ="name" items="${viewModel.locNames}" varStatus="stat">
                     ${name}
                     <c:if test="${!stat.last}">,</c:if>
                </c:forEach>
            </h2>
            <a href="${pageContext.request.contextPath}/hero/home"><button class="btn btn-primary" type="button" >Back</button></a>
            </sec:authorize>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>