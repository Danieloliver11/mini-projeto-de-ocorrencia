package com.carbigdata.api_ocorrencia.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.vo.ClienteVO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	ClienteEntity converterVOparaEntidade( ClienteVO clienteVO);

	ClienteVO converterEntidadeparaVO(ClienteEntity clienteEntity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "target.nome", source = "source.nome" )
	void atualizarEntidadePeloVO(@MappingTarget ClienteEntity target, ClienteVO source);

}
