package com.pontointeligente.api.services.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pontointeligente.api.entities.Funcionario;
import com.pontointeligente.api.repositories.FuncionarioRepository;
import com.pontointeligente.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = false)
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionario {}" , funcionario.toString());
		return funcionarioRepository.save(funcionario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando funcionario por CPF {}" , cpf);
		return Optional.ofNullable(funcionarioRepository.findByCpf(cpf));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando funcionario por EMAIL {}" , email);
		return Optional.ofNullable(funcionarioRepository.findByEmail(email));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Optional<Funcionario> buscaFuncionarioPorId(Long id) {
		log.info("Buscando funcionario por ID {}" , id);
		return this.funcionarioRepository.findById(id);		
	}

}
