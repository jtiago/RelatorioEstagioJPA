package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.RelatorioAdminFacade;

@ManagedBean(name="relatorioAdminBean")
@ViewScoped
public class RelatorioAdminBean implements Serializable {
	private static final long serialVersionUID = -7198762966541652474L;
	private RelatorioAdminFacade facade;
	
	public RelatorioAdminFacade getFacade() {
		return facade == null ? facade = new RelatorioAdminFacade() : facade;
	}
	
	public void pesquisarRelatorio(ActionEvent event) {
		getFacade().pesquisaRelatorio();
	}
	
	public void validarRelatorio(ActionEvent event) {
		getFacade().validarRelatorio();
	}
	
	public void revisarRelatorio(ActionEvent event) {
		getFacade().revisarRelatorio();
	}
	
	public void gerarRelatorio(ActionEvent event) {
		getFacade().geraRelatorio();
	}
}
