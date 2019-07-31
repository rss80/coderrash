
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Player1</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <%
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");
            String context = request.getContextPath();
            if (context == null) {
                context = "";
            } else {
                context = context + "/";
            }
        %>
        <script src="<%=context%>javascripts/ace.js" type="text/javascript" charset="utf-8"></script>
        <link type="text/css" rel="stylesheet" href="<%=context%>stylesheets/materialize.min.css"  media="screen,projection"/>
        <link rel='stylesheet' href='<%=context%>stylesheets/style.css' />
        <script type="text/javascript" src="<%=context%>javascripts/royal_coder_script.js"></script>
        <link rel="stylesheet" href="<%=context%>stylesheets/jquery-confirm.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
        <script>
            function getAce() {
                var editor = ace.edit("editor");
                editor.setTheme("ace/theme/monokai");
                editor.getSession().setMode("ace/mode/java");

                var editor2 = ace.edit("reflection");
                editor2.setTheme("ace/theme/monokai");
                editor2.getSession().setMode("ace/mode/java");
                editor2.setReadOnly(true);
            }
        </script>
    </head>
    <%
        String serv_message = request.getParameter("message");
        if (serv_message == null) {
            serv_message = "";
        }
        String othercoder = request.getParameter("othercoder");
        if (othercoder == null) {
            othercoder = "";
        }
        String totaltime = request.getParameter("totaltime");
        if (totaltime == null) {
            totaltime = "0";
        }
    %>
    <body onload="getAce()">
        <div class="row">
            <div class="col s12 m4 l2">
                <div id="button1" class="row">
                    <button type="button" id="start" class="waves-effect waves-light btn" onclick="doContinuous_coders();">Start</button>
                </div>
               
            </div>
            <div class="col s12 m4 l8">
                <h4><img src="<%=context%>icons/Logo.png"</h4>
                <h4><i><%=serv_message%></i></h4>
                <div id="clock"><h5><i>Timer:</i></h5></div>
            </div>
            <div class = "col s12 m4 l2">
                <div id = "button3" class = "row">
                    <button type="button" id="next" class = "waves-effect waves-light btn" onclick = "alert_user_submit();" disabled="true">Next</button>
                </div>
                <div id="button4" class="row">
                    <button type="button" id="exit" class="waves-effect waves-light btn">Exit</button>
                </div>
            </div>
        </div>
                <div class="row">Question: <span id="cr_question"/></div>
    <div id="mainPane" class="row">
        <div id="left" class="col s12 m4 l12">
            <h6>You</h6>
            <p><span>&nbsp;&nbsp;</span></p>
            <div>
                <div id="editor"></div>
            </div>
        </div>
        <div id="right" class="col s12 m4 l12">
            <h6>Opponent&nbsp;&nbsp;<%=othercoder%></h6>
            <p>Question number:&nbsp;&nbsp;<span id="opponent_question"></span></p>
            <div>
                <div id="reflection"></div>
            </div>
        </div>
    </div>
            <form action="../crremovelive" method="POST" id="royal_coders_logout"/>
            <input type = "hidden" id = "totaltime" value = "<%=totaltime%>"/>
</body>
</html>
