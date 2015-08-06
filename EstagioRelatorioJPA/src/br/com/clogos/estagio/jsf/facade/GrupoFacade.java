package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.GrupoController;
import br.com.clogos.estagio.model.Grupo;

public class GrupoFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Grupo grupo;
	private List<Grupo> listaGrupos;
	private GenericController genericController;
	private GrupoController grupoController;
	
	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
	
	public GrupoController getGrupoController() {
		return grupoController == null ? grupoController = new GrupoController() : grupoController;
	}
	
	public Grupo getGrupo() {
		return grupo == null ? grupo = new Grupo() : grupo;
	}
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
}
