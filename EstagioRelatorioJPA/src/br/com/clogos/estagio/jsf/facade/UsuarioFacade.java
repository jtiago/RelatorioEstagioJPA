package br.com.clogos.estagio.jsf.facade;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.UsuarioController;
import br.com.clogos.estagio.model.Usuario;

public class UsuarioFacade implements Serializable {
	private static final long serialVersionUID = 1L;
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
					FacesMessage.SEVERITY_INFO, "Senha alterado com suceso.", ""));
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
						FacesMessage.SEVERITY_ERROR, "Login ou Senha Invalida.", ""));
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
	
	public UsuarioController getUsuarioController() {
		return usuarioController == null ? usuarioController = new UsuarioController() : usuarioController;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
}
