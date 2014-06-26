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

@ManagedBean(name="relatorioAlunoBean")
@ViewScoped
public class RelatorioAlunoBean implements Serializable {
	private static final long serialVersionUID = 6378902231545732309L;
	private RelatorioAlunoFacade facade;
	private Boolean renderedEnfermagem = false;
	private Boolean renderedRadiologia = false;
	
	public RelatorioAlunoFacade getFacade() {
		return facade == null ? facade = new RelatorioAlunoFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
	}
	
	public void limpar(ActionEvent event) {
		getFacade().limpar();
	}
	
	public void defineRendered(ComponentSystemEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		
		if(aluno.getModulo() != null) {
			if(aluno.getModulo().contains("II M") || aluno.getModulo().contains("III M")) {
				renderedEnfermagem = true;
			} 
			
			if((aluno.getModulo().contains("II E III")) && (aluno.getTurmaT().getNomeCurso().contains("radiologia") || aluno.getTurmaT().getNomeCurso().contains("RADIOLOGIA"))) {
				renderedRadiologia =  true;
			}
		}
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
}
