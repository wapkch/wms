jQuery.ajaxSettings.traditional = true;
$(function () {
    /***********************新增操作**********************/
    $(".btn_input").click(function () {
        window.location.href = $(this).data("url");
    });

    /***********************删除操作**********************/
    $(".btn_delete").click(function () {
        var url = $(this).data("url");
        showDialogMsg("", "你确定要删除吗?", function () {
            $.get(url,function (data) {
                showDialogMsg("",data,function () {
                    window.location.reload();
                })
            });
        }, true);
    });
    /***********************审核操作**********************/
    $(".btn_audit").click(function () {
        var url = $(this).data("url");
        showDialogMsg("", "你确定要审核吗?", function () {
            $.get(url,function (data) {
                showDialogMsg("",data,function () {
                    window.location.reload();
                })
            });
        }, true);
    });
    /***********************批量删除操作**********************/
    $(".btn_batchDelete").click(function () {
        var url = $(this).data("url");
        var ids = $.map($(".acb:checked"),function (item,index) {
            return $(item).data("oid");
        });
        showDialogMsg("", "你确定要批量删除吗?", function () {
            $.post(url,{ids:ids},function (data) {
                showDialogMsg("",data,function () {
                    window.location.reload();
                })
            });
        }, true);
    });

    /***********************加载权限操作**********************/
    $(".btn_reload").click(function () {
        var url = $(this).data("url");
        showDialogMsg("", "你确定要重新加载权限吗,这可能会耗费较长时间?", function () {
            $.get(url,function (data) {
                showDialogMsg("",data,function () {
                    window.location.reload();
                })
            });
        }, true);
    });

    /***********************翻页操作**********************/
    $(".btn_page").click(function () {
        var page = $(this).data("page") || $("[name='qo.currentPage']").val();
        $("[name='qo.currentPage']").val(page);
        $("#searchForm").submit();
    });

});

/***********************漂亮的dialog弹窗**********************/
function showDialogMsg(title, content, ok, cancel) {
    $.dialog({
        title: title || "温馨提示",
        content: content || "",
        ok: ok || true,
        cancel: cancel || false,
        icon: 'face-smile'
    });
}

