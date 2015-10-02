package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Supervisor;

public interface SupervisorDAO {
	List<Supervisor> findAll();
	List<Supervisor> findPorCampo(Long idCampo);
	Supervisor validarAutenticacao(Supervisor param);
}
