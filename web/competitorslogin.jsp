<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>coderrash</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel='stylesheet' href='<%=request.getContextPath()%>/stylesheets/style.css' />
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/stylesheets/materialize.min.css"  media="screen,projection"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/javascripts/materialize.min.js"></script>
</head>
<%  String serv_message = request.getParameter("message"); %>
<script>
    function showError(){
        var str = "<%=serv_message%>";
        Materialize.toast(str, 4000);
    }
</script>
<body>
     <%
            
            if (serv_message == null) {
                serv_message = "";
            }
            else{
            %>
        <script>
            showError();
        </script>
            <%  }
            String compete_path = request.getPathInfo();
            if(compete_path == null)
                compete_path = "";
     %>
<h4><img src="<%=request.getContextPath()%>/icons/Logo.png"</h4>
<div class="row">
    <form action="${pageContext.request.contextPath}/crashcompete<%=compete_path%>" method="POST" class="col s12">
        <div class="row">
            <div class="col s12 m4 l3">
    </div>
            <div class="input-field col s6">
                <input id="coder_name" name="coder_name" type="text" class="validate">
                <label for="coder_name">Coder Name</label>
            </div>
            <div class="col s12 m4 l3">
    </div>
        </div>
        <div id="button1">
            <button class="btn red" type="submit" name="action">Compete</button>
        </div>
    </form>
</div>
</body>
</html>
