package com.carbigdata.api_ocorrencia.model.mapper;

import org.mapstruct.Mapper;

import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.vo.ClienteVO;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

	ClienteEntity converterVOparaEntidade( ClienteVO clienteVO);

}
