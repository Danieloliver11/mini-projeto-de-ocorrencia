package com.carbigdata.api_ocorrencia.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String uploadEvidencia(@RequestParam() MultipartFile file, @RequestParam() Long ocorrenciaId) {
		return FotoOrorrenciaService.uploadEvidencia(file,ocorrenciaId);
		
	}
	
	@PostMapping("/uploads")
	public String uploadsEvidencias(@RequestParam("file") List<MultipartFile> files, @RequestParam() Long ocorrenciaId) {
		return FotoOrorrenciaService.uploadsEvidencias(files,ocorrenciaId);
		
	}
	
	@GetMapping("/download")
    public ResponseEntity<Resource> downloadEvidencia(@RequestParam String fileName) {
		return FotoOrorrenciaService.downloadEvidencia(fileName);
	}
	
    @DeleteMapping("/delete")
    public void deleteArquivo(@RequestParam String fileName) {
    	FotoOrorrenciaService.deleteArquivo(fileName);
    }



}
