<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Location</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
    <body>
        <div class="container" >
            <h1>Create Location</h1>
            <hr/>
           <sec:authorize access="hasRole('ROLE_USER')">
            <sf:form class="form-horizontal" role="form" modelAttribute="commandModel" method="POST" action="${pageContext.request.contextPath}/location/create">
                <div class="form-group">
                    <label for="add-location-name" class="col-md-4 control-label">Location Name:</label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-location-name" class="form-control" path="locationName" placeholder="Enter Location Name"/>
                      <sf:errors  path="locationName" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location-description" class="col-md-4 control-label"> Description:</label>
                    <div class="col-md-8">
                        <sf:textarea id ="add-location-description" style="resize:none" type="textarea" class="form-control" path="locationDescription" rows="3"></sf:textarea>
                        <sf:errors  path="locationDescription" cssclass="error"></sf:errors>
                    </div>
                </div>
                 <div class="form-group">
                    <label for="add-location-street" class="col-md-4 control-label">Street:</label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-location-street" class="form-control" path="street" placeholder="Enter Street Name"/>
                      <sf:errors  path="street" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location-city" class="col-md-4 control-label">City:</label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-location-city" class="form-control" path="city" placeholder="Enter City Name"/>
                      <sf:errors  path="city" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location-state" class="col-md-4 control-label">State:</label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-location-state" class="form-control" path="state" placeholder="New York"/>
                      <sf:errors  path="state" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location-zipcode" class="col-md-4 control-label">Zipcode:</label>
                    <div class="col-md-8" >
                      <sf:input type="text" id="add-location-zipcode" class="form-control" path="zipCode" placeholder="44705"/>
                      <sf:errors  path="zipCode" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location-longitude" class="col-md-4 control-label">Longitude</label>
                    <div class="col-md-8" >
                      <sf:input type="number" pattern= "-?[1-9][0-9]*(\\.[0-9]+)" step=".000001" id="add-location-longitude" class="form-control" path="longitude" placeholder="30.000000"/>
                      <sf:errors  path="longitude" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location-latitude" class="col-md-4 control-label">Latitude</label>
                    <div class="col-md-8" >
                      <sf:input type="number" pattern= "-?[1-9][0-9]*(\\.[0-9]+)" step=".000001" id="add-location-latitude" class="form-control" path="latitude" placeholder="30.000000"/>
                      <sf:errors  path="latitude" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <a href="${pageContext.request.contextPath}/location/home"><button class="btn btn-primary" type="button" >Cancel</button></a>
                        <button class="btn btn-primary" type="submit" >Create Location</button>
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
