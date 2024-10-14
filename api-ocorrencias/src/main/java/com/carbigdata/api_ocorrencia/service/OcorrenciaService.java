package com.carbigdata.api_ocorrencia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.exceptions.MsgException;
import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.entity.EnderecoEntity;
import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;
import com.carbigdata.api_ocorrencia.model.mapper.EnderecoMapper;
import com.carbigdata.api_ocorrencia.model.mapper.OcorrenciaMapper;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaFiltroVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaResponseVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaVO;
import com.carbigdata.api_ocorrencia.repository.OcorrenciaRepository;
import com.carbigdata.api_ocorrencia.repository.specification.OcorrenciaSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {
	
	private final OcorrenciaMapper ocorrenciaMapper;
	private final EnderecoMapper enderecoMapper;

	
	private final ClienteService clienteService;
	
	private final OcorrenciaRepository ocorrenciaRepository;
	
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

		verificarOcorrenciaFinalizada(ocorrenciaEntity);

		ocorrenciaEntity.setCliente(cliente);

		enderecoMapper.atualizarEndereco(ocorrenciaEntity.getEndereco(), ocorrencia.endereco());

		ocorrenciaRepository.save(ocorrenciaEntity);

		return ocorrenciaMapper.converterEntidadeParaVO(ocorrenciaEntity);
	}

	private void verificarOcorrenciaFinalizada(OcorrenciaEntity ocorrenciaEntity) {
		if(ocorrenciaEntity.getStatus().equals(StatusOcorrenciaEnum.FINALIZADO)) {
			throw new MsgException("Não é possivel atualizar Ocorrencia já finalizada.");
		}
		
	}

	public Page<OcorrenciaResponseVO> filtrarOcorrencias(OcorrenciaFiltroVO filtro, boolean asc, Pageable pageable) {

		Page<OcorrenciaEntity> ocorenciasPage = ocorrenciaRepository.findAll(new OcorrenciaSpecification(filtro,asc),pageable);
		
		return ocorrenciaMapper.converterEntityPageParaPageVo(ocorenciasPage);
	}
	
	private OcorrenciaEntity recuperarOcorrenciaPorId(Long id) {
		return ocorrenciaRepository.findById(id).orElseThrow(() ->
		new NaoEncontradoException("A Ocorrencia não foi encontrado pelo id informado."));
	}

	public OcorrenciaResponseVO finalizarOcorrenciaPorId(Long id) {
		
		OcorrenciaEntity ocorrenciaEntity = recuperarOcorrenciaPorId(id);

		verificarOcorrenciaFinalizada(ocorrenciaEntity);

		ocorrenciaEntity.setStatus(StatusOcorrenciaEnum.FINALIZADO);
		ocorrenciaRepository.save(ocorrenciaEntity);

		return ocorrenciaMapper.converterEntidadeParaOcorrenciaResponseVO(ocorrenciaEntity);
	}



}
