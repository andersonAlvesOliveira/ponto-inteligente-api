package com.pontointeligente.api.services.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pontointeligente.api.entities.Lancamento;
import com.pontointeligente.api.repositories.LancamentoRepository;
import com.pontointeligente.api.services.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Page<Lancamento> buscaFuncionarioPorId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscando lancamento para o funcionario ID 	{}" ,funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Optional<Lancamento> buscaPorId(Long id) {
		log.info("Buscando um lancamento pelo ID {}" ,id);
		return this.lancamentoRepository.findById(id);		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public Lancamento persitir(Lancamento lancamento) {
		log.info("Persistindo o lancamento {}" ,lancamento.toString());
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED , readOnly = true)
	public void remover(Long id) {
		log.info("Removendo um lancamento pelo ID {}" ,id);
		this.lancamentoRepository.deleteById(id);
	}

}
