package com.carbigdata.api_ocorrencia.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthService authService;

    public JwtAuthenticationFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrair o token JWT do cabeçalho Authorization
        final String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String sub = null;
        String nome = null;
        String cpf = null;


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);  // Remove "Bearer " do token
            try {
                // Validar o token JWT e extrair o CPF
                Claims claims = Jwts.parserBuilder()
                    .setSigningKey(authService.getSigningKey())  // Obtenha a chave de assinatura
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
                
                sub = claims.getSubject();  // CPF como "subject" do token
                nome = claims.get("nome", String.class); 
                cpf = claims.get("cpf", String.class);
                
                } catch (Exception e) {
                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        // Se o token foi validado com sucesso, configure a autenticação no contexto de segurança
        if (sub != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	// Cria uma instância de UserDetails (ou use sua própria implementação)
            CustomUserDetails userDetails = new CustomUserDetails(sub,nome,cpf);
            
            // Cria uma instância de Authentication
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()); // Senha como null

            // Configura a autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);        }

        // Continua com a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}
