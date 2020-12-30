<%--
  Created by IntelliJ IDEA.
  User: huruipeng
  Date: 2020/12/14
  Time: 2:58 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>

    <%@include file="common/head.jsp"%>

    <script>
        $(function () {
            $("#regForm").submit(function () {

                var phoneText=$("#phoneId").val();
                if (phoneText.length!=11){
                    alert("手机号不正确");
                    return false;
                }
                var pass1=$("#pass1").val();
                var pass2=$("#pass2").val();
                if (pass1!=pass2){
                    alert("两次密码不一致");
                    return false;
                }
                return true;
            });
        });
    </script>
</head>
<body>
    <div class="container">
    <form class="form-signin" action="/skill/regist" method="post" id="regForm">
        <h2 class="form-signin-heading">用户注册</h2>
        <label for="inputEmail" class="sr-only">手机号</label>
        <input id="phoneId" type="text" id="inputEmail" class="form-control" placeholder="手机号" required autofocus name="phone">
        <label for="inputPassword1" class="sr-only">用户密码</label>
        <input id="pass1" type="password" id="inputPassword1" class="form-control" placeholder="用户密码" required name="password">
        <label for="inputPassword2" class="sr-only">确认密码</label>
        <input id="pass2" type="password" id="inputPassword2" class="form-control" placeholder="确认密码" required name="password1">
        <button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
    </form>
    </div>
</body>
</html>
