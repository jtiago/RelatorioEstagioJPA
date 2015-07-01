package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.LiberarRelatorioController;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Relatorio;

public class RelatorioAlunoFacade implements Serializable {
	private static final long serialVersionUID = 3016523488152504622L;
	private Relatorio relatorioAluno;
	private List<Relatorio> listaRelatorioEnviado;
	private GenericController genericController;
	private LiberarRelatorioController liberarRelatorioController;
	private RelatorioController relatorioController;	
	
	public void populaListaRevisao(Aluno aluno) {
		if(aluno != null) {
			listaRelatorioEnviado = getRelatorioController().findRelatorioEnviado(aluno);
		}
	}
	
	public void save(Aluno aluno) {
		//Foi retirado o relacionamento de Relatório e Semestre
		//getRelatorioAluno().getSemestre().setId(aluno.getSemestre().getId());
		getRelatorioAluno().setRevisao(false);
		getRelatorioAluno().setValidado(false);
		getRelatorioAluno().getAluno().setId(aluno.getId());
		getRelatorioAluno().setDataCadastro(new Date());
		getRelatorioAluno().setModulo(ModuloEnum.getModulo(aluno.getModulo()));
		if(getGenericController().save(getRelatorioAluno())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Relatório salvo e encaminhado para avaliação.", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar relatório, por favor entre em contato com a coordenação Logos", ""));
		}
		relatorioAluno = null;
	}
	
	public void limpar() {
		relatorioAluno = null;
	}
	
	public Boolean existeRelatorioPreenchido(Aluno aluno) {
		return getRelatorioController().existeRelatorioPreenchido(aluno);
	}
	
	public void ajustarDataIntervalo() {
		
	}
	
	public List<Relatorio> getListaRelatorioEnviado() {
		return listaRelatorioEnviado;
	}
	
	public Relatorio getRelatorioAluno() {
		return relatorioAluno == null ? relatorioAluno = new Relatorio() : relatorioAluno;
	}
	public void setRelatorioAluno(Relatorio relatorioAluno) {
		this.relatorioAluno = relatorioAluno;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
	
	public LiberarRelatorioController getLiberarRelatorioController() {
		return liberarRelatorioController == null ? liberarRelatorioController = new LiberarRelatorioController() : liberarRelatorioController;
	}
	
	public RelatorioController getRelatorioController() {
		return relatorioController == null ? relatorioController = new RelatorioController() : relatorioController;
	}
}
