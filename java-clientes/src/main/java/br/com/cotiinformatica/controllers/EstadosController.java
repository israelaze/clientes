package br.com.cotiinformatica.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.EstadosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Api(tags = "Menu Estados")
@RequestMapping(value = "/api/estados")
@CrossOrigin
public class EstadosController {

	private final EstadosService service;

	@GetMapping
	@ApiOperation(value = "buscar todos")
	public ResponseEntity<List<String>> buscarTodos() {

		try {
			List<String> lista = service.buscarTodos();
			return ResponseEntity.ok(lista);

		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
