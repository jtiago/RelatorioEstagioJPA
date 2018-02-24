package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import br.com.clogos.estagio.jsf.facade.RelatorioAlunoFacade;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Turma;

@ManagedBean(name="relatorioAlunoBean")
@ViewScoped
public class RelatorioAlunoBean implements Serializable {
	private static final long serialVersionUID = 6378902231545732309L;
	private RelatorioAlunoFacade facade;
	private boolean mensagem;
	
	public RelatorioAlunoFacade getFacade() {
		return facade == null ? facade = new RelatorioAlunoFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
		mensagem = true;
	}
	
	public void preencherRelatorio(ActionEvent event) {
		Turma turma = (Turma) event.getComponent().getAttributes().get("actionTurma");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		getFacade().atribuirDadosRelatorio(aluno, turma);
		mensagem = true;
	}
	
	public void limpar(ActionEvent event) {
		getFacade().limpar();
	}
	
	public void buscarListaRelatorio(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		getFacade().populaListaRevisao(aluno);
	}
	
	public void processaGrupoCampo(ValueChangeEvent event) {
		Long idCampo = Long.valueOf(event.getNewValue().toString());
		getFacade().buscaGrupoCampoESupervisores(idCampo);
	}
	
	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
}
