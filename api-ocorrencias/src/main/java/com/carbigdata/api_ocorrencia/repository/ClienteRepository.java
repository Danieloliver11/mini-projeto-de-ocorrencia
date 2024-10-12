package com.carbigdata.api_ocorrencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;

import jakarta.validation.constraints.NotBlank;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{

	boolean existsByCpf(String cpf);

}
