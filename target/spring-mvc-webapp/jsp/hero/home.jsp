<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hero/Villains Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/hero.css" rel="stylesheet">        
    </head>
    <body>
        <div id="pageBody" class="container-fluid">
            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class ="container-fluid" id="navbarNav">
                <sec:authorize access="hasRole('ROLE_USER')">
                  <ul class="nav navbar-nav">
                    <li class="nav-item ">
                      <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                    <li class="nav-item active">
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
                </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="nav-item">
                          <a class="nav-link" href="${pageContext.request.contextPath}/user/home">Users</a>
                    </li>
                    </sec:authorize>
                  </ul>
                </div>
            </nav>
            <h1 style="text-align: center">Heroes/Villains</h1>
            <hr/>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <p>Hello : ${pageContext.request.userPrincipal.name} | <a href="<c:url value="/j_spring_security_logout" />" > <button type="button" class="btn btn-danger" >Logout</button></a></p>
            </c:if>
           
            <div id="errorDiv" style= "text-align: center">
               <c:if test="${not empty errorMessage}">
                   <b id = errorMessage> ${errorMessage.message} </b>   
               </c:if>
            </div>
            <hr/>
          <sec:authorize access="hasRole('ROLE_USER')">
            <div id="createHeroDiv">
                <a href="${pageContext.request.contextPath}/hero/create"><button type="button" class="btn bg-primary">Create Hero</button></a>
            </div>
            <hr/>
          </sec:authorize>
            <table id="heroTable" class="table table-hover" >
                <tr>
                    <th width="20%">Hero/Villain Name</th>
                    <th width="30%">Organizations</th>
                    <th width="20%">Superpowers</th>
                    <th width="20%"></th>
                </tr>
                <tbody id="heroContentRows">
                    <c:forEach var="currentHero" items="${viewModel.heroes}" varStatus="counter">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/hero/details/${currentHero.heroId}"><c:out value="${currentHero.heroName}"/></a></td>
                            <td>                              
                                 <c:if test="${empty currentHero.orgs}">
                                    <c:out value="Unknown Organization"/>   
                                 </c:if>
                                 <c:if  test="${not empty currentHero.orgs}">
                                    <c:forEach var ="currentOrg" items="${currentHero.orgs}" varStatus="stat">
                                        <c:out value="${currentOrg.organizationName}"/>
                                        <c:if test="${!stat.last}"><c:out value="|"/></c:if>
                                    </c:forEach>
                                 </c:if>
                             </td>
                             <td>
                                <c:forEach var ="currentsuperpower" items="${currentHero.sps}" varStatus="stat">
                                    <c:out value="${currentsuperpower.superPowerName}"/>
                                    <c:if test="${!stat.last}"><c:out value="|"/></c:if>
                                </c:forEach>
                             </td>
                             <td><sec:authorize access="hasRole('ROLE_USER')"><a href="${pageContext.request.contextPath}/hero/edit/${currentHero.heroId}">Edit</a>></sec:authorize>|
                                 <sec:authorize access="hasRole('ROLE_ADMIN')"><a data-toggle="modal" data-page-context="${pageContext.request.contextPath}" data-id="${currentHero.heroId}" data-name="${currentHero.heroName}" data-target="#confirmationModal">Delete</a></sec:authorize></td>   
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <br />
            <c:forEach items="${viewModel.pageNumbers}" var="pageNumber">
                <a href="${pageContext.request.contextPath}/hero/home?offset=${(pageNumber - 1) * 5}">${pageNumber}</a>
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
                          <p>Are you sure you want to delete <b id="boldedHeroName"></b>?</p>
                          &nbsp;
                          <p>This will delete all sightings associated with this hero/villain.</p>
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
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/hero.js"></script>
    </body>
</html>