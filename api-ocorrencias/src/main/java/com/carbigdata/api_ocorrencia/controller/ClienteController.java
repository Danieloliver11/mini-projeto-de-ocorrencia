package com.carbigdata.api_ocorrencia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 
	public ClienteVO cadastrarClient(@Valid @RequestBody ClienteVO clienteVO) {
		return clienteService.cadastrarClient(clienteVO);
	}
	
	@PutMapping
	public ClienteVO atualizarClient(@Valid @RequestBody ClienteVO clienteVO) {
		return clienteService.atualizarClient(clienteVO);
	}
	
	@GetMapping("recuperar-clientes-por-cpf/{cpf}")
	public ClienteVO recuperarPorCpf( @PathVariable String cpf) {
		return clienteService.recuperarPorCpf(cpf);
	}
	
	@DeleteMapping("deletar-clientes-por-cpf/{cpf}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void deletarCidadaoPorCpf(@PathVariable String cpf) {
		clienteService.deletarCidadao(cpf);
	}
	
	

}
