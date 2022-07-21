package br.com.cotiinformatica.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

	Optional<Endereco> findByLogradouroAndNumeroAndComplementoAndCep(String logradouro, String numero, String Complemento, String cep);

}
