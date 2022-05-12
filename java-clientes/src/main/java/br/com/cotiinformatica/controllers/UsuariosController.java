package br.com.cotiinformatica.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.UsuarioGetDTO;
import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@Api(tags = "Menu Usuários")
@RequestMapping(value = "/api/usuarios")
public class UsuariosController {

	private final UsuarioService service;

	@PostMapping
	@ApiOperation(value = "cadastrar")
	public ResponseEntity<UsuarioGetDTO> cadastrar(@Valid @RequestBody UsuarioPostDTO dto) {

		try {
			UsuarioGetDTO getDto = service.cadastrar(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(getDto);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping
	@ApiOperation(value = "Buscar todos")
	public ResponseEntity<List<UsuarioGetDTO>> buscarTodos() {

		try {
			List<UsuarioGetDTO> lista = service.buscarTodos();
			return ResponseEntity.ok(lista);
		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Buscar por Id")
	public ResponseEntity<UsuarioGetDTO> buscarId(@PathVariable("id") Integer id) {

		try {
			UsuarioGetDTO getDto = service.buscarId(id);
			return ResponseEntity.ok(getDto);
		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Excluir")
	public ResponseEntity<String> excluir(@PathVariable("id") Integer id) {

		try {
			String response = service.excluir(id);
			return ResponseEntity.ok(response);
		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}