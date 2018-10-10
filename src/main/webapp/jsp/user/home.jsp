<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>User Home</title>
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
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/location/home">Locations</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/sighting/home">Sightings</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="${pageContext.request.contextPath}/organization/home">Organizations</a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                     <li class="nav-item active">
                          <a class="nav-link" href="${pageContext.request.contextPath}/user/home">Users</a>
                     </li>
                    </sec:authorize>
                  </ul>
                </div>
            </nav>
            <h1 style="text-align: center">Users</h1>
            <hr/>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name} | <a href="<c:url value="/j_spring_security_logout" />" > <button type="button" class="btn btn-danger" >Logout</button></a></p>
            </c:if>
          <sec:authorize access="hasRole('ROLE_ADMIN')">
            <hr/>
            <div id="createUserDiv">
                <a href="${pageContext.request.contextPath}/user/create"><button type="button" class="btn bg-primary">Create User</button></a>
            </div>
            <hr/>
            <table id="UserTable" class="table table-hover" >
                <tr>
                    <th width="80%">Username</th>
                    <th width="20%"></th>
                </tr>
                <tbody id="userContentRows">
                    <c:forEach var="currentUser" items="${viewModel.users}">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/user/edit/${currentUser.id}"><c:out value="${currentUser.username}"/></a></td>
                            <td>
                                <a data-toggle="modal" data-page-context="${pageContext.request.contextPath}" data-id="${currentUser.id}" data-name="${currentUser.username}" data-target="#confirmationModal">Delete</a>
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
                          <p>Are you sure you want to delete <b id="boldedUserName"></b>?</p>
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
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/user.js"></script>
    </body>
</html>
