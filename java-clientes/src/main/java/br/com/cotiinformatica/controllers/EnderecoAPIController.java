package br.com.cotiinformatica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.EnderecoDTO;
import br.com.cotiinformatica.exceptions.ServiceException;
import br.com.cotiinformatica.services.EnderecoAPIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@Api(tags = "Api externa")
@RequestMapping(value = "/api/cep")
public class EnderecoAPIController {
	
	private final EnderecoAPIService service;
	
	@GetMapping(value = "/{cep}")
	@ApiOperation(value = "Buscar endereço pelo cep na API externa")
	public ResponseEntity<EnderecoDTO> buscarEnderecoAPI(@PathVariable("cep") String cep) throws Exception{
		
		try {
		
			EnderecoDTO enderecoDto = service.buscarEnderecoAPI(cep);
			return ResponseEntity.ok(enderecoDto);
			
		} catch (ServiceException e) {
			return ResponseEntity.internalServerError().build();
		}	
	}
}