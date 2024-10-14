package com.carbigdata.api_ocorrencia.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ENDERECO")
public class EnderecoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
	@SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_seq", allocationSize = 1)
	@Column(name = "cod_endereco")
	@Include
	private Long id;

	@Column(name = "nme_locradouro", length = 60, nullable = false)
	private String locradouro;

	@Column(name = "nme_bairro", length = 30, nullable = false)
	private String bairro;

	@Column(name = "nmr_cep", length = 8, nullable = false)
	private String cep;

	@Column(name = "nme_cidade", length = 30, nullable = false)
	private String nomeCidade;

	@Column(name = "nme_estado", length = 30, nullable = false)
	private String nomeEstado;

}
