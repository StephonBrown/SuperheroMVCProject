<%-- 
    Document   : ErrorPage
    Created on : Aug 13, 2018, 1:18:33 PM
    Author     : sbrown6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet">   
    </head>
    <body>
        <div class="container-fluid">
            <nav class="navbar navbar-inverse navbar-fixed-top">
                <div class ="container-fluid" id="navbarNav">
                  <ul class="nav navbar-nav">
                    <li class="nav-item active">
                      <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                    </li>
                  </ul>
                </div>
            </nav>
            <br/>       
            <div>
                <h1>An error has occurred...</h1>
                <h3>${errorMessage}</h3>
            </div>

        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
