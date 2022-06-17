package br.com.cotiinformatica.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import br.com.cotiinformatica.enums.Estados;

@Service
public class EstadosService {

	public List<String> buscarTodos() {

		// Consultando todos os Estados
		List<String> lista = Stream.of(Estados.values())
				.map(Estados::name)
				.collect(Collectors.toList());

		return lista;
	}
}
