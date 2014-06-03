package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.clogos.estagio.jsf.facade.PerfilFacade;

@ManagedBean(name="perfilBean")
@ViewScoped
public class PerfilBean implements Serializable {
	private static final long serialVersionUID = 5268038016809597504L;
	private PerfilFacade facade;
	
	public PerfilFacade getFacade() {
		return facade == null ? facade = new PerfilFacade() : facade;
	}
}
