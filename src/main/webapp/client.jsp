<%--
  Created by IntelliJ IDEA.
  User: tan.jinming
  Date: 2018/6/28
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Java 后端 WebSocket 的 Tomcat 实现</title>
</head>
<body>
        Welcome<br/>
        <input id="text" type="text"/>
        <button onclick="send()">发送消息</button>
        <hr/>
        <button onclick="closeWebSocket()">关闭WebSocket连接</button>
         <hr/>
        <div id="message"></div>
</body>
<script type="text/javascript">
    var websocket = null;
    // 判断当前浏览器是否支持 WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8002/websocket");
    } else {
        alert('当前浏览器不支持 WebSocket');
    }

    // 连接发生错误回调方法
    websocket.onerror = function() {
        setMessageInnerHTML("WebSocket 连接发生错误");
    }

    // 连接建立成功的回调方法
    websocket.onopen = function() {
        setMessageInnerHTML("WebSocket 连接成功")
    }

    // 接收消息的回调方法
    websocket.onmessage = function(event) {
        setMessageInnerHTML(event.data);
    }

    // 连接关闭的回调方法
    websocket.onclose = function() {
        setMessageInnerHTML("WebSocket 关闭");
    }

    // 监听窗口事件，当关闭窗口时，主动去关闭 websocket 连接，放置连接还没端口就关闭窗口
    window.onbeforeunload = function() {
        setMessageInnerHTML("WebSocket 连接关闭");
    }

    // 将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    // 关闭 WebSocket 连接
    function closeWebSocket() {
        websocket.close();
    }

    // 发送消息
    function send() {
        var message = document.getElementById('text');
        var msg = message.value;
        websocket.send(msg);
    }
</script>
</html>
