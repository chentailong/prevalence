package com.lon.config;

import com.lon.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @author ctl
 */
@Component
@ServerEndpoint(value = "/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private HttpSession httpSession;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    /**
     * 新建webSocket配置类
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        log.info("【新建连接】，连接总数:{}", webSockets.size());
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose(){
        webSockets.remove(this);
        log.info("【断开连接】，连接总数:{}", webSockets.size());
    }

    /**
     * 接收到信息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        log.info("【收到】，客户端的信息:{}，连接总数:{}", message, webSockets.size());
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message){
        log.info("【广播发送】，信息:{}，总连接数:{}", message, webSockets.size());
        for (WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.info("【广播发送】，信息异常:{}", e.fillInStackTrace());
            }
        }
    }
}
