<%-- 
    Document   : create
    Created on : Aug 13, 2018, 4:28:33 PM
    Author     : sbrown6
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create User</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>
        <div class="container" >
            <h1>Create User</h1>
            <hr/>
           <sec:authorize access="hasRole('ROLE_ADMIN')">
            <sf:form class="form-horizontal" role="form" modelAttribute="commandModel" method="POST" action="${pageContext.request.contextPath}/user/create">
                <div class="form-group">
                    <label for="add-username" class="col-md-4 control-label">Username: </label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-username" placeholder="John" path="username"/>
                      <sf:errors  path="username" cssclass="error"></sf:errors>
                    </div>
                </div>
                 <div class="form-group">
                    <label for="add-password" class="col-md-4 control-label">Password: </label>
                    <div class="col-md-8" >
                      <sf:input type="password" id="add-password" path="password"/>
                      <sf:errors  path="password" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="admin-checkbox" class="col-md-4 control-label">Is this an Admin account?: </label>
                    <div class="col-md-8" >
                        <sf:hidden  value="ROLE_USER" path="authorities"/>
                        <sf:checkbox name="isAdmin" value="ROLE_ADMIN" path="authorities"/>
                        <sf:errors  path="authorities" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <a href="${pageContext.request.contextPath}/user/home"><button class="btn btn-primary" type="button" >Cancel</button></a>
                        <button class="btn btn-primary" type="submit" >Create User</button>
                    </div>
                </div>
            </sf:form>
            </sec:authorize>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
