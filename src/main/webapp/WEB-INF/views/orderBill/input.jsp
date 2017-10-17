<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-validate/jquery.validate.js"></script>
    <script type="text/javascript" src=" /js/plugins/artDialog/jquery.artDialog.source.js?skin=blue"></script>
    <script type="text/javascript" src=" /js/plugins/artDialog/iframeTools.source.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            //选择商品的点击事件
            $("#edit_table_body").on("click",".searchproduct",function () {
                var url="/product_selectProduct.action";
                var currentTr=$(this).closest("tr");//向上找到当前行
                $.dialog.open(url,{
                    id: 'productSelect',
                    title: '商品选择',
                    width: 950,
                    height: 680,
                    close: function () {
                        var productJson=$.dialog.data("productJson");
                        console.log(productJson)
                        if(productJson){
                            currentTr.find("input[tag=name]").val(productJson.name);
                            currentTr.find("input[tag=pid]").val(productJson.pId);
                            currentTr.find("span[tag=brand]").html(productJson.brandName);
                            currentTr.find("input[tag=costPrice]").val(productJson.costPrice);
                        }
                    }
                })
            }).on("change","[tag=costPrice],[tag=number]",function () {//采购价格和数量的值改变事件
                //console.log(123);
//                console.log($(this).val());
                var currentTr=$(this).closest("tr");//向上找到当前行
                var costPrice=currentTr.find("[tag=costPrice]").val();
                var number=currentTr.find("[tag=number]").val();
                if(costPrice && number ){
                    var amount=costPrice*number; //金额小计
                    currentTr.find("[tag=amount]").html(amount.toFixed(2));
                }else{
                    currentTr.find("[tag=amount]").html(0);
                }
//                console.log(costPrice,number);
            }).on("click",".removeItem",function () {
                var currentTr=$(this).closest("tr");
                if($("#edit_table_body tr").size()>1){
                    currentTr.remove();
                }else{
                    currentTr.find("[tag=name],[tag=pid],[tag=costPrice],[tag=number],[tag=remark]").val("");
                    currentTr.find("[tag=brand],[tag=amount]").html("");
                }
            })

            //给添加明细绑定点击事件
            $(".appendRow").click(function () {
                //1 克隆tbody中的第一行
                var cloneTr=$("#edit_table_body tr:first").clone();
                //2 清空相关的数据
                cloneTr.find("[tag=name],[tag=pid],[tag=costPrice],[tag=number],[tag=remark]").val("");
                cloneTr.find("[tag=brand],[tag=amount]").html("");
                //3 追加到tbody中
                cloneTr.appendTo($("#edit_table_body"));

            });
            //表单提交事件
            $("#editForm").submit(function () {
                //1 重新设置每行tr的name属性
                $.each($("#edit_table_body tr"),function (index, item) {
                    $(item).find("[tag=pid]").prop("name","orderBill.items["+index+"].product.id");
                    $(item).find("[tag=costPrice]").prop("name","orderBill.items["+index+"].costPrice");
                    $(item).find("[tag=number]").prop("name","orderBill.items["+index+"].number");
                    $(item).find("[tag=remark]").prop("name","orderBill.items["+index+"].remark");
                })
//                return false;
            });
        })
    </script>
</head>
<body>
<%@include file="/WEB-INF/views/common/common_msg.jsp"%>
<s:form name="editForm" namespace="/" action="orderBill_saveOrUpdate" method="post" id="editForm" enctype="multipart/form-data">
    <s:hidden name="orderBill.id"/>
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">采购订单编辑</span>
            <div id="page_close">
                <a>
                    <img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">订单编号</td>
                    <td class="ui_text_lt">
                        <s:textfield name="orderBill.sn" cssClass="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">供应商</td>
                    <td class="ui_text_lt">
                        <s:select list="#suppliers" listKey="id" listValue="name"
                        name="orderBill.supplier.id" cssClass="ui_select03"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <s:date name="orderBill.vdate" format="yyyy-MM-dd" var="vdate"/>
                        <s:textfield name="orderBill.vdate" cssClass="ui_input_txt02 Wdate" onclick="WdatePicker();" value="%{vdate}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                               <s:if test="orderBill.id==null">
                                   <tr>
                                       <td></td>
                                       <td>
                                           <s:textfield disabled="true" readonly="true" cssClass="ui_input_txt04" tag="name"/>
                                           <img src="/images/common/search.png" class="searchproduct"/>
                                           <s:hidden name="orderBill.items[0].product.id" tag="pid"/>
                                       </td>
                                       <td><span tag="brand"></span></td>
                                       <td><s:textfield tag="costPrice" name="orderBill.items[0].costPrice"
                                                        cssClass="ui_input_txt04"/></td>
                                       <td><s:textfield tag="number" name="orderBill.items[0].number"
                                                        cssClass="ui_input_txt04"/></td>
                                       <td><span tag="amount"></span></td>
                                       <td><s:textfield tag="remark" name="orderBill.items[0].remark"
                                                        cssClass="ui_input_txt02"/></td>
                                       <td>
                                           <a href="javascript:;" class="removeItem">删除明细</a>
                                       </td>
                                   </tr>
                               </s:if>
                                <s:else>
                                        <s:iterator value="orderBill.items">
                                            <tr>
                                                <td></td>
                                                <td>
                                                    <s:textfield name="product.name" disabled="true" readonly="true" cssClass="ui_input_txt04" tag="name"/>
                                                    <img src="/images/common/search.png" class="searchproduct"/>
                                                    <s:hidden name="product.id" tag="pid"/>
                                                </td>
                                                <td><span tag="brand"><s:property value="product.brand.name"/></span></td>
                                                <td><s:textfield tag="costPrice" name="costPrice"
                                                                 cssClass="ui_input_txt04"/></td>
                                                <td><s:textfield tag="number" name="number"
                                                                 cssClass="ui_input_txt04"/></td>
                                                <td><span tag="amount"><s:property value="amount"/></span></td>
                                                <td><s:textfield tag="remark" name="remark"
                                                                 cssClass="ui_input_txt02"/></td>
                                                <td>
                                                    <a href="javascript:;" class="removeItem">删除明细</a>
                                                </td>
                                            </tr>
                                        </s:iterator>
                                </s:else>

                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="submit" value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</s:form>
</body>
</html>