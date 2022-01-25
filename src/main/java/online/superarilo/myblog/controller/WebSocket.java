package online.superarilo.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
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

    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static final ConcurrentHashMap<String,WebSocket> webSocketSet = new ConcurrentHashMap<>();
    private final String gameSign = "minecraft";

    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name){
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name,this);
        log.info("连接成功，当前连接人数为：= {}",webSocketSet.size());
    }


    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.name);
        log.info("退出成功，当前连接人数为：= {}",webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message){
        log.info("收到消息：{} ",message);
        Boolean fromGame = (Boolean) JSONObject.parseObject(message).get("fromGame");
        if(fromGame != null){
            if(!fromGame){
                AppointSending(gameSign,message);
            }
            GroupSending(message);
        }
    }

    /**
     * 群发
     */
    @SneakyThrows
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            if(!name.equals(gameSign)) {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 指定发送
     */
    @SneakyThrows
    public void AppointSending(String name, String message){
        webSocketSet.get(name).session.getBasicRemote().sendText(message);
    }
}
