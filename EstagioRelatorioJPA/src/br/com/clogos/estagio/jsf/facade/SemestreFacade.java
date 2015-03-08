package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.model.Semestre;

public class SemestreFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Semestre> listaSemestre;
	private GenericController genericController;
	
	@SuppressWarnings("unchecked")
	public List<Semestre> getListaSemestre() {
		if(listaSemestre == null) {
			listaSemestre = (List<Semestre>) getGenericController().findAll(Semestre.class, "nomeSemestre", "desc", "");
		}
		return listaSemestre;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
}
