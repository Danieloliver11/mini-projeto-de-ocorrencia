package com.carbigdata.api_ocorrencia.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode.Include;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ENDERECO")
public class FotoOcorrenciaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "foto_ocorrencia_seq")
	@SequenceGenerator(name = "foto_ocorrencia_seq", sequenceName = "foto_ocorrencia_seq", allocationSize = 1)
	@Column(name = "cod_foto_ocorrencia")
	@Include
	private Long id;

	@JoinColumn(name = "cod_ocorrencia", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private OcorrenciaEntity ocorrencia;

	@Column(name = "disc_path_bucket", length = 255, nullable = false)
	private String pathBucket;

	@Column(name = "disc_hash", length = 36, nullable = false)
	private String discHash;

	@Column(name = "dt_criacao")
	private LocalDate dataCriacao;

}