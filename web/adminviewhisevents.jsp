<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin</title>
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
        <script type="text/javascript" src="<%=context%>javascripts/royal_coder_admin_script.js"></script>
        <link rel="stylesheet" href="<%=context%>stylesheets/jquery-confirm.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
        <script>
            function getAce() {
                
                var editor = ace.edit("editor");
                editor.setTheme("ace/theme/monokai");
                editor.getSession().setMode("ace/mode/java");
                editor.setReadOnly(true);

                var editor2 = ace.edit("reflection");
                editor2.setTheme("ace/theme/monokai");
                editor2.getSession().setMode("ace/mode/java");
                editor2.setReadOnly(true);
            }
        </script>
    </head>
    <%
        String serv_message = request.getParameter("message");
        String participant_one = "";
        String participant_two = "";
        String unique_event = "";
        if (serv_message != null) {
            String[] participant = serv_message.split("~");
            unique_event = participant[0];
            participant_one = participant[1];
            participant_two = participant[2];
        }

    %>
    <body onload="getAce()">
        <div class="row">
                <h4><img src="icons/Logo.png"</h4>
        </div>
        <div id="mainPane" class="row">
            <div id="left" class="col s12 m4 l12">
                <h6><%=participant_one%></h6>
                <div id = "button1" class = "row">
                    <button type="button" id="participant_one_database" class = "waves-effect waves-light btn" onclick = "calldatabase('participant_one_name');">View Submitted</button>
                </div>
                <div id = "button3" class = "row">
                    <button type="button" id="participant_one_live" class = "waves-effect waves-light btn" onclick = "calllive('participant_one_name');">View Live</button>
                </div>
                <p>
                Question number:&nbsp;&nbsp;<span id="participant_one_question"></span>
                </p>
                <div>
                    <div id="editor"></div>
                </div>
            </div>
            <div id="right" class="col s12 m4 l12">
                <h6><%=participant_two%></h6>
                <div id = "button3" class = "row">
                    <button type="button" id="participant_two_database" class = "waves-effect waves-light btn" onclick = "calldatabase('participant_two_name');">View Submitted</button>
                </div>
                <div id = "button4" class = "row">
                    <button type="button" id="participant_two_live" class = "waves-effect waves-light btn" onclick = "calllive('participant_two_name');">View Live</button>
                </div>
                <p>Question number:&nbsp;&nbsp;<span id="participant_two_question"></span></p>
                <div>
                    <div id="reflection"></div>
                </div>
            </div>
        </div>
        <input type = "hidden" id = "unique_event_id" value = "<%=unique_event%>"/>
        <input type = "hidden" id = "participant_one_name" value = "<%=participant_one%>"/>
        <input type = "hidden" id = "participant_two_name" value = "<%=participant_two%>"/>
        <footer class="footer">Booyah</footer>
    </body>
</html>
