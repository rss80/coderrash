function rand()
{
    var d = new Date();
    var unique = 'cr'+d.getTime()+''+Math.floor((Math.random() * 9999) + 1)+''+(Math.random()*0xFFFFFF<<0).toString(16); 
    return unique;
}
