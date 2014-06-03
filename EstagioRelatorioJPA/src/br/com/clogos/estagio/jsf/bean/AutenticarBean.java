package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.AutenticarFacade;
import br.com.clogos.estagio.jsf.facade.UsuarioFacade;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Perfil;
import br.com.clogos.estagio.model.Usuario;

@ManagedBean(name="autenticarBean")
@SessionScoped
public class AutenticarBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutenticarFacade facadeAutenticar;
	private UsuarioFacade facadeUsuario; 
	private Aluno aluno;
	private Perfil perfil;

	public UsuarioFacade getFacadeUsuario() {
		return facadeUsuario == null ? new UsuarioFacade() : facadeUsuario;
	}
	
	public AutenticarFacade getFacadeAutenticar() {
		return facadeAutenticar == null ? new AutenticarFacade() : facadeAutenticar;
	}
	
	public void login(ActionEvent event){
		if(getPerfil().getId().equals(1)) {
			Usuario usuario = new Usuario();
			getFacadeUsuario().login(usuario);
		} else {
			getFacadeAutenticar().login(getAluno());
		}
	}
	
	public void logout(ActionEvent event) {
		//getFacade().logout();
	}

	public Aluno getAluno() {
		return aluno == null ? aluno = new Aluno() : aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Perfil getPerfil() {
		return perfil == null ? perfil = new Perfil() : perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
