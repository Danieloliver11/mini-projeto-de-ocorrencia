package com.carbigdata.api_ocorrencia.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	
	@NotBlank(message = "Cpf é obrigatório")
	private String cpf;
	@NotBlank(message = "Password é obrigatório")
    private String password;

}
