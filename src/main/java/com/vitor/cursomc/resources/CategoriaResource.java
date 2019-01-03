package com.vitor.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vitor.cursomc.domain.Categoria;
import com.vitor.cursomc.dto.CategoriaDTO;
import com.vitor.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	// ResponseEntity = uma resposta http do tipo void, mas que não terá corpo
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		// @RequestBody = converte o json em objeto java automaticamente
		obj = service.insert(obj);
		// uri = url da aplicação
		// fromCurrentRequest = pega a uri igual la no postman, so que apenas ate o id
		// buildAndExpand = controi a uri com o id
		// toUri = converte o objeto para o tipo URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		// ResponseEntity.created = constroi essa uri para min, o build gera a resposta no brownser
	}
	
	//Usando a requisição PUT para fazer alteração de dados 
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id); // setando o id que sera alterado neste metodo
		obj = service.update(obj);
		return ResponseEntity.noContent().build(); // noContent() = response com conteudo vazio
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		// stream = percorre a lista
		// faz uma operação pra cada elemento da lista
		// .collect(Collector.toList()) = eu consigo converter uma lista para outra lista
		return ResponseEntity.ok().body(listDto);
	}

}
