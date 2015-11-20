package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.RevisaoRelatorioFacade;

@ManagedBean(name="revisaoRelatorioBean")
@ViewScoped
public class RevisaoRelatorioBean implements Serializable {
	private static final long serialVersionUID = -527942619504549019L;
	private RevisaoRelatorioFacade facade;
	
	public RevisaoRelatorioFacade getFacade() {
		return facade == null ? facade = new RevisaoRelatorioFacade() : facade;
	}
	
//	public void buscarListaRevisao(ComponentSystemEvent event) {
//		getFacade().populaListaRevisao();
//	}
	
	public void saveRevisao(ActionEvent event) {
		getFacade().saveRevisao();
	}
}
