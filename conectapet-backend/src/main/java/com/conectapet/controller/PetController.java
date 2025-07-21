package com.conectapet.controller;

import com.conectapet.dto.PetRequest;
import com.conectapet.dto.PetResponse;
import com.conectapet.model.AdoptionStatus;
import com.conectapet.model.Pet;
import com.conectapet.model.PetSize;
import com.conectapet.model.User;
import com.conectapet.service.PetService;
import com.conectapet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = "*")
public class PetController {
    
    @Autowired
    private PetService petService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<PetResponse>> getAllPets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PetResponse> pets = petService.getAllAvailablePets(pageable);
        
        return ResponseEntity.ok(pets.getContent());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        Optional<PetResponse> pet = petService.getPetById(id);
        return pet.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<PetResponse>> searchPets(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) PetSize size,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        if (name != null && !name.trim().isEmpty()) {
            List<PetResponse> pets = petService.searchPetsByName(name);
            return ResponseEntity.ok(pets);
        }
        
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        Page<PetResponse> pets = petService.searchPets(species, size, minAge, maxAge, pageable);
        
        return ResponseEntity.ok(pets.getContent());
    }
    
    @PostMapping
    public ResponseEntity<?> createPet(@Valid @RequestBody PetRequest request, Authentication authentication) {
        try {
            User owner = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            Pet pet = petService.createPet(request, owner);
            return ResponseEntity.ok(new PetResponse(pet));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Long id, @Valid @RequestBody PetRequest request, 
                                      Authentication authentication) {
        try {
            User owner = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            Pet pet = petService.updatePet(id, request, owner);
            return ResponseEntity.ok(new PetResponse(pet));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id, Authentication authentication) {
        try {
            User owner = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            petService.deletePet(id, owner);
            return ResponseEntity.ok().body("Pet removido com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updatePetStatus(@PathVariable Long id, @RequestParam AdoptionStatus status,
                                            Authentication authentication) {
        try {
            User owner = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            Pet pet = petService.updatePetStatus(id, status, owner);
            return ResponseEntity.ok(new PetResponse(pet));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/my-pets")
    public ResponseEntity<List<PetResponse>> getMyPets(Authentication authentication) {
        try {
            User owner = userService.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            List<PetResponse> pets = petService.getPetsByOwner(owner);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}