<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>秒杀列表</title>
    <%--    把公用的bootstrap内容放到head中--%>
        <%@include file="common/head.jsp"%>
    </head>
    <body>
    <div class="container">
        <div class="panel panel-danger">
            <!-- Default panel contents -->
            <div class="panel-heading">秒杀列表</div>

            <!-- Table -->
            <table class="table">
                <thead>
                <tr>
                    <td>名称</td>
                    <td>价格</td>
                    <td>库存</td>
                    <td>开始时间</td>
                    <td>结束时间</td>
                    <td>创建时间</td>
                    <td>商品详情</td>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${requestScope.list}">
                     <tr>
                        <td>${item.name}</td>
                        <td>${item.price}</td>
                        <td>${item.number}</td>
                        <td>${item.startTime}</td>
                        <td>${item.endTime}</td>
                        <td>${item.createTime}</td>
                        <td><a href="/skill/detail/${item.id}">进入秒杀</a></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    </body>
</html>
