package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.RelatorioDAO;
import br.com.clogos.estagio.jpa.dao.impl.RelatorioDAOImpl;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Relatorio;

public class RelatorioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RelatorioDAO relatorioDAO;
	
	public Boolean existeRelatorioPreenchido(Aluno aluno) {
		return getRelatorioDAO().existeRelatorioPreenchido(aluno);
	}
	
	public List<Relatorio> findRelatoriosAdmin(Relatorio relatorio) {
		return getRelatorioDAO().findRelatoriosAdmin(relatorio);
	}
	
	public Boolean updateValidarRelatorio(Long id, String observacao) {
		return getRelatorioDAO().updateValidarRelatorio(id, observacao);
	}
	
	public Boolean updateRevisaoRelatorio(Long id, String observacao) {
		return getRelatorioDAO().updateRevisaoRelatorio(id, observacao);
	}
	
	public List<Relatorio> findRelatoriosRevisao(Aluno aluno) {
		return getRelatorioDAO().findRelatoriosRevisao(aluno);
	}
	
	public Boolean saveRevisaoRelatorioAluno(Relatorio relatorio) {
		return getRelatorioDAO().saveRevisaoRelatorioAluno(relatorio);
	}
	
	public RelatorioDAO getRelatorioDAO() {
		return relatorioDAO == null ? relatorioDAO = new RelatorioDAOImpl() : relatorioDAO;
	}

}
