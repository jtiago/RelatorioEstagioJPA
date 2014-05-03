package br.com.clogos.estagio.jsf.facade;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.clogos.estagio.model.Aluno;

public class AutenticarFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Aluno usuario;
	
	public void login() {
		try {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest();
			FacesContext.getCurrentInstance().getExternalContext().redirect((new StringBuilder(String.valueOf(request.getContextPath()))).append("/pages/home.jsf").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void logout() {
		 HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    try {
		        request.getSession().invalidate();
		        request.getSession().isNew();
		        FacesContext.getCurrentInstance().getExternalContext().redirect((new StringBuilder(String.valueOf(request.getContextPath()))).append("/").toString());
		} catch(IOException e) {
		    e.printStackTrace();
		}
	}

	public Aluno getUsuario() {
		return usuario == null ? new Aluno() : usuario;
	}

	public void setUsuario(Aluno usuario) {
		this.usuario = usuario;
	}
	
	

}
