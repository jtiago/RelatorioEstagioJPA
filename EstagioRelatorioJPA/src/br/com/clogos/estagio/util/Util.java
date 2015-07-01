package br.com.clogos.estagio.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.clogos.estagio.model.Usuario;

public class Util {
	
	public static Usuario getUsuarioSessao() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Usuario usuario = (Usuario) httpSession.getAttribute("usuarioLogado");
		return usuario;
	}
}
