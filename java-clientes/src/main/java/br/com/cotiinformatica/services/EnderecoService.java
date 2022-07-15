package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.EnderecoDTO;
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
		Optional<Endereco> result = enderecoRepository.findByNumeroAndCepAndComplemento(
				dto.getNumero(), dto.getCep(), dto.getComplemento());

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
		Optional<Endereco> result = enderecoRepository.findByNumeroAndCepAndComplemento(
				dto.getNumero(), dto.getCep(), dto.getComplemento());
		
		// caso exista, retorne o mesmo endereço
		if(result.isPresent()) {
			Endereco endereco = result.get();
			return endereco;
		}
		
		Optional<Endereco> result2 = enderecoRepository.findById(dto.getIdEndereco());
		
		if (result2.isEmpty()) {
			throw new EntityNotFoundException("Endereço não encontrado.");
		}

		Endereco endereco = result2.get();
		endereco = dtoToEndereco(dto);

		enderecoRepository.save(endereco);

		return endereco;
	}

	public String excluir(Integer idEndereco) {

		Optional<Endereco> result = enderecoRepository.findById(idEndereco);

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Endereço não encontrado.");
		}

		Endereco endereco = result.get();

		enderecoRepository.delete(endereco);

		return "Endereço excluído com sucesso.";
	}

// Método para converter um EnderecoDTO em Endereco
	public Endereco dtoToEndereco(EnderecoDTO enderecoDto) {
		Endereco endereco = mapper.map(enderecoDto, Endereco.class);
		return endereco;
	}

}
