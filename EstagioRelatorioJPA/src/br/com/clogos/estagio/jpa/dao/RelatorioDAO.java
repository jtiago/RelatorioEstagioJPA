package br.com.clogos.estagio.jpa.dao;

import br.com.clogos.estagio.model.Aluno;

public interface RelatorioDAO {
	Boolean existeRelatorioPreenchido(Aluno aluno);
}
