package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Aluno;

public interface AlunoDAO {
	List<Aluno> findAll();
	Aluno validarAutenticacao(Aluno aluno);
}
