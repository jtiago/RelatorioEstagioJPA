package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import br.com.clogos.estagio.jsf.facade.RelatorioAlunoFacade;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Turma;

@ManagedBean(name="relatorioAlunoBean")
@ViewScoped
public class RelatorioAlunoBean implements Serializable {
	private static final long serialVersionUID = 6378902231545732309L;
	private RelatorioAlunoFacade facade;
	/*private Boolean renderedEnfermagem = false;
	private Boolean renderedRadiologia = false;
	private Boolean relatorioAvaliando;*/
	
	public RelatorioAlunoFacade getFacade() {
		return facade == null ? facade = new RelatorioAlunoFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		/*FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");*/
		getFacade().save();
	}
	
	public void preencherRelatorio(ActionEvent event) {
		Turma turma = (Turma) event.getComponent().getAttributes().get("actionTurma");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		getFacade().atribuirDadosRelatorio(aluno, turma);
	}
	
	public void limpar(ActionEvent event) {
		getFacade().limpar();
	}
	
	/*public void defineRendered(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		relatorioAvaliando = getFacade().existeRelatorioPreenchido(aluno);
		
		if(aluno.getModulo() != null) {
			if((aluno.getModulo().contains("II M") || aluno.getModulo().contains("III M")) && (aluno.getTurmaT().getNomeCurso().contains("enfermagem") || aluno.getTurmaT().getNomeCurso().contains("ENFERMAGEM"))) {
				renderedEnfermagem = true;
			} 
			
			if((aluno.getModulo().contains("II E III")) && (aluno.getTurmaT().getNomeCurso().contains("radiologia") || aluno.getTurmaT().getNomeCurso().contains("RADIOLOGIA"))) {
				renderedRadiologia =  true;
			}
		}
	}*/
	
	public void buscarListaRelatorio(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		getFacade().populaListaRevisao(aluno);
	}
	
	
	/*public Boolean getRenderedEnfermagem() {
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
	}*/
}
