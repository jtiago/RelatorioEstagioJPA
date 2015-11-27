package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.GrupoDAO;
import br.com.clogos.estagio.jpa.dao.impl.GrupoDAOImpl;
import br.com.clogos.estagio.model.Grupo;

public class GrupoController implements Serializable {
	private static final long serialVersionUID = 1L;
	private GrupoDAO dao;
	
	public GrupoDAO getDao() {
		return dao == null ? dao = new GrupoDAOImpl() : dao;
	}
	
	public List<Grupo> findAll(Long idSemestre) {
		return getDao().findAll(idSemestre);
	}
	
	public Grupo findGrupoAluno(Long idSemestre, Long idGrupo) {
		return getDao().findGrupoAluno(idSemestre, idGrupo);
	}
	
	public Grupo findGrupoCPF(Long idSemestre, String cpf, Long idCampo) {
		return getDao().findGrupoCPF(idSemestre, cpf, idCampo);
	}
	
	public Grupo findGrupoPorTurmaAluno(Long idTurma, Long idAluno, Long idSemestre) {
		return getDao().findGrupoPorTurmaAluno(idTurma, idAluno, idSemestre);
	}
}
