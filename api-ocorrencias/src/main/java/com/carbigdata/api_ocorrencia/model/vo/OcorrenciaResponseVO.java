package com.carbigdata.api_ocorrencia.model.vo;

import java.time.LocalDate;
import java.util.List;

import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;

import lombok.Builder;

@Builder
public record OcorrenciaResponseVO(
		Long id,
		LocalDate dataOcorrencia,
		StatusOcorrenciaEnum status,
		ClienteVO cliente,
		EnderecoVO endereco,
		List<ArquivosVO> arquivos
		) {

}
