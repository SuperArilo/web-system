package online.superarilo.myblog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websockets/{name}")
public class WebSocket {

    private Session session;
    //标识当前连接客户端的用户名
    private String name;

    //用于存所有的连接服务的客户端，这个对象存储是安全的
    private static final ConcurrentHashMap<String,WebSocket> webSocketSet = new ConcurrentHashMap<>();


    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name){
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name,this);
        log.info("[WebSocket] 连接成功，当前连接人数为：= {}",webSocketSet.size());
    }


    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.name);
        log.info("[WebSocket] 退出成功，当前连接人数为：= {}",webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message){
        log.info("[WebSocket] 收到消息：{}",message);
        //判断是否需要指定发送，具体规则自定义
        AppointSending(name,"服务器收到了消息");

    }
    //群发
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //指定name 发送
    public void AppointSending(String name,String message){
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
