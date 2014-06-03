package br.com.clogos.estagio.jpa.dao;

import br.com.clogos.estagio.model.Aluno;

public interface AutenticarDAO {
	Aluno validarAutenticacao(Aluno aluno);
}
