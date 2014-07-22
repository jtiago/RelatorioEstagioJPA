package br.com.clogos.estagio.jsf.facade;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import br.com.clogos.estagio.jpa.controller.AlunoController;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.util.CriptografiaBase64;

public class AlunoFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Aluno aluno;
	private Aluno alunoAltera;
	private List<Aluno> listaAlunos;
	private List<Aluno> listaAlunosFilter;
	private GenericController genericController;
	private AlunoController alunoController;
	
	public List<Aluno> getListaAlunos() {
		if(listaAlunos == null) {
			listaAlunos = getAlunoController().findAll();
		}
		return listaAlunos;
	}
	
	public void save() {
		try {
			getAluno().setCpf(getAluno().getCpf().replace(".", "").replace("-", ""));
			getAluno().getPerfil().setId(2L);
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
	
	public void saveList() {
		
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
				getAlunoAltera().setCpf(getAlunoAltera().getCpf().replace(".", "").replace("-", ""));
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
	
	public void updateSenha(String cpf, String senha) {
		if(getAlunoController().updateSenha(cpf, senha)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Senha alterado com suceso.", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Senha.", ""));
		}
	}
	
	public void login(Aluno usuario) {
		usuario.setCpf(usuario.getCpf().replace(".", "").replace("-", ""));
		Aluno alunoLogado = getAlunoController().validarAutenticacao(usuario);
		
		try {
			if(alunoLogado != null) { 
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest();
				request.getSession().setAttribute("usuarioLogado", alunoLogado);
				FacesContext.getCurrentInstance().getExternalContext().redirect((
						new StringBuilder(String.valueOf(request.getContextPath()))).append("/pages/home.jsf").toString());
			} else {
				usuario = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Login ou Senha Invalida.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void logout() {
		try {
			HttpServletRequest servletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Enumeration enumeration = servletRequest.getSession().getAttributeNames();
			
			while(enumeration.hasMoreElements()){
				servletRequest.getSession().removeAttribute(enumeration.nextElement().toString());
			}
			servletRequest.getSession().invalidate();
			FacesContext.getCurrentInstance().getExternalContext().redirect(servletRequest.getContextPath()+"/");
		} catch (IOException e) {
			e.printStackTrace();
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

	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
	public AlunoController getAlunoController() {
		return alunoController == null ? alunoController = new AlunoController() : alunoController;
	}

	public List<Aluno> getListaAlunosFilter() {
		return listaAlunosFilter;
	}

	public void setListaAlunosFilter(List<Aluno> listaAlunosFilter) {
		this.listaAlunosFilter = listaAlunosFilter;
	}
}
