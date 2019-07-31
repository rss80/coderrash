<%@page import="royal.bean.Crbean"%>
<%@page import="javax.servlet.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script type="text/javascript" src="javascripts/materialize.min.js"></script>
    <link rel="stylesheet" type="text/css" href="stylesheets/style.css">
        <link rel="stylesheet" type="text/css" href="stylesheets/materialize.min.css">
    </head>
    <body>
       
        <h4><img src="icons/Logo.png"</h4>
        <%
        HttpSession Session = request.getSession(false);
        Crbean cr1 = (Crbean) Session.getAttribute("coderrashbean");
        if(cr1 != null)
        {
            Session.invalidate();
        }
        %>
        <div class="row">
            <div class="col s12 m4 l3">
    </div>
            <div class="col s12 m4 l6" id="fore">
        <form id="adminloginform" method="POST" action="adminlogin">
        <div class="row">
            <div class="input-field col s12">
                <input name="email" id="email" type="email" class="validate">
                <label for="email">Email</label>
            </div>
        </div>
        <div class="row">
            <div class="input-field col s12">
                <input name="password" id="password" type="password" class="validate">
                <label for="password">Password</label>
            </div>
        </div>
        <button class="btn red" type="submit" name="action">Submit</button>
    </form>
        </div>
            <div class="col s12 m4 l3">
    </div>
        </div>
      
       
        <footer class="footer">Booyah</footer>
    </body>
</html>
