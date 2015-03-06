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
	private List<Semestre> listaSemeste;
	private GenericController genericController;
	
	@SuppressWarnings("unchecked")
	public List<Semestre> getListaSemeste() {
		if(listaSemeste == null) {
			listaSemeste = (List<Semestre>) getGenericController().findAll(Semestre.class, "nomeSemeste", "desc", "");
		}
		return listaSemeste;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
}
