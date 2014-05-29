package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.jpa.controller.AlunoController;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.util.CriptografiaBase64;

public class AlunoFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Aluno aluno;
	private Aluno alunoAltera;
	private List<Aluno> listaAlunos;
	private GenericController<Aluno> genericController;
	private AlunoController alunoController;
	
	public List<Aluno> getListaAlunos() {
		if(listaAlunos == null) {
			listaAlunos = getAlunoController().findAll();
		}
		return listaAlunos;
	}
	
	public void save() {
		try {
			getAluno().setSenha(CriptografiaBase64.encrypt(getAluno().getSenha()));
			getGenericController().save(getAluno());
			aluno=null; genericController = null; listaAlunos = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Aluno salvo com suceso.", ""));
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Aluno.", ""));
		}
	}
	
	public void remover() {
		try {
			if(aluno != null) {
				getGenericController().remove(getAluno());
				aluno = null; genericController = null; listaAlunos = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Aluno removido com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao remover Aluno.", ""));
		}
	}
	
	public void update() {
		try {
			if(alunoAltera != null) {
				getGenericController().update(getAlunoAltera());
				alunoAltera = null; genericController = null; listaAlunos = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Aluno alterado com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Aluno.", ""));
		}
	}
	
	
	public Aluno getAluno() {
		return aluno == null ? aluno = new Aluno() : aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	public Aluno getAlunoAltera() {
		return alunoAltera == null ? alunoAltera = new Aluno() : alunoAltera;
	}

	public void setAlunoAltera(Aluno alunoAltera) {
		this.alunoAltera = alunoAltera;
	}

	public GenericController<Aluno> getGenericController() {
		return genericController == null ? genericController = new GenericController<Aluno>() : genericController;
	}
	public AlunoController getAlunoController() {
		return alunoController == null ? alunoController = new AlunoController() : alunoController;
	}
	
}