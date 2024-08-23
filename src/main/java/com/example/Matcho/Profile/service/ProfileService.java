package com.example.Matcho.Profile.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Matcho.Profile.model.Profile;
import com.example.Matcho.Profile.repository.ProfileRepository;

import jakarta.transaction.Transactional;

@Service
public class ProfileService {
private  ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile saveProfile(Profile profile) {
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile profile) {
        profile.setUpdatedAt(LocalDateTime.now());
        return profileRepository.save(profile);
    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    public Profile getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId).orElse(null);
    }

    public List<Profile> getProfilesByLocation(String location) {
        return profileRepository.findByLocation(location);
    }

    public List<Profile> getProfilesCreatedAfter(LocalDateTime date) {
        return profileRepository.findByCreatedAtAfter(date);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}