package com.conectapet.dto;

import com.conectapet.model.AdoptionStatus;
import com.conectapet.model.Pet;
import com.conectapet.model.PetImage;
import com.conectapet.model.PetSize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PetResponse {
    
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private PetSize size;
    private String color;
    private String description;
    private AdoptionStatus status;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
    private UserSummary owner;
    
    // Construtores
    public PetResponse() {}
    
    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.age = pet.getAge();
        this.size = pet.getSize();
        this.color = pet.getColor();
        this.description = pet.getDescription();
        this.status = pet.getStatus();
        this.createdAt = pet.getCreatedAt();
        this.imageUrls = pet.getImages() != null ? 
            pet.getImages().stream()
                .map(PetImage::getImageUrl)
                .collect(Collectors.toList()) : List.of();
        this.owner = new UserSummary(pet.getUser());
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
    
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public PetSize getSize() { return size; }
    public void setSize(PetSize size) { this.size = size; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public AdoptionStatus getStatus() { return status; }
    public void setStatus(AdoptionStatus status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }
    
    public UserSummary getOwner() { return owner; }
    public void setOwner(UserSummary owner) { this.owner = owner; }
    
    // Classe interna para informações resumidas do usuário
    public static class UserSummary {
        private Long id;
        private String name;
        private String email;
        private String userType;
        
        public UserSummary() {}
        
        public UserSummary(com.conectapet.model.User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.userType = user.getUserType().name();
        }
        
        // Getters e Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getUserType() { return userType; }
        public void setUserType(String userType) { this.userType = userType; }
    }
}