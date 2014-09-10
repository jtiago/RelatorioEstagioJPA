package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Usuario;

public interface UsuarioDAO {
	List<Usuario> findAll();
	Usuario validarAutenticacao(Usuario usuario);
	Boolean updateSenha(String cpf, String senha);
}
