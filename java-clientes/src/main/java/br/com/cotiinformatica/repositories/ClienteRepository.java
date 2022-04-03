package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	// Busca um cliente pelo seu cpf 
	@Query("from Cliente c where c.cpf = :param") //JPQL
	Cliente findByCpf(@Param("param") String cpf);
	
}
