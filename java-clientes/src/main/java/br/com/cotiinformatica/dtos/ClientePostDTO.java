package br.com.cotiinformatica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class ClientePostDTO {

	@NotBlank(message = "{nome.not.blank}")
	private String nome;

	@NotBlank(message = "{cpf.not.blank}")
	@Size(min= 11, max = 15, message = "{cpf.size}")
	private String cpf;

	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.email}")
	private String email;

}
