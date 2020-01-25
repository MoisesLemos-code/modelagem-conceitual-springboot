package com.moises.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.moises.cursomc.domain.Cliente;
import com.moises.cursomc.domain.enums.TipoCliente;
import com.moises.cursomc.dto.ClienteNewDTO;
import com.moises.cursomc.repositories.ClienteRepository;
import com.moises.cursomc.resources.exception.FieldMessage;
import com.moises.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido!"));
		}
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido!"));
		}
		
		Cliente aux = null;
		aux = repo.findByEmail(objDto.getEmail());
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existente! "
					+ "(Cliente: "+ aux.getNome() + ")"));
		}
		
		aux = repo.findByCpfOuCnpj(objDto.getCpfOuCnpj());
		if(aux != null) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF/CNPJ já cadastrado! "
					+ "(Cliente: "+ aux.getNome() + ")"));
		}
		
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
