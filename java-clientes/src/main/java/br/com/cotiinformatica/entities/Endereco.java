package br.com.cotiinformatica.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.cotiinformatica.enums.Estados;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enderecos")
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEndereco")
	private Integer idEndereco;

	@Column(length = 60, nullable = false)
	private String logradouro;

	@Column(length = 10, nullable = false)
	private String numero;

	@Column(length = 60)
	private String complemento;
	
	@Column(length = 60)
	private String bairro;
	
	@Column(length = 60)
	private String municipio;
	
	@Enumerated(EnumType.STRING)
	private Estados estado;
	
	@Column(length = 15)
	private String cep;

	@Column(columnDefinition = "TEXT")
	private String observacao;

	@OneToMany(mappedBy = "endereco")
	private Set<Cliente> clientes = new HashSet<>();

}
