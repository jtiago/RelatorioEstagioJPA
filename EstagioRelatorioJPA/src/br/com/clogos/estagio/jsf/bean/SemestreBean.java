package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.clogos.estagio.jsf.facade.SemestreFacade;

@ManagedBean(name="semestreBean")
@ViewScoped
public class SemestreBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SemestreFacade facade;
	
	public SemestreFacade getFacade() {
		return facade == null ? facade = new SemestreFacade() : facade;
	}
	
}
