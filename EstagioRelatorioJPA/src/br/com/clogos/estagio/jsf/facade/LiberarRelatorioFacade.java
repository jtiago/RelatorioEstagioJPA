package br.com.clogos.estagio.jsf.facade;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.LiberarRelatorioController;
import br.com.clogos.estagio.jpa.controller.TurmaController;
import br.com.clogos.estagio.model.LiberarRelatorio;
import br.com.clogos.estagio.model.Turma;

public class LiberarRelatorioFacade implements Serializable {
	private static final long serialVersionUID = -1656621873140148824L;
	private LiberarRelatorio liberarRelatorio;
	private GenericController genericController;
	private LiberarRelatorioController liberarRelatorioController;
	private TurmaController turmaController;
	private List<LiberarRelatorio> listaLiberados;
	private List<LiberarRelatorio> listaLiberadosFilter;
	
	public List<LiberarRelatorio> getListaLiberados() {
		if(listaLiberados == null) {
			listaLiberados = getLiberarRelatorioController().findoAll();
		}
		return listaLiberados;
	}
	
	public void save() {
		try {
			if(!validaCastroModulo()) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Não pode ser liberado este modulo para a turma selecionada.", ""));
				return;
			}
		
			if(!getLiberarRelatorioController().existeModuloLiberado(getLiberarRelatorio())) {
				if(!getLiberarRelatorioController().existeModuloAberto(getLiberarRelatorio())) {
					getGenericController().save(getLiberarRelatorio());
					liberarRelatorio=null; genericController = null; listaLiberados = null;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Relatorio Liberado para esta Turma com sucesso.", ""));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN, " Existe Relatório em aberto para esta turma.", ""));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, " Este Modulo já foi liberado para esta turma.", ""));
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao salvar Liberação de Relatório.", ""));
		}
	}
	
	public void fecharModuloLiberado() {
		getLiberarRelatorio().setAberto(false);
		if(getGenericController().update(getLiberarRelatorio())) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Fechamento do Modulo feito com sucesso.", ""));
			liberarRelatorio = null; listaLiberados = null;
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao fechar modulo.", ""));
		}
	}
	
	public ModuloEnum[] getModulo() {
		return ModuloEnum.values();
	}
	
	private Boolean validaCastroModulo() {
		Turma turma = getTurmaController().obterTurma(getLiberarRelatorio().getTurmaLiberarRelatorio().getId());
		if((turma.getNome().contains("RAD") || turma.getNome().contains("LAB")) &&
				(getLiberarRelatorio().getModulo().equals(ModuloEnum.Modulo_II) 
			     || getLiberarRelatorio().getModulo().equals(ModuloEnum.Modulo_III))) {
			return false;
		} else {
			return true;
		}
	}
	
	public LiberarRelatorio getLiberarRelatorio() {
		return liberarRelatorio == null ? liberarRelatorio = new LiberarRelatorio() : liberarRelatorio;
	}
	public void setLiberarRelatorio(LiberarRelatorio liberarRelatorio) {
		this.liberarRelatorio = liberarRelatorio;
	}
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
	public LiberarRelatorioController getLiberarRelatorioController() {
		return liberarRelatorioController == null ? liberarRelatorioController = new LiberarRelatorioController() : liberarRelatorioController;
	}
	public TurmaController getTurmaController() {
		return turmaController == null ? turmaController = new TurmaController() : turmaController;
	}

	public List<LiberarRelatorio> getListaLiberadosFilter() {
		return listaLiberadosFilter;
	}

	public void setListaLiberadosFilter(List<LiberarRelatorio> listaLiberadosFilter) {
		this.listaLiberadosFilter = listaLiberadosFilter;
	}
	
}
