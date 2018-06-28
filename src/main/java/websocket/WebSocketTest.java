package websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * create by tan on 2018/6/26
 * WebSocket的tomcat实现
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 **/
@ServerEndpoint("/websocket")
public class WebSocketTest {
    // 静态变量，用来记录当前在线连接数
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketTest> webSocketTests = new CopyOnWriteArraySet<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session 表示与某个客户端的连接会话，需要通过它来向客户端发送信息
     * */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketTests.add(this);
        addOnlineCount();   // 在线数加 1
        System.out.println("有连接加入！当前在线人数为：" + getOnlineCount());
    }

    /**
     * 连接关闭调用方法
     * */
    @OnClose
    public void onClose() {
        webSocketTests.remove(this); // 从 set 中删除
        subOnlineCount(); // 在线人数减 1
        System.out.println("有一连接关闭！当前在线人数为：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session
     * */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息：" + message);
        // 群发消息
        for (WebSocketTest item: webSocketTests) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     * */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     * */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 获取在线人数
     * @return onlineCount 返回在线人数
     * */
    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线人数加 1
     * */
    private static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    /**
     * 在线人数减 1
     * */
    private static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }
}
