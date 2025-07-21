package com.conectapet.controller;

import com.conectapet.model.Pet;
import com.conectapet.model.User;
import com.conectapet.model.UserType;
import com.conectapet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        try {
            User user = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            UserProfileResponse profile = new UserProfileResponse(user);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserUpdateRequest request, Authentication authentication) {
        try {
            User currentUser = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            User updatedData = new User();
            updatedData.setName(request.getName());
            updatedData.setDescription(request.getDescription());
            updatedData.setProfileImage(request.getProfileImage());
            
            User updatedUser = userService.updateUser(currentUser.getId(), updatedData);
            return ResponseEntity.ok(new UserProfileResponse(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/ongs")
    public ResponseEntity<List<UserSummaryResponse>> getONGs() {
        List<User> ongs = userService.findActiveUsersByType(UserType.ONG);
        List<UserSummaryResponse> response = ongs.stream()
                .map(UserSummaryResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserSummaryResponse> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(u -> ResponseEntity.ok(new UserSummaryResponse(u)))
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/favorites/{petId}")
    public ResponseEntity<?> addToFavorites(@PathVariable Long petId, Authentication authentication) {
        // Implementar lógica de favoritos
        return ResponseEntity.ok().body("Pet adicionado aos favoritos");
    }
    
    @DeleteMapping("/favorites/{petId}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable Long petId, Authentication authentication) {
        // Implementar lógica de favoritos
        return ResponseEntity.ok().body("Pet removido dos favoritos");
    }
    
    @GetMapping("/favorites")
    public ResponseEntity<List<Long>> getFavorites(Authentication authentication) {
        // Implementar lógica de favoritos
        return ResponseEntity.ok(List.of());
    }
    
    // Classes internas para DTOs
    public static class UserProfileResponse {
        private Long id;
        private String name;
        private String email;
        private String userType;
        private String cnpj;
        private String description;
        private String profileImage;
        private List<Long> favoritesPets;
        private List<Long> registeredPets;
        
        public UserProfileResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.userType = user.getUserType().name().toLowerCase();
            this.cnpj = user.getCnpj();
            this.description = user.getDescription();
            this.profileImage = user.getProfileImage();
            
            // Converter favoritos para IDs
            if (user.getFavoritePets() != null) {
                this.favoritesPets = user.getFavoritePets().stream()
                        .map(Pet::getId)
                        .collect(Collectors.toList());
            }
            
            // Converter pets registrados para IDs
            if (user.getPets() != null) {
                this.registeredPets = user.getPets().stream()
                        .map(Pet::getId)
                        .collect(Collectors.toList());
            }
        }
        
        // Getters
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getUserType() { return userType; }
        public String getCnpj() { return cnpj; }
        public String getDescription() { return description; }
        public String getProfileImage() { return profileImage; }
        public List<Long> getFavoritesPets() { return favoritesPets; }
        public List<Long> getRegisteredPets() { return registeredPets; }
    }
    
    public static class UserSummaryResponse {
        private Long id;
        private String name;
        private String email;
        private String userType;
        private String description;
        
        public UserSummaryResponse(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.userType = user.getUserType().name().toLowerCase();
            this.description = user.getDescription();
        }
        
        // Getters
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getUserType() { return userType; }
        public String getDescription() { return description; }
    }
    
    public static class UserUpdateRequest {
        private String name;
        private String description;
        private String profileImage;
        
        // Getters e Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getProfileImage() { return profileImage; }
        public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    }
}