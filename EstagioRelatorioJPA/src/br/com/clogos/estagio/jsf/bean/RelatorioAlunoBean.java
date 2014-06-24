package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.RelatorioAlunoFacade;

@ManagedBean(name="relatorioAlunoBean")
@ViewScoped
public class RelatorioAlunoBean implements Serializable {
	private static final long serialVersionUID = 6378902231545732309L;
	private RelatorioAlunoFacade facade;
	
	public RelatorioAlunoFacade getFacade() {
		return facade == null ? facade = new RelatorioAlunoFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
	}
	
	public void limpar(ActionEvent event) {
		getFacade().limpar();
	}
	
	public Boolean getMensagemEnf(String modulo) {
		if(modulo.contains("II M") || modulo.contains("III M")) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean getMensagemRad(String modulo, String curso) {
		if(modulo.contains("II e III") && (curso.contains("radiologia")) || curso.contains("RADIOLOGIA")) {
			return true;
		} else {
			return false;
		}
	}
}