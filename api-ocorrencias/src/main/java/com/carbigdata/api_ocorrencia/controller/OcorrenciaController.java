package com.carbigdata.api_ocorrencia.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaFiltroVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaResponseVO;
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
	
	@PutMapping //TODO atualizar IMAGEM 
	public OcorrenciaVO atualizarOcorrencia(@Valid @RequestBody OcorrenciaVO ocorrencia) {
		return ocorrenciaService.atualizarOcorrencia(ocorrencia);
	}
	
	@PutMapping("finalizar-ocorrencias/{id}")
	public OcorrenciaResponseVO finalizarOcorrenciaPorId(@PathVariable Long id) {
		return ocorrenciaService.finalizarOcorrenciaPorId(id);
	}
	
	@GetMapping("filtrar-ocorrencias")
	private Page<OcorrenciaResponseVO> filtrarOcorrencias(OcorrenciaFiltroVO filtro,boolean asc, @PageableDefault(size = 5) Pageable pageable) {
		return ocorrenciaService.filtrarOcorrencias(filtro, asc, pageable);
	}
	
	
	

}
