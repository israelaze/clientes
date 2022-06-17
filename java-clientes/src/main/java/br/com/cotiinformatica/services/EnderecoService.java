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

	public Endereco cadastrar(EnderecoPostDTO dto) {

		// buscando um endereço
		Optional<Endereco> result = enderecoRepository.findByNumeroAndCepAndComplemento(dto.getNumero(), dto.getCep(),
				dto.getComplemento());

		// retornando endereço já cadastrado(Não cadastro um endereço já cadstrado)
		if (result.isPresent()) {
			Endereco endereco = result.get();
			return endereco;
		}

		// cadastrando e salvando novo endereço
		Endereco endereco = new Endereco();
		mapper.map(dto, endereco);
		enderecoRepository.save(endereco);

		return endereco;
	}

	public List<EnderecoGetDTO> buscarEnderecos() {

		List<Endereco> list = enderecoRepository.findAll();
		List<EnderecoGetDTO> listaGetDto = new ArrayList<EnderecoGetDTO>();

		for (Endereco endereco : list) {
			EnderecoGetDTO getDto = new EnderecoGetDTO();
			mapper.map(endereco, getDto);

			listaGetDto.add(getDto);
		}

		return listaGetDto;
	}

	public EnderecoGetDTO buscarId(Integer idEndereco) {

		Optional<Endereco> result = enderecoRepository.findById(idEndereco);

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Endereço não encontrado.");
		}

		Endereco endereco = result.get();

		EnderecoGetDTO getDto = new EnderecoGetDTO();
		mapper.map(endereco, getDto);

		return getDto;
	}

	public EnderecoGetDTO atualizar(EnderecoPutDTO dto) {

		// buscando um endereço
		Optional<Endereco> result = enderecoRepository.findById(dto.getIdEndereco());

		Endereco endereco = result.get();
		mapper.map(dto, endereco);

		enderecoRepository.save(endereco);

		EnderecoGetDTO getDto = new EnderecoGetDTO();
		mapper.map(endereco, getDto);

		return getDto;
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

}
