/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function win_size() {
    win_width = $(window).width()-10;
    win_height = $(window).height();
    //alert(win_width);
}

function frame_resize(){
   win_size()
    width = win_width * 0.5
    height = win_height * 0.3
 
}


$('#twitch_com','#youtube.com').ready(function() {
    frame_resize();
        $(this).css('width', width).css('height',height);
}
)

$(document).ready(function(){
    win_size();
    right_width=win_width*0.15;
    content_width=win_width*0.85-45;
    $('#right').css('width',right_width);
    $('#content').css('width',content_width);
    right_height=$('#right').height();
    content_height=$('#content').height();
    if(right_height>content_height){
     $('#bottom').css('top',right_height-content_height)
 };
 $('#banner').attr('width', win_width-50);
}
)

 $('.searchBar').load(function(){
    win_size();
        $(this).css('width', win_width*0.5);
        $(this).css('margin-left',win_width*0.25);
})

        