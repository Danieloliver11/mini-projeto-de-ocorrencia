package com.carbigdata.api_ocorrencia.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
	
	 @Value("${jwt.secret}")
	    private String secretKey; 

	    @Value("${jwt.expiration}")
	    private long expirationTime; 
	    
	    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    
	    // Método para criptografar a senha
	    public String encoderPassword(String plainPassword) {
	        return passwordEncoder.encode(plainPassword);
	    }

	    // Método para verificar a senha
	    public boolean verificarSenha(String plainPassword, String hashedPassword) {
	        return passwordEncoder.matches(plainPassword, hashedPassword);
	    }

    public String login(ClienteEntity request) {
        
            return generateToken(request.getId(),request.getNome(),request.getCpf());
        
    }
    
    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));  // Use a chave secreta configurada
    }


    public String generateToken(long id, String username,String cpf) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));  // Gerar a chave HMAC

        Claims claims = Jwts.claims().setSubject(String.valueOf(id));
        claims.put("cpf", cpf); 
        claims.put("nome", username); 


        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plus(expirationTime, ChronoUnit.MINUTES));  // Token expira em 30 minutos

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now))
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    }
}
