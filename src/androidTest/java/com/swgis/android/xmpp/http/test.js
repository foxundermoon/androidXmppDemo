function tp() {

    $.getJSON("http://www.fxingw.com/e/public/ViewClick/?classid=128&id=88406&down=8", function (data) {
            var width = 764;
            var radio, twidth, dwidth;
            if (data.diggtop == 0 && data.diggdown != 0) {
                twidth = '0px';
                dwidth = '100%';
            }
            else if (data.diggdown == 0 && data.diggtop != 0) {
                dwidth = '0px';
                twidth = '100%';
            }
            else if (data.diggdown == 0 && data.diggtop == 0) {
                twidth = '382px';
                dwidth = '382px';
            }
            else {
                radiot = data.diggtop / (parseInt(data.diggdown) + parseInt(data.diggtop));
                radiod = data.diggdown / (parseInt(data.diggdown) + parseInt(data.diggtop));
                twidth = parseInt(width * radiot) + 'px';
                dwidth = parseInt(width * radiod) + 'px';
            }
            str = '<div class="hzc" style="width:' + twidth + '"><span id="diggnum">' + data.diggtop + '</span></div><div class="lzc" style="width:' + dwidth + '"><span id="diggdown">' + data.diggdown + '</span></div>';
            //alert(str);
            alert("test a test");
            document.getElementById("id001").innerHTML("hello 先不改这里 测试显示了再替换我");

        }
    );


}
