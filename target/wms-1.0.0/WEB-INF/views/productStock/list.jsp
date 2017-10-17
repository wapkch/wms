<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src=" /js/jquery/jquery.js"></script>
    <script type="text/javascript" src=" /js/plugins/artDialog/jquery.artDialog.source.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>PSS-即时库存报表</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<%@include file="/WEB-INF/views/common/common_msg.jsp"%>
<s:form id="searchForm" namespace="/" action="productStock" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        货品:<s:textfield name="qo.keyword" cssClass="ui_input_txt02" placeholder="货品编码/货品名称"/>
                        仓库
                        <s:select list="#depots" listKey="id" listValue="name" headerKey="-1" headerValue="全部"
                                  cssClass="ui_select01" name="qo.depotId">
                        </s:select>
                        品牌
                        <s:select list="#brands" listKey="id" listValue="name" headerKey="-1" headerValue="全部"
                                  cssClass="ui_select01" name="qo.brandId">
                        </s:select>
                        阈值<s:textfield name="qo.limitNum" cssClass="ui_input_txt02" placeholder="库存最高值"/>
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
                        <th>商品编码</th>
                        <th>商品名称</th>
                        <th>仓库</th>
                        <th>品牌</th>
                        <th>库存价格</th>
                        <th>库存数量</th>
                        <th>库存总金额</th>
                    </tr>
                    <tbody>
                    <s:iterator value="#result.listData">
                        <tr>
                            <td><input type="checkbox" name="IDCheck" class="acb" data-oid="<s:property value="id"/>"/></td>
                            <td><s:property value="product.sn"/></td>
                            <td><s:property value="product.name"/></td>
                            <td><s:property value="depot.name"/></td>
                            <td><s:property value="product.brand.name"/></td>
                            <td><s:property value="price"/></td>
                            <td><s:property value="storeNumber"/></td>
                            <td><s:property value="amount"/></td>
                        </tr>
                    </s:iterator>
                    </tbody>
                </table>
            </div>
            <%@include file="/WEB-INF/views/common/common_page.jsp"%>
        </div>
    </div>
</s:form>
</body>
</html>
