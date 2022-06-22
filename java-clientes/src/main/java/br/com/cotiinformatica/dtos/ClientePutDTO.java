package br.com.cotiinformatica.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientePutDTO {

	@NotNull
	private Integer idCliente;

	@NotBlank(message = "{nome.not.blank}")
	private String nome;
	
	@NotBlank(message = "{telefone.not.blank}")
	@Size(min= 8, max = 15, message = "{telefone.size}")
	private String telefone;

	@Email(message = "{email.email}")
	private String email;
	
	private String observacao;
	
	@NotNull
	private Integer idEndereco;

	@NotBlank(message = "{logradouro.not.blank}")
	private String logradouro;

	@NotBlank(message = "{numero.not.blank}")
	private String numero;

	private String complemento;

	@NotBlank(message = "{bairro.not.blank}")
	private String bairro;
	
	private String municipio;
	private String estado;
	
	@Size(max= 9, message = "{cep.size}")
	private String cep;
}
