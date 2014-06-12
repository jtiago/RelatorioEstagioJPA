package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;

import br.com.clogos.estagio.jpa.dao.LiberarRelatorioDAO;
import br.com.clogos.estagio.jpa.dao.impl.LiberarRelatorioDAOImpl;
import br.com.clogos.estagio.model.LiberarRelatorio;

public class LiberarRelatorioController implements Serializable {
	private static final long serialVersionUID = -7893894309861626614L;
	private LiberarRelatorioDAO liberarRelatorioDAO;
	
	public LiberarRelatorioDAO getLiberarRelatorioDAO() {
		return liberarRelatorioDAO == null ? liberarRelatorioDAO = new LiberarRelatorioDAOImpl() : liberarRelatorioDAO;
	}
	
	public Boolean existeModuloLiberado(LiberarRelatorio oT) {
		return getLiberarRelatorioDAO().existeModuloLiberado(oT);
	}
	
	public Boolean existeModuloAberto(LiberarRelatorio oT) {
		return getLiberarRelatorioDAO().existeModuloAberto(oT);
	}
	
	public Boolean fecharModuloLiberado(Long idTurma, String modulo) {
		return getLiberarRelatorioDAO().fecharModuloLiberado(idTurma, modulo);
	}
}
