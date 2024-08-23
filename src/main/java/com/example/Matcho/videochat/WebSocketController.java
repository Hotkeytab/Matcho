package com.example.Matcho.videochat;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class WebSocketController {

    @MessageMapping("/video-chat/join")
    @SendTo("/topic/room")
    public String joinRoom(String message) {
        // Handle joining the room (e.g., notifying others that a user joined)
        return HtmlUtils.htmlEscape(message) + " joined the room!";
    }

    @MessageMapping("/video-chat/leave")
    @SendTo("/topic/room")
    public String leaveRoom(String message) {
        // Handle leaving the room (e.g., notifying others that a user left)
        return HtmlUtils.htmlEscape(message) + " left the room!";
    }

    @MessageMapping("/video-chat/message")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        // Handle sending a chat message within the room
        return HtmlUtils.htmlEscape(message);
    }
}