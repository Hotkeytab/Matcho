package com.example.Matcho.User.service;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Matcho.Profile.model.Profile;
import com.example.Matcho.Profile.repository.ProfileRepository;
import com.example.Matcho.User.model.Role;
import com.example.Matcho.User.model.User;
import com.example.Matcho.User.repository.RoleRepository;
import com.example.Matcho.User.repository.UserRepository;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.profileRepository = profileRepository;
    }

    public User saveUser(User user) {
        // Ensure role is set (if not already)
        if (user.getRole() == null) {
            Role defaultRole = roleRepository.findByName("USER");
            user.setRole(defaultRole);
        }

        // Create and assign a new profile if not already set
        
        
        if (user.getProfile() == null ) {
            Profile profile = new Profile();
            profile.setBio("New user profile");
            profile.setCreatedAt(LocalDateTime.now());
            profile.setUpdatedAt(LocalDateTime.now());
            profile.setUser(user); // Set the user reference in the profile
            user.setProfile(profile); // Set the profile reference in the user
        }

        // Save the user (this will also save the profile because of CascadeType.ALL)
        return userRepository.save(user);
    }
    public User updateUser(Long id, User userUpdates) {
        // Fetch the existing user from the database
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        // Update only the fields that are provided
        if (userUpdates.getUsername() != null && !userUpdates.getUsername().isEmpty()) {
            existingUser.setUsername(userUpdates.getUsername());
        }

        if (userUpdates.getPassword() != null && !userUpdates.getPassword().isEmpty()) {
            existingUser.setPassword(new BCryptPasswordEncoder().encode(userUpdates.getPassword()));
        }

        if (userUpdates.getEmail() != null && !userUpdates.getEmail().isEmpty()) {
            existingUser.setEmail(userUpdates.getEmail());
        }

        if (userUpdates.getGender() != null && !userUpdates.getGender().isEmpty()) {
            existingUser.setGender(userUpdates.getGender());
        }

        if (userUpdates.getFirstName() != null && !userUpdates.getFirstName().isEmpty()) {
            existingUser.setFirstName(userUpdates.getFirstName());
        }

        if (userUpdates.getLastName() != null && !userUpdates.getLastName().isEmpty()) {
            existingUser.setLastName(userUpdates.getLastName());
        }

        if (userUpdates.getPhoneNumber() != null && !userUpdates.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(userUpdates.getPhoneNumber());
        }

        if (userUpdates.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(userUpdates.getDateOfBirth());
        }

        if (userUpdates.getStatus() != null && !userUpdates.getStatus().isEmpty()) {
            existingUser.setStatus(userUpdates.getStatus());
        }

        if (userUpdates.getRole() != null && userUpdates.getRole().getId() != null) {
            Role role = roleRepository.findById(userUpdates.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            existingUser.setRole(role);
        }

        if (userUpdates.getProfile() != null && userUpdates.getProfile().getLocation() != null
                && !userUpdates.getProfile().getLocation().isEmpty()) {
            if (existingUser.getProfile() == null) {
                existingUser.setProfile(new Profile());
            }
            existingUser.getProfile().setLocation(userUpdates.getProfile().getLocation());
        }

        // Set the updated timestamp
        existingUser.setUpdatedAt(LocalDateTime.now());

        // Save the updated user to the database
        return userRepository.save(existingUser);
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public User addFriend(Long userId, Long friendId) {
        // Fetch the user and friend from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found with id " + friendId));

        // Add the friend to the user's friends list
        user.getFriends().add(friend);

        // Save the updated user to the database
        return userRepository.save(user);
    }
    
    public User removeFriend(Long userId, Long friendId) {
        // Fetch the user and friend from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new RuntimeException("Friend not found with id " + friendId));

        // Remove the friend from the user's friends list
        user.getFriends().remove(friend);

        // Save the updated user to the database
        return userRepository.save(user);
    }

}