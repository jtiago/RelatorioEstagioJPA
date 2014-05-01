package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class UtilBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Boolean mensage;

    public Boolean getMensage() {
        return mensage;
    }

    public void setMensage(Boolean mensage) {
        this.mensage = mensage;
    }
}