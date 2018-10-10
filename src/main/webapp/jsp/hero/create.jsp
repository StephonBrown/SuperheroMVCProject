<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Hero</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>
        <div class="container" >
            <h1>Create Hero</h1>
            <hr/>
            <sec:authorize access="hasRole('ROLE_USER')">
            <sf:form class="form-horizontal" role="form" modelAttribute="commandModel" method="POST" action="create">
                <div class="form-group">
                    <label for="add-hero-name" class="col-md-4 control-label">Hero Name:</label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-hero-name" class="form-control" path="heroName" placeholder="Enter Hero Name"/>
                      <sf:errors  path="heroName" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-hero-description" class="col-md-4 control-label">Hero Description:</label>
                    <div class="col-md-8">
                        <sf:textarea id ="add-hero-description" style="resize:none" type="textarea" class="form-control" path="heroDescription" rows="3"></sf:textarea>
                        <sf:errors  path="heroDescription" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-rating" class="col-md-4 control-label">Organizations: </label>
                    <div class="col-md-8">  
                        <sf:select class="form-control" id="selectOrganization" multiple = "true" path="orgs">
                            <sf:options items = "${viewModel.orgs}" itemValue="organizationId" itemLabel="organizationName"/>
                        </sf:select>
                        <sf:errors  path="orgs" cssclass="error"></sf:errors>        
                    </div>
                </div>
                <div class="form-group">
                    <label for="selectSuperPower" class="col-md-4 control-label">Super Powers</label>
                    <div class="col-md-8">  
                        <sf:select class="form-control" id="selectSuperPower" multiple = "true" path="sps">
                             <sf:options items = "${viewModel.sps}" itemValue="superPowerId" itemLabel="superPowerName"/>
                        </sf:select>
                        <sf:errors  path="sps" cssclass="error"></sf:errors>        
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <a href="${pageContext.request.contextPath}/hero/home"><button class="btn btn-primary" type="button" >Cancel</button></a>
                        <button class="btn btn-primary" type="submit" >Create Hero</button>
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
