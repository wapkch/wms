$(function () {
    // 1.
    $("#selectAll").click(function () {
        $(".roleAll option").appendTo($(".roleSelected"));
    });
    // 2.
    $("#deselectAll").click(function () {
        $(".roleSelected option").appendTo($(".roleAll"));
    });
    // 3.
    $("#select").click(function () {
        $(".roleAll option:selected").appendTo($(".roleSelected"));
    });
    // 4.
    $("#deselect").click(function () {
        $(".roleSelected option:selected").appendTo($(".roleAll"));
    });

    // 提交表单,将select选中
    $("#editForm").submit(function () {
        $(".roleSelected option").prop("selected", "selected");
        // return false;
    });

    // 去重
    var selectedOptions = $.map($(".roleSelected option"), function (item, index) {
        return $(item).val();
    });
    $.each($(".roleAll option"), function (index, item) {
        if (($.inArray($(item).val(), selectedOptions)) > -1) {
            $(item).remove();
        }
    })

});

/**
 * 员工页面的额easyui实现
 */
$(function () {
    $('#employee_datagrid').datagrid({
        url: '/employee.action',
        fit: true,
        fitColumns: true,
        toolbar: '#tb',
        striped: true,
        pagination: true,
        rownumbers: true,
        checkOnSelect: true,
        columns: [[
            {checkbox: true, field: 'id'},
            {field: 'name', title: '姓名', width: 100, align: 'center'},
            {field: 'email', title: '邮箱', width: 100, align: 'center'},
            {field: 'age', title: '年龄', width: 100, align: 'center'},
            {
                field: 'admin', title: '超级管理员', width: 100, align: 'center', formatter: function (value, row, index) {
                return value ? "是" : "否";
            }
            },
            {
                field: 'dept', title: '部门名称', width: 100, align: 'center', formatter: function (value, row, index) {
                return value ? value.name : "";
            }
            }
        ]]
    });

    $('#employee_dialog').dialog({
        width: 300,
        height: 260,
        buttons: '#btns',
        closed: true
    })

})

function add() {
    $("#employee_form").form("clear");
    $('#employee_dialog').dialog("setTitle", "添加员工");
    $('#employee_dialog').dialog("open");
}

function cancel() {
    $('#employee_dialog').dialog("close");
}

function save() {
    $("#employee_form").form("submit", {
        url: '/employee_saveOrUpdate.action',
        onSubmit: function () {
            var stat = $('[name="employee.admin"]').prop("value");
            if (stat == 'on'){
                $('[name="employee.admin"]').prop("value",true);
            }
        },
        success: function (data) {
            $.messager.alert('温馨提示', data, "info", function () {
                $('#employee_dialog').dialog("close");
                $('#employee_datagrid').datagrid("reload");
            });
        }
    })
}

function edit() {
    var rows = $('#employee_datagrid').datagrid("getSelections");
    if (rows.length != 1) {
        $.messager.alert('温馨提示', "必须选中一行！", "warning");
        return;
    }
    var e = rows[0];
    e['employee.id'] = e.id;
    e['employee.name'] = e.name;
    e['employee.email'] = e.email;
    e['employee.age'] = e.age;
    e['employee.admin'] = e.admin;
    e['employee.dept.id'] = e.dept.id;

    $('#employee_dialog').dialog("setTitle", "编辑员工");
    $("#employee_form").form("load", e);
    $('#employee_dialog').dialog("open");
}

function removeBtn() {
    var rows = $('#employee_datagrid').datagrid("getSelections");
    if (rows.length != 1) {
        $.messager.alert('温馨提示', "必须选中一行！", "warning");
        return;
    }
    $.messager.confirm("温馨提示", "你确定要删除改行吗？", function (cmd) {
        if (cmd) {
            $.get("/employee_delete.action?employee.id=" + rows[0].id, function (data) {
                $.messager.alert('温馨提示', data, "info", function () {
                    $('#employee_datagrid').datagrid("reload");
                });
            })
        }
    })
}

function reload() {
    $.messager.confirm("温馨提示", "你确定要重新加载吗？", function (cmd) {
        if (cmd) {
            $('#employee_datagrid').datagrid("reload");
        }
    })
}

function searchForKey() {
    var keyword = $("[name='keyword']").val();
    var dept_id = $("[name='dept_id']").val();
    $('#employee_datagrid').datagrid("load", {
        'qo.keyword': keyword,
        'qo.deptId': dept_id
    });
}


jQuery.ajaxSettings.traditional = true;
function batchDelete() {
    var rows = $('#employee_datagrid').datagrid("getSelections");
    if (rows.length < 2) {
        $.messager.alert('温馨提示', "必须选中多行！", "warning");
        return;
    }
    var arr = new Array();
    for(var i in rows){
        arr.push(rows[i].id);
    }
    $.messager.confirm("温馨提示", "你确定要删除多行吗？", function (cmd) {
        if (cmd) {
            $.get("/employee_batchDelete.action",{ids:arr},function (data) {
                $.messager.alert('温馨提示', data, "info",function () {
                    $('#employee_datagrid').datagrid("reload");
                });
            })
        }
    })

}