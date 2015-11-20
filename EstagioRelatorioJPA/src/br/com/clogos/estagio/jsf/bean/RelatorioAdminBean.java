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
	private boolean mensagem;
	
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
	
	public void alterarDataInicioTerminio(ActionEvent event) {
		getFacade().alterarDataInicioTerminioRelatorio();
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
		mensagem = true;
	}

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
}
