package com.carbigdata.api_ocorrencia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carbigdata.api_ocorrencia.exceptions.MsgException;
import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.RoleEnum;
import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;
import com.carbigdata.api_ocorrencia.model.mapper.EnderecoMapper;
import com.carbigdata.api_ocorrencia.model.mapper.OcorrenciaMapper;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaFiltroVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaResponseVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaVO;
import com.carbigdata.api_ocorrencia.repository.OcorrenciaRepository;
import com.carbigdata.api_ocorrencia.repository.specification.OcorrenciaSpecification;
import com.carbigdata.api_ocorrencia.security.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {
	
	private final OcorrenciaMapper ocorrenciaMapper;
	private final EnderecoMapper enderecoMapper;
	
	private final AuthService authService;

	
	private final ClienteService clienteService;
	
	private final OcorrenciaRepository ocorrenciaRepository;
	
	@Transactional
	public OcorrenciaVO salvarOcorrencia(OcorrenciaVO ocorrencia) {

		ClienteEntity cliente = clienteService
				.recuperarClientePorNomeCpf(ocorrencia.nome(), ocorrencia.cpf());
		
		OcorrenciaEntity ocorrenciaEntity = ocorrenciaMapper.converterVOparaEntidade(ocorrencia, cliente, StatusOcorrenciaEnum.ATIVO);
		
		ocorrenciaRepository.save(ocorrenciaEntity);
				
		return ocorrenciaMapper.converterEntidadeParaVO(ocorrenciaEntity);
	}
	
	public OcorrenciaVO atualizarOcorrencia(OcorrenciaVO ocorrencia) {

		ClienteEntity cliente = clienteService.recuperarClientePorNomeCpf(ocorrencia.nome(), ocorrencia.cpf());

		OcorrenciaEntity ocorrenciaEntity = recuperarOcorrenciaPorId(ocorrencia.id());
		
		verificarUsuarioLogadoParaAtualizaOcorrenciar(ocorrenciaEntity.getCliente().getId());

		verificarOcorrenciaFinalizada(ocorrenciaEntity);

		ocorrenciaEntity.setCliente(cliente);

		enderecoMapper.atualizarEndereco(ocorrenciaEntity.getEndereco(), ocorrencia.endereco());

		ocorrenciaRepository.save(ocorrenciaEntity);

		return ocorrenciaMapper.converterEntidadeParaVO(ocorrenciaEntity);
	}

	public void verificarOcorrenciaFinalizada(OcorrenciaEntity ocorrenciaEntity) {
		if(ocorrenciaEntity.getStatus().equals(StatusOcorrenciaEnum.FINALIZADO)) {
			throw new MsgException("Não é possível acessar as informações de uma ocorrência que já foi finalizada.");
		}
		
	}

	public Page<OcorrenciaResponseVO> filtrarOcorrencias(OcorrenciaFiltroVO filtro, boolean asc, Pageable pageable) {
		
		ClienteEntity usuarioLogado = authService.recuperarUsuarioLogado();

		Page<OcorrenciaEntity> ocorenciasPage = ocorrenciaRepository.findAll(new OcorrenciaSpecification(filtro,asc,authService.isAdmRoles(),usuarioLogado.getId()),pageable);
		
		return ocorrenciaMapper.converterEntityPageParaPageVo(ocorenciasPage);
	}
	
	public OcorrenciaEntity recuperarOcorrenciaPorId(Long id) {
		return ocorrenciaRepository.findById(id).orElseThrow(() ->
		new NaoEncontradoException("A Ocorrencia não foi encontrado pelo id informado."));
	}

	public OcorrenciaResponseVO finalizarOcorrenciaPorId(Long id) {
		
		authService.verificarAutorizacaoRoles(RoleEnum.ROLE_ADM);
		
		OcorrenciaEntity ocorrenciaEntity = recuperarOcorrenciaPorId(id);

		verificarOcorrenciaFinalizada(ocorrenciaEntity);

		ocorrenciaEntity.setStatus(StatusOcorrenciaEnum.FINALIZADO);
		ocorrenciaRepository.save(ocorrenciaEntity);

		return ocorrenciaMapper.converterEntidadeParaOcorrenciaResponseVO(ocorrenciaEntity);
	}
	
public void verificarUsuarioLogadoParaAtualizaOcorrenciar(Long idCliente) {
		
		ClienteEntity usuarioLogadoEntity = authService.recuperarUsuarioLogado();
		
		if(!authService.isAdmRoles() && !usuarioLogadoEntity.getId().equals(idCliente)) {
			throw new MsgException("O usuário logado não tem permissão para atualizar os dados de outra Ocorrencia, exceto se for um administrador.");
		}
		
	}



}
