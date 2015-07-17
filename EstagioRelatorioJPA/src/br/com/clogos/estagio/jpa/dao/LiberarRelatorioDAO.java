package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.LiberarRelatorio;
import br.com.clogos.estagio.model.Turma;

public interface LiberarRelatorioDAO {
	Boolean existeModuloLiberado(LiberarRelatorio oT);
	Boolean existeModuloAberto(LiberarRelatorio oT);
	Boolean fecharModuloLiberado(Long idTurma, String modulo);
	List<LiberarRelatorio> findAll(Long idSemestre);
	Boolean verificaQtdRelatorio(Aluno aluno);
	LiberarRelatorio obterLiberarRelatorioPorTurma(Turma turma);
}
