package br.com.clogos.estagio.jpa.dao;

import java.util.List;

import br.com.clogos.estagio.model.Supervisor;
import br.com.clogos.estagio.vo.SupervisorVO;

public interface SupervisorDAO {
	List<Supervisor> findAll();
	List<Supervisor> findPorCampo(Long idCampo);
	Supervisor validarAutenticacao(Supervisor param);
	List<SupervisorVO> findSupervisorAnalitico(Long idSemestre);
	Boolean updateSenha(String cpf, String senha);
}
