package com.carbigdata.api_ocorrencia.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carbigdata.api_ocorrencia.model.vo.LoginRequest;
import com.carbigdata.api_ocorrencia.service.LoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService loginService;
	
	@PostMapping
	public String logarUsuario( @Valid @RequestBody LoginRequest login) {
		return loginService.logarUsuario(login);
	}
	
	

}
