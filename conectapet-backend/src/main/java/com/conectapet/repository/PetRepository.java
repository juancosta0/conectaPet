package com.conectapet.repository;

import com.conectapet.model.AdoptionStatus;
import com.conectapet.model.Pet;
import com.conectapet.model.PetSize;
import com.conectapet.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    List<Pet> findByStatus(AdoptionStatus status);
    
    Page<Pet> findByStatus(AdoptionStatus status, Pageable pageable);
    
    List<Pet> findByUser(User user);
    
    Page<Pet> findByUser(User user, Pageable pageable);
    
    List<Pet> findBySpeciesIgnoreCase(String species);
    
    List<Pet> findBySize(PetSize size);
    
    @Query("SELECT p FROM Pet p WHERE p.status = :status AND LOWER(p.species) = LOWER(:species)")
    List<Pet> findAvailablePetsBySpecies(@Param("status") AdoptionStatus status, @Param("species") String species);
    
    @Query("SELECT p FROM Pet p WHERE p.status = :status AND p.size = :size")
    List<Pet> findAvailablePetsBySize(@Param("status") AdoptionStatus status, @Param("size") PetSize size);
    
    @Query("SELECT p FROM Pet p WHERE p.status = 'DISPONIVEL' AND " +
           "(:species IS NULL OR LOWER(p.species) = LOWER(:species)) AND " +
           "(:size IS NULL OR p.size = :size) AND " +
           "(:minAge IS NULL OR p.age >= :minAge) AND " +
           "(:maxAge IS NULL OR p.age <= :maxAge)")
    Page<Pet> findAvailablePetsWithFilters(
        @Param("species") String species,
        @Param("size") PetSize size,
        @Param("minAge") Integer minAge,
        @Param("maxAge") Integer maxAge,
        Pageable pageable
    );
    
    @Query("SELECT p FROM Pet p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.status = 'DISPONIVEL'")
    List<Pet> findAvailablePetsByNameContaining(@Param("name") String name);
    
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.status = :status")
    long countByStatus(@Param("status") AdoptionStatus status);
    
    @Query("SELECT COUNT(p) FROM Pet p WHERE p.user = :user")
    long countByUser(@Param("user") User user);
}