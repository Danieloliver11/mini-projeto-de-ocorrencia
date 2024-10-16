package com.carbigdata.api_ocorrencia.service;

import java.time.LocalDate;
import java.util.List;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FotoOrorrenciaService {
	
	 	@Value("${minio.bucket}")
	    private String bucket;
	 	
	 	@Value("${minio.filePath}")
	    private String filePath;


	 	
	 	private final FotoOcorrenciaRepository fotoOcorrenciaRepository;
	 	
	 	private final ArquivoService arquivoService;
	 	private final OcorrenciaService ocorrenciaService;
	
		@Transactional
		public String uploadEvidencia(MultipartFile file, Long ocorrenciaId) {

			String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
			String path = filePath + "/" + nomeArquivo;

			OcorrenciaEntity ocorrenciaEntity = ocorrenciaService.recuperarOcorrenciaPorId(ocorrenciaId);

			ocorrenciaService.verificarUsuarioLogadoParaAtualizaOcorrenciar(ocorrenciaEntity.getCliente().getId());
			ocorrenciaService.verificarOcorrenciaFinalizada(ocorrenciaEntity);


			FotoOcorrenciaEntity foto = FotoOcorrenciaEntity.builder().ocorrencia(ocorrenciaEntity).pathBucket(path)
					.discHash(nomeArquivo).dataCriacao(LocalDate.now()).build();

			fotoOcorrenciaRepository.save(foto);

			arquivoService.upload(bucket, path, nomeArquivo, file);

			return nomeArquivo;
		}

	public String uploadsEvidencias(List<MultipartFile> files, Long ocorrenciaId) {
		StringBuilder uploadedFileNames = new StringBuilder();

		OcorrenciaEntity ocorrenciaEntity = ocorrenciaService.recuperarOcorrenciaPorId(ocorrenciaId);
		ocorrenciaService.verificarUsuarioLogadoParaAtualizaOcorrenciar(ocorrenciaEntity.getCliente().getId());
		ocorrenciaService.verificarOcorrenciaFinalizada(ocorrenciaEntity);


		for (MultipartFile file : files) {
			String uploadedFileName = uploadEvidencia(file, ocorrenciaId);
			uploadedFileNames.append(uploadedFileName).append(", ");
		}
		
	    uploadedFileNames.setLength(uploadedFileNames.length() - 2);

		return uploadedFileNames.toString();
	}

	public ResponseEntity<Resource> downloadEvidencia(String fileName) {
		
    	FotoOcorrenciaEntity fotoOcorrenciaEntity = recuperarFotoOcorrenciaPorDiscHash(fileName);

		ocorrenciaService.verificarUsuarioLogadoParaAtualizaOcorrenciar(fotoOcorrenciaEntity.getOcorrencia().getCliente().getId());
		ocorrenciaService.verificarOcorrenciaFinalizada(fotoOcorrenciaEntity.getOcorrencia());


		
		return arquivoService.download(fileName,bucket,filePath);
	}

	@Transactional
	public void deleteArquivo(String fileName) {

		FotoOcorrenciaEntity fotoOcorrenciaEntity = recuperarFotoOcorrenciaPorDiscHash(fileName);
		
		ocorrenciaService.verificarUsuarioLogadoParaAtualizaOcorrenciar(
				fotoOcorrenciaEntity.getOcorrencia().getCliente().getId());
		ocorrenciaService.verificarOcorrenciaFinalizada(fotoOcorrenciaEntity.getOcorrencia());


		String pathBucket = fotoOcorrenciaEntity.getPathBucket();
		fotoOcorrenciaRepository.deleteByDiscHash(fileName);

		arquivoService.deleteArquivo(pathBucket, bucket);

	}

	private FotoOcorrenciaEntity recuperarFotoOcorrenciaPorDiscHash(String fileName) {
		return fotoOcorrenciaRepository.findByDiscHash(fileName).orElseThrow(()->
    	new NaoEncontradoException("Não foi encontrado nenhuma foto da ocorrência"));
	}



}
