package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.AutenticarFacade;

@ManagedBean(name="autenticarBean")
@ViewScoped
public class AutenticarBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutenticarFacade facade;
	
	public AutenticarFacade getFacade() {
		return facade == null ? new AutenticarFacade() : facade;
	}
	
	public void login(ActionEvent event){
		getFacade().login();
	}
	
	public void logout(ActionEvent event) {
		getFacade().logout();
	}

}
