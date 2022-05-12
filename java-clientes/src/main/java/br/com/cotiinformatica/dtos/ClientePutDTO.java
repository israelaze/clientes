package br.com.cotiinformatica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class ClientePutDTO {
	
	@NotNull
	private Integer idCliente;

	@NotBlank(message = "{nome.not.blank}")
	private String nome;

	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.email}")
	private String email;

}
