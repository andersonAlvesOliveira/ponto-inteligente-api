package com.pontointeligente.api.services;

import java.util.Optional;
import com.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	/**
	 * Retorna uma Empresa dado um CNPJ
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 * 
	 */
	
	Optional<Empresa> buscaPorCnpj(String cnpj);
	
	/**
	 * Cadastra uma nova empresa no banco de dados
	 * 
	 * @param empresa
	 * @return Empresa
	 */	
	Empresa persistir(Empresa empresa);
	
}
