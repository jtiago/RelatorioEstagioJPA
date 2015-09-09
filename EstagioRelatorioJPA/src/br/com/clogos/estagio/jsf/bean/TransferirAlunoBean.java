package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.TransferirAlunoFacade;

@ManagedBean(name="transferirAluno")
@ViewScoped
public class TransferirAlunoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TransferirAlunoFacade facade;
	
	public TransferirAlunoFacade getFacade() {
		return facade == null ? facade = new TransferirAlunoFacade() : facade;
	}
	
	public void pesquisarAlunoTurma(ActionEvent event) {
		getFacade().pesquisarAlunoTurma();
	}

}
