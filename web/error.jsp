<%--
  Created by IntelliJ IDEA.
  User: louis
  Date: 30/01/18
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="static/css/bootstrap.css">
    <script src="static/js/jquery.min.js"></script>
    <script src="static/js/popper.min.js"></script>
    <script src="static/js/bootstrap.js"></script>
</head>
<body>
<div class="container">
    <a href="/main.html">Back to main page</a>
    <br />
    <h1 id="errorTitle"><%= request.getAttribute("title") == null? "Unknown Error" : request.getAttribute("title")%></h1>
    <hr />
    <p id="errorContent"><%= request.getAttribute("content") == null? "Unknown Reason" : request.getAttribute("content") %></p>
</div>
</body>
</html>
