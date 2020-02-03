package com.dimitar.dtwebfluxwebsocketexample;

import com.dimitar.dtwebfluxwebsocketexample.handler.EchoHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;


import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DtWebfluxWebsocketExampleApplication {

    public static final String ECHO_PATH = "/echo";

    public static void main(String[] args) {
        SpringApplication.run(DtWebfluxWebsocketExampleApplication.class, args);
    }

    @Bean
    public EchoHandler echoHandler() {
        return new EchoHandler();
    }

    @Bean
    public HandlerMapping handlerMapping() {
        final Map<String, WebSocketHandler> map = new HashMap<>();
        map.put(DtWebfluxWebsocketExampleApplication.ECHO_PATH, echoHandler());

        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setUrlMap(map);
        simpleUrlHandlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return simpleUrlHandlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    public WebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }

}
