// 点击主页 -> 相片管理
$('#pic_manage').click(function (){
    // 后面加入日期, 是的 iframe 不缓存
    $('#content_iframe').attr("src", "photo_manage.html?" + (new Date()).getTime());

    // 显示内容到主内容框中
    // var str =
    //     "<table class='layui-table' lay-size='lg' id='fold-list' style='border-left: 0px'>" +
    //     "<tr>" +
    //     "<td style='border-right-width: 0px;border-left-width: 0px;'>" +
    //     "<img src='image/folder-icon.png' alt='' width='30px'>" +
    //     "<a href='#' style='padding-left: 10px'>wxid</a>" +
    //     "</td>" +
    //     "</tr>" +
    //     "<tr>" +
    //     "<td style='border-right-width: 0px;border-left-width: 0px;'>" +
    //     "<img src='image/folder-icon.png' alt='' width='30px' >" +
    //     "<span style='padding-left: 10px'>文件夹</span>" +
    //     "</td>" +
    //     "</tr>" +
    //     "</table>";
    //
    // $('#content_div').html(str);
})

// 点击主页 -> 反馈管理
$('#feedback_manage').click(function (){
    $('#content_iframe').attr("src", "feedback.html?" + (new Date()).getTime());
});

// 点击主页 -> 公告管理管理
$('#announcement_manage').click(function (){
    $('#content_iframe').attr("src", "announce.html?" + (new Date()).getTime());
});


// 点击主页 -> 系统设置
$('#system_setting').click(function (){
    $('#content_iframe').attr("src", "feedback.html?" + (new Date()).getTime());
});
