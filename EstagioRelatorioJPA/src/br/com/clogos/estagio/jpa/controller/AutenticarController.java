package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;

import br.com.clogos.estagio.jpa.dao.AutenticarDAO;
import br.com.clogos.estagio.jpa.dao.impl.AutenticarDAOImpl;
import br.com.clogos.estagio.model.Aluno;

public class AutenticarController implements Serializable {
	private static final long serialVersionUID = 1L;
	private AutenticarDAO autenticarDAO;
	
	public AutenticarDAO getAutenticarDAO() {
		return autenticarDAO == null ? autenticarDAO = new AutenticarDAOImpl() : autenticarDAO;
	}
	
	public Aluno validarAutenticacao(Aluno aluno) {
		return getAutenticarDAO().validarAutenticacao(aluno);
	}
	

}
