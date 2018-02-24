package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class UtilBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Boolean mensage = true;

    public Boolean getMensage() {
        return mensage;
    }

    public void setMensage(Boolean mensage) {
        this.mensage = mensage;
    }
    
    public void verificaSession() {
    	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
                "No activity.", "What are you doing over there?"));
    }
}