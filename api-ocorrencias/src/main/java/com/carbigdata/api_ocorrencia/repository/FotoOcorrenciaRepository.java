package com.carbigdata.api_ocorrencia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.carbigdata.api_ocorrencia.model.entity.FotoOcorrenciaEntity;

@Repository
public interface FotoOcorrenciaRepository extends JpaRepository<FotoOcorrenciaEntity, Long> {

	@Modifying
	void deleteByDiscHash(String fileName);

	Optional<FotoOcorrenciaEntity> findByDiscHash(String fileName);

}
