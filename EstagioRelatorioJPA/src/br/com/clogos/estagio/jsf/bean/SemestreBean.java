package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.SemestreFacade;

@ManagedBean(name="semestreBean")
@ViewScoped
public class SemestreBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SemestreFacade facade;
	private boolean mensagem;
	
	public SemestreFacade getFacade() {
		return facade == null ? facade = new SemestreFacade() : facade;
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

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
}
