package com.moises.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moises.cursomc.domain.Categoria;
import com.moises.cursomc.repositories.CategoriaRepository;
import com.moises.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired //Instanciar automaticamente.
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: "+ Categoria.class.getName());
		}
		return obj;
	}
}
