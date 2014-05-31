package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Turma;

public interface TurmaDAO {
	List<Turma> findAll();
	Boolean verificaDuplicidade(String nomeTurma);
}
