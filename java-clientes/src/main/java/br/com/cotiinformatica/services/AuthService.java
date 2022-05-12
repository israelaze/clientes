package br.com.cotiinformatica.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.dtos.AuthGetDTO;
import br.com.cotiinformatica.dtos.AuthPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.exceptions.BadRequestException;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.security.Criptografia;
import br.com.cotiinformatica.security.TokenSecurity;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

	private final UsuarioRepository repository;
	private final ModelMapper mapper;
	
	public AuthGetDTO autenticar(AuthPostDTO dto) {
		
		// Criptografando a senha do usuário
		String senha = Criptografia.criptografar(dto.getSenha());

		// Buscando no banco um usuário, pelo email e pela senha já criptografada
		Optional<Usuario> result = repository.findByEmailAndSenha(dto.getEmail(), senha);
		
		if(result.isEmpty()) {
			throw new BadRequestException("Email ou senha inválidos!");
		}
		
		Usuario usuario = result.get();
		
		// Gereando um Token para o usuário
		String token = TokenSecurity.generateToken(usuario.getEmail());
		
		AuthGetDTO getDto = new AuthGetDTO();
		mapper.map(usuario, getDto);
		getDto.setAccessToken(token);
		
		return getDto;
		
	}
}
