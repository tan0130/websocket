<%--
  Created by IntelliJ IDEA.
  User: tan.jinming
  Date: 2018/6/28
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy.js"></script>

<script type="text/javascript">
    var goEasy = new GoEasy({appkey: 'BC-fbfd34d19b4646ba89bd412cdf589fa1'});
    goEasy.subscribe({
        channel: 'your_channel',
        onMessage: function(message){
            alert('接收到消息:'+ message.content);// 拿到了信息之后，你可以做你任何想做的事
        }
    });
    goEasy.publish('your_channel', 'First message');
</script>
</html>
