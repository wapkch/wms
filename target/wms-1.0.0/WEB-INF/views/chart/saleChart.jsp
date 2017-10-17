<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src=" /js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src=" /js/plugins/artDialog/jquery.artDialog.source.js?skin=blue"></script>
    <script type="text/javascript" src=" /js/plugins/artDialog/iframeTools.source.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript" >
        $(function () {
            $("[name='sqo.beginDate']").click(function () {
                WdatePicker({
                    maxDate: $("[name='sqo.endDate']").val() || new Date()
                });
            });
            $("[name='sqo.endDate']").click(function () {
                WdatePicker({
                    minDate: $("[name='sqo.beginDate']").val(),
                    maxDate: new Date()
                });
            });
            //图表值改变事件
            $("#chartSelect").change(function () {
                var url="";
                if(this.value=='line'){
                    url= "/chart_saleChartByLine?"+$("#searchForm").serialize();
                }else if(this.value=='pie'){
                    url= "/chart_saleChartByPie?"+$("#searchForm").serialize();
                }
                $.dialog.open(url,{
                    id:"saleChartByLine",
                    title:"销售报表",
                    width:650,
                    height:450
                })

            })
        })
    </script>
    <title>PSS-销售报表</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/views/common/common_msg.jsp"%>
<s:form id="searchForm" namespace="/" action="chart_saleChart" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        <s:date name="sqo.beginDate"   format="yyyy-MM-dd" var="beginDate"/>
                        业务时间:<s:textfield name="sqo.beginDate" value="%{beginDate}" cssClass="ui_input_txt02 Wdate" />
                        <s:date name="sqo.endDate"  format="yyyy-MM-dd" var="endDate"/>
                        ~<s:textfield name="sqo.endDate"  value="%{endDate}" cssClass="ui_input_txt02 Wdate"/>
                        货品: <s:textfield name="sqo.keyword" cssClass="ui_input_txt02"/>
                        客户
                        <s:select list="#clients" listKey="id" listValue="name" headerKey="-1" headerValue="全部"
                                  cssClass="ui_select01" name="sqo.clientId">
                        </s:select>
                        品牌
                        <s:select list="#brands" listKey="id" listValue="name" headerKey="-1" headerValue="全部"
                                  cssClass="ui_select01" name="sqo.brandId">
                        </s:select>
                        图表:<s:select list="#{'line':'线性图','pie':'饼状图'}"  cssClass="ui_select01" id="chartSelect"></s:select>
                        分组
                        <s:select list='#{  "saleman.name":"销售人员",
                                            "p.name":"货品名称",
                                            "c.name":"客户",
                                            "b.name":"货品品牌",
                                            "date_format(sc.vdate,\'%Y-%m\')":"销售日期(月)",
                                            "date_format(sc.vdate,\'%Y-%m-%d\')":"销售日期(日)"}'
                                  cssClass="ui_select01" name="sqo.groupType">
                        </s:select>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>分组类型</th>
                        <th>销售总数量</th>
                        <th>销售总金额</th>
                        <th>毛利润</th>
                    </tr>
                    <tbody>
                    <s:iterator value="#mapList">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb" data-oid="<s:property value="id"/>"/></td>
                            <td><s:property value="groupType"/></td>
                            <td><s:property value="totalNumber"/></td>
                            <td><s:property value="totalAmount"/></td>
                            <td><s:property value="grossProfit"/></td>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</s:form>
</body>
</html>
