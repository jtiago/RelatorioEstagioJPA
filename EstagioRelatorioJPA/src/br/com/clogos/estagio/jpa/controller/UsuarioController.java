package br.com.clogos.estagio.jpa.controller;

import java.io.Serializable;

import br.com.clogos.estagio.jpa.dao.UsuarioDAO;
import br.com.clogos.estagio.jpa.dao.impl.UsuarioDAOImpl;
import br.com.clogos.estagio.model.Usuario;

public class UsuarioController implements Serializable {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO;
	
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO == null ? usuarioDAO = new UsuarioDAOImpl() : usuarioDAO;
	}
	
	public Usuario validarAutenticacao(Usuario usuario) {
		return getUsuarioDAO().validarAutenticacao(usuario);
	}
}
