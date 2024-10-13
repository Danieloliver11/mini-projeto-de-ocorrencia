package com.carbigdata.api_ocorrencia.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record EnderecoVO(
		Long id,
		@NotBlank(message = "Locradouro é obrigatório")
		@Size(max = 60, message = "O número máximo de caracteres para  locradouro permitido é 60.")
		String locradouro,
		@NotBlank(message = "Bairro é obrigatório")
		@Size(max = 30, message = "O número máximo de caracteres para bairro permitido é 30.")
		String bairro,
		@NotBlank(message = "Cep é obrigatório")
		@Size(max = 8, message = "O número máximo de caracteres para o cep permitido é 8.")
		String cep,
		@NotBlank(message = "Nome da cidade é obrigatório")
		@Size(max = 30, message = "O número máximo de caracteres para nome da cidade permitido é 30.")
		String nomeCidade,
		@NotBlank(message = "Nome do estado é obrigatório")
		@Size(max = 30, message = "O número máximo de caracteres para o nome do estado permitido é 30.")
		String nomeEstado
		) {

}