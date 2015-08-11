package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.CloseEvent;
import org.primefaces.model.DualListModel;

import br.com.clogos.estagio.jsf.facade.AlunoFacade;
import br.com.clogos.estagio.jsf.facade.CampoEstagioFacade;
import br.com.clogos.estagio.jsf.facade.GrupoFacade;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.CampoEstagio;
import br.com.clogos.estagio.model.Grupo;

@ManagedBean(name="grupoBean")
@ViewScoped
public class GrupoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private GrupoFacade facade;
	private AlunoFacade facadeAluno;
	private CampoEstagioFacade facadeCampoEstagio;
	private boolean mensagem;
	private Long idTurma = 0L;
	private DualListModel<Aluno> dualListModelAluno;
	private DualListModel<CampoEstagio> dualListModelCampo;
	
	public GrupoFacade getFacade() {
		return facade == null ? facade = new GrupoFacade() : facade;
	}
	
	public AlunoFacade getFacadeAluno() {
		return facadeAluno == null ? facadeAluno = new AlunoFacade() : facadeAluno;
	}
	
	public CampoEstagioFacade getFacadeCampoEstagio() {
		return facadeCampoEstagio == null ? facadeCampoEstagio = new CampoEstagioFacade() : facadeCampoEstagio;
	}
	
	public void carregarTurma(ValueChangeEvent event) {
		this.idTurma = Long.valueOf(event.getNewValue().toString());
		 System.out.println("New value: " + event.getNewValue());
	}
	
	public DualListModel<Aluno> getDualListModelAluno() {
		List<Aluno> target = new ArrayList<Aluno>();
		List<Aluno> source = new ArrayList<Aluno>();
		if(idTurma != null && idTurma != 0) {
			source = getFacadeAluno().getListaAlunoPorTurma(getIdTurma());
		}
		this.dualListModelAluno = new DualListModel<Aluno>(source, target);
		return dualListModelAluno;
	}

	public void setDualListModelAluno(DualListModel<Aluno> dualListModelAluno) {
		this.dualListModelAluno.getSource().removeAll(dualListModelAluno.getTarget());
		this.dualListModelAluno = dualListModelAluno;
	}
	
	public DualListModel<CampoEstagio> getDualListModelCampo() {
		List<CampoEstagio> target = new ArrayList<CampoEstagio>();
		List<CampoEstagio> source = getFacadeCampoEstagio().getListaCampoEstagio();
		dualListModelCampo = new DualListModel<CampoEstagio>(source, target);
		return dualListModelCampo;
	}

	public void setDualListModelCampo(DualListModel<CampoEstagio> dualListModelCampo) {
		this.dualListModelCampo = dualListModelCampo;
	}

	public void limpar(CloseEvent event) {
		this.idTurma = null;
		getFacade().setGrupo(new Grupo());
	}
	
	public void save(ActionEvent event) {
//		getFacade().save();
		mensagem = true;
	}
	
	public void remove(ActionEvent event) {
//		getFacade().remover();
		mensagem = true;
	}
	
	public void update(ActionEvent event) {
//		getFacade().update();
		mensagem = true;
	}

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
	
	public Long getIdTurma() {
		return idTurma;
	}
	
	public void setIdTurma(Long idTurma) {
		this.idTurma = idTurma;
	}
}
