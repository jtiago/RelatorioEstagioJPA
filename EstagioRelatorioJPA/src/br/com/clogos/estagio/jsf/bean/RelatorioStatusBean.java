package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.RelatorioStatusFacade;

@ManagedBean(name="relatorioStatusBean")
@ViewScoped
public class RelatorioStatusBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RelatorioStatusFacade facade;
	private boolean mensagem;
	
	public RelatorioStatusFacade getFacade() {
		return facade == null ? facade = new RelatorioStatusFacade() : facade;
	}
	
	public void pesquisarRelatorio(ActionEvent event) {
		getFacade().pesquisaRelatorio();
	}

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
}
