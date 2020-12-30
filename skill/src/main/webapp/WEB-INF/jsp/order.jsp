<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: huruipeng
  Date: 2020/12/14
  Time: 2:46 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>商品列表</title>
        <%@include file="common/head.jsp"%>
        <style type="text/css">
            * {
                margin: 0;
                padding: 0;
            }

            #time{
                background-color: coral;
                line-height: 120px;
                margin: auto auto;
                text-align: center;
            }

            .zz{
                color: cornflowerblue;
                border-radius: 5px;
                border: 1px solid cornflowerblue;
                font-size: 45px;
                background-color: antiquewhite;
            }

            .st{
                font-size: 18px;
                color: aliceblue;
            }

        </style>
        <script type="application/javascript">

            function countDown(htmlId,val) {
                if (val<10){
                    val="0"+val;
                }
                document.getElementById(htmlId).innerHTML=val;
            }

            function countDownComp(temp) {
                var m,s=0;
                m=Math.floor(temp/1000/60%60);
                s=Math.floor(temp/1000%60);

                // console.log("aaa");
                countDown("skill_min",m);
                countDown("skill_sec",s);


            }

            <%--var orderTime="${orderTime}";--%>
            // var orderExpire=5*60*1000+orderTime;
            var temp=5*60*1000;
            var intervalID=window.setInterval(function () {
                // temp=orderExpire-new Date().getTime();
                    if (temp<0){
                        clearInterval(intervalID);
                        $('#st').text("支付时间超时");
                        $('#payButton').removeClass("btn-success");
                        $('#payButton').addClass("disabled");
                        $('#payButton').removeClass("btn-default");
                        return;
                    }
                   countDownComp(temp);
                   temp=temp-1000;
                },1000);
        </script>
    </head>
    <body>
    <div class="container">
        <div class="panel panel-danger">
            <!-- Default panel contents -->
            <div class="panel-heading">${requestScope.item.name}</div>
            <div class="alert alert-success" role="alert">秒杀价格:${requestScope.item.price}</div>
            <div class="alert alert-success" role="alert">订单号:${requestScope.orderCode}</div>


            <h1 id="skillOver" class="text-center">付款链接已发至您的邮箱，您也可以选择在PC端扫码支付</h1>
            <div id="skillBox">
                <div id="time">
                    <span class="st">支付时间剩余</span>

                    <span id="skill_min" class="zz">00</span>
                    <span>分</span>
                    <span id="skill_sec" class="zz">00</span>
                    <span>秒</span>

                    <span id="st" class="st">请尽快支付</span>
                </div>
                <div id="img">
                    <img src="/imgs/pay.png" width="400">
                    <button id="payButton" class="btn btn-success" style="width: 150px"><a href="/imgs/123.jpeg">彩蛋</a></button>
                    <span>扫码支付完成点击左侧支付按钮有彩蛋</span>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
