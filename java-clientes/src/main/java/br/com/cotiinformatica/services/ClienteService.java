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
import br.com.cotiinformatica.dtos.EnderecoDTO;
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

	public ClienteGetDTO cadastrar(ClientePostDTO dto) throws Exception {

		// verificar se o CPF já está cadastrado(obrigatório)
		Optional<Cliente> result = clienteRepository.findByCpf(dto.getCpf());
		if (result.isPresent()) {
			throw new BadRequestException("O CPF informado já encontra-se cadastrado. Tente outro.");
		}
	
		// verificar se o telefone já está cadastrado(obrigatório)
		Optional<Cliente> result2 = clienteRepository.findByTelefone(dto.getTelefone());
		if (result2.isPresent()) {
			throw new BadRequestException("O telefone informado já encontra-se cadastrado. Tente outro.");
		}
		
		// Se usuário inserir email, verificar se ele já está cadastrado	
		if (dto.getEmail() != null) {
			Optional<Cliente> result3 = clienteRepository.findByEmail(dto.getEmail());
			if (result3.isPresent()) {
				throw new BadRequestException("O email informado já encontra-se cadastrado. Tente outro.");
			}
		}
		
		// convertendo um objeto dto(Endereco) em postDto(EnderecoDto)
		EnderecoDTO enderecoDto = mapper.map(dto, EnderecoDTO.class);
		
		// cadastrando um endereçoDto e retornando um endereço
		Endereco endereco = endService.cadastrar(enderecoDto);

		// cadastrando novo Cliente
		Cliente cliente = mapper.map(dto, Cliente.class);
		// setando o endereço ao cliente
		cliente.setEndereco(endereco);
		
		clienteRepository.save(cliente);

		// convertendo o cliente em dto e retornando
		return clienteToDto(cliente);
	}

	public List<ClienteGetDTO> buscarTodos() {

		// declarando uma lista da classe ClienteGetDTO
		List<ClienteGetDTO> lista = new ArrayList<ClienteGetDTO>();

		// consultar e percorrer os clientes obtidos no banco de dados..
		for (Cliente cliente : clienteRepository.findAll()) {

			// convertendo os clientes em dto e adicionando um a um na lista
			lista.add(clienteToDto(cliente));
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

		return clienteToDto(cliente);
	}

	public ClienteGetDTO atualizar(ClientePutDTO dto) {

		// procurar o cliente no banco de dados atraves do id..
		Optional<Cliente> result = clienteRepository.findById(dto.getIdCliente());

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}
		
		// Cliente encontrado
		Cliente cliente = result.get();
		
		
		// verificar se o telefone já está cadastrado(obrigatório)
		Optional<Cliente> result2 = clienteRepository.findByTelefone(dto.getTelefone());
		if (result2.isPresent()) {
			if(result2.get().getIdCliente() != cliente.getIdCliente()) {
				throw new BadRequestException("O telefone informado já encontra-se cadastrado. Tente outro.");
			}
		}

		// Se usuário inserir email, verificar se ele já está cadastrado
		if (dto.getEmail() != null) {
			Optional<Cliente> result3 = clienteRepository.findByEmail(dto.getEmail());
			if (result3.isPresent()) {
				if(result3.get().getIdCliente() != cliente.getIdCliente()) {
					throw new BadRequestException("O email informado já encontra-se cadastrado. Tente outro.");
				}
			}
		}

		// Endereco do cliente
		Endereco end1 = cliente.getEndereco();
		
		// convertendo os dados do dto(Endereco) em EnderecoDto
		EnderecoDTO enderecoDto = mapper.map(dto, EnderecoDTO.class);
		// setando o idEndereco do endereço do Cliente
		enderecoDto.setIdEndereco(end1.getIdEndereco());
		
		// atualizando o endereço no banco
		Endereco endereco = endService.atualizar(enderecoDto);
		
		// transferindo os dados do dto para o cliente
		mapper.map(dto, cliente);
		if(end1.getIdEndereco() != endereco.getIdEndereco()) {
			cliente.setEndereco(endereco);
		}
		
		// salvando o cliente atualizado no banco
		clienteRepository.save(cliente);

		// convertendo o cliente em dto e retornando
		return clienteToDto(cliente);
	}

	public String excluir(Integer idCliente) {

		// procurar o cliente no banco de dados atraves do id
		Optional<Cliente> result = clienteRepository.findById(idCliente);

		// verificar se o cliente foi encontrado..
		if (result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}

		// Cliente
		Cliente cliente = result.get();
		// Endereço do cliente
		Endereco endereco = cliente.getEndereco();

		// Excluindo cliente e seu endereço
		clienteRepository.delete(cliente);
		endService.excluir(endereco.getIdEndereco());
		
		String response = "Cliente " + cliente.getNome() + " excluído com sucesso.";
		return response;
	}

	// Método para converter um Cliente em getDto
	public ClienteGetDTO clienteToDto(Cliente cliente) {
		ClienteGetDTO getDto = mapper.map(cliente, ClienteGetDTO.class);
		return getDto;
	}

}
