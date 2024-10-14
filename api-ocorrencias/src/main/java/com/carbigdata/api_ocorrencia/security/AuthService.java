package com.carbigdata.api_ocorrencia.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.exceptions.NaoAutorizadoException;
import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.RoleEnum;
import com.carbigdata.api_ocorrencia.repository.ClienteRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expirationTime;

	private final ClienteRepository ClienteRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public String login(ClienteEntity request) {

		return generateToken(request.getId(), request.getNome(), request.getCpf(), request.getRole());

	}

	public String encoderPassword(String plainPassword) {
		return passwordEncoder.encode(plainPassword);
	}

	public boolean verificarSenha(String plainPassword, String hashedPassword) {
		return passwordEncoder.matches(plainPassword, hashedPassword);
	}

	public ClienteEntity recuperarUsuarioLogado() {

		CustomUserDetails userDetails = recuperarLogado();

		return ClienteRepository.findByCpf(userDetails.getCpf())
				.orElseThrow(() -> new NaoEncontradoException("Não foi possivel recuperar Usuario logado do banco"));

	}

	public void verificarAutorizacaoRoles(RoleEnum role) {

		CustomUserDetails userDetails = recuperarLogado();

		if (!role.getValor().equals(userDetails.getRole()))
			throw new NaoAutorizadoException("Usuario " + userDetails.getNome() + " não esta autorizado");

	}

	public boolean isAdmRoles() {

		CustomUserDetails userDetails = recuperarLogado();

		if (!RoleEnum.ROLE_ADM.getValor().equals(userDetails.getRole())) {
			return false;

		}
		return true;

	}

	public boolean isUsuarioRoles() {

		CustomUserDetails userDetails = recuperarLogado();

		if (!RoleEnum.ROLE_USUARIO.getValor().equals(userDetails.getRole())) {
			return false;

		}
		return true;

	}

	public Key getSigningKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // Use a chave secreta configurada
	}

	private CustomUserDetails recuperarLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Verifique se a autenticação não é nula e é uma instância de
		// JwtAuthenticationToken 
		if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
			return (CustomUserDetails) authentication.getPrincipal();
		}
		return null;
	}

	public String generateToken(long id, String username, String cpf, RoleEnum roleEnum) {
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // Gerar a chave HMAC

		Claims claims = Jwts.claims().setSubject(String.valueOf(id));
		claims.put("cpf", cpf);
		claims.put("nome", username);
		claims.put("role", roleEnum);

		Instant now = Instant.now();
		Date expiryDate = Date.from(now.plus(expirationTime, ChronoUnit.MINUTES)); 

		return Jwts.builder().setClaims(claims).setIssuedAt(Date.from(now)).setExpiration(expiryDate)
				.signWith(key, SignatureAlgorithm.HS512).compact();
	}
}
