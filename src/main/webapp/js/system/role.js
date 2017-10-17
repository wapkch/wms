$(function () {
    // 1.权限
    $("#selectAll").click(function () {
        $(".permissionAll option").appendTo($(".permissionSelected"));
    });
    $("#deselectAll").click(function () {
        $(".permissionSelected option").appendTo($(".permissionAll"));
    });
    $("#select").click(function () {
        $(".permissionAll option:selected").appendTo($(".permissionSelected"));
    });
    $("#deselect").click(function () {
        $(".permissionSelected option:selected").appendTo($(".permissionAll"));
    });
});

$(function () {
    // 提交表单,将select选中
    $("#editForm").submit(function () {
        $(".permissionSelected option").prop("selected","selected");
    });
});

$(function () {
    // 去重
    var selectedOptions = $.map($(".permissionSelected option"),function (item, index) {
        return $(item).val();
    });
    $.each($(".permissionAll option"),function (index, item) {
        if (($.inArray($(item).val(),selectedOptions))>-1){
            $(item).remove();
        }
    })
});

$(function () {
    // 2.系统菜单
    $("#mselectAll").click(function () {
        console.debug("hsdfa");
        $(".menuAll option").appendTo($(".menuSelected"));
    });
    $("#mdeselectAll").click(function () {
        $(".menuSelected option").appendTo($(".menuAll"));
    });
    $("#mselect").click(function () {
        $(".menuAll option:selected").appendTo($(".menuSelected"));
    });
    $("#mdeselect").click(function () {
        $(".menuSelected option:selected").appendTo($(".menuAll"));
    });
});

$(function () {
    // 提交表单,将select选中
    $("#editForm").submit(function () {
        $(".menuSelected option").prop("selected","selected");
    });
});

$(function () {
    // 去重
    var mselectedOptions = $.map($(".menuSelected option"),function (item, index) {
        return $(item).val();
    });
    $.each($(".menuAll option"),function (index, item) {
        if (($.inArray($(item).val(),mselectedOptions))>-1){
            $(item).remove();
        }
    })
});