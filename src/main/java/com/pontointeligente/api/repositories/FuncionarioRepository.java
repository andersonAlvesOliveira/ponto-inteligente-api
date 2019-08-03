package com.pontointeligente.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pontointeligente.api.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Transactional(propagation = Propagation.MANDATORY , readOnly = true)
	public Funcionario findByCpf(String cpf);
	
	@Transactional(propagation = Propagation.MANDATORY , readOnly = true)
	public Funcionario findByEmail(String email);
	
	@Transactional(propagation = Propagation.MANDATORY , readOnly = true)
	public Funcionario findByCpfOrEmail(String cpf , String email);
	
	@Transactional(propagation = Propagation.MANDATORY , readOnly = true)
	public Optional<Funcionario> findById(Long id);
}
