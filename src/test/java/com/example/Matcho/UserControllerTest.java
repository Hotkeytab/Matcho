package com.example.Matcho;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.Matcho.User.UserController;
import com.example.Matcho.User.model.User;
import com.example.Matcho.User.repository.RoleRepository;
import com.example.Matcho.User.repository.UserRepository;
import com.example.Matcho.User.service.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.Matcho.User.UserController;
import com.example.Matcho.User.model.Role;
import com.example.Matcho.User.model.User;
import com.example.Matcho.User.service.UserService;
import com.example.Matcho.Profile.model.Profile;
import com.example.Matcho.Profile.repository.ProfileRepository;
import com.example.Matcho.Profile.service.ProfileService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private User user;
    private Role role;
    private Profile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

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
    void testCreateUser() throws Exception {
    	   when(roleRepository.findByName("USER")).thenReturn(role); 
    	   when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"testuser\", \"password\": \"password\", \"email\": \"testuser@example.com\", \"phoneNumber\": \"1234567890\", \"gender\": \"male\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).saveUser(any(User.class));
        verify(profileRepository, times(1)).save(any(Profile.class));
        verify(roleRepository, times(1)).findByName("USER");
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(user, new User());
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserByUsername() throws Exception {
        when(userService.getUserByUsername("testuser")).thenReturn(user);

        mockMvc.perform(get("/api/users/username/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(userService, times(1)).getUserByUsername("testuser");
    }

    @Test
    void testGetUserByEmail() throws Exception {
        when(userService.getUserByEmail("testuser@example.com")).thenReturn(user);

        mockMvc.perform(get("/api/users/email/testuser@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testuser@example.com"));

        verify(userService, times(1)).getUserByEmail("testuser@example.com");
    }

    @Test
    void testGetUserByPhoneNumber() throws Exception {
        when(userService.getUserByPhoneNumber("1234567890")).thenReturn(user);

        mockMvc.perform(get("/api/users/phone/1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));

        verify(userService, times(1)).getUserByPhoneNumber("1234567890");
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}
	