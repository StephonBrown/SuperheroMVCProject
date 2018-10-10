<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Organization</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>
        <div class="container" >
            <h1>Edit Organization</h1>
            <hr/>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <sf:form class="form-horizontal" role="form" modelAttribute="commandModel" method="POST" action="${pageContext.request.contextPath}/organization/edit">
                <div class="form-group">
                    <label for="add-organization-name" class="col-md-4 control-label">Organization Name:</label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-organization-name" class="form-control" path="organizationName" placeholder="Enter Organization Name"/>
                      <sf:errors  path="organizationName" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-organization-description" class="col-md-4 control-label">Organization Description:</label>
                    <div class="col-md-8">
                        <sf:textarea id ="add-organization-description" style="resize:none" type="textarea" class="form-control" path="organizationDescription" rows="3"></sf:textarea>
                        <sf:errors  path="organizationDescription" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="selectLocation" class="col-md-4 control-label">Locations: </label>
                    <div class="col-md-8">  
                        <sf:select class="form-control" id="selectOrganization" path="locationId">
                            <sf:options items = "${viewModel.locs}" itemValue="locationId" itemLabel="locationName"/>
                        </sf:select>
                        <sf:errors  path="locationId" cssclass="error"></sf:errors>        
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-telephone-number" class="col-md-4 control-label">Telephone Number: </label>
                    <div class="col-md-8">  
                        <sf:input type="tel" class="form-control" id="add-telephone-number" path="telephoneNumber"/>
                        <sf:errors  path="telephoneNumber" cssclass="error"></sf:errors>        
                    </div>
                </div>
                <sf:hidden path="organizationId"/>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <a href="${pageContext.request.contextPath}/organization/home"><button class="btn btn-primary" type="button" >Cancel</button></a>
                        <button class="btn btn-primary" type="submit" >Edit Organization</button>
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
