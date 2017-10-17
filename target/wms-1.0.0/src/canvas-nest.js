/**
 * Copyright (c) 2016 hustcc
 * License: MIT
 * Version: %%GULP_INJECT_VERSION%%
 * GitHub: https://github.com/hustcc/canvas-nest.js
 **/
! function() {




//获取节点的属性的值
  //节点 属性 默认值
  function get_attribute(node, attr, default_value) {
    return node.getAttribute(attr) || default_value;
  }



  //封装获取标签的方法
  function get_by_tagname(name) {
    return document.getElementsByTagName(name);
  }


//封装配置
  //获取页面中的script标签 获取其中的配置参数
  //如果有属性就取其参数 如果没有用默认值
  function get_config_option() {
    //获取页面中的script
    var scripts = get_by_tagname("script"),
        script_len = scripts.length,
        script = scripts[script_len - 1]; //当前加载的script
    return {
      l: script_len, //长度，用于生成id用
      z: get_attribute(script, "zIndex", -1), //z-index
      o: get_attribute(script, "opacity", 0.5), //opacity透明度
      c: get_attribute(script, "color", "0,0,0"), //color颜色RPG
      n: get_attribute(script, "count", 99) //count数量
    };
  }




  //设置初始化canvas的高宽为窗口的大小   定义图形
  function set_canvas_size() {
    //获取显示窗口宽度
    canvas_width = the_canvas.width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
        canvas_height = the_canvas.height = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
  }











//循环绘制绘制图形的方法==========================================
  //绘制过程
  function draw_canvas() {
    //清空页面   绘制一个窗口那么大的画板
    context.clearRect(0, 0, canvas_width, canvas_height);
    //随机的线条和当前位置联合数组
    var e, i, d, x_dist, y_dist, dist; //临时节点

    //遍历处理每一个点==================
    random_points.forEach(function(r, idx) {
      //每个点的移动
          r.x += r.xa,
          r.y += r.ya, //移动
          //宽的坐标是否超出边界
          r.xa *= r.x > canvas_width || r.x < 0 ? -1 : 1,
          //长的坐标是否超出边界
          r.ya *= r.y > canvas_height || r.y < 0 ? -1 : 1, //碰到边界，反向反弹
          context.fillRect(r.x - 0.5, r.y - 0.5, 1, 1); //在绘制一个宽高为1的像素

      //从下一个点开始
      for (i = idx + 1; i < all_array.length; i++) {
        e = all_array[i];
        // 如果当前点存在于位置   !==不进行类型转换
        if (null !== e.x && null !== e.y) {
          //范围
          x_dist = r.x - e.x; //x轴距离 l
          y_dist = r.y - e.y; //y轴距离 n
          dist = x_dist * x_dist + y_dist * y_dist; //总距离, m

          //设定运动方式  //靠近鼠标影响范围的时候加速
          //如果点在鼠标的影响范围
          dist < e.max && (e === current_point && dist >= e.max / 2 && (r.x -= 0.03 * x_dist, r.y -= 0.03 * y_dist),
              //两个点越近线越粗
              d = (e.max - dist) / e.max,
              //定义连接线
              context.beginPath(),
              //定义线的宽度
              context.lineWidth = d / 2,
              //定义渐变效果    越近越明显
              context.strokeStyle = "rgba(" + config.c + "," + (d + 0.2) + ")",
              //定义点移动
              context.moveTo(r.x, r.y),
              //定义连接两个点的线  连到e
              context.lineTo(e.x, e.y),
              //stroke() 方法会实际地绘制出通过 moveTo() 和 lineTo() 方法定义的路径
              context.stroke());
        }
      }
    }),
        //frame_func方法在多少毫秒之后调用自身绘制图形的方法
        frame_func(draw_canvas);
  }













//创建预设对象
  //创建画布标签 设置id
  var the_canvas = document.createElement("canvas"), //画布
      config = get_config_option(), //获取配置集合
      canvas_id = "c_n" + config.l, //创建canvas id

//画布创建一个二维的绘图面板   长宽  还有面板中的执行的动画方式
      context = the_canvas.getContext("2d"),
      canvas_width,
      canvas_height,
      frame_func = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame
          //多少壕秒执行一次
          || function(func) {window.setTimeout(func, 1000 / 60);},

      //随机数
      random = Math.random,
      //创建一个鼠标坐标对象
      current_point = {
        x: null, //当前鼠标x
        y: null, //当前鼠标y
        max: 20000 // 影响圈半径的平方
      },
      //判断鼠标所在的位置是否处于随机的位置中
      all_array;


  //设置id
  the_canvas.id = canvas_id;
  //cssText批量设置当前面板的style 定位 面板所处图层深度 透明度
  the_canvas.style.cssText = "position:fixed;top:0;left:0;z-index:" + config.z + ";opacity:" + config.o;
  //往窗口中 添加画布
  get_by_tagname("body")[0].appendChild(the_canvas);

  //初始化画布大小
  set_canvas_size();
  //事件会在窗口或框架被调整大小时发生
  //重置画布大小
  window.onresize = set_canvas_size;
  //事件会在鼠标指针移动时发生，   释放当前位置信息
  window.onmousemove = function(e) {
    e = e || window.event;
    //获取当前鼠标位置
    current_point.x = e.clientX;
    current_point.y = e.clientY;
  },
      //当鼠标指针移动到窗口之外时执行  清空鼠标坐标
      window.onmouseout = function() {
        //
        current_point.x = null;
        current_point.y = null;
      };

  //随机生成config.n 点位置信息
  //创建随机的点集合    config.n数量
  for (var random_points = [], i = 0; config.n > i; i++) {
    var x = random() * canvas_width, //随机位置
        y = random() * canvas_height,
        xa = 2 * random() - 1, //随机运动方向
        ya = 2 * random() - 1;
    // 添加一个随机点
    random_points.push({
      //坐标
      x: x,
      y: y,
      //移动的距离
      xa: xa,
      ya: ya,
      //影响范围
      max: 6000 //沾附距离
    });
  }






//当前鼠标位置是否存在于随机的位置内
  all_array = random_points.concat([current_point]);









  //0.1秒后开始绘制
  setTimeout(function() {
    frame_func(draw_canvas);
  }, 100);
}();
