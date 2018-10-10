<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet">        
    </head>
    <body>
        <div id="pageBody" class="container-fluid">
            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class ="container-fluid" id="navbarNav">
                  <ul class="nav navbar-nav">
                    <li class="nav-item active">
                      <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/hero/home">Hero/Villains</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/location/home">Locations</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/sighting/home">Sightings</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/organization/home">Organizations</a>
                    </li>
                    <li class="nav-item">
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                              <a class="nav-link" href="${pageContext.request.contextPath}/user/home">Users</a>
                            </sec:authorize>
                    </li>
                  </ul>
                </div>
            </nav>
            <sec:authorize access="hasRole('ROLE_USER')">        
            <h1 style="text-align: center"> Welcome to Hero/Villain Sightings</h1>
                        <h4 style="text-align: center"> This is a state of the art tracker for villain and Hero Sightings and information</h4>
            <hr/>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name} | <a href="<c:url value="/j_spring_security_logout" />" > <button type="button" class="btn btn-danger" >Logout</button></a></p>
            </c:if>
            <div id="errorDiv" style= "text-align: center">
                ${errorMessage.message}
            </div>
            <h2 style="text-align: center">Recent Sightings</h2>
            <table id="recentSightingTable" class="table table-hover" >
                <tr>
                    <th width="20%">Hero/Villain</th>
                    <th width="30%">Organizations</th>
                    <th width="20%">Sighting Date</th>
                    <th width="20%">Location Name</th>
                </tr>
                <tbody id="newSightingContentRows">
                    <c:forEach var ="currentSighting" items="${viewModel.sts}" varStatus="counter">
                        <tr>
                            <td><c:out value="${currentSighting.hero.heroName}"/></td>
                            <td>
                              <c:if test="${empty currentSighting.hero.orgs}">
                                 <c:out value="Unknown Organization"/>   
                              </c:if>
                              <c:if  test="${not empty currentSighting.hero.orgs}">
                                <c:forEach var ="currentItem" items="${currentSighting.hero.orgs}" varStatus="stat">
                                    <c:out value="${currentItem.organizationName}"/>
                                    <c:if test="${!stat.last}"><c:out value="|"/></c:if>
                                </c:forEach>
                              </c:if>
                            </td>
                            <td>
                                <fmt:parseDate value="${currentSighting.sightingDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                                <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="M/dd/yyyy" />
                                <c:out value="${newParsedDate}"/>
                            </td>
                            <td><c:out value="${currentSighting.location.locationName}"/></td>   
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
           </sec:authorize>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>