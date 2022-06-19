package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.EnderecoGetDTO;
import br.com.cotiinformatica.dtos.EnderecoPostDTO;
import br.com.cotiinformatica.dtos.EnderecoPutDTO;
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

	public EnderecoGetDTO cadastrar(EnderecoPostDTO dto) {

		// buscando um endereço existente no banco
		Optional<Endereco> result = enderecoRepository.findByNumeroAndCepAndComplemento(
				dto.getNumero(), dto.getCep(), dto.getComplemento());

		// caso exista
		if (result.isPresent()) {
			Endereco endereco = result.get();

			// convertendo o endereço em dto e retornando
			return getEndereco(endereco);
		}

		// convertendo o dto em endereço
		Endereco endereco = new Endereco();
		mapper.map(dto, endereco);
		
		enderecoRepository.save(endereco);

		// convertendo o endereço em dto e retornando
		return getEndereco(endereco);

	}

	public List<EnderecoGetDTO> buscarEnderecos() {

		List<Endereco> list = enderecoRepository.findAll();
		List<EnderecoGetDTO> listaGetDto = new ArrayList<EnderecoGetDTO>();

		for (Endereco endereco : list) {
			// convertendo o endereco em dto e adicionando na lista
			listaGetDto.add(getEndereco(endereco));
		}

		return listaGetDto;
	}

	public EnderecoGetDTO buscarId(Integer idEndereco) {

		Optional<Endereco> result = enderecoRepository.findById(idEndereco);

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Endereço não encontrado.");
		}

		Endereco endereco = result.get();

		// convertendo o endereco em dto e retornando
		return getEndereco(endereco);
	}

	public EnderecoGetDTO atualizar(EnderecoPutDTO dto) {

		// buscando um endereço existente no banco
		Optional<Endereco> result = enderecoRepository.findByNumeroAndCepAndComplemento(
				dto.getNumero(), dto.getCep(), dto.getComplemento());
		
		// caso exista
		if(result.isPresent()) {
			Endereco endereco = result.get();

			// convertendo o endereço em dto e retornando
			return getEndereco(endereco);
		}
		
		Optional<Endereco> result2 = enderecoRepository.findById(dto.getIdEndereco());

		Endereco endereco = result2.get();
		mapper.map(dto, endereco);

		enderecoRepository.save(endereco);

		// convertendo o endereco em dto e retornando
		return getEndereco(endereco);
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

	// Método para converter um Endereco em getDto
	public EnderecoGetDTO getEndereco(Endereco endereco) {
		EnderecoGetDTO getDto = new EnderecoGetDTO();
		mapper.map(endereco, getDto);

		return getDto;

	}

}
