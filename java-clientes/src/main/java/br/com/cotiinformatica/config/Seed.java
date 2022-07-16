package br.com.cotiinformatica.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.Endereco;
import br.com.cotiinformatica.enums.Estados;
import br.com.cotiinformatica.repositories.ClienteRepository;
import br.com.cotiinformatica.repositories.EnderecoRepository;
import lombok.AllArgsConstructor;

@Configuration
@Profile("test")
@AllArgsConstructor
public class Seed implements CommandLineRunner{

	private final EnderecoRepository enderecoRepository;
	private final ClienteRepository clienteRepository;
	
	@Override
	public void run(String... args) throws Exception {

	// CADASTRANDO ENDEREÇOS
	Endereco end1 = new Endereco(null, "Av Robert", "500", "Apto 20", "Bnh", "Mesquita", Estados.RJ, "00000000");
	Endereco end2 = new Endereco(null, "Rua Alves", "40", "", "Barra Funda", "São Paulo", Estados.SP, "11111111");
	Endereco end3 = new Endereco(null, "Rua Rufino", "23", "Edificio Solar, Sala 99", "Abranches", "Curitiba", Estados.PR, "33333333");
	
	enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));

	// CADASTRANDO CLIENTES
	Cliente cli1 = new Cliente(null, "Edy Silva", "111111111-11", "21955555555", "edy@bol.com", "", end3);
	Cliente cli2 = new Cliente(null, "Ana Silva", "222222222-11", "21222222222", "eana@bol.com", "", end2);
	Cliente cli3 = new Cliente(null, "Bia Silva", "333333333-11", "95555555522", "bia@bol.com", "", end3);
	Cliente cli4 = new Cliente(null, "Tom Silva", "444444444-11", "88966666622", "tom@bol.com", "", end1);
	Cliente cli5 = new Cliente(null, "Ivo Silva", "555555555-11", "23500000000", null, "sem email", end1);
	
	clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4, cli5));
	
	}
	
}
