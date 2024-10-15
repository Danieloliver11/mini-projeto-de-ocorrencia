package com.carbigdata.api_ocorrencia.service;

import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.exceptions.DadosJaCadastradosException;
import com.carbigdata.api_ocorrencia.exceptions.MsgException;
import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.RoleEnum;
import com.carbigdata.api_ocorrencia.model.mapper.ClienteMapper;
import com.carbigdata.api_ocorrencia.model.vo.ClienteVO;
import com.carbigdata.api_ocorrencia.repository.ClienteRepository;
import com.carbigdata.api_ocorrencia.security.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
	
	private final ClienteRepository clienteRepository;
	
	private final ClienteMapper clienteMapper;
	
	private final AuthService authService;
	
	
	public ClienteVO cadastrarClient(@Valid ClienteVO clienteVO) {
		
		verificarCpfJaCadastrado(clienteVO);
				
		ClienteEntity clienteEntity = clienteMapper.converterVOparaEntidade(clienteVO);
		clienteEntity.setSenha(authService.encoderPassword(clienteVO.senha()));
		clienteEntity.setRole(RoleEnum.ROLE_USUARIO);

		clienteRepository.save(clienteEntity);
		
		return clienteMapper.converterEntidadeparaVO(clienteEntity);
	}
	
	public ClienteVO atualizarClient(@Valid ClienteVO clienteVO) {
				
		ClienteEntity clienteEntity =  clienteRepository.findById(clienteVO.id()).orElseThrow(
				() -> new NaoEncontradoException("O cidadão não foi encontrado."));
		
		verificarUsuarioLogadoParaAtualizar(clienteVO.id());

		verificarCpfJaCadastradoParaAtualizar(clienteVO,clienteEntity);
				
		clienteMapper.atualizarEntidadePeloVO(clienteEntity,clienteVO);
		
		clienteEntity.setSenha(authService.encoderPassword(clienteVO.senha()));

		clienteRepository.save(clienteEntity);

		return clienteMapper.converterEntidadeparaVO(clienteEntity);
	}

	public void verificarUsuarioLogadoParaAtualizar(Long id) {
		
		ClienteEntity usuarioLogadoEntity = authService.recuperarUsuarioLogado();
		
		if(!authService.isAdmRoles() && !usuarioLogadoEntity.getId().equals(id)) {
			throw new MsgException("O usuário logado não tem permissão para atualizar os dados de outro cliente, exceto se for um administrador.");
		}
		
	}

	private void verificarCpfJaCadastrado(@Valid ClienteVO clienteVO) {
		if(clienteRepository.existsByCpf(clienteVO.cpf()))
			throw new DadosJaCadastradosException("Esse cpf já foi cadastrado o sistema");
		
	}

	public ClienteVO recuperarPorCpf(String cpf) {
		
		ClienteEntity clienteEntity = recuperarCidadaoEntityPorCpf(cpf);

		verificarUsuarioLogadoParaAtualizar(clienteEntity.getId());

		
		return clienteMapper.converterEntidadeparaVO(clienteEntity);
	}

	public ClienteEntity recuperarCidadaoEntityPorCpf(String cpf) {
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
		verificarUsuarioLogadoParaAtualizar(clienteEntity.getId());

		
		clienteRepository.deleteById(clienteEntity.getId());

		
	}

	public ClienteEntity recuperarClientePorNomeCpf(String nome,String cpf) {
		return clienteRepository.findByNomeAndCpf(nome,cpf).orElseThrow(() ->
		new NaoEncontradoException("O cidadão não foi encontrado pelo nome e cpf enformado."));
		
	}



}
