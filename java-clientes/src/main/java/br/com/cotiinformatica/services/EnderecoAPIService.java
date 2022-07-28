package br.com.cotiinformatica.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.cotiinformatica.dtos.EnderecoApiDTO;
import br.com.cotiinformatica.dtos.EnderecoDTO;
import br.com.cotiinformatica.exceptions.BadRequestException;

@Service
public class EnderecoAPIService {
	
	public EnderecoDTO buscarEnderecoAPI(String cep) throws Exception {
		
		// Consumindo API pública externa
		URL url = new URL("https://viacep.com.br/ws/"+cep+"/json/");
		URLConnection connection =url.openConnection();
		InputStream iS = connection.getInputStream();
		BufferedReader bR = new BufferedReader(new InputStreamReader(iS, "UTF-8"));
		
		String api = "";
		StringBuilder jsonCep = new StringBuilder();
		
		while ((api = bR.readLine()) != null) {
			jsonCep.append(api);
		}
		// recebendo uma string no formato Json
		String enderecoApi = jsonCep.toString();
		
		// Verificando se o cep existe
		String erro = "{  \"erro\": \"true\"}";
		if(enderecoApi.contentEquals(erro)){
			throw new BadRequestException("Cep incorreto.");
		}
		// Exibindo no console o resultado
		System.out.println(enderecoApi);
		
		// Convertendo a string em objeto(EnderecoApiDTO)
		EnderecoApiDTO apiDto = new Gson().fromJson(enderecoApi, EnderecoApiDTO.class);
		
		// Convertendo os objetos(apiDto -> enderecoDto)
		EnderecoDTO enderecoDto = new EnderecoDTO();
		enderecoDto.setLogradouro(apiDto.getLogradouro());
		enderecoDto.setBairro(apiDto.getBairro());
		enderecoDto.setMunicipio(apiDto.getLocalidade());
		enderecoDto.setEstado(apiDto.getUf());
		enderecoDto.setCep(apiDto.getCep());
		
		return enderecoDto;
	}
	
}
