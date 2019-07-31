var parser, xmlDoc;
parser = new DOMParser();



function doSomething() {
    var editor = ace.edit("editor");
    var editor2 = ace.edit("reflection");
    var code = editor.getValue();
    if (code == null || code == '')
        code = ' ';

    $.ajax({
        type: 'POST',
        url: '../crchachier',
        data: {
            code: code,
        },
        success: function (response) {
            var final = response;
            xmlDoc = parser.parseFromString(final, "text/xml");
            var question_number = xmlDoc.getElementsByTagName("question_number")[0].childNodes[0].nodeValue;
            var opponent_code = xmlDoc.getElementsByTagName("code_live")[0].childNodes[0].nodeValue;
            document.getElementById("opponent_question").innerHTML = question_number;
            editor2.setValue(opponent_code);
            editor2.find('\\n');
            editor2.replaceAll('\n');
        }
    })
}



function timer() {
    var totaltime = parseInt(document.getElementById("totaltime").value);
    totaltime = totaltime * 60;
    var x = setInterval(function () {
        totaltime = totaltime - 1;
        document.getElementById("clock").innerHTML = totaltime + "s ";

        if (totaltime < 0) {
            clearInterval(x);
            document.getElementById("clock").innerHTML = "EXPIRED";
            doSomething();
            $.confirm({
                title: 'CODERRASH ALERT',
                content: 'Time out. Your answers will be saved successfully after you confirm.<br><br> Thankyou for coding with us',
                buttons: {
                    confirm: function () {
                        document.getElementById("royal_coders_logout").submit();
                    }
                }
            });
        }
    }, 1000);
}



function doContinuous_coders() {
    document.getElementById("start").disabled = true;
    document.getElementById("next").disabled = false;
    setInterval(doSomething, 5000);
    getquestion();
    timer();
}



function getquestion()
{
    $('#cr_question').html("");
    doSomething();
    var editor = ace.edit("editor");
    var code = editor.getValue();
    if (code == null || code == '')
        code = ' ';
    $.ajax({
        type: 'post',
        url: '../royalcoderrashquestion',
        data: {
            code: code,
        },
        success: function (response) {
            editor.setValue(" ");
            if (response != 'questions_3') {
                $('#cr_question').html(response);
                if (response == "session_expired")
                {
                    alert("Session Expeired due to Inactivity. Please login again");
                }
            } else {
                document.getElementById("next").disabled = true;
                alert('Thankyou for submitting your answers. All answers have been saved.');
                document.getElementById("royal_coders_logout").submit();
            }
        }
    });
}



function alert_user_submit() {
    /*var r = confirm("You are about to submit");
     if (r == true) {
     txt = "You pressed OK!";
     } else {
     txt = "You pressed Cancel!";
     }*/
    $.confirm({
        title: 'CODERRASH ALERT',
        content: 'You are about to submit your code and go to next question. You cannot revert or come back to your previous question.',
        buttons: {
            confirm: function () {
                //submitquestion();
                getquestion();
            },
            cancel: function () {
                $.alert('Operation Cancled');
            }
        }
    });
}



function submitquestion() {
    var editor = ace.edit("editor");
    var code = editor.getValue();
    if (code == null || code == '')
        code = ' ';
    $.ajax({
        type: 'post',
        url: '../crrashsubmit',
        data: {
            code: code,
        },
        success: function (response) {

        }
    });
}