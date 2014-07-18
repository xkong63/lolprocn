/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function win_size() {
    win_width = $(window).width();
    win_height = $(window).height();

}

function frame_resize(){
   win_size()
    width = win_width * 0.8
    height = win_height * 0.3
 
}

$('#banner').ready(function banner_resize() {
    win_size()
    $('#banner').css('width', win_width);
    $('#banner').css('height',200);
}
)
$('#twitch_com').ready(function twitch_size() {
    frame_resize();
        $('#twitch_com').css('width', width);
    $('#twitch_com').css('height', height);
}
)

$('#youtube_com').ready(function google_size() {
   frame_resize()
    $('#youtube_com').css('width', width);
    $('#youtube_com').css('height', height);
}
)

$(window).ready(function layout(){
    win_size();
    right_width=win_width*0.15;
    $('#right').css('width',right_width);
})

$('#searchBar').ready(function searchBar_size(){
    win_size();
        $('#searchBar').css('width', win_width*0.5);
        $('#searchBar').css('margin-left',win_width*0.25);
})
        