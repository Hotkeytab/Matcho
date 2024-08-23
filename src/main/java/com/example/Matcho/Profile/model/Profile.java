package com.example.Matcho.Profile.model;

import java.time.LocalDateTime;

import com.example.Matcho.User.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;


@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String photo;

    @Column
    private String bio;

    @Column
    private String location;



	@Column
    private String website;

    @Column
    private String socialLinks; // This could be a JSON string or separate entity depending on requirements

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference

   private User user;

    
    
    // Getters and setters
    
    
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSocialLinks() {
		return socialLinks;
	}

	public void setSocialLinks(String socialLinks) {
		this.socialLinks = socialLinks;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
