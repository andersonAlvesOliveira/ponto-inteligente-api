package com.pontointeligente.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.pontointeligente.api.entities.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	@Transactional(propagation = Propagation.MANDATORY , readOnly = true)
	public Empresa findByCnpj(String cnpj);
	
}
