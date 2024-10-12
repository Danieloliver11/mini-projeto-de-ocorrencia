package com.carbigdata.api_ocorrencia.service;

import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.exceptions.DadosJaCadastradosException;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.mapper.ClienteMapper;
import com.carbigdata.api_ocorrencia.model.vo.ClienteVO;
import com.carbigdata.api_ocorrencia.repository.ClienteRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final ClienteRepository clienteRepository;
	
	private final ClienteMapper clienteMapper;
	
	
	public ClienteVO cadastrarClient(@Valid ClienteVO clienteVO) {
		
		verificarCpfJaCadastrado(clienteVO);
		
		ClienteEntity clienteEntity = clienteMapper.converterVOparaEntidade(clienteVO);
		
		return null;
	}

	private void verificarCpfJaCadastrado(@Valid ClienteVO clienteVO) {
		if(clienteRepository.existsByCpf(clienteVO.cpf()))
			throw new DadosJaCadastradosException("Esse cpf já foi cadastrado o sistema");
		
	}

}
