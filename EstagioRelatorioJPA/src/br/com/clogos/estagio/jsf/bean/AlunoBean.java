package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;

import br.com.clogos.estagio.jsf.facade.AlunoFacade;

@ManagedBean(name="alunoBean")
@ViewScoped
public class AlunoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private AlunoFacade facade;
	private boolean mensagem;
	
	public AlunoBean() {
		mensagem = false;
	}
	
	public AlunoFacade getFacade() {
		return facade == null ? facade = new AlunoFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
		mensagem = true;
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
		mensagem = true;
	}
	
	public void update(ActionEvent event) {
		getFacade().update();
		mensagem = true;
	}
	
	public void fileUpload(FileUploadEvent event) {
		System.out.println(event.getFile().getFileName());
        
    }

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
	
	

}
