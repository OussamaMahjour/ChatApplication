package org.master.chatservice.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import javax.sql.ConnectionEvent;


@Configuration
public class ConnectionListener implements ApplicationListener<SessionConnectedEvent> {
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
            System.out.println("Connection established from " + event.getMessage());
    }
}
