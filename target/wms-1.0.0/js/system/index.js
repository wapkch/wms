//加载当前日期
function loadDate(){
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."	+ myDay;
}

/**
 * 隐藏或者显示侧边栏
 *
 **/
function switchSysBar(flag){
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if( flag==true ){	// flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({width:'280px'});
        $('#top_nav').css({width:'77%', left:'304px'});
        $('#main').css({left:'280px'});
    }else{
        if ( left_menu_cnt.is(":visible") ) {
            left_menu_cnt.hide(10, 'linear');
            side.css({width:'60px'});
            $('#top_nav').css({width:'100%', left:'60px', 'padding-left':'28px'});
            $('#main').css({left:'60px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({width:'280px'});
            $('#top_nav').css({width:'77%', left:'304px', 'padding-left':'0px'});
            $('#main').css({left:'280px'});
            $("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
        }
    }
}

$(function(){
    loadDate();
    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function() {
        switchSysBar();
    });
});

$(function () {
    $("#dleft_tab1 a").click(function () {
        $("#rightMain").prop("src",$(this).data("url"));
    });
});

$(function () {
    $("#TabPage2 li").click(function () {
        console.debug($(this));
        $.each($("#TabPage2 li"),function (index, item) {
            $(item).removeClass("selected");
            $(item).find("img").prop("src","/images/common/"+(index+1)+".jpg");
        });
        $(this).addClass("selected");
        $(this).find("img").prop("src","/images/common/"+($(this).index()+1)+"_hover.jpg")
        loadMenu($(this).data("rootmenu"));
    });
});

//==========================系统菜单树==========================
var setting = {
    async: {
        enable: true,
        url: "/systemMenu_queryMenusBySn.action",
        autoParam: ["sn=qo.parentSn"]
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: zTreeOnClick
    }
};

function zTreeOnClick(event, treeId, treeNode) {
    console.debug(treeNode);
    $("#rightMain").prop("src",treeNode.action);
    $("#here_area").html("当前位置："+treeNode.getParentNode().name+"&nbsp;>&nbsp;"+treeNode.name);
};

var business = [
    { id:1, pId:0, name:"业务模块", isParent:true, sn:"business"}
];
var systemManage = [
    { id:2, pId:0, name:"系统模块", isParent:true, sn:"system"}
];
var charts = [
    { id:3, pId:0, name:"报表模块", isParent:true, sn:"chart"}
];
var zNodes = {
    "business": business,
    "systemManage": systemManage,
    "charts": charts
};
function loadMenu(parentSn) {
    $.fn.zTree.init($("#dleft_tab1"), setting, zNodes[parentSn]);
};

$(function () {
    loadMenu("business");
});