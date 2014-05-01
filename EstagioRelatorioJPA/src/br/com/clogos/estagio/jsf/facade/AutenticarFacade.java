package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;

import br.com.clogos.estagio.model.Aluno;

public class AutenticarFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Aluno usuario;
	
	public void login() {
		
	}

	public Aluno getUsuario() {
		return usuario == null ? new Aluno() : usuario;
	}

	public void setUsuario(Aluno usuario) {
		this.usuario = usuario;
	}
	
	

}
