package com.conectapet.repository;

import com.conectapet.model.User;
import com.conectapet.model.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByCnpj(String cnpj);
    
    List<User> findByUserType(UserType userType);
    
    Page<User> findByUserType(UserType userType, Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.userType = :userType AND u.enabled = true")
    List<User> findActiveUsersByType(@Param("userType") UserType userType);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.userType = :userType")
    long countByUserType(@Param("userType") UserType userType);
}