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
@ServerEndpoint("/onlinetalk/{name}")
public class OnlineTalk {

    private Session session;
    private String name;
    private static final ConcurrentHashMap<String, OnlineTalk> onlineUser = new ConcurrentHashMap<>();
    private final String gameSign = "minecraft";

    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name){
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        onlineUser.put(name,this);
        log.info("连接成功，当前连接人数为：= {}",onlineUser.size());
    }


    @OnClose
    public void OnClose(){
        onlineUser.remove(this.name);
        log.info("退出成功，当前连接人数为：= {}",onlineUser.size());
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
        for (String name : onlineUser.keySet()){
            if(!name.equals(gameSign)) {
                onlineUser.get(name).session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 指定发送
     */
    @SneakyThrows
    public void AppointSending(String name, String message){
        onlineUser.get(name).session.getBasicRemote().sendText(message);
    }
}
