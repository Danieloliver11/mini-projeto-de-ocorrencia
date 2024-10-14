package com.carbigdata.api_ocorrencia.service;

import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.vo.LoginRequest;
import com.carbigdata.api_ocorrencia.repository.ClienteRepository;
import com.carbigdata.api_ocorrencia.security.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {
		
	private final AuthService authService;
	private final ClienteService clienteService;
	
	public String logarUsuario(@Valid LoginRequest login) {
				
		ClienteEntity cliente = clienteService.recuperarCidadaoEntityPorCpf(login.getCpf());
		authService.verificarSenha(login.getPassword(), cliente.getSenha());
		
		
		return authService.login(cliente);		
	}

}
