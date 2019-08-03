package com.pontointeligente.api.services.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pontointeligente.api.entities.Empresa;
import com.pontointeligente.api.repositories.EmpresaRepository;
import com.pontointeligente.api.services.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Optional<Empresa> buscaPorCnpj(String cnpj) {
		log.info("Buscando uma empresa para o CNPJ {}" , cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Empresa persistir(Empresa empresa) {
		log.info("Persistindo empresa : {}" , empresa);
		return this.empresaRepository.save(empresa);
	}

}
