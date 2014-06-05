package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.LiberarRelatorioFacade;
import br.com.clogos.estagio.jsf.facade.TurmaFacade;

@ManagedBean(name="turmaBean")
@ViewScoped
public class TurmaBean implements Serializable {
	private static final long serialVersionUID = 7722041858940329653L;
	private TurmaFacade facade;
	private LiberarRelatorioFacade facadeLiberarRelatorio;
	private boolean mensagem;
	
	public TurmaBean() {
		mensagem = false;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
		mensagem = true;
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
		mensagem = true;
	}
	
	public void update(ActionEvent event) {
		getFacade().update();
		mensagem = true;
	}
	
	public void saveLiberarRelatorio(ActionEvent event) {
		getFacadeLiberarRelatorio().save();
		mensagem = true;
	}
	
	public TurmaFacade getFacade() {
		return facade == null ? facade = new TurmaFacade() : facade;
	}
	
	public LiberarRelatorioFacade getFacadeLiberarRelatorio() {
		return facadeLiberarRelatorio == null ? facadeLiberarRelatorio = new LiberarRelatorioFacade() : facadeLiberarRelatorio;
	}

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}

}
