package com.conectapet.service;

import com.conectapet.dto.LoginRequest;
import com.conectapet.dto.LoginResponse;
import com.conectapet.dto.RegisterRequest;
import com.conectapet.model.User;
import com.conectapet.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            // Adicionar informações extras ao token
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("userId", user.getId());
            extraClaims.put("userType", user.getUserType().name());
            
            String token = jwtUtil.generateToken(userDetails, extraClaims);
            
            return new LoginResponse(token, user.getName(), user.getEmail(), user.getUserType().name());
            
        } catch (Exception e) {
            throw new RuntimeException("Credenciais inválidas");
        }
    }
    
    public User register(RegisterRequest request) {
        return userService.createUser(request);
    }
}