package br.com.clogos.estagio.jpa.dao;

import java.io.Serializable;
import java.util.List;

import br.com.clogos.estagio.model.Grupo;

public interface GrupoDAO extends Serializable {
	List<Grupo> findAll(Long idSemestre);
	Grupo findGrupoAluno(Long idSemestre, Long idGrupo);
	Grupo findGrupoCPF(Long idSemestre, String cpf, Long idCampo);
}
