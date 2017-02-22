package br.com.clogos.estagio.exception;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ExceptionNegocial {
	
	public static void exibirMsgSucesso(String detail) {
		String summary = "";
		exibirMsg(summary, detail, FacesMessage.SEVERITY_INFO);
	}
	
	public static void exibirMsgErro(String summary) {
		exibirMsg(summary, "", FacesMessage.SEVERITY_ERROR);
	}
	
	public static void exibirMsgWarn(String summary) {
		exibirMsg(summary, "", FacesMessage.SEVERITY_WARN);
	}
	
	private static void exibirMsg(String summary, String detail, FacesMessage.Severity severity) {
		FacesMessage facesMessage = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

}
