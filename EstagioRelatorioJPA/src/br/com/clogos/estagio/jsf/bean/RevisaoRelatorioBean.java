package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;

import br.com.clogos.estagio.jsf.facade.RevisaoRelatorioFacade;
import br.com.clogos.estagio.model.Aluno;

@ManagedBean(name="revisaoRelatorioBean")
@ViewScoped
public class RevisaoRelatorioBean implements Serializable {
	private static final long serialVersionUID = -527942619504549019L;
	private RevisaoRelatorioFacade facade;
	
	public RevisaoRelatorioFacade getFacade() {
		return facade == null ? facade = new RevisaoRelatorioFacade() : facade;
	}
	
	public void buscarListaRevisao(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		getFacade().populaListaRevisao(aluno);
	}
}
