package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.EnderecoDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.Endereco;
import br.com.cotiinformatica.exceptions.EntityNotFoundException;
import br.com.cotiinformatica.repositories.EnderecoRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class EnderecoService {

	private final EnderecoRepository enderecoRepository;
	private final ModelMapper mapper;

	public Endereco cadastrar(EnderecoDTO dto) {

		// buscando um endereço existente no banco
		Optional<Endereco> result = enderecoRepository.findByLogradouroAndNumeroAndComplementoAndCep(
				dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getCep());

		// caso exista, retorne o mesmo endereço
		if (result.isPresent()) {
			Endereco endereco = result.get();

			return endereco;
		}
		
		// convertendo o dto em endereço
		Endereco endereco = dtoToEndereco(dto);
		enderecoRepository.save(endereco);

		return endereco;
	}

	public List<Endereco> buscarEnderecos() {

		List<Endereco> list = enderecoRepository.findAll();
		List<Endereco> listaEndereco = new ArrayList<Endereco>();

		for (Endereco endereco : list) {
			listaEndereco.add(endereco);
		}
		return listaEndereco;
	}

	public Endereco buscarId(Integer idEndereco) {

		Optional<Endereco> result = enderecoRepository.findById(idEndereco);

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Endereço não encontrado.");
		}

		Endereco endereco = result.get();

		return endereco;
	}

	public Endereco atualizar(EnderecoDTO dto) {
		
		// buscando um endereço existente no banco
		Optional<Endereco> result = enderecoRepository.findById(dto.getIdEndereco());
		
		// Endereço encontrado
		Endereco endereco = result.get();
	
		// recebendo lista de clientes do endereço
		Set<Cliente> lista = endereco.getClientes();

		if (lista.size() > 1) {
			// se o mesmo endereço pertencer a outro cliente
			Endereco end = mapper.map(dto, Endereco.class);
			end.setIdEndereco(null);
			enderecoRepository.save(end);
			return end;
		}
		
		// endereco do dto
		Endereco end2 = dtoToEndereco(dto);
		if(endereco.equals(end2)){
			return endereco;
		}
		 
		// convertendo dto em Endereco
		endereco = dtoToEndereco(dto);

		// salvando endereço atualizado
		enderecoRepository.save(endereco);

		return endereco;
	}

	public String excluir(Integer idEndereco) {

		Optional<Endereco> result = enderecoRepository.findById(idEndereco);

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Endereço não encontrado.");
		}

		Endereco endereco = result.get();
		
		// recebendo lista de clientes do endereço
		Set<Cliente> lista = endereco.getClientes();
		
		// não deletar caso o endereço pertença a mais de um Cliente
		if(lista.size() > 1) {
			return null;
		}

		enderecoRepository.delete(endereco);

		return "Endereço excluído com sucesso.";
	}

    // Método para converter um EnderecoDTO em Endereco
	public Endereco dtoToEndereco(EnderecoDTO dto) {
		Endereco endereco = mapper.map(dto, Endereco.class);
		return endereco;
	}

}
