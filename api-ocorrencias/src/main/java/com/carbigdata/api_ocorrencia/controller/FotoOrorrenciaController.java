package com.carbigdata.api_ocorrencia.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carbigdata.api_ocorrencia.service.FotoOrorrenciaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/imagens-ocorrencias")
@RequiredArgsConstructor
public class FotoOrorrenciaController {
	
	private final FotoOrorrenciaService FotoOrorrenciaService;
	
	@PostMapping("/upload")
	public String uploadEvidencia(@RequestParam("file") MultipartFile file, @RequestParam("ocorrenciaId") Long ocorrenciaId) {
		return FotoOrorrenciaService.uploadEvidencia(file,ocorrenciaId);
		
	}
	
	@GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadEvidencia(@PathVariable String fileName) {
		return FotoOrorrenciaService.downloadEvidencia(fileName);
	}

}
