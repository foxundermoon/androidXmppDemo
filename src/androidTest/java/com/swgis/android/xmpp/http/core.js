function macfix() {
    $('#gotomac').css('display', 'block');
    $('#p0_btn a').html("<img src='8.0/images/p0btnmac.png'>");
}
function mac() {
    str = navigator.platform.toString();
    if (str == 'MacIntel') {
        macfix();
    }
}
ie = false;
function isIE() {
    if (!!window.ActiveXObject || "ActiveXObject" in window) {
        return true;
    } else {
        return false;
    }
}
if (navigator.userAgent.indexOf("Firefox") > 0) {
    window.location.href = "./index_static.html" + location.search;
}
;
function IEVersion() {
    var rv = -1;
    if (navigator.appName == 'Microsoft Internet Explorer') {
        var ua = navigator.userAgent;
        var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
        if (re.exec(ua) != null)
            rv = parseFloat(RegExp.$1);
    }
    else if (navigator.appName == 'Netscape') {
        var ua = navigator.userAgent;
        var re = new RegExp("Trident/.*rv:([0-9]{1,}[\.0-9]{0,})");
        if (re.exec(ua) != null)
            rv = parseFloat(RegExp.$1);
    }
    return rv;
}
if (isIE()) {
    if (IEVersion() < 10) {
        if (IEVersion() > 7) {
            window.location.href = "./index_parallax.html" + location.search;
        } else {
            window.location.href = "./index_static.html" + location.search;
        }
        ;
    }
    ;
}
;

