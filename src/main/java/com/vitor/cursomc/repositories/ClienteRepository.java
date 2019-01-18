package com.vitor.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vitor.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	@Transactional(readOnly=true) // ela não necessita ser envolvida em uma transação com BD
	Cliente findByEmail(String email); // apenas com esse método, o spring já faz a validação por email p min

}
