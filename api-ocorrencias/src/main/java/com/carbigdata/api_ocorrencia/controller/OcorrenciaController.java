package com.carbigdata.api_ocorrencia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaVO;
import com.carbigdata.api_ocorrencia.service.OcorrenciaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OcorrenciaController {
	
	private final OcorrenciaService ocorrenciaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //TODO IMAGEM
	public OcorrenciaVO salvarOcorrencia(@Valid @RequestBody OcorrenciaVO ocorrencia) {
		return ocorrenciaService.salvarOcorrencia(ocorrencia);
	}

}
