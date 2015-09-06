package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.GrupoController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Grupo;
import br.com.clogos.estagio.model.GrupoCampoEstagio;

public class GrupoFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Grupo grupo;
	private List<Grupo> listaGrupos;
	private GenericController genericController;
	private GrupoController grupoController;
	
	private GrupoCampoEstagio campo1;
	private GrupoCampoEstagio campo2;
	private GrupoCampoEstagio campo3;
	private GrupoCampoEstagio campo4;
	
	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}
	
	public void save(List<Aluno> listaalunos) {
		getGrupo().setAlunosGrupo(listaalunos);
		try {
			getGenericController().save(getGrupo());
			saveGrupoCampoEstagio();
			listaGrupos = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Aluno salvo com sucesso.", ""));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Aluno.", ""));
		}
	}
	
	private void saveGrupoCampoEstagio() {
		getCampo1().getGrupo().setId(getGrupo().getId());;
		getCampo2().getGrupo().setId(getGrupo().getId());;
		getCampo3().getGrupo().setId(getGrupo().getId());;
		getCampo4().getGrupo().setId(getGrupo().getId());;
		
		try {
			getGenericController().save(campo1);
			getGenericController().save(campo2);
			getGenericController().save(campo3);
			getGenericController().save(campo4);
			grupo = null; campo1 = null; campo2 = null; campo3 = null; campo4 = null;
			genericController = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar GrupoCampoEstagio.", ""));
			e.printStackTrace();
		}
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
	
	public GrupoController getGrupoController() {
		return grupoController == null ? grupoController = new GrupoController() : grupoController;
	}
	
	public Grupo getGrupo() {
		return grupo == null ? grupo = new Grupo() : grupo;
	}
	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public GrupoCampoEstagio getCampo1() {
		return campo1 == null ? campo1 = new GrupoCampoEstagio() : campo1;
	}

	public void setCampo1(GrupoCampoEstagio campo1) {
		this.campo1 = campo1;
	}

	public GrupoCampoEstagio getCampo2() {
		return campo2 == null ? campo2 = new GrupoCampoEstagio() : campo2;
	}

	public void setCampo2(GrupoCampoEstagio campo2) {
		this.campo2 = campo2;
	}

	public GrupoCampoEstagio getCampo3() {
		return campo3 == null ? campo3 = new GrupoCampoEstagio() : campo3;
	}

	public void setCampo3(GrupoCampoEstagio campo3) {
		this.campo3 = campo3;
	}

	public GrupoCampoEstagio getCampo4() {
		return campo4 == null ? campo4 = new GrupoCampoEstagio() : campo4;
	}

	public void setCampo4(GrupoCampoEstagio campo4) {
		this.campo4 = campo4;
	}
}
