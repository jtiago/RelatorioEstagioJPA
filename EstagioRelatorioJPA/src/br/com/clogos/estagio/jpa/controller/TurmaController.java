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
	
	public List<Turma> findAll(Long idSemestre) {
		return getTurmaDAO().findAll(idSemestre);
	}
	
	public Boolean verificaDuplicidade(String nomeTurma, Long idSemestre) {
		return getTurmaDAO().verificaDuplicidade(nomeTurma, idSemestre);
	}
	
	public Turma obterCurso(String nomeTurma) {
		return getTurmaDAO().obterCurso(nomeTurma);
	}
	
	public Turma obterTurma(Long id) {
		return getTurmaDAO().obterTurma(id);
	}
	
	public Turma obterTurmaPorNome(String nomeTurma, Long idSemestre) {
		return getTurmaDAO().obterTurmaPorNome(nomeTurma, idSemestre);
	}
	
	public List<Turma> obterTurmaPorAluno(Long idSemestre, Long idAluno) {
		return getTurmaDAO().obterTurmaPorAluno(idSemestre, idAluno);
	}
	
	public List<Turma> obterTurmaSemVinculoAluno(Long idAluno, Long idSemestre) {
		return getTurmaDAO().obterTurmaSemVinculoAluno(idAluno, idSemestre);
	}
	
	public List<Turma> obterTurmaGrupo(Long idSemestre) {
		return getTurmaDAO().obterTurmaGrupo(idSemestre);
	}
}
