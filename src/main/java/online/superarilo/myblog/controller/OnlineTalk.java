package online.superarilo.myblog.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import online.superarilo.myblog.service.OnlineTalkService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
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
@Aspect
@ServerEndpoint("/onlinetalk/{name}")
public class OnlineTalk {

    private static online.superarilo.myblog.service.OnlineTalkService onlineTalkService;
    private Session session;
    private String name;
    private static final ConcurrentHashMap<String, OnlineTalk> onlineUser = new ConcurrentHashMap<>();
    private final String gameSign = "minecraft";

    @Autowired
    public void onlineTalkServiceSet(OnlineTalkService onlineTalkService){
        OnlineTalk.onlineTalkService = onlineTalkService;
    }

    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name){
        this.session = session;
        this.name = name;
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
        JSONObject object = JSONObject.parseObject(message);
        String type = object.getString("type");
        switch (type){
            case "web":
                AppointSending(gameSign,message);
                GroupSending(message);
                break;
            case gameSign:
                object.putAll(onlineTalkService.getUserHeadAndName(object.getString("mcJavaId"),object.getString("mc_uuid")));
                GroupSending(object.toJSONString());
                break;
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
