package com.example.Matcho.User.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Matcho.Profile.model.Profile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
  

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int coins;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)

    private String phoneNumber;

    @Column
    private LocalDate dateOfBirth;
    
    @Column
    private String country;

    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

    private String status;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    
    @JsonManagedReference
    private Profile profile;

    @ManyToMany
    @JoinTable(
        name = "friendships",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;

    @ManyToMany
    @JoinTable(
        name = "user_privileges",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Getters and setters

    public Long getId() {
  		return id;
  	}

  	public void setId(Long id) {
  		this.id = id;
  	}

  	public String getUsername() {
  		return username;
  	}

  	public void setUsername(String username) {
  		this.username = username;
  	}

  	public String getPassword() {
  		return password;
  	}

  	public void setPassword(String password) {
  		this.password = password;
  	}

  	public String getEmail() {
  		return email;
  	}

  	public void setEmail(String email) {
  		this.email = email;
  	}

  	public String getGender() {
  		return gender;
  	}

  	public void setGender(String gender) {
  		this.gender = gender;
  	}

  	public int getCoins() {
  		return coins;
  	}

  	public void setCoins(int coins) {
  		this.coins = coins;
  	}

  	public String getFirstName() {
  		return firstName;
  	}

  	public void setFirstName(String firstName) {
  		this.firstName = firstName;
  	}

  	public String getLastName() {
  		return lastName;
  	}

  	public void setLastName(String lastName) {
  		this.lastName = lastName;
  	}

  	public String getPhoneNumber() {
  		return phoneNumber;
  	}

  	public void setPhoneNumber(String phoneNumber) {
  		this.phoneNumber = phoneNumber;
  	}

  	public LocalDate getDateOfBirth() {
  		return dateOfBirth;
  	}

  	public void setDateOfBirth(LocalDate dateOfBirth) {
  		this.dateOfBirth = dateOfBirth;
  	}

  	public String getStatus() {
  		return status;
  	}

  	public void setStatus(String status) {
  		this.status = status;
  	}

  	public Role getRole() {
  		return role;
  	}

  	public void setRole(Role role) {
  		this.role = role;
  	}

  	public Profile getProfile() {
  		return profile;
  	}

  	public void setProfile(Profile profile) {
  		this.profile = profile;
  	}

  	public Set<User> getFriends() {
  		return friends;
  	}

  	public void setFriends(Set<User> friends) {
  		this.friends = friends;
  	}

  	public Set<Privilege> getPrivileges() {
  		return privileges;
  	}

  	public void setPrivileges(Set<Privilege> privileges) {
  		this.privileges = privileges;
  	}

  	public LocalDateTime getCreatedAt() {
  		return createdAt;
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
