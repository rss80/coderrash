<%-- 
    Document   : index
    Created on : Nov 25, 2017, 6:34:42 AM
    Author     : Rohit Surana
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
        <link rel="stylesheet" type="text/css" href="stylesheets/materialize.min.css">
    </head>
    <body>
        <div class="row">
            <h4><img src="icons/Logo.png"</h4>
        </div>
        <% 
            if(request.getParameter("message") != null)
            {
        %>
                <h2><%= request.getParameter("message")%></h2>
        <%
            }
        %>
        <div class="row">
                <div id = "button3" class = "row">
                    <a id="next" class = "btn red" href="adminsignup.jsp">SignUp</a>
                </div>
                <div id="button4" class="row">
                    <a id="exit" class="btn red" href="adminlogin.jsp">SignIn</a>
                </div>
        </div>
        
        
    </body>
</html>
