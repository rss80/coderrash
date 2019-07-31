<%-- 
    Document   : dashboard
    Created on : Nov 25, 2017, 6:48:49 AM
    Author     : Rohit Surana
--%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="royal.database.databaseconn" %>
<%@ page import="royal.bean.Crbean" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Dashboard</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/jquery-confirm.css">
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/stylesheets/style.css">
        <link rel="stylesheet" type="text/css" href="stylesheets/materialize.min.css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/javascripts/dashboard.js"></script>
    </head>
    <body>
        <div class="row">
            
   
        <%
        HttpSession Session = request.getSession(false);
        Crbean cr1 = (Crbean) Session.getAttribute("coderrashbean");
        if(cr1 == null)
        {
            RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
            rd.forward(request, response);
        }
        %>
    <form action="loadevent" method="POST" id="loadevent" name="loadevent">
        <input name="unique_event" id="unique_event" type="hidden" value="">
    </form>
    <%
        databaseconn dc = new databaseconn();
        //HttpSession Session = request.getSession(true);
        try{
            Crbean cb = (Crbean) session.getAttribute("coderrashbean");
        
        int hid = cb.getHid();
        PrintWriter o = response.getWriter();
        String query = "SELECT * FROM EVENT_HOST WHERE H_ID = ?";
        Map<Integer, Object> m = new HashMap();
        m.put(1, hid);
        dc.set_sqlstatement(query);
        dc.set_sqlparameters(m);
        dc.process_select_Query();
        
    %> 
    <div class="col s12 m4 l2" id="fore">
        <h4><%="Home    " + cb.getName()%></h4>
    </div>
    <div class="col s12 m4 l8">
        <h4><img src="icons/Logo.png"</h4>
    </div>
    <div class="col s12 m4 l2">
        <a href="logout"><button class="btn red" id="logout">Logout</button></a>
        </div>
    </div>
    <div class="row">
        <div id="button1"><a id="eventbutton" href="event.jsp"><button class="btn red">Create new Event</button></a></div>
        <div id="fancy">List of all events</div>
        </div>
    <div class="row">
        <div id="button3">
    <%    while (dc.rs.next()) {    %>
        Event:  <button id="button2" class="btn" value="<%=dc.rs.getString("UNIQUE_EVENT_ID")%>" onclick="eventdetails(<%= "\'" + dc.rs.getString("UNIQUE_EVENT_ID") + "\'"%>)"><%= dc.rs.getString("EVENT_NAME")%></button>
        <br />
        Link:   <div class="chip"><%out.print("http://localhost:8080/coderrash/rashers/"+dc.rs.getString("UNIQUE_EVENT_ID"));%></div>
    <br/>
    <%   }  
}
catch (Exception e)
{
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp?message=Some error occured on our end. Please try again");
            rd.forward(request, response);
}
%>
        </div>
</div>
</body>
</html>
