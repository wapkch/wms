<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src=" /js/jquery/jquery.js"></script>
    <script type="text/javascript" src=" /js/plugins/artDialog/jquery.artDialog.source.js?skin=green"></script>
    <!-- ECharts单文件引入 -->
    <script src="/js/plugins/echart/echarts-all.js"></script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts图表
            var myChart = echarts.init(document.getElementById('main'));
            var option = {
                tooltip: {
                    show: true
                },
                legend: {
                    data:['销量']
                },
                xAxis : [
                    {
                        type : 'category',
                        data : ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        "name":"销量",
                        "type":"bar",
                        "data":[5, 20, 40, 10, 10, 20]
                    }
                ]
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        })
    </script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="height:400px"></div>
</body>
</html>
