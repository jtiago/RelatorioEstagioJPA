package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.UsuarioFacade;
import br.com.clogos.estagio.model.Usuario;

@ManagedBean(name="usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = -5826199740184047009L;
	private UsuarioFacade facade;
	private static final String SENHA_PADRA = "12345678";
	private Usuario usuario;
	private boolean mensagem;
	
	public UsuarioFacade getFacade() {
		return facade == null ? facade = new UsuarioFacade() : facade;
	}
	
	public void resetSenha(ActionEvent event) {
		getFacade().updateSenha(getUsuario().getCpf(), SENHA_PADRA);
		mensagem = true;
	}
	
	public void save(ActionEvent event) {
		getFacade().save();
		mensagem = true;
	}
	
	public void update(ActionEvent event) {
		getFacade().update();
		mensagem = true;
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
		mensagem = true;
	}

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getUsuario() {
		return usuario == null ? usuario = new Usuario() : usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
