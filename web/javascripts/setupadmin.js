function doSomething(){

    var editor = ace.edit("editor");
    var editor2 = ace.edit("reflection");
    var code = editor.getValue();
    var sendcode = {xyz: code};

    $.ajax({
        type: 'POST',
        url: 'http://localhost:3000/admin/ajaxcall1',
        data: sendcode

    })
        .done(function(data){
            var actualcode = data.xyz;
            console.log('POST response: ', JSON.stringify(actualcode));
            var noquote = JSON.stringify(actualcode, "", 2);
            var final = noquote.replace(/\"/g, "");
            editor.setValue(final);
            editor.find('\\n');
            editor.replaceAll('\n');
        })
        .fail(function(jqXHR, textStatus, err){
            conosle.log('AJAX error response: ', textStatus);
        })

    $.ajax({
        type: 'POST',
        url: 'http://localhost:3000/admin/ajaxcall2',
        data: sendcode

    })
        .done(function(data){
            var actualcode = data.xyz;
            console.log('POST response: ', JSON.stringify(actualcode));
            var noquote = JSON.stringify(actualcode, "", 2);
            var final = noquote.replace(/\"/g, "");
            editor2.setValue(final);
            editor2.find('\\n');
            editor2.replaceAll('\n');
        })
        .fail(function(jqXHR, textStatus, err){
            conosle.log('AJAX error response: ', textStatus);
        })
}
function timer(){
    // Set the date we're counting down to
    var countDownDate = new Date("Nov 19, 2017 17:20:25").getTime();

// Update the count down every 1 second
    var x = setInterval(function() {

        // Get todays date and time
        var now = new Date().getTime();

        // Find the distance between now an the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Output the result in an element with id="demo"
        document.getElementById("clock").innerHTML = days + "d " + hours + "h "
            + minutes + "m " + seconds + "s ";

        // If the count down is over, write some text
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("clock").innerHTML = "EXPIRED";
        }
    }, 1000);
}
function doContinuous(){
    setInterval(doSomething, 1000);
    timer();
}