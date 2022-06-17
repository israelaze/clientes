package br.com.cotiinformatica.dtos;

import lombok.Getter;

@Getter
public class EnderecoPutDTO {

	private Integer idEndereco;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String municipio;
	private String estado;
	private String cep;
	private String observacao;
 
}
