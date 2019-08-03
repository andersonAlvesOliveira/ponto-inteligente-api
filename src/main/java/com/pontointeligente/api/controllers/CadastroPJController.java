package com.pontointeligente.api.controllers;

import java.math.BigDecimal;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pontointeligente.api.dto.CadastroPJDTO;
import com.pontointeligente.api.entities.Empresa;
import com.pontointeligente.api.entities.Funcionario;
import com.pontointeligente.api.enums.PerfilEnum;
import com.pontointeligente.api.response.Response;
import com.pontointeligente.api.services.EmpresaService;
import com.pontointeligente.api.services.FuncionarioService;
import com.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	public CadastroPJController() {}
	
	
	/**
	 * Cadastra uma empresa
	 * 
	 * @param cadastroPJDTO
	 * @param result
	 * @return ResponseEntity<Response<CadastroPJDTO>>
	 */
	
	@PostMapping("/cadastro")
	public ResponseEntity<Response<CadastroPJDTO>>  cadastrar(@Valid @RequestBody CadastroPJDTO cadastroPJDTO , BindingResult result) {
		
		log.info("Cadastrando PJ: {} " , cadastroPJDTO.toString());
		Response<CadastroPJDTO> response = new  Response<CadastroPJDTO>();
		
		validarDadosExistentes(cadastroPJDTO , result);
		Empresa empresa = this.converteDTOParaEmpresa(cadastroPJDTO);
		Funcionario funcionario = this.converteDTOParaFuncionario(cadastroPJDTO, result);
		
		if(result.hasErrors()) {
			log.info("Erro validando dados de cadastro {} " , cadastroPJDTO.toString());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPJDTO(funcionario));
		return ResponseEntity.ok(response);
		
	}

	/**
	 * Valida se a Empresa ou Funcionario ja existe no banco de dados
	 * 
	 * @param cadastroPJDTO
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPJDTO cadastroPJDTO, BindingResult result) {
		
		empresaService.buscaPorCnpj(cadastroPJDTO.getCnpj()).ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente")));
		funcionarioService.buscarPorCpf(cadastroPJDTO.getCpf()).ifPresent(func -> result.addError(new ObjectError("funcionario", "Este CPF ja está cadastrado no sistema")));
		funcionarioService.buscarPorEmail(cadastroPJDTO.getEmail()).ifPresent(func -> result.addError(new ObjectError("funcionario", "Este Email ja está cadastrado no sistema")));
		
	}
	
	/**
	 * Converte os dados do DTO para empresa
	 * 
	 * @param cadastroPJDTO
	 * @return
	 */
	private Empresa converteDTOParaEmpresa(CadastroPJDTO cadastroPJDTO) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDTO.getCnpj());
		empresa.setRazaoSocial(cadastroPJDTO.getRazaoSocial());		
		return empresa;
	}
	
	/**
	 * Converte os dados do DTO para funcionario
	 * 
	 * @param cadastroPJDTO
	 * @param result
	 * @return
	 */
	
	private Funcionario converteDTOParaFuncionario(CadastroPJDTO cadastroPJDTO, BindingResult result) {
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPJDTO.getNome());
		funcionario.setEmail(cadastroPJDTO.getEmail());
		funcionario.setCpf(cadastroPJDTO.getCpf());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setQtdHorasAlmoco(10D);
		funcionario.setQtdHorasTrabalhoDia(5D);
		funcionario.setValorHora(new BigDecimal("50"));
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDTO.getSenha()));
		return funcionario;
	}

	/**
	 * Popula o DTO de Cadastro com os dados do Funcionario e Empresa
	 * 
	 * @param funcionario
	 * @return
	 */
	private CadastroPJDTO converterCadastroPJDTO(Funcionario funcionario) {

		CadastroPJDTO cadastro = new CadastroPJDTO();
		cadastro.setId(funcionario.getId());
		cadastro.setNome(funcionario.getNome());
		cadastro.setEmail(funcionario.getEmail());
		cadastro.setCpf(funcionario.getCpf());
		cadastro.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastro.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastro;
	}

}
