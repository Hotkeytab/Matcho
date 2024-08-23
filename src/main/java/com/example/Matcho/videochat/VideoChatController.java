package com.example.Matcho.videochat;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Matcho.videochat.model.VideoRoom;
import com.example.Matcho.videochat.service.VideoChatService;

import java.util.List;

@RestController
@RequestMapping("/api/video-chat")
public class VideoChatController {

    private final VideoChatService videoChatService;

    public VideoChatController(VideoChatService videoChatService) {
        this.videoChatService = videoChatService;
    }

    @PostMapping("/rooms")
    public ResponseEntity<VideoRoom> createRoom(@RequestBody VideoRoom videoRoom) {
        VideoRoom createdRoom = videoChatService.createRoom(videoRoom);
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<VideoRoom>> getAllRooms() {
        List<VideoRoom> rooms = videoChatService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/{roomName}")
    public ResponseEntity<VideoRoom> getRoomByName(@PathVariable String roomName) {
        VideoRoom room = videoChatService.getRoomByName(roomName);
        return room != null ? ResponseEntity.ok(room) : ResponseEntity.notFound().build();
    }

    @PostMapping("/rooms/{roomName}/join")
    public ResponseEntity<VideoRoom> joinRoom(@PathVariable String roomName, @RequestParam Long userId) {
        VideoRoom updatedRoom = videoChatService.addUserToRoom(roomName, userId);
        return ResponseEntity.ok(updatedRoom);
    }

    @PostMapping("/rooms/{roomName}/leave")
    public ResponseEntity<VideoRoom> leaveRoom(@PathVariable String roomName, @RequestParam Long userId) {
        VideoRoom updatedRoom = videoChatService.removeUserFromRoom(roomName, userId);
        return ResponseEntity.ok(updatedRoom);
    }
}
