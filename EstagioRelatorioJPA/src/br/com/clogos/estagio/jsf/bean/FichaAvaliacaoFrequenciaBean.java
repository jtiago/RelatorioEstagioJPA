package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.FichaAvaliacaoFrequenciaFacade;

@ManagedBean(name="fichaBean")
@ViewScoped
public class FichaAvaliacaoFrequenciaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FichaAvaliacaoFrequenciaFacade facade;
	
	private boolean mensagem;
	
	public FichaAvaliacaoFrequenciaBean() {
		mensagem = false;
	}
	
	public FichaAvaliacaoFrequenciaFacade getFacade() {
		return facade == null ? facade = new FichaAvaliacaoFrequenciaFacade() : facade;
	}
	
	public void pesquisarAlunosTurma(ActionEvent event) {
		getFacade().pesquisarAlunoPorTurma();
	}
	
	public void pesquisarDadosFicha(ActionEvent event) {
		getFacade().pesquisarDadosFicha();
	}
	
	public void gerarRelatorio(ActionEvent event) {
		getFacade().geraRelatorio();
	}
	
	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
}

