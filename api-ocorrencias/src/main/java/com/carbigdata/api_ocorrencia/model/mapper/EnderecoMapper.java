package com.carbigdata.api_ocorrencia.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.carbigdata.api_ocorrencia.model.entity.EnderecoEntity;
import com.carbigdata.api_ocorrencia.model.vo.EnderecoVO;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "locradouro", source = "locradouro")
	EnderecoEntity atualizarEndereco(@MappingTarget EnderecoEntity enderecoEntity, EnderecoVO enderecoVO);

}
