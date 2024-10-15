package com.carbigdata.api_ocorrencia.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaResponseVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaVO;

@Mapper(componentModel = "spring")
public interface OcorrenciaMapper {

	@Mapping(target = "id", ignore = true)
    @Mapping(target = "endereco.id", ignore = true) 
	@Mapping(target = "status", source =  "status")
	OcorrenciaEntity converterVOparaEntidade(OcorrenciaVO ocorrencia, ClienteEntity cliente, StatusOcorrenciaEnum status);

	@Mapping(target = "nome", expression = "java(ocorrenciaEntity.getCliente().getNome())")
	@Mapping(target = "cpf", expression = "java(ocorrenciaEntity.getCliente().getCpf())")
	@Mapping(target = "endereco.locradouro", source = "ocorrenciaEntity.endereco.locradouro")
	OcorrenciaVO converterEntidadeParaVO(OcorrenciaEntity ocorrenciaEntity, String nomeArquivos);

	default Page<OcorrenciaResponseVO> converterEntityPageParaPageVo(Page<OcorrenciaEntity> ocorenciasPage){
		return ocorenciasPage.map(this::converterEntidadeParaOcorrenciaResponseVO) ;
	}

	@Mapping(target = "cliente.senha", ignore = true)
	@Mapping(target = "endereco.locradouro", source =  "endereco.locradouro")
	@Mapping(target = "cliente.nome", source =  "cliente.nome")
	OcorrenciaResponseVO converterEntidadeParaOcorrenciaResponseVO(OcorrenciaEntity ocorrenciaEntity);
	
}





