package com.moises.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moises.cursomc.domain.Pedido;
import com.moises.cursomc.repositories.PedidoRepository;
import com.moises.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired //Instanciar automaticamente.
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", Tipo: "+ Pedido.class.getName());
		}
		return obj;
	}
}
