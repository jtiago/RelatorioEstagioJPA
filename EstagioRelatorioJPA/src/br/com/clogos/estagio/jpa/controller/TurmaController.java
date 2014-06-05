package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.TurmaDAO;
import br.com.clogos.estagio.jpa.dao.impl.TurmaDAOImpl;
import br.com.clogos.estagio.model.Turma;

public class TurmaController implements Serializable {
	private static final long serialVersionUID = -7806340542085082099L;
	private TurmaDAO turmaDAO;
	
	public TurmaDAO getTurmaDAO() {
		if(turmaDAO == null) {
			turmaDAO = new TurmaDAOImpl();
		}
		return turmaDAO;
	}
	
	public List<Turma> findAll() {
		return getTurmaDAO().findAll();
	}
	
	public Boolean verificaDuplicidade(String nomeTurma) {
		return getTurmaDAO().verificaDuplicidade(nomeTurma);
	}
	
	public Turma obterCurso(String nomeTurma) {
		return getTurmaDAO().obterCurso(nomeTurma);
	}
}
