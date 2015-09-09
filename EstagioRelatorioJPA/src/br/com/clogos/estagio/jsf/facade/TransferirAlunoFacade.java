package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.AlunoController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.util.Util;

public class TransferirAlunoFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cpfProcura;
	private Aluno aluno;
	private List<Aluno> listaAlunoTurma;
	private AlunoController alunoController;
	
	public List<Aluno> getListaAlunoTurma() {
		return listaAlunoTurma;
	}
	
	public void pesquisarAlunoTurma() {
		if(cpfProcura != null) {
			String cpf = cpfProcura.replace(".", "").replace("-", "");
			listaAlunoTurma = getAlunoController().findPorCpf(cpf, Util.getUsuarioSessao().getIdSemestre());
			if(listaAlunoTurma.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Nenhum Aluno encontrado.", ""));
			}
		}
	}
	
	public Aluno getAluno() {
		return aluno == null ? aluno = new Aluno() : aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}	
	
	public AlunoController getAlunoController() {
		return alunoController == null ? alunoController = new AlunoController() : alunoController;
	}

	public String getCpfProcura() {
		return cpfProcura;
	}

	public void setCpfProcura(String cpfProcura) {
		this.cpfProcura = cpfProcura;
	}
}
