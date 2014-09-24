package br.com.clogos.estagio.jsf.facade;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.UsuarioController;
import br.com.clogos.estagio.model.Usuario;
import br.com.clogos.estagio.util.CriptografiaBase64;

public class UsuarioFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Usuario usuarioAltera;
	private UsuarioController usuarioController;
	private GenericController genericController;
	private List<Usuario> listaUsuarios;
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getListaUsuarios() {
		if(listaUsuarios == null) {
			listaUsuarios = (List<Usuario>) getGenericController().findAll(Usuario.class, "nome", "asc");
		}
		return listaUsuarios;
	}
	
	public void updateSenha(String cpf, String senha) {
		if(getUsuarioController().updateSenha(cpf, senha)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Senha Resetada. Atribuida senha padrão.", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Senha.", ""));
		}
	}
	
	public void login(Usuario usuario) {
		usuario.setCpf(usuario.getCpf().replace(".", "").replace("-", ""));
		Usuario usuarioLogado = getUsuarioController().validarAutenticacao(usuario);
		
		try {
			if(usuarioLogado != null) { 
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest();
				request.getSession().setAttribute("usuarioLogado", usuarioLogado);
				FacesContext.getCurrentInstance().getExternalContext().redirect((
						new StringBuilder(String.valueOf(request.getContextPath()))).append("/pages/home.jsf").toString());
			} else {
				usuario = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Login ou Senha Inválida.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void logout() {
		try {
			HttpServletRequest servletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Enumeration enumeration = servletRequest.getSession().getAttributeNames();
			
			while(enumeration.hasMoreElements()){
				servletRequest.getSession().removeAttribute(enumeration.nextElement().toString());
			}
			servletRequest.getSession().invalidate();
			FacesContext.getCurrentInstance().getExternalContext().redirect(servletRequest.getContextPath()+"/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		try {
			getUsuario().setCpf(getUsuario().getCpf().replace(".", "").replace("-", ""));
			getUsuario().setSenha(CriptografiaBase64.encrypt("12345678")); // Senha padrão ao fazer novo cadastro
			getGenericController().save(getUsuario());
			usuario=null; genericController = null; listaUsuarios = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Usuário salvo com sucesso. Senha padrão para primeiro acesso", ""));
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Usuário.", ""));
		}
	}
	
	public void update() {
		try {
			if(usuarioAltera != null) {
				usuarioAltera.setCpf(getUsuarioAltera().getCpf().replace(".", "").replace("-", ""));
				getGenericController().update(getUsuarioAltera());
				usuarioAltera = null; genericController = null; listaUsuarios = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Usuário alterado com sucesso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Usuário.", ""));
		}
	}
	
	public void remover() {
		try {
			if(usuario != null) {
				getGenericController().remove(getUsuario());
				usuario = null; genericController = null; listaUsuarios = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Usuário removido com sucesso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao remover Usuário.", ""));
		}
	}
	
	public UsuarioController getUsuarioController() {
		return usuarioController == null ? usuarioController = new UsuarioController() : usuarioController;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}

	public Usuario getUsuario() {
		return usuario == null ? usuario = new Usuario() : usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioAltera() {
		return usuarioAltera == null ? usuarioAltera = new Usuario() : usuarioAltera;
	}

	public void setUsuarioAltera(Usuario usuarioAltera) {
		this.usuarioAltera = usuarioAltera;
	}
}
