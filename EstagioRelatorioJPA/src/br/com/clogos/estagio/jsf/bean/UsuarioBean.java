package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.clogos.estagio.jsf.facade.UsuarioFacade;

@ManagedBean(name="usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = -5826199740184047009L;
	private UsuarioFacade facade;
	
	public UsuarioFacade getFacade() {
		return facade == null ? facade = new UsuarioFacade() : facade;
	}

}
