package com.example.Matcho;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Matcho.User.model.User;
import com.example.Matcho.User.repository.UserRepository;
import com.example.Matcho.videochat.model.VideoRoom;
import com.example.Matcho.videochat.repository.VideoRoomRepository;
import com.example.Matcho.videochat.service.VideoChatService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class VideoChatServiceTest {

    @Mock
    private VideoRoomRepository videoRoomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VideoChatService videoChatService;

    private VideoRoom videoRoom;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize VideoRoom
        videoRoom = new VideoRoom();
        videoRoom.setRoomName("Test Room");
        
        // Initialize User
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        // Add user to participants
        Set<User> participants = new HashSet<>();
        participants.add(user);
        videoRoom.setParticipants(participants);  // Set participants including the user
    }

    @Test
    void testCreateRoom() {
        when(videoRoomRepository.save(any(VideoRoom.class))).thenReturn(videoRoom);

        VideoRoom createdRoom = videoChatService.createRoom(videoRoom);

        assertNotNull(createdRoom);
        assertEquals("Test Room", createdRoom.getRoomName());
        verify(videoRoomRepository, times(1)).save(videoRoom);
    }

    @Test
    void testGetRoomByName() {
        when(videoRoomRepository.findByRoomName("Test Room")).thenReturn(Optional.of(videoRoom));

        VideoRoom foundRoom = videoChatService.getRoomByName("Test Room");

        assertNotNull(foundRoom);
        assertEquals("Test Room", foundRoom.getRoomName());
        verify(videoRoomRepository, times(1)).findByRoomName("Test Room");
    }

    @Test
    void testAddUserToRoom() {
        when(videoRoomRepository.findByRoomName("Test Room")).thenReturn(Optional.of(videoRoom));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(videoRoomRepository.save(any(VideoRoom.class))).thenReturn(videoRoom);  // Mock the save operation

        VideoRoom updatedRoom = videoChatService.addUserToRoom("Test Room", 1L);

        assertNotNull(updatedRoom);
        assertTrue(updatedRoom.getParticipants().contains(user));
        verify(videoRoomRepository, times(1)).findByRoomName("Test Room");
        verify(userRepository, times(1)).findById(1L);
        verify(videoRoomRepository, times(1)).save(videoRoom);
    }


    @Test
    void testRemoveUserFromRoom() {
        // Setup participants with the user
        Set<User> participants = new HashSet<>();
        participants.add(user);
        videoRoom.setParticipants(participants);

        // Ensure the video room is returned correctly by the repository
        when(videoRoomRepository.findByRoomName("Test Room")).thenReturn(Optional.of(videoRoom));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(videoRoomRepository.save(any(VideoRoom.class))).thenReturn(videoRoom);  // Mock the save operation

        // Call the method under test
        VideoRoom updatedRoom = videoChatService.removeUserFromRoom("Test Room", 1L);

        // Assertions
        assertNotNull(updatedRoom);  // Ensure that updatedRoom is not null
        assertFalse(updatedRoom.getParticipants().contains(user));  // Ensure the user is removed from participants

        // Verify interactions with the mock objects
        verify(videoRoomRepository, times(1)).findByRoomName("Test Room");
        verify(userRepository, times(1)).findById(1L);
        verify(videoRoomRepository, times(1)).save(videoRoom);
    }
    @Test
    void testDeleteRoom() {
        when(videoRoomRepository.findByRoomName("Test Room")).thenReturn(Optional.of(videoRoom));

        videoChatService.deleteRoom("Test Room");

        verify(videoRoomRepository, times(1)).findByRoomName("Test Room");
        verify(videoRoomRepository, times(1)).delete(videoRoom);
    }

    @Test
    void testGetParticipantsInRoom() {
        when(videoRoomRepository.findByRoomName("Test Room")).thenReturn(Optional.of(videoRoom));

        Set<User> participants = videoChatService.getParticipantsInRoom("Test Room");

        assertNotNull(participants);
        verify(videoRoomRepository, times(1)).findByRoomName("Test Room");
    }
}