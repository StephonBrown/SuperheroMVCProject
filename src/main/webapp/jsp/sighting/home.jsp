<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sightings Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/sighting.css" rel="stylesheet">        
    </head>
    <body>
        <div id="pageBody" class="container-fluid">
            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class ="container-fluid" id="navbarNav">
                  <ul class="nav navbar-nav">
                    <li class="nav-item ">
                      <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <li class="nav-item ">
                      <a class="nav-link" href="${pageContext.request.contextPath}/hero/home">Hero/Villains</a>
                    </li>
                    <li class="nav-item ">
                      <a class="nav-link" href="${pageContext.request.contextPath}/location/home">Locations</a>
                    </li>
                    <li class="nav-item active">
                      <a class="nav-link" href="${pageContext.request.contextPath}/sighting/home">Sightings</a>
                    </li>
                    <li class="nav-item ">
                      <a class="nav-link" href="${pageContext.request.contextPath}/organization/home">Organizations</a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                          <a class="nav-link" href="${pageContext.request.contextPath}/user/home">Users</a>
                    </li>
                    </sec:authorize>
                  </ul>
                </div>
            </nav>
            <h1 style="text-align: center">Hero/Villain Sightings</h1>
            <hr/>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name} | <a href="<c:url value="/j_spring_security_logout" />" > <button type="button" class="btn btn-danger" >Logout</button></a></p>
            </c:if>
          <sec:authorize access="hasRole('ROLE_USER')">
            <div id="errorDiv" style= "text-align: center">
                <c:if test="${not empty errorMessage}">
                   <b id = errorMessage> ${errorMessage.message} </b>   
                </c:if>
            </div>
            <hr/>
            <div id="createSightingDiv">
                <a href="${pageContext.request.contextPath}/sighting/create"><button type="button" class="btn bg-primary">Create Sighting</button></a>
            </div>
            <hr/>
            <table id="recentSightingTable" class="table table-hover" >
                <tr>
                    <th width="20%">Hero/Villain</th>
                    <th width="20%">Organizations</th>
                    <th width="20%">Sighting Date</th>
                    <th width="20%">Location Name</th>
                    <th width="20%"></th>
                </tr>
                <tbody id="sightingContentRows">
                    <c:forEach var ="currentSighting" items="${viewModel.sts}" varStatus="counter">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/sighting/details/${currentSighting.sightingId}"><c:out value="${currentSighting.hero.heroName}"/></a></td>  
                            <td>
                                <c:if test="${empty currentSighting.hero.orgs}">
                                   <c:out value="Unknown Organization"/>   
                                </c:if>
                                <c:if test="${not empty currentSighting.hero.orgs}">
                                    <c:forEach var ="currentItem" items="${currentSighting.hero.orgs}" varStatus="stat">
                                        <c:out value="${currentItem.organizationName}"/>
                                        <c:if test="${!stat.last}"><c:out value="|"/></c:if>
                                    </c:forEach>
                                </c:if>
                            </td>
                            <td>
                                <fmt:parseDate value="${currentSighting.sightingDate}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />
                                <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date" pattern="MM/dd/yyyy" />
                                <c:out value="${newParsedDate}"/>
                            </td>
                            <td><c:out value="${currentItem.location.locationName}"/></td>
                            <td><a href="${pageContext.request.contextPath}/sighting/edit/${currentSighting.sightingId}">Edit</a> | 
                                <sec:authorize access="hasRole('ROLE_ADMIN')"><a data-toggle="modal" data-page-context="${pageContext.request.contextPath}" data-id="${currentSighting.sightingId}" 
                                                                                 data-target="#confirmationModal">Delete</a></sec:authorize></td>    
                        </tr>
                        <c:set var="organizations" value=""/>
                    </c:forEach>
                </tbody>
            </table>
            <c:forEach items="${viewModel.pageNumbers}" var="pageNumber">
                <a href="${pageContext.request.contextPath}/sighting/home?offset=${(pageNumber - 1) * 5}">${pageNumber}</a>
            </c:forEach>
            <div id="confirmationModal" class="modal fade" role="dialog">
                <div class="modal-dialog">
                  <!-- Modal content-->
                  <div class="modal-content">
                    <div class="modal-header">
                      <h4 class="modal-title">Confirmation</h4>
                    </div>
                    <div class="modal-body">
                          &nbsp;
                          &nbsp;
                          &nbsp;
                          <p>Are you sure you want to delete this sighting?</p>
                          &nbsp;
                          &nbsp;
                          &nbsp;
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                      <sec:authorize access="hasRole('ROLE_ADMIN')"><a id="deleteButton" href=""><button type="button" class="btn btn-default">Yes</button></a></sec:authorize>
                    </div>
                  </div>
                </div>
            </div>
            </sec:authorize>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/sighting.js"></script>
    </body>
</html>