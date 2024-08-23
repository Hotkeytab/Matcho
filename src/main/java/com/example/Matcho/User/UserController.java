package com.example.Matcho.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Matcho.Profile.model.Profile;
import com.example.Matcho.Profile.repository.ProfileRepository;
import com.example.Matcho.User.model.Role;
import com.example.Matcho.User.model.User;
import com.example.Matcho.User.repository.RoleRepository;
import com.example.Matcho.User.service.UserService;
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository, ProfileRepository profileRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        List<String> errors = new ArrayList<>();

        // Validate required fields
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errors.add("Username cannot be null or empty");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.add("Password cannot be null or empty");
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.add("Email cannot be null or empty");
        }

        if (user.getGender() == null || user.getGender().isEmpty()) {
            errors.add("Gender is required");
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            errors.add("First name is required");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            errors.add("Last name is required");
        }

        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            errors.add("Phone number is required");
        }

       

        if (user.getRole() == null || user.getRole().getId() == null) {
            Role defaultRole = roleRepository.findByName("USER");
            if (defaultRole == null) {
                errors.add("Default role not found");
            } else {
                user.setRole(defaultRole);
            }
        }

        if (user.getCoins() < 0) {
            errors.add("Coins cannot be negative");
        }

        // Return the errors if any
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", errors));
        }

        // Set default status if not provided
        if (user.getStatus() == null || user.getStatus().isEmpty()) {
            user.setStatus("active");
        }

        // Hash the user's password before saving
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        
        
        // Set creation and update timestamps
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());


        // Save the user
        User savedUser = userService.saveUser(user);

        // Return the saved user (excluding password in the response for security reasons)
        return ResponseEntity.ok(savedUser);
    }



    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User userUpdates) {
        User updatedUser = userService.updateUser(id, userUpdates);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        User user = userService.getUserByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @PutMapping("/{userId}/addFriend/{friendId}")
    public ResponseEntity<User> addFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        User updatedUser = userService.addFriend(userId, friendId);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{userId}/removeFriend/{friendId}")
    public ResponseEntity<User> removeFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        User updatedUser = userService.removeFriend(userId, friendId);
        return ResponseEntity.ok(updatedUser);
    }
}
