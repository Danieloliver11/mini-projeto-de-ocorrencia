package com.carbigdata.api_ocorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carbigdata.api_ocorrencia.model.entity.FotoOcorrenciaEntity;

@Repository
public interface FotoOcorrenciaRepository extends JpaRepository<FotoOcorrenciaEntity, Long> {

}
