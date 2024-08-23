package com.example.Matcho.videochat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Matcho.videochat.model.VideoRoom;

import java.util.Optional;

@Repository
public interface VideoRoomRepository extends JpaRepository<VideoRoom, Long> {
    
    // Find a VideoRoom by its roomName
    Optional<VideoRoom> findByRoomName(String roomName);
}