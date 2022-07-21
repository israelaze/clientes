package br.com.cotiinformatica.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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
	
	@Column(length = 60, nullable = false)
	private String bairro;
	
	@Column(length = 60)
	private String municipio;
	
	@Enumerated(EnumType.STRING)
	private Estados estado;
	
	@Column(length = 15)
	private String cep;
	
	@OneToMany(mappedBy = "endereco")
	private Set<Cliente> clientes = new HashSet<>();

	public Endereco(Integer idEndereco, String logradouro, String numero, String complemento, String bairro,
			String municipio, Estados estado, String cep) {
		this.idEndereco = idEndereco;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.municipio = municipio;
		this.estado = estado;
		this.cep = cep;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(complemento, other.complemento) && Objects.equals(logradouro, other.logradouro)
				&& Objects.equals(municipio, other.municipio) && Objects.equals(numero, other.numero);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, complemento, logradouro, municipio, numero);
	}
		
}
