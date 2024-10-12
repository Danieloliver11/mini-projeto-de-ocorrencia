package com.carbigdata.api_ocorrencia.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carbigdata.api_ocorrencia.model.vo.ClienteVO;
import com.carbigdata.api_ocorrencia.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
	
	private final ClienteService clienteService;
	
	//TODO POST GET POR ID E DELETE E PUT  de cliente 
	
	@PostMapping
	public ClienteVO cadastrarClient(@Valid @RequestBody ClienteVO clienteVO) {
		return clienteService.cadastrarClient(clienteVO);
	}
	
	

}
