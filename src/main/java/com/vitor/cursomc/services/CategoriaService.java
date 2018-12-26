package com.vitor.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitor.cursomc.domain.Categoria;
import com.vitor.cursomc.repositories.CategoriaRepository;
import com.vitor.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id = " + id + ", Tipo: " + Categoria.class.getName());
		}
		return obj.orElse(null);
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // o Objeto novo a ser inserido precisa o ter p id nulo
		return repo.save(obj);
	}

}
