package com.conectapet.service;

import com.conectapet.dto.UserProfileResponse;
import com.conectapet.model.User;
import com.conectapet.model.UserType;
import com.conectapet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        return user;
    }
    
    public User createUser(String name, String email, String password, String userType, 
                          String cnpj, String description) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já está em uso");
        }
        
        if (cnpj != null && !cnpj.isEmpty() && userRepository.existsByCnpj(cnpj)) {
            throw new RuntimeException("CNPJ já está em uso");
        }
        
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserType(UserType.valueOf(userType.toUpperCase()));
        
        if (user.getUserType() == UserType.ONG) {
            user.setCnpj(cnpj);
            user.setDescription(description);
        }
        
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public List<User> findByUserType(UserType userType) {
        return userRepository.findByUserType(userType);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public UserProfileResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserProfileResponse(user);
    }
    
    public UserProfileResponse updateUserProfile(String email, UserProfileResponse profileData) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        user.setName(profileData.getName());
        user.setDescription(profileData.getDescription());
        
        if (user.getUserType() == UserType.ONG && profileData.getCnpj() != null) {
            user.setCnpj(profileData.getCnpj());
        }
        
        User updatedUser = userRepository.save(user);
        return new UserProfileResponse(updatedUser);
    }
    
    public long countUsers() {
        return userRepository.count();
    }
    
    public long countUsersByType(UserType userType) {
        return userRepository.countByUserType(userType);
    }
}