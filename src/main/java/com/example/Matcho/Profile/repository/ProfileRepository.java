package com.example.Matcho.Profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.example.Matcho.Profile.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // Find profile by user id
    Optional<Profile> findByUserId(Long userId);

    // Find profiles by location
    List<Profile> findByLocation(String location);

    // Find profiles created after a certain date
    List<Profile> findByCreatedAtAfter(LocalDateTime date);
}