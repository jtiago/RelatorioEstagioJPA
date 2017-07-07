package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Relatorio;
import br.com.clogos.estagio.vo.FichaAvaliacaoVO;
import br.com.clogos.estagio.vo.RelatorioStatusVO;

public interface RelatorioDAO {
	Boolean existeRelatorioPreenchido(Aluno aluno);
	List<Relatorio> findRelatoriosAdmin(Relatorio relatorio);
	Boolean updateValidarRelatorio(Long id, String observacao);
	Boolean updateRevisaoRelatorio(Long id, String observacao);
	List<Relatorio> findRelatoriosRevisao(Aluno aluno);
//	Boolean saveRevisaoRelatorioAluno(Relatorio relatorio);
	List<Relatorio> findRelatorioEnviado(Aluno aluno);
	Boolean alterarDataInicioTerminioRelatorio(Relatorio relatorio);
	Boolean existeRelatorioGrupoCampoEstagio(Relatorio relatorio);
	
	List<RelatorioStatusVO> findRelatorioStatus(Long idTurma, Long idSemestre);
	FichaAvaliacaoVO findFichaAvaliacao(Long idAluno, Long idTurma, Long idSemestre);
}
