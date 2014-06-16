package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.LiberarRelatorioFacade;

@ManagedBean(name="liberarBean")
@ViewScoped
public class LiberarRelatorioBean implements Serializable {
	private static final long serialVersionUID = 8407938873639812800L;
	private LiberarRelatorioFacade facade;
	private Boolean mensagem;
	
	public LiberarRelatorioBean() {
		this.mensagem = false;
	}
	
	public LiberarRelatorioFacade getFacade() {
		return facade == null ? facade = new LiberarRelatorioFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
		mensagem = true;
	}
	
	public void fecharModuloLiberado() {
		getFacade().fecharModuloLiberado();
		mensagem = true;
	}

	public Boolean getMensagem() {
		return mensagem;
	}

	public void setMensagem(Boolean mensagem) {
		this.mensagem = mensagem;
	}
}
