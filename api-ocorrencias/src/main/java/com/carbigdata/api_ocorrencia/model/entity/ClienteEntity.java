package com.carbigdata.api_ocorrencia.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CLIENTE")
public class ClienteEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
	@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", allocationSize = 1)
	@Column(name = "cod_cliente")
	@Include
	private Long id;

	@Column(name = "nme_cliente", length = 30, nullable = false)
	private String nome;

	@Column(name = "dt_nascimento")
	private LocalDate dataNascimento;

	@Column(name = "nmo_cpf", length = 11)
	private String cpf;

	@Column(name = "dt_criacao")
	private LocalDate dataCriacao;
	
	@PrePersist
	private void onCreate() {
		dataCriacao = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDate();
	}

}
