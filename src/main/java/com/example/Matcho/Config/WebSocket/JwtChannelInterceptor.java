package com.example.Matcho.Config.WebSocket;

import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import com.example.Matcho.Utils.JwtUtil;


@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    public JwtChannelInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // Retrieve the "Authorization" header from the native headers
        List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");
        String jwt = (authorizationHeaders != null && !authorizationHeaders.isEmpty()) ? authorizationHeaders.get(0) : null;

        if (jwt != null && jwt.startsWith("Bearer ")) {
            String token = jwt.substring(7); // Remove "Bearer " prefix
            String username = jwtUtil.extractUsername(token);

            if (username != null && jwtUtil.validateToken(token)) {
                // Set the authentication in the context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        return message;
    }
}