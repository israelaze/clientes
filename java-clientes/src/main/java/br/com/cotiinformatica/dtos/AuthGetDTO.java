//Classe para retornar os dados de um Usuário Autenticado para o Angular

package br.com.cotiinformatica.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthGetDTO {

	//
	private Integer idUsuario;
	private String nome;
	private String email;
	private String accessToken;
	//
}


