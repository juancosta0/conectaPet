package com.conectapet.repository;

import com.conectapet.model.AdoptionRequest;
import com.conectapet.model.Pet;
import com.conectapet.model.RequestStatus;
import com.conectapet.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
    
    List<AdoptionRequest> findByPet(Pet pet);
    
    List<AdoptionRequest> findByAdopter(User adopter);
    
    List<AdoptionRequest> findByStatus(RequestStatus status);
    
    Page<AdoptionRequest> findByStatus(RequestStatus status, Pageable pageable);
    
    @Query("SELECT ar FROM AdoptionRequest ar WHERE ar.pet.user = :owner")
    List<AdoptionRequest> findRequestsForOwner(@Param("owner") User owner);
    
    @Query("SELECT ar FROM AdoptionRequest ar WHERE ar.pet.user = :owner AND ar.status = :status")
    List<AdoptionRequest> findRequestsForOwnerByStatus(@Param("owner") User owner, @Param("status") RequestStatus status);
    
    Optional<AdoptionRequest> findByPetAndAdopter(Pet pet, User adopter);
    
    @Query("SELECT COUNT(ar) FROM AdoptionRequest ar WHERE ar.pet = :pet AND ar.status = 'PENDENTE'")
    long countPendingRequestsForPet(@Param("pet") Pet pet);
    
    @Query("SELECT COUNT(ar) FROM AdoptionRequest ar WHERE ar.adopter = :adopter")
    long countByAdopter(@Param("adopter") User adopter);
    
    @Query("SELECT COUNT(ar) FROM AdoptionRequest ar WHERE ar.pet.user = :owner")
    long countRequestsForOwner(@Param("owner") User owner);
}