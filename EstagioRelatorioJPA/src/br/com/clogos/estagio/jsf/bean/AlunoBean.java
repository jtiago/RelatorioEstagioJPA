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
	
	public AlunoFacade getFacade() {
		return facade == null ? facade = new AlunoFacade() : facade;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
	}
	
	public void update(ActionEvent event) {
		getFacade().update();
	}
	
	public void fileUpload(FileUploadEvent event) {
		System.out.println(event.getFile().getFileName());
        
    }

}
