package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.AlunoController;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.jpa.controller.TurmaController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Turma;
import br.com.clogos.estagio.util.Util;

public class TransferirAlunoFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cpfProcura;
	private Aluno aluno;
	private Turma turma;
	private List<Aluno> listaAlunoTurma;
	private List<Turma> listaTurmaSemVinculoAluno;
	private AlunoController alunoController;
	private RelatorioController relatorioController;
	private TurmaController turmaController;
	
	public List<Aluno> getListaAlunoTurma() {
		return listaAlunoTurma;
	}
	
	public List<Turma> getListaTurmaSemVinculoAluno() {
		listaTurmaSemVinculoAluno = getTurmaController().obterTurmaSemVinculoAluno(getAluno().getId(), 
				Util.getUsuarioSessao().getSemestre().getId());
		return listaTurmaSemVinculoAluno;
	}
	
	public void pesquisarAlunoTurma() {
		if(cpfProcura != null) {
			String cpf = cpfProcura.replace(".", "").replace("-", "");
			listaAlunoTurma = getAlunoController().findPorCpf(cpf, Util.getUsuarioSessao().getSemestre().getId());
			if(listaAlunoTurma.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Nenhum Aluno encontrado.", ""));
			}
		}
	}
	
	public void transferirAluno() {
		if(aluno != null) {
			if(getAlunoController().transferirAlunoTurmaRelatorio(getAluno(), getTurma().getId())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Transferência efetuada com sucesso.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Problemas ao transferir aluno.", ""));
			}
			cpfProcura=null;listaAlunoTurma=null;aluno=null;turma=null;
		}
	}
	
	public void limpar() {
		this.turma = null;
		this.aluno = null;
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
	
	public RelatorioController getRelatorioController() {
		return relatorioController == null ? relatorioController = new RelatorioController() : relatorioController;
	}
	
	public TurmaController getTurmaController() {
		return turmaController == null ? turmaController = new TurmaController() : turmaController;
	}

	public String getCpfProcura() {
		return cpfProcura;
	}

	public void setCpfProcura(String cpfProcura) {
		this.cpfProcura = cpfProcura;
	}

	public Turma getTurma() {
		return turma == null ? turma = new Turma() : turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
