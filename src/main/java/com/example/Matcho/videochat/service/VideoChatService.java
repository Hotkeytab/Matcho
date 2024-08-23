package com.example.Matcho.videochat.service;

import com.example.Matcho.User.model.User;
import com.example.Matcho.User.repository.UserRepository;
import com.example.Matcho.videochat.model.VideoRoom;
import com.example.Matcho.videochat.repository.VideoRoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VideoChatService {

    private final VideoRoomRepository videoRoomRepository;
    private final UserRepository userRepository;

    public VideoChatService(VideoRoomRepository videoRoomRepository, UserRepository userRepository) {
        this.videoRoomRepository = videoRoomRepository;
        this.userRepository = userRepository;
    }

    // Create a new video chat room
    public VideoRoom createRoom(VideoRoom videoRoom) {
        videoRoom.setCreatedAt(LocalDateTime.now());
        return videoRoomRepository.save(videoRoom);
    }

    // Get a video chat room by its name
    public VideoRoom getRoomByName(String roomName) {
        return videoRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    // Get all video chat rooms
    public List<VideoRoom> getAllRooms() {
        return videoRoomRepository.findAll();
    }

    // Add a user to the video chat room by room name
    public VideoRoom addUserToRoom(String roomName, Long userId) {
        VideoRoom videoRoom = getRoomByName(roomName);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        videoRoom.getParticipants().add(user);
        return videoRoomRepository.save(videoRoom);
    }

    // Remove a user from the video chat room by room name
    public VideoRoom removeUserFromRoom(String roomName, Long userId) {
        VideoRoom videoRoom = getRoomByName(roomName);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        videoRoom.getParticipants().remove(user);
        return videoRoomRepository.save(videoRoom);
    }

    // Delete a video chat room by room name
    public void deleteRoom(String roomName) {
        VideoRoom videoRoom = getRoomByName(roomName);
        videoRoomRepository.delete(videoRoom);
    }

    // Get all participants in a room by room name
    public Set<User> getParticipantsInRoom(String roomName) {
        VideoRoom videoRoom = getRoomByName(roomName);
        return videoRoom.getParticipants();
    }
}