package br.com.cotiinformatica.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.UsuarioGetDTO;
import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.exceptions.EntityNotFoundException;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;
	private final ModelMapper mapper;

	public UsuarioGetDTO cadastrar(UsuarioPostDTO dto) {

		Optional<Usuario> result = repository.findByEmail(dto.getEmail());

		// verificar se o email já está cadastrado no banco
		if (result.isPresent()) {
			throw new BadRequestException("Erro: Email já cadastrado!");
		}
		
		// Criptografando a senha do usuário
		String senha = Criptografia.criptografar(dto.getSenha());

		// inserindo os dados do Usuário
		Usuario usuario = new Usuario();
		mapper.map(dto, usuario);
		usuario.setSenha(senha);
	
		//salvando
		repository.save(usuario);
		
		//passando o usuário para um dto
		UsuarioGetDTO getDto = new UsuarioGetDTO();
		mapper.map(usuario, getDto);
		return getDto;
	}
	
	public List<UsuarioGetDTO> buscarTodos() {

		List<UsuarioGetDTO> listaGetDto = new ArrayList<UsuarioGetDTO>();
		List<Usuario> listaUsuarios = repository.findAll();

		for (Usuario usuario : listaUsuarios) {

			UsuarioGetDTO getDto = new UsuarioGetDTO();
			mapper.map(usuario, getDto);

			listaGetDto.add(getDto);
		}

		return listaGetDto;
	}

	public UsuarioGetDTO buscarId(Integer id) {

		Optional<Usuario> result = repository.findById(id);

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Usuário não encontrado.");
		}

		Usuario usuario = result.get();

		UsuarioGetDTO getDto = new UsuarioGetDTO();
		mapper.map(usuario, getDto);

		return getDto;
	}

	public String excluir(Integer id) {

		Optional<Usuario> result = repository.findById(id);

		if (result.isEmpty()) {
			throw new EntityNotFoundException("Usuário não encontrado.");
		}

		Usuario usuario = result.get();

		repository.delete(usuario);

		return "Usuário " + result.get().getNome() + " excluído com sucesso.";
	}

}
