package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.CampoEstagioFacade;

@ManagedBean(name="campoBean")
@ViewScoped
public class CampoEstagioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private CampoEstagioFacade facade;
	
	public CampoEstagioFacade getFacade() {
		return facade == null ? facade = new CampoEstagioFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
	}
	
	public void update(ActionEvent event) {
		getFacade().update();
	}

}
