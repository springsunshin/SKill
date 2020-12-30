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

            function skillFinish() {
                $('#skillButton').removeClass("btn-default");
                $('#skillButton').addClass("btn-warning");
                $('#skillButton').text("抢购结束");
                $('#skillOver').text("本场次秒杀活动已结束");
                return;
            }

            function countDown(htmlId,val) {
                if (val<10){
                    val="0"+val;
                }
                document.getElementById(htmlId).innerHTML=val;
            }

            function countDownComp(temp) {
                var d,h,m,s=0;
                d=Math.floor(temp/1000/60/60/24);
                h=Math.floor(temp/1000/60/60%24);
                m=Math.floor(temp/1000/60%60);
                s=Math.floor(temp/1000%60);

                // console.log("aaa");
                countDown("skill_day",d);
                countDown("skill_hour",h);
                countDown("skill_min",m);
                countDown("skill_sec",s);


            }

            var end="${item.endTime}";
            var skillEnd=new Date(end);
            // var serverTimes;
            function skillOverTime() {
                var interval=window.setInterval(function () {
                    var test=skillEnd.getTime()-serverTime;
                    if (test<=0){
                        clearInterval(interval);
                        skillFinish();
                        return;
                    }
                   countDownComp(test);

                    serverTime=serverTime+1000;
                },1000);
            }

            function StartSkill() {
                skillOverTime();
                // alert("开始秒杀");
                $('#skillOver').text("本场次秒杀活动正在进行中");
                var skillId="${requestScope.item.id}";
                $.post("/skill/getUrl/"+skillId,{},function (result) {
                    if (result&&result['success']){
                        var skillUrl=result['data'];
                        $('#skillButton').removeClass("btn-default");
                        $('#skillButton').removeClass("disabled");
                        $('#skillButton').addClass("btn-success");
                        if (skillUrl['enable']){
                            // alert(skillUrl['md5']);
                            //判断是否在秒杀期间
                            //给下单按钮添加一次点击事件
                            $('#skillButton').one('click',function () {
                                var url="/skill/excSkill/"+skillId+"/"+skillUrl['md5'];
                                $.post(url,{},function (result) {
                                    // alert(result['message']);
                                    if (result&&result['success']){
                                        // $.post("/skill/pay/"+skillId,{},function (result) {
                                        //
                                        // });
                                        var orderCode=result['data'];
                                        window.location.href="/skill/pay/"+skillId+"/"+orderCode;
                                    }
                                });
                                //设置下单按钮不可用
                                $(this).addClass('disable');
                            });
                        }else {

                        }
                    }else {
                        alert("请先登录");
                    }
                });
            }


            var startTime="${item.startTime}";
            var endTime="${item.endTime}";
            var skillStartDate=new Date(startTime);
            var skillEndDate=new Date(endTime);
            var serverTime;
            $.get("/skill/now",{},function (result) {
                serverTime=result['data'];
                var skillOver=serverTime-skillEndDate.getTime();
                if (skillOver>=0){
                    skillFinish();
                    return;
                }
                $('#skillOver').text("本场次秒杀活动即将开始");
                var intervalID=window.setInterval(function () {

                    var temp=skillStartDate.getTime() - serverTime;
                    if (temp<0){
                        //结束周期函数，开始秒杀，显示秒杀按钮。
                        clearInterval(intervalID);
                        $('#st').text("后结束抢购");
                        StartSkill();
                        return;
                    }
                   countDownComp(temp);
                    serverTime=serverTime+1000;
                },1000);
            });


        </script>
    </head>
    <body>
    <div class="container">
        <div class="panel panel-danger">
            <!-- Default panel contents -->
            <div class="panel-heading">${requestScope.item.name}</div>
            <div class="alert alert-success" role="alert">秒杀价格:${requestScope.item.price},商品数量:${requestScope.item.number}</div>

            <h1 id="skillOver" class="text-center"></h1>
            <div id="skillBox">
                <div id="time">
                    <span class="st">当前场次</span>

                    <span id="skill_day" class="zz">00</span>
                    <span>天</span>
                    <span id="skill_hour" class="zz">00</span>
                    <span>时</span>
                    <span id="skill_min" class="zz">00</span>
                    <span>分</span>
                    <span id="skill_sec" class="zz">00</span>
                    <span>秒</span>

                    <span id="st" class="st">后开始抢购</span>
                </div>
                <div id="img">
                    <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1608302118696&di=b3494efabc7dd258e8644ca777a50529&imgtype=0&src=http%3A%2F%2Fww4.sinaimg.cn%2Fmw690%2F006vctkBly1gjsp8rsmp8j30m80m8q44.jpg" width="400">
                    <button id="skillButton" class="btn btn-default disabled"  style="width: 150px">开始抢购</button>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>
