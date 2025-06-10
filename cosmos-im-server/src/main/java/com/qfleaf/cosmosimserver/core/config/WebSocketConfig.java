package com.qfleaf.cosmosimserver.core.config;

import com.qfleaf.cosmosimserver.chat.interfaces.ws.ChatEndpoint;
import com.qfleaf.cosmosimserver.contact.interfaces.ws.ContactNotificationEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        ServerEndpointExporter exporter = new ServerEndpointExporter();

        // 手动注册 WebSocket 端点
//        exporter.setAnnotatedEndpointClasses(EchoChannel.class);
        exporter.setAnnotatedEndpointClasses(
                ContactNotificationEndpoint.class,
                ChatEndpoint.class
        );

        return exporter;
    }
}
