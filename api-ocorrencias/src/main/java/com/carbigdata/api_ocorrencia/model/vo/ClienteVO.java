package com.carbigdata.api_ocorrencia.model.vo;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ClienteVO(
		Long id,
		
		@NotBlank(message = "Nome deve ser informado")
		@Size(max = 30, message = "O número máximo de caracteres para nome permitido é 30.")
		String nome,
		
		@NotNull
		LocalDate dataNascimento,
		
		@NotBlank(message = "Cpf deve ser informado")
		@CPF
		String cpf,
		
		@NotBlank(message = "Senha deve ser informado")
		@Size(max = 10, message = "O número máximo de caracteres para  senha permitido é 10.")
		String senha
		 ) {

}
