package br.com.clogos.estagio.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Supervisor;
import br.com.clogos.estagio.model.Usuario;

public class Util {
	
	public static Usuario getUsuarioSessao() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Usuario usuario = (Usuario) httpSession.getAttribute("usuarioLogado");
		return usuario;
	}
	
	public static Aluno getAlunoSessao() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Aluno aluno = (Aluno) httpSession.getAttribute("usuarioLogado");
		return aluno;
	}
	
	public static Long getIdSemestre() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false); 
		Object object = httpSession.getAttribute("usuarioLogado");
		
		if (object instanceof Usuario) {
			Usuario usuario = (Usuario) object;
			return usuario.getSemestre().getId();
		} else {
			Supervisor supervisor = (Supervisor) object;
			return supervisor.getSemestre().getId();
		}
	}
	
	public static Date convertStringToDate(String param) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date data = null;
		try {
			data = formato.parse(param);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return data;
	}
}
