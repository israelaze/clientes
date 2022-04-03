package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.exceptions.EntityNotFoundException;
import br.com.cotiinformatica.repositories.ClienteRepository;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class ClienteService {

	private final ClienteRepository repository;

	public ClienteGetDTO cadastrar(ClientePostDTO dto) {

		// verificar se o CPF já está cadastrado no banco de dados
		if (repository.findByCpf(dto.getCpf()) != null) {
			throw new BadRequestException("O CPF informado já encontra-se cadastrado. Tente outro.");
		}

		// inserir os dados do dto na entidade
		Cliente cliente = new Cliente();
		cliente.setNome(dto.getNome());
		cliente.setCpf(dto.getCpf());
		cliente.setEmail(dto.getEmail());
		
		//salvando
		repository.save(cliente);
		
		//passando o cliente para um dto
		ClienteGetDTO getDto = new ClienteGetDTO();
		getDto.setIdCliente(cliente.getIdCliente());
		getDto.setNome(cliente.getNome());
		getDto.setCpf(cliente.getCpf());
		getDto.setEmail(cliente.getEmail());
		
		return getDto;
	}

	public List<ClienteGetDTO> buscarTodos() {

		// declarando uma lista da classe ClienteGetDTO
		List<ClienteGetDTO> lista = new ArrayList<ClienteGetDTO>();

		// consultar e percorrer os clientes obtidos no banco de dados..
		for (Cliente cliente : repository.findAll()) {

			// transferindo os dados do cliente pro objeto dto
			ClienteGetDTO dto = new ClienteGetDTO();
			dto.setIdCliente(cliente.getIdCliente());
			dto.setNome(cliente.getNome());
			dto.setCpf(cliente.getCpf());
			dto.setEmail(cliente.getEmail());

			// adicionar os clientes um a um na lista
			lista.add(dto);
		}

		return lista;
	}

	public ClienteGetDTO buscarId(Integer idCliente) {

		// procurar o cliente no banco de dados atraves do id
		Optional<Cliente> result = repository.findById(idCliente);

		// verificar se o cliente não foi encontrado..
		if (result == null || result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}

		// obter os dados do cliente encontrado
		Cliente cliente = result.get();

		// criando objeto para receber os dados
		ClienteGetDTO dto = new ClienteGetDTO();

		// transferindo os dados da entidade pro dto
		dto.setIdCliente(cliente.getIdCliente());
		dto.setNome(cliente.getNome());
		dto.setCpf(cliente.getCpf());
		dto.setEmail(cliente.getEmail());

		return dto;
	}

	public String atualizar(ClientePutDTO dto) {

		// procurar o cliente no banco de dados atraves do id..
		Optional<Cliente> result = repository.findById(dto.getIdCliente());

		if (result == null || result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}

		// obter os dados do cliente encontrado
		Cliente cliente = result.get();

		// atualizando os dados do cliente(inserindo do dto para o cliente encontrado)
		cliente.setNome(dto.getNome());
		cliente.setEmail(dto.getEmail());

		repository.save(cliente);

		String response = "Cliente " + cliente.getNome() + " atualizado com sucesso.";
		return response;
	}

	public String excluir(Integer idCliente) {

		// procurar o cliente no banco de dados atraves do id
		Optional<Cliente> result = repository.findById(idCliente);

		// verificar se o cliente não foi encontrado..
		if (result == null || result.isEmpty()) {
			throw new EntityNotFoundException("Cliente não encontrado!");
		}

		// obter os dados do cliente encontrado
		Cliente cliente = result.get();

		repository.delete(cliente);
		
		String response = "Cliente " + cliente.getNome() + " excluído com sucesso.";
		return response;
	}

}
