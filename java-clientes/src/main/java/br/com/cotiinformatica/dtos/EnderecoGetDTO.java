package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoGetDTO {

	private Integer idEndereco;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String estado;
	private String cep;
	
}
