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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Api(tags = "Menu Clientes")
@RequestMapping(value = "/api/clientes")
@CrossOrigin
public class ClientesController {

	private ClienteService service;

	@PostMapping
	@ApiOperation(value = "cadastrar")
	public ResponseEntity<ClienteGetDTO> cadastrar(@Valid @RequestBody ClientePostDTO dto) {

		try {
			ClienteGetDTO getDto = service.cadastrar(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(getDto);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping
	@ApiOperation(value = "buscar todos")
	public ResponseEntity<List<ClienteGetDTO>> buscarTodos() {

		try {
			List<ClienteGetDTO> lista = service.buscarTodos();
			return ResponseEntity.ok(lista);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping(value = "/{idCliente}")
	@ApiOperation(value = "buscar pelo ID")
	public ResponseEntity<ClienteGetDTO> busrcarId(@PathVariable("idCliente") Integer idCliente) {

		try {
			ClienteGetDTO dto = service.buscarId(idCliente);
			return ResponseEntity.ok(dto);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping
	@ApiOperation(value = "atualizar")
	public ResponseEntity<String> atualizar(@Valid @RequestBody ClientePutDTO dto) {

		try {
			String response = service.atualizar(dto);
			return ResponseEntity.ok(response);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@DeleteMapping(value = "/{idCliente}")
	@ApiOperation(value = "excluir")
	public ResponseEntity<String> excluir(@PathVariable("idCliente") Integer idCliente) {

		try {
			String response = service.excluir(idCliente);
			return ResponseEntity.ok(response);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

}
