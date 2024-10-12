package com.carbigdata.api_ocorrencia.exceptions.handler;

import java.util.List;

import lombok.Builder;

@Builder
public record ExceptionResponseVO(
		 String type,
		 String title,
		 List<String> detail,
		 String instance) {

}
