package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoApiDTO {

	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;
	private String cep;
	
}
