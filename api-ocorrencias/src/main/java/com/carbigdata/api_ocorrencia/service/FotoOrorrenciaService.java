package com.carbigdata.api_ocorrencia.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.model.entity.FotoOcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.repository.FotoOcorrenciaRepository;
import com.carbigdata.api_ocorrencia.repository.OcorrenciaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FotoOrorrenciaService {
	
	 	@Value("${minio.bucket}")
	    private String bucket;
	 	
	 	private final FotoOcorrenciaRepository fotoOcorrenciaRepository;
	 	private final OcorrenciaRepository ocorrenciaRepository;
	 	
	 	private final ArquivoService arquivoService;
	
	@Transactional
	public String uploadEvidencia(MultipartFile file, Long ocorrenciaId) {
		
	
        String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String path = "evidencias/" + nomeArquivo;
        
        OcorrenciaEntity ocorrenciaEntity = ocorrenciaRepository.findById(ocorrenciaId).orElseThrow(
        		() -> new NaoEncontradoException("Ocorrencia não encontrada"));
        
        FotoOcorrenciaEntity foto = FotoOcorrenciaEntity.builder()
                .ocorrencia(ocorrenciaEntity)
                .pathBucket(path)
                .discHash(nomeArquivo)
                .dataCriacao(LocalDate.now())
                .build();
        
        fotoOcorrenciaRepository.save(foto);

        arquivoService.upload(bucket, path, nomeArquivo, file);
	
		return nomeArquivo;
	}

	public ResponseEntity<Resource> downloadEvidencia(String fileName) {
		
		
		return arquivoService.download(fileName,bucket);
	}

}
