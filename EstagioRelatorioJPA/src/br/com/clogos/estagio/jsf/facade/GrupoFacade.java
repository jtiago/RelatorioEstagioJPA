package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.GrupoCampoEstagioController;
import br.com.clogos.estagio.jpa.controller.GrupoController;
import br.com.clogos.estagio.jpa.controller.TurmaController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Grupo;
import br.com.clogos.estagio.model.GrupoCampoEstagio;
import br.com.clogos.estagio.model.Turma;
import br.com.clogos.estagio.util.Util;

public class GrupoFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Grupo grupo;
	private List<Grupo> listaGrupos;
	private List<Aluno> listaAlunoGrupo;
	private List<Turma> listaTurmas;
	private List<GrupoCampoEstagio> listaGrupoCampoEstagio;
	private GenericController genericController;
	private GrupoController grupoController;
	private TurmaController turmaController;
	private GrupoCampoEstagioController grupoCampoEstagioController;
	
	private GrupoCampoEstagio campo1;
	private GrupoCampoEstagio campo2;
	private GrupoCampoEstagio campo3;
	private GrupoCampoEstagio campo4;
	private GrupoCampoEstagio campo5;
	private GrupoCampoEstagio campo6;
	private GrupoCampoEstagio campo7;
	
	public List<Grupo> getListaGrupos() {
		if(listaGrupos== null) {
			listaGrupos = getGrupoController().findAll(Util.getUsuarioSessao().getSemestre().getId());
		}
		return listaGrupos;
	}
	
	public List<Aluno> getListaAlunoGrupo() {
		listaAlunoGrupo = getGrupoController().findGrupoAluno(Util.getIdSemestre(), 
				getGrupo().getId()).getAlunosGrupo();
		return listaAlunoGrupo;
		
	}
	
	public List<Turma> getListaTurmas() {
		if(listaTurmas == null) {
			listaTurmas = getTurmaController().obterTurmaGrupo(Util.getIdSemestre());
		}
		return listaTurmas;
	}
	
	public List<GrupoCampoEstagio> getListaGrupoCampoEstagio() {
		if(grupo != null && listaGrupoCampoEstagio == null && grupo.getNomeGrupo() != null) {
			listaGrupoCampoEstagio = getGrupoCampoEstagioController().findPorGrupo(grupo);
		}
		return listaGrupoCampoEstagio == null ? new LinkedList<GrupoCampoEstagio>() : listaGrupoCampoEstagio;
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
		try {
			if(campo1.getDataInicial() != null) {
				getCampo1().getGrupo().setId(getGrupo().getId());
				getGenericController().save(campo1);
			}
			if(campo2.getDataInicial() != null) {
				getCampo2().getGrupo().setId(getGrupo().getId());
				getGenericController().save(campo2);
			}
			if(campo3.getDataInicial() != null) {
				getCampo3().getGrupo().setId(getGrupo().getId());
				getGenericController().save(campo3);
			}
			if(campo4.getDataInicial() != null) {
				getCampo4().getGrupo().setId(getGrupo().getId());
				getGenericController().save(campo4);
			}
			if(campo5.getDataInicial() != null) {
				getCampo5().getGrupo().setId(getGrupo().getId());
				getGenericController().save(campo5);
			}
			if(campo6.getDataInicial() != null) {
				getCampo6().getGrupo().setId(getGrupo().getId());
				getGenericController().save(campo6);
			}
			if(campo7.getDataInicial() != null) {
				getCampo7().getGrupo().setId(getGrupo().getId());
				getGenericController().save(campo7);
			}
			
			grupo = null; campo1 = null; campo2 = null; campo3 = null; campo4 = null; campo5 = null; campo6 = null; campo7 = null;
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
	
	public GrupoCampoEstagioController getGrupoCampoEstagioController() {
		return grupoCampoEstagioController == null ? grupoCampoEstagioController = new GrupoCampoEstagioController() : grupoCampoEstagioController;
	}
	
	public TurmaController getTurmaController() {
		return turmaController == null ? turmaController = new TurmaController() : turmaController;
	}
	
	public Grupo getGrupo() {
		if(grupo == null) {
			grupo = new Grupo();
		}
		return grupo;
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

	public GrupoCampoEstagio getCampo5() {
		return campo5 == null ? campo5 = new GrupoCampoEstagio() : campo5;
	}

	public void setCampo5(GrupoCampoEstagio campo5) {
		this.campo5 = campo5;
	}

	public GrupoCampoEstagio getCampo6() {
		return campo6 == null ? campo6 = new GrupoCampoEstagio() : campo6;
	}

	public void setCampo6(GrupoCampoEstagio campo6) {
		this.campo6 = campo6;
	}
	
	public GrupoCampoEstagio getCampo7() {
		return campo7 == null ? campo7 = new GrupoCampoEstagio() : campo7;
	}

	public void setCampo7(GrupoCampoEstagio campo7) {
		this.campo7 = campo7;
	}

	public void setListaGrupoCampoEstagio(
			List<GrupoCampoEstagio> listaGrupoCampoEstagio) {
		this.listaGrupoCampoEstagio = listaGrupoCampoEstagio;
	}
}
