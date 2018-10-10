<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Sighting</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">  
    </head>
      <body>
        <div class="container" >
            <h1>Edit Sighting</h1>
            <hr/>
            <sec:authorize access="hasRole('ROLE_USER')">
            <sf:form class="form-horizontal" role="form" modelAttribute="commandModel" method="POST" action="${pageContext.request.contextPath}/sighting/edit">
                <div class="form-group">
                    <label for="add-sighting-date" class="col-md-4 control-label">Sighting Date</label>
                    <div class="col-md-8" >
                      <sf:input type="date" id="add-sighting-date" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" placeholder="2018/12/15" path="sightingDate"/>
                      <sf:errors  path="sightingDate" cssclass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location" class="col-md-4 control-label">Locations: </label>
                    <div class="col-md-8">  
                        <sf:select class="form-control" id="add-location" path="locationID">
                            <sf:options items = "${viewModel.locs}" itemValue="locationId" itemLabel="locationName"/>
                        </sf:select>
                        <sf:errors  path="locationID" cssclass="error"></sf:errors>        
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-hero" class="col-md-4 control-label">Hero/Villain Present: </label>
                    <div class="col-md-8">  
                        <sf:select class="form-control" id="add-hero" path="heroID">
                             <sf:options items = "${viewModel.heroes}" itemValue="heroId" itemLabel="heroName"/>
                        </sf:select>
                        <sf:errors  path="heroID" cssclass="error"></sf:errors>        
                    </div>
                </div>
                <sf:hidden path="sightingId"/>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <a href="${pageContext.request.contextPath}/sighting/home"><button class="btn btn-primary" type="button" >Cancel</button></a>
                        <button class="btn btn-primary" type="submit" >Edit Sighting</button>
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