pn = 0;
direction = true;
inital = true;
playing = false;
playingduration = 1500;
var scrollFunc = function (event) {
    event = event || window.event;
    if (!playing) {
        if (event.wheelDelta < 0) {
            motion(true);
        } else {
            motion(false);
        }
    }
    ;
}
if (document.addEventListener) {
    document.addEventListener('DOMMouseScroll', scrollFunc, false);
}
window.onmousewheel = document.onmousewheel = scrollFunc;
document.onkeydown = function (event) {
    event = event || window.event;
    var c = event.keyCode;
    if (c == 40 || c == 32 || c == 39) {
        motion(true);
    } else if (c == 38 || c == 37) {
        motion(false);
    }
}
function playingdelay() {
    playing = true;
    setTimeout(function () {
        playing = false;
    }, playingduration);
}
function motion(direction) {
    if (direction == true) {
        if (pn == 3) {
            pn = 0;
            core(0, true);
            playingdelay();
        } else {
            pnsnap = pn;
            pn += 1;
            core(pn, false);
            playingdelay();
        }
    } else {
        if (pn == 0) {
        } else {
            pnsnap = pn;
            pn -= 1;
            core(pn, false);
            playingdelay();
        }
    }

}
function core(pn, loop) {
    if (typeof(pgvMain) == 'function') {
        pgvSendClick({hottag: 'browser.qb8.scroll' + (pn + 1)});
    }
    // var bg = $('#container');
    $('#add_nav div').removeClass('on');
    $('#add_nav div:nth-child(' + (pn + 1) + ')').addClass('on');
    var bg = $('#bg');
    var pub_t = 1000;
    var p0 = $('#p0');
    var p1 = $('#p1');
    var p2 = $('#p2');
    var p3 = $('#p3');
    var p081 = $('#p0_8_1');
    var p082 = $('#p0_8_2');
    var p0t1 = $('#p0_t_1');
    var p0t2 = $('#p0_t_2');
    var slogan1 = $('#slogan_1');
    var slogan2 = $('#slogan_2');
    var slogan3 = $('#slogan_3');
    var slogan4 = $('#slogan_4');
    var p0btn = $('#p0_btn');

    var p1t1 = $('#p1_t_1');
    var p1t2 = $('#p1_t_2');
    var p1ui0 = $('#p1_ui_0');

    var p2t1 = $('#p2_t_1');
    var p2t2 = $('#p2_t_2');
    var p2pop = $('#p2_pop');
    var p2pop1 = $('#p2_pop_1');
    var p2pop2 = $('#p2_pop_2');
    var p2pop3 = $('#p2_pop_3');
    var p2pop4 = $('#p2_pop_4');
    var p2popicon = $('#p2_pop img');

    var p3t1 = $('#p3_t_1');
    var p3t2 = $('#p3_t_2');
    var p3def = $('#p3_defence');
    var p3list = $('#p3_list');
    var bgspeed = pub_t * 4;
    if (pn == 0) {
        $('#add_links').css('display', 'none');
        $('#add_scrolling').velocity({opacity: 1}, {duration: 1000});
        $('#add_lefttop').velocity({opacity: 0}, {duration: pub_t});
        bg.velocity({
            opacity: 1,
            scale: 0.6,
            translateX: '0px',
            translateY: '0px',
            rotateZ: '0deg'
        }, {duration: pub_t * 3, easing: 'ease-in-out'});
    } else if (pn == 1) {
        $('#gotomac').css('display', 'none');
        $('#add_scrolling').velocity({opacity: 0}, {duration: 500});
        $('#add_lefttop').velocity({opacity: 1}, {duration: pub_t});
        bg.velocity('stop').velocity({
            scale: 1,
            translateX: '-200px',
            translateY: '300px',
            rotateZ: '-60deg'
        }, {duration: pub_t * 3, easing: 'ease-in-out'});
    } else if (pn == 2) {
        $('#add_links').velocity({opacity: 0}, {duration: 1500})
        // bg.velocity('stop').velocity({translateX:'-300px',translateY:'200px',rotateZ:'0deg'},{duration:pub_t*4,easing:'ease-in-out'});
    } else if (pn == 3) {
        $('#add_links').css('display', 'block').velocity({opacity: 1}, {duration: 1500});
        // bg.velocity('stop').velocity({translateX:'-400px',translateY:'300px',rotateZ:'-30deg'},{duration:pub_t*4,easing:'ease-in-out'});
    }
    ;
    if (pn == 0) {
        // $('#p2_pop_1 img').velocity({translateX:'700px'},{duration:6000,loop:true});
        // $('#p2_pop_2 img').velocity({translateX:'600px'},{duration:6000,loop:true});
        // $('#p2_pop_3 img').velocity({translateX:'500px'},{duration:6000,loop:true});
        // $('#p2_pop_4 img').velocity({translateX:'-700px'},{duration:6000,loop:true});
        // loop
        if (inital == true) {
            p1.css('visibility', 'hidden');
            p3.css('visibility', 'hidden');
            inital = false;
        }
        p2.css('visibility', 'hidden');
        if (loop == true) {
            setTimeout(function () {
                p3.css('visibility', 'hidden');
            }, 1000)
            var p3back = function () {
                p3t1.velocity({rotateY: '-60deg', opacity: 0}, {duration: pub_t * 0.5});
                p3t2.velocity({rotateY: '0deg', opacity: 0}, {duration: pub_t * 0.5});
                p3def.velocity({rotateY: '-30deg', opacity: 0}, {duration: pub_t * 0.5});
                p2t1.css('visibility', 'hidden');
                p3list.velocity({opacity: 0}, {duration: pub_t * 1});
                $('#p3_point .pt1').velocity({
                    scale: 1,
                    rotateY: '15deg',
                    translateZ: '1000px'
                }, {duration: pub_t * 1}).velocity({opacity: 0});
                $('#p3_point .pt2').velocity({
                    scale: 1,
                    rotateY: '14deg',
                    translateZ: '1000px'
                }, {duration: pub_t * 1.05}).velocity({opacity: 0});
                $('#p3_point .pt3').velocity({
                    scale: 1,
                    rotateY: '13deg',
                    translateZ: '1000px'
                }, {duration: pub_t * 1.1}).velocity({opacity: 0});
                $('#p3_point .pt4').velocity({
                    scale: 1,
                    rotateY: '12deg',
                    translateZ: '1000px'
                }, {duration: pub_t * 1.15}).velocity({opacity: 0});
                $('#p3_point .pt5').velocity({
                    scale: 1,
                    rotateY: '11deg',
                    translateZ: '1000px'
                }, {duration: pub_t * 1.2}).velocity({opacity: 0});
            }
            p3back();
            p2t1.velocity({opacity: 1, translateZ: 1100, rotateX: '-30deg'}, {duration: pub_t});
            p2pop1.velocity({rotateY: '30deg', translateZ: '1300px', rotateX: '-180deg'}, {duration: pub_t});
            p2pop2.velocity({rotateY: '30deg', translateZ: '1400px', rotateX: '-180deg'}, {duration: pub_t});
            p2pop3.velocity({rotateY: '30deg', translateZ: '1500px', rotateX: '-180deg'}, {duration: pub_t});
            p2pop4.velocity({rotateY: '-30deg', translateZ: '1600px', rotateX: '180deg'}, {duration: pub_t});
        }
        // p0 init & back
        p081.velocity({rotateY: '20deg', translateZ: '1000px', rotateX: '180deg'}, {duration: 0});
        p082.velocity({rotateY: '20deg', translateZ: '1500px', rotateX: '360deg'}, {duration: 0});
        p0t1.velocity({opacity: 0, translateZ: 0, rotateY: 0, rotateX: 0}, {duration: 0});
        // p0t2.velocity({opacity:0,translateZ:0,rotateY:0,rotateX:0},{duration:0});
        slogan1.velocity({rotateY: '30deg', translateZ: '1000px', rotateX: '90deg', opacity: 0}, {duration: 0});
        slogan2.velocity({rotateY: '25deg', translateZ: '1300px', rotateX: '90deg', opacity: 0}, {duration: 0});
        slogan3.velocity({rotateY: '20deg', translateZ: '1300px', rotateX: '90deg', opacity: 0}, {duration: 0});
        slogan4.velocity({rotateY: '20deg', translateZ: '1300px', rotateX: '90deg', opacity: 0}, {duration: 0});
        // p0 in
        p081.velocity({rotateY: 0, translateZ: '0px', rotateX: 0}, {duration: pub_t * 2});
        p082.velocity({rotateY: 0, translateZ: '0px', rotateX: 0}, {duration: pub_t * 2.4});
        p0t1.velocity({opacity: 1, rotateY: 0, rotateX: 0}, {duration: pub_t / 2, delay: pub_t * 2});
        // p0t2.velocity('fadeIn',{duration:pub_t,delay:pub_t*2});
        slogan1.velocity({rotateY: '0deg', translateZ: '0px', rotateX: '0deg', opacity: 1}, {duration: pub_t * 2});
        slogan2.velocity({rotateY: '0deg', translateZ: '0px', rotateX: '0deg', opacity: 1}, {duration: pub_t * 2.2});
        slogan3.velocity({rotateY: '0deg', translateZ: '0px', rotateX: '0deg', opacity: 1}, {duration: pub_t * 2.4});
        slogan4.velocity({rotateY: '0deg', translateZ: '0px', rotateX: '0deg', opacity: 1}, {duration: pub_t * 2.4});

        setTimeout(function () {
            p0btn.css('display', 'block');
            p0btn.velocity({opacity: 1}, {duration: pub_t, delay: pub_t / 2});
        }, 1400);

        // all page initalization

        // p1 back
        p1ui0.velocity({rotateY: '30deg', translateZ: '1000px', opacity: 0}, {duration: pub_t * 1});
        p1t1.velocity({rotateX: '0deg', translateZ: '0px', rotateY: '20deg', opacity: 0}, {duration: pub_t * 1});
        p1t2.velocity({opacity: 0}, {duration: pub_t * 1});
    } else if (pn == 1) {
        p1.css('visibility', 'visible');
        // p0 away
        var p0away = function () {
            slogan1.velocity({
                rotateY: '-10deg',
                translateZ: '1000px',
                rotateX: '30deg',
                opacity: 0
            }, {duration: pub_t * 0.7});
            slogan2.velocity({
                rotateY: '-5deg',
                translateZ: '1300px',
                rotateX: '60deg',
                opacity: 0
            }, {duration: pub_t * 1});
            slogan3.velocity({
                rotateY: '0deg',
                translateZ: '1600px',
                rotateX: '90deg',
                opacity: 0
            }, {duration: pub_t * 1.3});
            slogan4.velocity({
                rotateY: '0deg',
                translateZ: '1600px',
                rotateX: '90deg',
                opacity: 0
            }, {duration: pub_t * 1.3});
            p0btn.velocity({opacity: 0}, {duration: pub_t / 2});
            setTimeout(function () {
                p0btn.css('display', 'none');
            }, 600)
            p081.velocity({rotateY: '-60deg', translateZ: '1000px', rotateX: '180deg'}, {duration: pub_t * 1});
            p082.velocity({rotateY: '-60deg', translateZ: '1200px', rotateX: '360deg'}, {duration: pub_t * 1});
            p0t1.velocity({rotateX: '0deg', translateZ: '0px', opacity: 0}, {duration: pub_t / 2});
            // p0t2.velocity('fadeOut',{duration:pub_t});
        }
        p0away();
        // p1 in
        var p1in = function () {
            p1ui0.velocity({
                rotateX: '0deg',
                rotateY: '0deg',
                translateZ: '0px',
                translateY: 0,
                opacity: 1
            }, {duration: pub_t * 1.5});
            p1t1.velocity({
                rotateX: '0deg',
                rotateY: '0deg',
                translateZ: '0px',
                translateY: 0,
                opacity: 1
            }, {duration: pub_t * 1, delay: pub_t * 1.1});
            p1t2.velocity({opacity: 1}, {duration: pub_t * 1, delay: pub_t * 1});
        }
        p1in();
        // p2 back
        var p2back = function () {
            p2t1.velocity({rotateY: '-60deg', translateZ: '800px', rotateX: '0deg', opacity: 0}, {duration: pub_t * 1});
            p2t2.velocity({opacity: 0}, {duration: pub_t / 2});
            p2pop1.velocity({
                rotateY: '-60deg',
                translateZ: '800px',
                rotateX: '120deg',
                scale: 0.1
            }, {duration: pub_t * 1});
            p2pop2.velocity({
                rotateY: '-60deg',
                translateZ: '900px',
                rotateX: '120deg',
                scale: 0.1
            }, {duration: pub_t * 1.2});
            p2pop3.velocity({
                rotateY: '-60deg',
                translateZ: '1000px',
                rotateX: '120deg',
                scale: 0.1
            }, {duration: pub_t * 1.4});
            p2pop4.velocity({
                rotateY: '-60deg',
                translateZ: '1100px',
                rotateX: '120deg',
                scale: 0.1
            }, {duration: pub_t * 1.6});
        }
        p2back();
        p2.velocity({rotateZ: '-30deg'}, {duration: pub_t * 1.5});
    } else if (pn == 2) {
        p2.velocity({rotateZ: '0deg'}, {duration: pub_t * 2});
        p2.css('visibility', 'visible');
        p2t1.css('visibility', 'visible');
        // p1 away
        var p1away = function () {
            // p1ui0.velocity({rotateY:'0deg',translateZ:'-200px',opacity:0},{duration:pub_t*0.6});
            p1ui0.velocity({rotateY: '0deg', translateZ: '1000px', opacity: 0}, {duration: pub_t * 1});
            p1t1.velocity({rotateX: 0, translateZ: '0px', rotateY: '-20deg', opacity: 0}, {duration: pub_t * 0.6});
            p1t2.velocity({opacity: 0}, {duration: pub_t * 0.6});
        }
        p1away();
        var p2wave = function (num) {
            eval('wave' + num).velocity({opacity: 1}, {duration: pub_t});
        }
        var p2in = function () {
            p2t1.velocity({rotateY: 0, translateZ: 0, rotateX: 0, opacity: 1}, {duration: pub_t * 2})
            p2t2.velocity({opacity: 1}, {duration: pub_t, delay: pub_t * 1.5});
            p2pop1.velocity({rotateY: 0, translateZ: 0, rotateX: 0, opacity: 1, scale: 1}, {duration: pub_t * 2});
            p2pop2.velocity({rotateY: 0, translateZ: 0, rotateX: 0, opacity: 1, scale: 1}, {duration: pub_t * 2});
            p2pop3.velocity({rotateY: 0, translateZ: 0, rotateX: 0, opacity: 1, scale: 1}, {duration: pub_t * 2});
            p2pop4.velocity({rotateY: 0, translateZ: 0, rotateX: 0, opacity: 1, scale: 1}, {duration: pub_t * 2});
            // p2popicon.velocity({opacity:1,scale:1,rotateZ:'-30deg'},{duration:pub_t*0.5,delay:pub_t*1.5,easing:'easeOutBack'});
        }
        p2in();
        // p2 in

        var p3back = function () {
            p3t1.velocity({rotateY: '-60deg', scale: 0.7, opacity: 0}, {duration: pub_t * 0.5});
            p3t2.velocity({rotateY: '0deg', opacity: 0}, {duration: pub_t * 0.5});
            p3def.velocity({rotateY: '-30deg', scale: 0.7, opacity: 0}, {duration: pub_t * 0.5});
            p3list.velocity({opacity: 0}, {duration: pub_t * 1});
            $('#p3_point .pt1').velocity({
                scale: 1,
                rotateY: '-15deg',
                translateZ: '1000px'
            }, {duration: pub_t * 1.4}).velocity({opacity: 0});
            $('#p3_point .pt2').velocity({
                scale: 2,
                rotateY: '-14deg',
                translateZ: '1000px'
            }, {duration: pub_t * 1.3}).velocity({opacity: 0});
            $('#p3_point .pt3').velocity({
                scale: 3,
                rotateY: '-13deg',
                translateZ: '1000px'
            }, {duration: pub_t * 1.2}).velocity({opacity: 0});
            $('#p3_point .pt4').velocity({
                scale: 4,
                rotateY: '-12deg',
                translateZ: '1000px'
            }, {duration: pub_t * 1.1}).velocity({opacity: 0});
            $('#p3_point .pt5').velocity({
                scale: 5,
                rotateY: '-11deg',
                translateZ: '1000px'
            }, {duration: pub_t}).velocity({opacity: 0});
        }
        p3back();
        p3.velocity({scale: 0.6}, {duration: pub_t * 1.5});
    } else if (pn == 3) {
        p2.velocity({rotateZ: '30deg'}, {duration: pub_t * 1.5});
        p3.velocity({scale: 1}, {duration: pub_t * 2});
        p3.css('visibility', 'visible');
        // p2 away
        var p2away = function () {
            p2t1.velocity({rotateY: '30deg', translateZ: '1300px', rotateX: '0deg', opacity: 0}, {duration: pub_t});
            p2t2.velocity({opacity: 0}, {duration: pub_t / 2});
            p2pop1.velocity({rotateY: '60deg', translateZ: '1300px', rotateX: '90deg', scale: 0.1}, {duration: pub_t});
            p2pop2.velocity({
                rotateY: '60deg',
                translateZ: '1400px',
                rotateX: '90deg',
                scale: 0.1
            }, {duration: pub_t * 1.2});
            p2pop3.velocity({
                rotateY: '60deg',
                translateZ: '1500px',
                rotateX: '90deg',
                scale: 0.1
            }, {duration: pub_t * 1.4});
            p2pop4.velocity({
                rotateY: '60deg',
                translateZ: '1600px',
                rotateX: '90deg',
                scale: 0.1
            }, {duration: pub_t * 1.6});
        }
        p2away();
        var p3in = function () {
            p3t1.velocity({translateZ: 0, opacity: 1, rotateY: 0, scale: 1}, {duration: pub_t, delay: pub_t / 2});
            p3t2.velocity({translateZ: 0, opacity: 1, rotateY: 0}, {duration: pub_t / 2, delay: pub_t});
            p3def.velocity({rotateY: 0, opacity: 1, scale: 1}, {duration: pub_t * 1, delay: pub_t * 1});
            p3list.velocity({opacity: 1}, {duration: pub_t * 1});
            $('#p3_point .pt1,#p3_point .pt2,#p3_point .pt3,#p3_point .pt4,#p3_point .pt5').velocity({opacity: 1}, {duration: 0});
            $('#p3_point .pt1').velocity({scale: 1, rotateY: 0, translateZ: '0px'}, {duration: pub_t});
            $('#p3_point .pt2').velocity({scale: 1, rotateY: 0, translateZ: '0px'}, {duration: pub_t * 1.2});
            $('#p3_point .pt3').velocity({scale: 1, rotateY: 0, translateZ: '0px'}, {duration: pub_t * 1.4});
            $('#p3_point .pt4').velocity({scale: 1, rotateY: 0, translateZ: '0px'}, {duration: pub_t * 1.6});
            $('#p3_point .pt5').velocity({scale: 1, rotateY: 0, translateZ: '0px'}, {duration: pub_t * 1.8});
        }
        p3in();
    }
    ;
}
$(document).ready(function () {
    core(0, false);
    mac();
});