package com.carbigdata.api_ocorrencia.service;

import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.exceptions.DadosJaCadastradosException;
import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.mapper.ClienteMapper;
import com.carbigdata.api_ocorrencia.model.vo.ClienteVO;
import com.carbigdata.api_ocorrencia.repository.ClienteRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final ClienteRepository clienteRepository;
	
	private final ClienteMapper clienteMapper;
	
	
	public ClienteVO cadastrarClient(@Valid ClienteVO clienteVO) {
		
		verificarCpfJaCadastrado(clienteVO);
		
		ClienteEntity clienteEntity = clienteMapper.converterVOparaEntidade(clienteVO);
		
		clienteRepository.save(clienteEntity);
		
		return clienteMapper.converterEntidadeparaVO(clienteEntity);
	}
	
	public ClienteVO atualizarClient(@Valid ClienteVO clienteVO) {
		
		ClienteEntity clienteEntity =  clienteRepository.findById(clienteVO.id()).orElseThrow(
				() -> new NaoEncontradoException("O cidadão não foi encontrado."));

		verificarCpfJaCadastradoParaAtualizar(clienteVO,clienteEntity);
		
		clienteMapper.atualizarEntidadePeloVO(clienteEntity,clienteVO);
		
		clienteRepository.save(clienteEntity);

		return clienteMapper.converterEntidadeparaVO(clienteEntity);
	}

	private void verificarCpfJaCadastrado(@Valid ClienteVO clienteVO) {
		if(clienteRepository.existsByCpf(clienteVO.cpf()))
			throw new DadosJaCadastradosException("Esse cpf já foi cadastrado o sistema");
		
	}

	public ClienteVO recuperarPorCpf(String cpf) {
		
		ClienteEntity clienteEntity = recuperarCidadaoEntityPorCpf(cpf);
		
		return clienteMapper.converterEntidadeparaVO(clienteEntity);
	}

	private ClienteEntity recuperarCidadaoEntityPorCpf(String cpf) {
		ClienteEntity clienteEntity = clienteRepository.findByCpf(cpf).orElseThrow(
				() -> new NaoEncontradoException("O cidadão não foi encontrado."));
		return clienteEntity;
	}
	
	private void verificarCpfJaCadastradoParaAtualizar(ClienteVO clienteVO, ClienteEntity clienteEntity) {
		
		ClienteEntity cpfDoVO = clienteRepository.findByCpf((clienteVO.cpf())).orElse(null);
		
		if( cpfDoVO != null && !cpfDoVO.getCpf().equals(clienteEntity.getCpf())) {
			verificarCpfJaCadastrado(clienteVO);

		}
		
	}

	public void deletarCidadao(String cpf) {
		
		ClienteEntity clienteEntity = recuperarCidadaoEntityPorCpf(cpf);
		
		clienteRepository.deleteById(clienteEntity.getId());

		
	}

	public ClienteEntity recuperarClientePorNomeCpf(String nome,String cpf) {
		return clienteRepository.findByNomeAndCpf(nome,cpf).orElseThrow(() ->
		new NaoEncontradoException("O cidadão não foi encontrado pelo nome e cpf enformado."));
		
	}



}
