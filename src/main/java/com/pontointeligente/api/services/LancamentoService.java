package com.pontointeligente.api.services;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.pontointeligente.api.entities.Lancamento;

public interface LancamentoService {

	Page<Lancamento> buscaFuncionarioPorId(Long funcionarioId, PageRequest pageRequest);
	Optional<Lancamento>buscaPorId(Long id);
	Lancamento persitir(Lancamento lancamento);
	void remover(Long id);
}
