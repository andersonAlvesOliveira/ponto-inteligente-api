package com.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import com.pontointeligente.api.entities.Empresa;
import com.pontointeligente.api.entities.Funcionario;
import com.pontointeligente.api.entities.Lancamento;
import com.pontointeligente.api.enums.PerfilEnum;
import com.pontointeligente.api.enums.TipoEnum;
import com.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private	EmpresaRepository empresaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	private static final String EMAIL = "andersonalvesoliveira@gmail.com";
	
	private static final String CPF = "32306647879";
	
	private static final String CNPJ = "51463645000100";
	
	private Long funcionarioId;
	
	
	@Before
	public void setup() {
		
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Funcionario funcionario = funcionarioRepository.save(obterDadosFuncionario(empresa));
		funcionarioId = funcionario.getId();
		
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));
		this.lancamentoRepository.save(obterDadosLancamento(funcionario));		
		
	}

	@After
	public void tearDown() {
		this.empresaRepository.deleteAll();		
	}
	
	@Test
	public void testBuscarLancamentoPorFuncionarioId() {
		List<Lancamento> findByFuncionarioId = this.lancamentoRepository.findByFuncionarioId(funcionarioId);
		
		assertEquals(2, findByFuncionarioId.size());
	}

//	@Test
//	public void testBuscarLancamentoPorFuncionarioIdPaginado() {
//		PageRequest page = new PageRequest(0,10);		
//		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);		
//		assertEquals(2, lancamentos.getTotalElements());
//	}
	
	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new  Funcionario();
		funcionario.setNome("Anderson Alves de Oliveira");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setEmpresa(empresa);
		funcionario.setValorHora(new BigDecimal("10"));
		funcionario.setQtdHorasAlmoco(12D);
		funcionario.setQtdHorasTrabalhoDia(12D);
		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa Exemplo");
		empresa.setCnpj(CNPJ);		 	
		return empresa; 	
	}

	private Lancamento obterDadosLancamento(Funcionario funcionario) {
		
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setDescricao("xpto");
		lancamento.setLocalizacao("Aqui");
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}
	
}
