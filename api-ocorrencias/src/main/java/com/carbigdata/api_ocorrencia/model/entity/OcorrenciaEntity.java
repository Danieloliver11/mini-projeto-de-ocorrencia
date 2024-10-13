package com.carbigdata.api_ocorrencia.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "OCORRENCIA")
public class OcorrenciaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ocorrencia_seq")
	@SequenceGenerator(name = "ocorrencia_seq", sequenceName = "ocorrencia_seq", allocationSize = 1)
	@Column(name = "cod_ocorrencia")
	@Include
	private Long id;

	@JoinColumn(name = "cod_cliente", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ClienteEntity cliente;

	@JoinColumn(name = "cod_endereco", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private EnderecoEntity endereco;

	@Column(name = "dta_ocorrencia", nullable = false)
	private LocalDate dataOcorrencia;

	@Column(name = "sta_ocorrencia", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private StatusOcorrenciaEnum status;
	
	@PrePersist
	private void onCreate() {
		dataOcorrencia = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDate();
	}

}
