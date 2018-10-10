<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Location Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/location.css" rel="stylesheet">        
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
                    <li class="nav-item active">
                      <a class="nav-link" href="${pageContext.request.contextPath}/location/home">Locations</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/sighting/home">Sightings</a>
                    </li>
                    <li class="nav-item">
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
            <h1 style="text-align: center">Locations</h1>
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
            <div id="createLocationDiv">
                <a href="${pageContext.request.contextPath}/location/create"><button type="button" class="btn bg-primary">Create Location</button></a>
            </div>
            <hr/>
            <table id="LocationTable" class="table table-hover" >
                <tr>
                    <th width="20%">Location Name</th>
                    <th width="30%">Location Description</th>
                    <th width="20%">Address</th>
                    <th width="20%"></th>
                </tr>
                <tbody id="locationContentRows">
                    <c:forEach var="currentLoc" items="${viewModel.locs}">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/location/details/${currentLoc.locationId}"><c:out value="${currentLoc.locationName}"/></a></td>
                            <td><c:out value="${currentLoc.locationDescription}"/></td>
                            <td><c:out value="${currentLoc.street} ${currentLoc.city}, ${currentLoc.state} ${currentLoc.zipCode}"/></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/location/edit/${currentLoc.locationId}">Edit</a> | 
                                <sec:authorize access="hasRole('ROLE_ADMIN')"><a data-toggle="modal" data-page-context="${pageContext.request.contextPath}" data-id="${currentLoc.locationId}" data-name="${currentLoc.locationName}" data-target="#confirmationModal">Delete</a></sec:authorize>
                            </td>      
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br/>
            <c:forEach items="${viewModel.pageNumbers}" var="pageNumber">
                <a href="${pageContext.request.contextPath}/location/home?offset=${(pageNumber - 1) * 5}">${pageNumber}</a>
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
                          <p>Are you sure you want to delete <b id="boldedLocationName"></b>?</p>
                          &nbsp;
                          <p>This will delete all sightings, organizations, and members associated with this location.</p>
                          &nbsp;
                          &nbsp;
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                      <a id="deleteButton" href=""><button type="button" class="btn btn-default">Yes</button></a>
                    </div>
                  </div>
                </div>
            </div>
            </sec:authorize>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/location.js"></script>
    </body>
</html>