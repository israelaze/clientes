package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteGetDTO {
	
	private Integer idCliente;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String observacao;
	private EnderecoGetDTO endereco;
}
