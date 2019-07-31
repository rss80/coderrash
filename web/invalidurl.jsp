<%-- 
    Document   : invalidurl
    Created on : Nov 24, 2017, 6:38:01 PM
    Author     : Ajay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>coderrash</title>
        <%
            String serv_message = request.getParameter("message");
            if (serv_message == null) {
                serv_message = "";
            }
        %>
    </head>
    <body>
        <div><%=serv_message%></div>
    </body>
</html>
