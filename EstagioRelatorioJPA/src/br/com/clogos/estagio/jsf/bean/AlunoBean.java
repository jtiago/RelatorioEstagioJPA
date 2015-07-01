package br.com.clogos.estagio.jsf.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

import br.com.clogos.estagio.jsf.facade.AlunoFacade;
import br.com.clogos.estagio.jsf.facade.TurmaFacade;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Perfil;
import br.com.clogos.estagio.model.Turma;

@ManagedBean(name="alunoBean")
@ViewScoped
public class AlunoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String SENHA_PADRA = "12345678";
	private AlunoFacade facade;
	private TurmaFacade facadeTurma;
	private Perfil perfil;
	private Aluno aluno;
	private Turma turma;
	private boolean mensagem;
	
	private DualListModel<Turma> dualListModel;
	
	public AlunoBean() {
		mensagem = false;
		
		List<Turma> source = getFacadeTurma().getListaTurma();
		List<Turma> target = new ArrayList<Turma>();
		dualListModel = new DualListModel<Turma>(source, target);
	}
	
	public DualListModel<Turma> getDualListModel() {
		return dualListModel;
	}
	
	public void setDualListModel(DualListModel<Turma> dualListModel) {
		this.dualListModel.getSource().removeAll(dualListModel.getTarget());
		this.dualListModel = dualListModel;
	}
	
	public AlunoFacade getFacade() {
		return facade == null ? facade = new AlunoFacade() : facade;
	}
	
	public TurmaFacade getFacadeTurma() {
		return facadeTurma == null ? facadeTurma = new TurmaFacade() : facadeTurma;
	}

	public void save(ActionEvent event) {
		getFacade().save(getDualListModel().getTarget());
		mensagem = true;
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
		mensagem = true;
	}
	
	public void update(ActionEvent event) {
		getFacade().update();
		mensagem = true;
	}
	
	public void fileUpload(FileUploadEvent event) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(event.getFile().getInputstream());
			while(scanner.hasNext()) {
				String str = scanner.nextLine();
				if(!str.contains("nu_matricula")) {
					String[] token = str.split(";");
					saveTurma(token);
					saveAluno(token);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
    }
	
	private Turma saveTurma(String[] token) {
		Turma turma = new Turma();
		turma.setNome(token[2]);
		turma.setNomeCurso(token[1]);
		turma.setTurno(token[3]);
		getFacadeTurma().setTurma(turma);
		getFacadeTurma().save();
		return turma;
	}
	
	//Matricula - Nome Curso - Nome Turma - Turno - Nome Aluno - Status do Aluno - CPF - Sexo
	private void saveAluno(String[] token) {
		Aluno aluno = new Aluno();
		aluno.setCpf(token[6]);
		aluno.setMatricula(token[0]);
		aluno.setNome(token[4]);
		aluno.setSenha("12345678");
		aluno.setSexo(token[7]);
		aluno.setStatus(token[5]);
		//aluno.setNomeTurma(token[2]);
		aluno.setPerfil(getPerfil());
		getFacade().setAluno(aluno);
		//getFacade().save();
	}
	
	public void resetSenha(ActionEvent event) {
		getFacade().updateSenha(getAluno().getCpf(), SENHA_PADRA);
		mensagem = true;
	}

	public boolean isMensagem() {
		return mensagem;
	}

	public void setMensagem(boolean mensagem) {
		this.mensagem = mensagem;
	}
	
	public Perfil getPerfil() {
		if(perfil == null) {
			perfil = new Perfil();
			perfil.setId(2L);
		}
		return perfil;
	}

	public Aluno getAluno() {
		return aluno == null ? aluno = new Aluno() : aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
