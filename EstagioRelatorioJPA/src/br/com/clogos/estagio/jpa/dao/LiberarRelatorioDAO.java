package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.LiberarRelatorio;

public interface LiberarRelatorioDAO {
	Boolean existeModuloLiberado(LiberarRelatorio oT);
	Boolean existeModuloAberto(LiberarRelatorio oT);
	Boolean fecharModuloLiberado(Long idTurma, String modulo);
	List<LiberarRelatorio> findAll(Long idSemestre);
}
