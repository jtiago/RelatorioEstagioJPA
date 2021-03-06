package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Aluno;

public interface AlunoDAO {
	List<Aluno> findAll();
	Aluno validarAutenticacao(Aluno aluno);
	Boolean updateSenha(String cpf, String senha);
	Aluno find(Long idAluno);
	List<Aluno> findPorTurma(Long idTurma, Long idSemestre);
	List<Aluno> findPorTurmaGrupo(Long idTurma, Long idSemestre);
	List<Aluno> findPorCpf(String cpf, Long idSemestre);
	Boolean transferirAlunoTurmaRelatorio(Aluno aluno, Long idTurmaTransferir);
}
