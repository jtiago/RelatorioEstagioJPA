package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.CloseEvent;

import br.com.clogos.estagio.jsf.facade.TransferirAlunoFacade;

@ManagedBean(name="transferirAluno")
@ViewScoped
public class TransferirAlunoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TransferirAlunoFacade facade;
	private Boolean mensagem;
	
	public TransferirAlunoFacade getFacade() {
		return facade == null ? facade = new TransferirAlunoFacade() : facade;
	}
	
	public void pesquisarAlunoTurma(ActionEvent event) {
		getFacade().pesquisarAlunoTurma();
	}
	
	public void transferirAluno(ActionEvent event) {
		getFacade().transferirAluno();
		this.mensagem = true;
	}
	
	public void limpar(CloseEvent event) {
		getFacade().limpar();
	}

	public Boolean getMensagem() {
		return mensagem;
	}

	public void setMensagem(Boolean mensagem) {
		this.mensagem = mensagem;
	}
}
