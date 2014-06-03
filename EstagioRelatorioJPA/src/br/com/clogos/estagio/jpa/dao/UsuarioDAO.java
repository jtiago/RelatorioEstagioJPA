package br.com.clogos.estagio.jpa.dao;

import br.com.clogos.estagio.model.Usuario;

public interface UsuarioDAO {
	Usuario validarAutenticacao(Usuario usuario);
}
