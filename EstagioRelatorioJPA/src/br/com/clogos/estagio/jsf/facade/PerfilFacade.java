package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.model.Perfil;

public class PerfilFacade implements Serializable {
	private static final long serialVersionUID = 3636148167288087307L;
	private List<Perfil> listaPerfil;
	
	@SuppressWarnings("unchecked")
	public List<Perfil> getListaPerfil() {
		if(listaPerfil == null) {
			GenericController genericController = new GenericController();
			listaPerfil = (List<Perfil>) genericController.findAll(Perfil.class, "nome", "asc", "");
			genericController = null;
		}
		return listaPerfil;
	}
}
