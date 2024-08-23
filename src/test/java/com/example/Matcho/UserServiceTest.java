package com.example.Matcho;


import static org.mockito.Mockito.*;


import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Matcho.Profile.model.Profile;
import com.example.Matcho.Profile.repository.ProfileRepository;
import com.example.Matcho.User.model.Role;
import com.example.Matcho.User.model.User;
import com.example.Matcho.User.repository.RoleRepository;
import com.example.Matcho.User.repository.UserRepository;
import com.example.Matcho.User.service.UserService;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private Role role;
    private Profile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = new Role();
        role.setId(1L);
        role.setName("USER");
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());

        profile = new Profile();
        profile.setId(1L);
        profile.setBio("New user profile");
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("1234567890");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(role);
        user.setProfile(profile);
    }

    @Test
    void testSaveUser() {
        when(roleRepository.findByName("USER")).thenReturn(role);
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(role, savedUser.getRole());
        assertEquals(profile, savedUser.getProfile());
        verify(userRepository, times(1)).save(user);
        verify(profileRepository, times(1)).save(profile);
        verify(roleRepository, times(1)).findByName("USER");
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        User foundUser = userService.getUserByUsername("testuser");

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(user);

        User foundUser = userService.getUserByEmail("testuser@example.com");

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void testGetUserByPhoneNumber() {
        when(userRepository.findByPhoneNumber("1234567890")).thenReturn(user);

        User foundUser = userService.getUserByPhoneNumber("1234567890");

        assertNotNull(foundUser);
        assertEquals(user.getPhoneNumber(), foundUser.getPhoneNumber());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}