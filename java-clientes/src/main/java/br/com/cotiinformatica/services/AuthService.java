/*package br.com.cotiinformatica.services;

import javax.transaction.Transactional;

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

	public AuthGetDTO autenticar(AuthPostDTO dto) {

		// Procurar o usuário no banco através do email e senha. Criptografar a senha
		Usuario usuario = repository.findByEmailAndSenha(dto.getEmail(), Criptografia.criptografar(dto.getSenha()));

		if (usuario == null) {
			throw new BadRequestException("Email ou senha inválidos!");
		}

		// objeto para aramazenar os dados do usuário
		AuthGetDTO getDto = new AuthGetDTO();

		getDto.setIdUsuario(usuario.getIdUsuario());
		getDto.setNome(usuario.getNome());
		getDto.setEmail(usuario.getEmail());
		getDto.setAccessToken(TokenSecurity.generateToken(usuario.getEmail()));

		// retornando o objeto ao controller
		return getDto;
	}

}
*/