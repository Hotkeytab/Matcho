package com.example.Matcho.Profile;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Matcho.Profile.model.Profile;
import com.example.Matcho.Profile.service.ProfileService;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile savedProfile = profileService.saveProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        profile.setId(id);
        Profile updatedProfile = profileService.updateProfile(profile);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        Profile profile = profileService.getProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable Long userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Profile>> getProfilesByLocation(@PathVariable String location) {
        List<Profile> profiles = profileService.getProfilesByLocation(location);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/createdAfter/{date}")
    public ResponseEntity<List<Profile>> getProfilesCreatedAfter(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        List<Profile> profiles = profileService.getProfilesCreatedAfter(dateTime);
        return ResponseEntity.ok(profiles);
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}