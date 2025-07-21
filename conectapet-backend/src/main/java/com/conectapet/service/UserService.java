package com.conectapet.service;

import com.conectapet.dto.RegisterRequest;
import com.conectapet.model.User;
import com.conectapet.model.UserType;
import com.conectapet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
    
    public User createUser(RegisterRequest request) {
        // Verificar se email já existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        
        // Verificar CNPJ para ONGs
        if (request.getUserType() == UserType.ONG && request.getCnpj() != null) {
            if (userRepository.existsByCnpj(request.getCnpj())) {
                throw new RuntimeException("CNPJ já está em uso");
            }
        }
        
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(request.getUserType());
        user.setCnpj(request.getCnpj());
        user.setDescription(request.getDescription());
        
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> findByUserType(UserType userType) {
        return userRepository.findByUserType(userType);
    }
    
    public List<User> findActiveUsersByType(UserType userType) {
        return userRepository.findActiveUsersByType(userType);
    }
    
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        user.setName(updatedUser.getName());
        user.setDescription(updatedUser.getDescription());
        user.setProfileImage(updatedUser.getProfileImage());
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        user.setEnabled(false);
        userRepository.save(user);
    }
    
    public long countByUserType(UserType userType) {
        return userRepository.countByUserType(userType);
    }
    
    public List<User> searchByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
}