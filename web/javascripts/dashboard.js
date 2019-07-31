function alterstatusajax(param)
{
    $.ajax({
        type: 'POST',
        url: 'alterstatus',
        data:
                {
                    id: param,
                },
        success: function (response) {
        }
    });
}

function eventdetails(param)
{
    //alert('hello');
    $.ajax({
        type: 'POST',
        url: 'checkstatus',
        data:
                {
                    id: param,
                },
        success: function (response) {
            document.getElementById("unique_event").value=param;
            if (response == 'yes')
            {
                $.confirm({
                    title: 'CODERRASH ALERT',
                    content: 'Do you want to start this event?',
                    buttons: {
                        start: function () {
                            alterstatusajax(param);
                            var x = document.getElementById("loadevent");
                            document.getElementById("unique_event").value=param;
                            x.submit();
                        },
                        cancel: function () {

                        }
                    }
                });
            } else
            {
                var x1 = document.getElementById("loadevent");
                document.getElementById("unique_event").value=param;
                x1.submit();
            }
        }
    });
}