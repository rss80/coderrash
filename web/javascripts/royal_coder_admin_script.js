var parser, xmlDoc;
parser = new DOMParser();

var parser_2, xmlDoc_2;
parser_2 = new DOMParser();

//comment

function calldatabase(participant) {
    var participant_name = document.getElementById(participant).value;
    var unique_event_id = document.getElementById('unique_event_id').value;
    $.ajax({
        type: 'post',
        url: 'getentries',
        data: {
            participant_name: participant_name,
            unique_event_id: unique_event_id,
        },
        success: function (response) {
            response = response.replace(/\n/g, "<br />");
            $.alert({
                title: 'coderrash view: ' + participant_name,
                content: response,
                columnClass: 'medium',
            });
        }
    });
}



function interval_on_one() {
    var participant_name = document.getElementById('participant_one_name').value;
    var unique_event_id = document.getElementById('unique_event_id').value;
    var editor = ace.edit("editor");

    $.ajax({
        type: 'POST',
        url: 'crgettinglivechachier',
        data: {
            unique_event_id: unique_event_id,
            participant_name: participant_name,
        },
        success: function (response) {
            var final = response;
            xmlDoc = parser.parseFromString(final, "text/xml");
            var question_number = xmlDoc.getElementsByTagName("question_number")[0].childNodes[0].nodeValue;
            var coders_code = xmlDoc.getElementsByTagName("code_live")[0].childNodes[0].nodeValue;
            document.getElementById("participant_one_question").innerHTML = question_number;
            editor.setValue(coders_code);
            editor.find('\\n');
            editor.replaceAll('\n');
        }
    });
}



function interval_on_two() {
    var participant_name = document.getElementById('participant_two_name').value;
    var unique_event_id = document.getElementById('unique_event_id').value;
    var editor = ace.edit("reflection");

    $.ajax({
        type: 'POST',
        url: 'crgettinglivechachier',
        data: {
            unique_event_id: unique_event_id,
            participant_name: participant_name,
        },
        success: function (response) {
            var final = response;
            xmlDoc_2 = parser_2.parseFromString(final, "text/xml");
            var question_number = xmlDoc_2.getElementsByTagName("question_number")[0].childNodes[0].nodeValue;
            var coders_code = xmlDoc_2.getElementsByTagName("code_live")[0].childNodes[0].nodeValue;
            document.getElementById("participant_two_question").innerHTML = question_number;
            editor.setValue(coders_code);
            editor.find('\\n');
            editor.replaceAll('\n');
        }
    });
}



function calllive(participant) {
    var participant_name = document.getElementById(participant).value;
    var unique_event_id = document.getElementById('unique_event_id').value;
    $.ajax({
        type: 'post',
        url: 'getliveentries',
        data: {
            participant_name: participant_name,
            unique_event_id: unique_event_id,
        },
        success: function (response) {
            response = response.replace(/\n/g, "<br />");
            if (response == 'present') {
                $.confirm({
                    title: 'CODERRASH ALERT',
                    content: 'You Requested for Live Code for ' + participant_name + '. Start viewing live code',
                    columnClass: 'medium',
                    buttons: {
                        confirm: function () {
                           if(participant == 'participant_one_name'){
                               document.getElementById("participant_one_live").disabled = true;
                               setInterval(function(){ interval_on_one() }, 5000);
                           }else if(participant == 'participant_two_name'){
                               document.getElementById("participant_two_live").disabled = true;
                               setInterval(function(){ interval_on_two() }, 5000);
                           }
                        },
                        cancel: function () {
                            $.alert('Operation Cancled');
                        }
                    }
                });
            } else {
                $.alert({
                    title: 'CODERRASH ALERT',
                    content: 'You Requested for Live Code for ' + participant_name + '. But he is not currently signed-in...!!',
                    columnClass: 'medium',
                });
            }
        }
    });
}