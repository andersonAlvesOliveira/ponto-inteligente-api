package com.pontointeligente.api.repositories;

import java.util.List;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.pontointeligente.api.entities.Lancamento;

@NamedQueries({
	@NamedQuery(name = "LancamentoRepository.findByFuncionarioId" , query = "SELECT lacn FROM Lancamento WHERE lanc.funcionario.id = :funcionarioId")
})

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	@Transactional(propagation = Propagation.MANDATORY , readOnly = true)
	public List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
	
	@Transactional(propagation = Propagation.MANDATORY , readOnly = true)
	public Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId , Pageable pageable);
}
