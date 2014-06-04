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
	
	public LiberarRelatorioFacade getFacade() {
		return facade == null ? facade = new LiberarRelatorioFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
	}
}
