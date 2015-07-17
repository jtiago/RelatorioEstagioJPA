package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.LiberarRelatorioController;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.LiberarRelatorio;
import br.com.clogos.estagio.model.Relatorio;
import br.com.clogos.estagio.model.Turma;

public class RelatorioAlunoFacade implements Serializable {
	private static final long serialVersionUID = 3016523488152504622L;
	private Relatorio relatorioAluno;
	private LiberarRelatorio liberarRelatorio;
	private List<Relatorio> listaRelatorioEnviado;
	private GenericController genericController;
	private LiberarRelatorioController liberarRelatorioController;
	private RelatorioController relatorioController;
	private Boolean relatorioAvaliando;
	private Boolean moduloLiberado;
	private Boolean limiteRelatorio;
	private Boolean renderedEnfermagem = false;
	private Boolean renderedRadiologia = false;
	
	public void populaListaRevisao(Aluno aluno) {
		if(aluno != null) {
			listaRelatorioEnviado = getRelatorioController().findRelatorioEnviado(aluno);
		}
	}
	
	public void save() {
		//Foi retirado o relacionamento de Relatório e Semestre
		//getRelatorioAluno().getSemestre().setId(aluno.getSemestre().getId());
		getRelatorioAluno().setRevisao(false);
		getRelatorioAluno().setValidado(false);
		//getRelatorioAluno().getAluno().setId(aluno.getId());
		getRelatorioAluno().setDataCadastro(new Date());
		getRelatorioAluno().setModulo(getLiberarRelatorio().getModulo());
		if(getGenericController().save(getRelatorioAluno())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Relatório salvo e encaminhado para avaliação.", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar relatório, por favor entre em contato com a coordenação Logos", ""));
		}
		limpar();
	}
	
	public void limpar() {
		relatorioAluno = null;
		liberarRelatorio = null;
		relatorioAvaliando = null;
		moduloLiberado = null;
		limiteRelatorio = null;
		renderedEnfermagem = false;
		renderedRadiologia = false;
	}
	
	/*public Boolean existeRelatorioPreenchido(Aluno aluno) {
		return getRelatorioController().existeRelatorioPreenchido(aluno);
	}*/
	
	public void atribuirDadosRelatorio(Aluno aluno, Turma turma) {
		turma.getSemestre().setId(aluno.getSemestre().getId());
		getRelatorioAluno().setAluno(aluno);
		getRelatorioAluno().setTurmaRelatorio(turma);
		
		setRelatorioAvaliando(getRelatorioController().existeRelatorioPreenchido(preencheAluno(aluno, turma)));
		setModuloLiberado(getLiberarRelatorioController().existeModuloAberto(preencheLiberarRelatorio(aluno, turma)));
		setLimiteRelatorio(getLiberarRelatorioController().verificaQtdRelatorio(preencheAluno(aluno, turma)));
		setLiberarRelatorio(getLiberarRelatorioController().obterLiberarRelatorioPorTurma(turma));
		
		if(getLiberarRelatorio().getModulo() != null) {
			if((getLiberarRelatorio().getModulo().getLabel().contains("II M") || getLiberarRelatorio().getModulo().getLabel().contains("III M")) 
					&& (getRelatorioAluno().getTurmaRelatorio().getNomeCurso().toUpperCase().contains("ENFERMAGEM"))) {
				renderedEnfermagem = true;
			} 
			
			if((getLiberarRelatorio().getModulo().getLabel().toUpperCase().contains("II E III")) 
					&& (getRelatorioAluno().getTurmaRelatorio().getNomeCurso().toUpperCase().contains("RADIOLOGIA"))) {
				renderedRadiologia =  true;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao verificar Módulo. Favor entrar em contato com a Coordenação.", ""));
		}
	}
	
	private LiberarRelatorio preencheLiberarRelatorio(Aluno aluno, Turma turma) {
		LiberarRelatorio liberarRelatorio = new LiberarRelatorio();
		liberarRelatorio.getTurmaLiberarRelatorio().setId(turma.getId());
		liberarRelatorio.getTurmaLiberarRelatorio().getSemestre().setId(aluno.getSemestre().getId());
		return liberarRelatorio;
	}
	
	private Aluno preencheAluno(Aluno aluno, Turma turma) {
		Aluno alunoPreenchido = new Aluno();
		alunoPreenchido.setCpf(aluno.getCpf());
		alunoPreenchido.getSemestre().setId(aluno.getSemestre().getId());
		alunoPreenchido.getTurmaT().setId(turma.getId());
		
		return alunoPreenchido;
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
	public Boolean getRenderedEnfermagem() {
		return renderedEnfermagem;
	}

	public void setRenderedEnfermagem(Boolean renderedEnfermagem) {
		this.renderedEnfermagem = renderedEnfermagem;
	}

	public Boolean getRenderedRadiologia() {
		return renderedRadiologia;
	}

	public void setRenderedRadiologia(Boolean renderedRadiologia) {
		this.renderedRadiologia = renderedRadiologia;
	}
	public Boolean getRelatorioAvaliando() {
		return relatorioAvaliando;
	}

	public void setRelatorioAvaliando(Boolean relatorioAvaliando) {
		this.relatorioAvaliando = relatorioAvaliando;
	}

	public Boolean getModuloLiberado() {
		return moduloLiberado;
	}

	public void setModuloLiberado(Boolean moduloLiberado) {
		this.moduloLiberado = moduloLiberado;
	}

	public Boolean getLimiteRelatorio() {
		return limiteRelatorio;
	}

	public void setLimiteRelatorio(Boolean limiteRelatorio) {
		this.limiteRelatorio = limiteRelatorio;
	}

	public LiberarRelatorio getLiberarRelatorio() {
		return liberarRelatorio;
	}

	public void setLiberarRelatorio(LiberarRelatorio liberarRelatorio) {
		this.liberarRelatorio = liberarRelatorio;
	}
}
