package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.jpa.dao.AlunoDAO;
import br.com.clogos.estagio.jpa.dao.impl.AlunoDAOImpl;
import br.com.clogos.estagio.model.Aluno;


public class AlunoController implements Serializable {
	private static final long serialVersionUID = 1L;
	private AlunoDAO alunoDAO;
	
	public List<Aluno> findAll() {
		return getAlunoDAO().findAll();
	}
	
	public Aluno validarAutenticacao(Aluno param) {
		return getAlunoDAO().validarAutenticacao(param);
	}
	
	public Boolean updateSenha(String cpf, String senha) {
		return getAlunoDAO().updateSenha(cpf, senha);
	}
	
	public Aluno find(Long idAluno) {
		return getAlunoDAO().find(idAluno);
	}
	
	public List<Aluno> findPorTurma(Long idTurma, Long idSemestre) {
		return getAlunoDAO().findPorTurma(idTurma, idSemestre);
	}
	
	public List<Aluno> findPorCpf(String cpf, Long idSemestre) {
		return getAlunoDAO().findPorCpf(cpf, idSemestre);
	}
	
	public AlunoDAO getAlunoDAO() {
		if(alunoDAO == null) {
			alunoDAO = new AlunoDAOImpl();
		}
		return alunoDAO;
	}

}
