package com.carbigdata.api_ocorrencia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.repository.specification.OcorrenciaSpecification;

@Repository
public interface OcorrenciaRepository extends JpaRepository<OcorrenciaEntity, Long> {

	Page<OcorrenciaEntity> findAll(Specification<OcorrenciaEntity>  ocorrenciaSpecification, Pageable pageable);


}
