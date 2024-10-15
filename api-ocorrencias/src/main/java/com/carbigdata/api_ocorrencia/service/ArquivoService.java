package com.carbigdata.api_ocorrencia.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArquivoService {
	
	@Autowired
    private MinioClient minioClient;
	
	public void upload(String bucket, String path, String nomeArquivo, MultipartFile file) {
		
		 try {
			 
			 String contentType = file.getContentType();
		        if (contentType == null) {
		            contentType = "application/octet-stream"; // Tipo genérico para arquivos binários
		        }

			 
			minioClient.putObject(PutObjectArgs.builder()
			            .bucket(bucket)
			            .object(path)
			            .stream(file.getInputStream(), file.getSize(), -1)
			            .contentType(contentType)
			            .build());
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			
			e.printStackTrace();
		}
		
	}

	public ResponseEntity<Resource> download(String fileName, String bucket)  {
		 try {
	            // Faz o download do arquivo do MinIO
	            InputStream stream = null;
				
	            try {
					stream = minioClient.getObject(GetObjectArgs.builder()
					    .bucket(bucket)
					    .object("evidencias/" + fileName)
					    .build());
					
				} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            InputStreamResource resource = new InputStreamResource(stream);

	            // Retorna a resposta com o Resource
	            return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
	                .body(resource);
	        } catch (MinioException e) {
	            // Erro ao acessar o MinIO
	            return ResponseEntity.status(500).body(null);
	        } catch (IOException e) {
	            // Erro ao ler o InputStream
	            return ResponseEntity.status(500).body(null);
	        }
	    }
	

}
