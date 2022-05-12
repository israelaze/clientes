package br.com.cotiinformatica.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity   
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "idcliente")
	private Integer idCliente;
	
	@Column(name = "nome", length = 60, nullable = false)
	private String nome;
	
	@Column(name = "cpf", length = 15, nullable = false, unique = true)
	private String cpf;
	
	@Column(name = "email", length = 60, nullable = false)
	private String email;
	
}
