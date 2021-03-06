package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Turma;

public interface TurmaDAO {
	List<Turma> findAll(Long idSemestre);
	List<Turma> obterTurmaPorAluno(Long idSemestre, Long idAluno);
	List<Turma> obterTurmaSemVinculoAluno(Long idAluno, Long idSemestre);
	List<Turma> obterTurmaGrupo(Long idSemestre);
	Boolean verificaDuplicidade(String nomeTurma, Long idSemestre);
	Turma obterCurso(String nomeTurma);
	Turma obterTurma(Long id);
	Turma obterTurmaPorNome(String nomeTurma, Long idSemestre);
}
