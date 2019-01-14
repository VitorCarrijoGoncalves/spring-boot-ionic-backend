package com.vitor.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vitor.cursomc.domain.Cliente;
import com.vitor.cursomc.dto.ClienteDTO;
import com.vitor.cursomc.dto.ClienteNewDTO;
import com.vitor.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	// ResponseEntity = uma resposta http do tipo void, mas que não terá corpo
		@RequestMapping(method=RequestMethod.POST)
		public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
			// @RequestBody = converte o json em objeto java automaticamente
			Cliente obj = service.fromDto(objDto);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	//Usando a requisição PUT para fazer alteração de dados 
		@RequestMapping(value="/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
			Cliente obj = service.fromDto(objDto);
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
		public ResponseEntity<List<ClienteDTO>> findAll() {
			List<Cliente> list = service.findAll();
			List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
			// stream = percorre a lista
			// faz uma operação pra cada elemento da lista
			// .collect(Collector.toList()) = eu consigo converter uma lista para outra lista
			return ResponseEntity.ok().body(listDto);
		}
		
		// categorias/page = endpoint para listar todas as páginas
		// @RequestParam = anotação para deixar que os parâmetros do método sejam opcionais
		// defaultValue="0" = valor padrão da page, caso o cliente não a informe
		// ASC = ascendente
		@RequestMapping(value="/page", method=RequestMethod.GET)
		public ResponseEntity<Page<ClienteDTO>> findPage(
				@RequestParam(value="page", defaultValue="0") Integer page, 
				@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
				@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
				@RequestParam(value="direction", defaultValue="ASC") String direction) {
			Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
			Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
			// situação acima converte cada objeto da lista de page de Cliente para ClienteDTO
			return ResponseEntity.ok().body(listDto);
		}
		
		// http://localhost:8090/categorias/page?linesPerPage=3&page=1&direction=DESC
		// exwmplo de endpoint da paginação, mais os seus parâmetros


}
