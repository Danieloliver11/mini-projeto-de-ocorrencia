package com.carbigdata.api_ocorrencia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;
import com.carbigdata.api_ocorrencia.model.mapper.OcorrenciaMapper;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaFiltroVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaVO;
import com.carbigdata.api_ocorrencia.repository.OcorrenciaRepository;
import com.carbigdata.api_ocorrencia.repository.specification.OcorrenciaSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {
	
	private final OcorrenciaMapper ocorrenciaMapper;
	
	private final ClienteService clienteService;
	
	private final OcorrenciaRepository ocorrenciaRepository;
	
	public OcorrenciaVO salvarOcorrencia(OcorrenciaVO ocorrencia) {

		ClienteEntity cliente = clienteService
				.recuperarClientePorNomeCpf(ocorrencia.nome(), ocorrencia.cpf());
		
		OcorrenciaEntity OcorrenciaEntity = ocorrenciaMapper.converterVOparaEntidade(ocorrencia, cliente, StatusOcorrenciaEnum.ATIVO);
		
		ocorrenciaRepository.save(OcorrenciaEntity);
		
		return ocorrenciaMapper.converterEntidadeParaVO(OcorrenciaEntity);
	}

	public Page<OcorrenciaVO> filtrarOcorrencias(OcorrenciaFiltroVO filtro, boolean asc, Pageable pageable) {

		Page<OcorrenciaEntity> ocorenciasPage = ocorrenciaRepository.findAll(new OcorrenciaSpecification(filtro,asc),pageable);
		
		return ocorrenciaMapper.converterEntityPageParaPageVo(ocorenciasPage);
	}

}
