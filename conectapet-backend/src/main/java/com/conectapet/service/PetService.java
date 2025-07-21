package com.conectapet.service;

import com.conectapet.dto.PetRequest;
import com.conectapet.dto.PetResponse;
import com.conectapet.model.*;
import com.conectapet.repository.PetImageRepository;
import com.conectapet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService {
    
    @Autowired
    private PetRepository petRepository;
    
    @Autowired
    private PetImageRepository petImageRepository;
    
    public Pet createPet(PetRequest request, User owner) {
        Pet pet = new Pet();
        pet.setName(request.getNome());
        pet.setSpecies(request.getTipoAnimal());
        pet.setBreed(request.getRaca());
        pet.setAge(request.getIdade());
        pet.setSize(request.getPorte());
        pet.setColor(request.getCor());
        pet.setDescription(request.getDescricao());
        pet.setUser(owner);
        pet.setStatus(AdoptionStatus.DISPONIVEL);
        
        Pet savedPet = petRepository.save(pet);
        
        // Salvar imagens se fornecidas
        if (request.getImagens() != null && !request.getImagens().isEmpty()) {
            for (int i = 0; i < request.getImagens().size(); i++) {
                String imageUrl = request.getImagens().get(i);
                PetImage petImage = new PetImage();
                petImage.setImageUrl(imageUrl);
                petImage.setIsMain(i == 0); // Primeira imagem é a principal
                petImage.setPet(savedPet);
                petImageRepository.save(petImage);
            }
        }
        
        return savedPet;
    }
    
    public List<PetResponse> getAllAvailablePets() {
        return petRepository.findByStatus(AdoptionStatus.DISPONIVEL)
                .stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }
    
    public Page<PetResponse> getAllAvailablePets(Pageable pageable) {
        return petRepository.findByStatus(AdoptionStatus.DISPONIVEL, pageable)
                .map(PetResponse::new);
    }
    
    public Optional<PetResponse> getPetById(Long id) {
        return petRepository.findById(id)
                .map(PetResponse::new);
    }
    
    public List<PetResponse> getPetsByOwner(User owner) {
        return petRepository.findByUser(owner)
                .stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }
    
    public Page<PetResponse> searchPets(String species, PetSize size, Integer minAge, Integer maxAge, Pageable pageable) {
        return petRepository.findAvailablePetsWithFilters(species, size, minAge, maxAge, pageable)
                .map(PetResponse::new);
    }
    
    public List<PetResponse> searchPetsByName(String name) {
        return petRepository.findAvailablePetsByNameContaining(name)
                .stream()
                .map(PetResponse::new)
                .collect(Collectors.toList());
    }
    
    public Pet updatePet(Long id, PetRequest request, User owner) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        // Verificar se o usuário é o dono do pet
        if (!pet.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("Você não tem permissão para editar este pet");
        }
        
        pet.setName(request.getNome());
        pet.setSpecies(request.getTipoAnimal());
        pet.setBreed(request.getRaca());
        pet.setAge(request.getIdade());
        pet.setSize(request.getPorte());
        pet.setColor(request.getCor());
        pet.setDescription(request.getDescricao());
        
        // Atualizar imagens se fornecidas
        if (request.getImagens() != null) {
            // Remover imagens antigas
            petImageRepository.deleteByPet(pet);
            
            // Adicionar novas imagens
            for (int i = 0; i < request.getImagens().size(); i++) {
                String imageUrl = request.getImagens().get(i);
                PetImage petImage = new PetImage();
                petImage.setImageUrl(imageUrl);
                petImage.setIsMain(i == 0);
                petImage.setPet(pet);
                petImageRepository.save(petImage);
            }
        }
        
        return petRepository.save(pet);
    }
    
    public void deletePet(Long id, User owner) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        // Verificar se o usuário é o dono do pet
        if (!pet.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("Você não tem permissão para deletar este pet");
        }
        
        petRepository.delete(pet);
    }
    
    public Pet updatePetStatus(Long id, AdoptionStatus status, User owner) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        // Verificar se o usuário é o dono do pet
        if (!pet.getUser().getId().equals(owner.getId())) {
            throw new RuntimeException("Você não tem permissão para alterar o status deste pet");
        }
        
        pet.setStatus(status);
        return petRepository.save(pet);
    }
    
    public long countAvailablePets() {
        return petRepository.countByStatus(AdoptionStatus.DISPONIVEL);
    }
    
    public long countPetsByOwner(User owner) {
        return petRepository.countByUser(owner);
    }
}