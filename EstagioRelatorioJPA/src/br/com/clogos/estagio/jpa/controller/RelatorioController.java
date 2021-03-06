package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.RelatorioDAO;
import br.com.clogos.estagio.jpa.dao.impl.RelatorioDAOImpl;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Relatorio;
import br.com.clogos.estagio.vo.FichaAvaliacaoVO;
import br.com.clogos.estagio.vo.RelatorioStatusVO;

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
	
	/*public Boolean saveRevisaoRelatorioAluno(Relatorio relatorio) {
		return getRelatorioDAO().saveRevisaoRelatorioAluno(relatorio);
	}*/
	
	public List<Relatorio> findRelatorioEnviado(Aluno aluno) {
		return getRelatorioDAO().findRelatorioEnviado(aluno);
	}
	
	public Boolean alterarDataInicioTerminioRelatorio(Relatorio relatorio) {
		return getRelatorioDAO().alterarDataInicioTerminioRelatorio(relatorio);
	}
	
	public Boolean existeRelatorioGrupoCampoEstagio(Relatorio relatorio) {
		return getRelatorioDAO().existeRelatorioGrupoCampoEstagio(relatorio);
	}
	
	public List<RelatorioStatusVO> findRelatorioStatus(Long idTurma, Long idSemestre) {
		return getRelatorioDAO().findRelatorioStatus(idTurma, idSemestre);
	}
	
	public FichaAvaliacaoVO findFichaAvaliacao(Long idAluno, Long idTurma, Long idSemestre) {
		return getRelatorioDAO().findFichaAvaliacao(idAluno, idTurma, idSemestre);
	}
	
	public RelatorioDAO getRelatorioDAO() {
		return relatorioDAO == null ? relatorioDAO = new RelatorioDAOImpl() : relatorioDAO;
	}

}
