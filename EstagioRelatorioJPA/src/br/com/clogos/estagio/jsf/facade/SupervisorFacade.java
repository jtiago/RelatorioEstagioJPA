package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.clogos.estagio.enums.CursoEnum;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.SupervisorController;
import br.com.clogos.estagio.model.ImagemAssinatura;
import br.com.clogos.estagio.model.Semestre;
import br.com.clogos.estagio.model.Supervisor;
import br.com.clogos.estagio.util.CriptografiaBase64;
import br.com.clogos.estagio.util.Util;
import br.com.clogos.estagio.vo.SupervisorVO;

public class SupervisorFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Supervisor supervisor;
	private Supervisor supervisorAltera;
	private SupervisorController supervisorController;
	private GenericController genericControl;
	private List<Supervisor> listaSupervisores;
	private List<SupervisorVO> listaSupervisoresAnalitico;
	
	public List<Supervisor> getListaSupervisores() {
		if(listaSupervisores == null) {
			listaSupervisores = getSupervisorController().findAll();
		}
		return listaSupervisores;
	}
	
	public List<SupervisorVO> getListaSupervisoresAnalitico() {
		if(listaSupervisoresAnalitico == null) {
			listaSupervisoresAnalitico = getSupervisorController().findSupervisorAnalitico(Util.getIdSemestre());
		}
		return listaSupervisoresAnalitico;
	}
	
	public void save(ImagemAssinatura assinatura) {
		getSupervisor().setCpf(getSupervisor().getCpf().replace(".", "").replace("-", ""));
		getSupervisor().setCodigoSituacao(1);
		getSupervisor().getPerfil().setId(3L);
		getSupervisor().setImagem(assinatura);
		getSupervisor().setSenha(CriptografiaBase64.encrypt("12345678"));
		try {
			getGenericControl().save(getSupervisor());
			supervisor=null; genericControl = null; listaSupervisores = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Supervisor salvo com suceso.", ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Supervisor.", ""));
		}
	}
	
	public CursoEnum[] getCursoEnum() {
		return CursoEnum.values();
	}
	
	public void remover() {
		try {
			if(supervisor != null) {
				getGenericControl().remove(getSupervisor());
				supervisor = null; genericControl = null; listaSupervisores = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Supervisor removido com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao remover Supervisor.", ""));
		}
	}
	
	public void update() {
		try {
			if(supervisorAltera != null) {
				getGenericControl().update(getSupervisorAltera());
				supervisorAltera = null; genericControl = null; listaSupervisores = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Supervisor alterado com suceso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Supervisor.", ""));
		}
	}
	
	public void updateSenha(String cpf, String senha) {
		if(getSupervisorController().updateSenha(cpf, senha)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Senha alterado com suceso.", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao alterar Senha.", ""));
		}
	}
	
	public void login(Supervisor supervisor) {
		supervisor.setCpf(supervisor.getCpf().replace(".", "").replace("-", ""));
		Supervisor usuarioLogado = getSupervisorController().validarAutenticacao(supervisor);
		
		try {
			if(usuarioLogado != null) { 
				usuarioLogado.setSemestre((Semestre) getGenericControl().findID(Semestre.class, "idSemestre", supervisor.getSemestre().getId()));
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest();
				request.getSession().setAttribute("usuarioLogado", usuarioLogado);
				FacesContext.getCurrentInstance().getExternalContext().redirect((
						new StringBuilder(String.valueOf(request.getContextPath()))).append("/pages/home.jsf").toString());
			} else {
				supervisor = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Login ou Senha Inválida.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Supervisor getSupervisor() {
		return supervisor == null ? supervisor = new Supervisor() : supervisor;
	}
	
	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	public Supervisor getSupervisorAltera() {
		return supervisorAltera == null ? supervisorAltera = new Supervisor() : supervisorAltera;
	}

	public void setSupervisorAltera(Supervisor supervisorAltera) {
		this.supervisorAltera = supervisorAltera;
	}

	public SupervisorController getSupervisorController() {
		return supervisorController == null ? supervisorController = new SupervisorController() : supervisorController;
	}
	
	public GenericController getGenericControl() {
		return genericControl == null ? genericControl = new GenericController() : genericControl;
	}
}
