package com.dimitar.dtwebfluxwebsocketexample.handler;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * Simple handler that returns received text and text length.
 */
public class EchoHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(
                webSocketSession
                        .receive()
                        .map(
                                receivedMessage -> {
                                    final String receivedTxt = receivedMessage.getPayloadAsText();
                                    return String.format("Client message: %s, size: %d", receivedTxt, receivedTxt.length());
                                }
                        )
                        .log()
                        .map(webSocketSession::textMessage)
        );
    }
}
