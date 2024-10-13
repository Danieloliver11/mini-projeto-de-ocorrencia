package com.carbigdata.api_ocorrencia.service;

import org.springframework.stereotype.Service;

import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;
import com.carbigdata.api_ocorrencia.model.mapper.OcorrenciaMapper;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaVO;
import com.carbigdata.api_ocorrencia.repository.OcorrenciaRepository;

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

}
