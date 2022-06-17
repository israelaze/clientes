package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.Endereco;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.exceptions.EntityNotFoundException;
import br.com.cotiinformatica.repositories.ClienteRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final EnderecoService endService;
	private final ModelMapper mapper;

	public ClienteGetDTO cadastrar(ClientePostDTO dto) {

		// verificar se o CPF já está cadastrado
		Optional<Cliente> result = clienteRepository.findByCpf(dto.getCpf());
		if (result.isPresent()) {
			throw new BadRequestException("O CPF informado já encontra-se cadastrado. Tente outro.");
		}

		// cadastrando e retornando um endereço
		Endereco endereco = endService.cadastrar(dto.getEndereco());

		// cadastrando novo Cliente
		Cliente cliente = new Cliente();
		mapper.map(dto, cliente);
		cliente.setEndereco(endereco);

		clienteRepository.save(cliente);

		// passando o cliente para um dto
		ClienteGetDTO getDto = new ClienteGetDTO();
		mapper.map(cliente, getDto);

		return getDto;
	}

	public List<ClienteGetDTO> buscarTodos() {

		// declarando uma lista da classe ClienteGetDTO
		List<ClienteGetDTO> lista = new ArrayList<ClienteGetDTO>();

		// consultar e percorrer os clientes obtidos no banco de dados..
		for (Cliente cliente : clienteRepository.findAll()) {

			// transferindo os dados do cliente pro objeto dto
			ClienteGetDTO dto = new ClienteGetDTO();
			mapper.map(cliente, dto);

			// adicionar os clientes um a um na lista
			lista.add(dto);
		}

		return lista;
	}

	public ClienteGetDTO buscarId(Integer idCliente) {

		// procurar o cliente no banco de dados atraves do id
		Optional<Cliente> result = clienteRepository.findById(idCliente);

		// verificar se o cliente não foi encontrado..
		if (result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}

		// obter os dados do cliente encontrado
		Cliente cliente = result.get();

		// criando objeto para receber os dados
		ClienteGetDTO dto = new ClienteGetDTO();

		// transferindo os dados da entidade pro dto
		mapper.map(cliente, dto);

		return dto;
	}

	public ClienteGetDTO atualizar(ClientePutDTO dto) {

		// procurar o cliente no banco de dados atraves do id..
		Optional<Cliente> result = clienteRepository.findById(dto.getIdCliente());

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}

		// obter os dados do cliente encontrado
		Cliente cliente = result.get();

		// transferindo os dados do dto para o cliente
		mapper.map(dto, cliente);

		// salvando o cliente atualizado no banco
		clienteRepository.save(cliente);

		// passando o cliente para um dto
		ClienteGetDTO getDto = new ClienteGetDTO();
		mapper.map(cliente, getDto);

		return getDto;
	}

	public String excluir(Integer idCliente) {

		// procurar o cliente no banco de dados atraves do id
		Optional<Cliente> result = clienteRepository.findById(idCliente);

		// verificar se o cliente não foi encontrado..
		if (result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}

		// obter os dados do cliente encontrado
		Cliente cliente = result.get();

		clienteRepository.delete(cliente);

		String response = "Cliente " + cliente.getNome() + " excluído com sucesso.";
		return response;
	}

}
