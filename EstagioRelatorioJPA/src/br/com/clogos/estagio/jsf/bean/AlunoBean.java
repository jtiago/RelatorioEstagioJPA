package br.com.clogos.estagio.jsf.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
	private Aluno alunoAssociado;
	private Turma turma;
	private boolean mensagem;
	
	private DualListModel<Turma> dualListModel;
	
	public AlunoBean() {
		mensagem = false;
	}
	
	public void processarAlunoAssociado(ActionEvent event) {
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	public DualListModel<Turma> getDualListModel() {
		List<Turma> source = getFacadeTurma().getListaTurma();
		List<Turma> target = new ArrayList<Turma>();
		dualListModel = new DualListModel<Turma>(source, target);
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
		//getFacade().save(getDualListModel().getTarget());
		getFacade().save();
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
		Turma turma = null;
		List<Aluno> listaAlunos = getFacade().getListaAlunos();
		try {
			scanner = new Scanner(event.getFile().getInputstream());
			while(scanner.hasNext()) {
				String str = scanner.nextLine();
				if(!str.contains("nu_matricula")) {
					String[] token = str.split(";");
					turma = facadeTurma.obterTurmaPorNome(token[2]);
					if(turma == null) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Não foi encontrado a Turma cadastrada", ""));
						return;
					} else {
						validarAluno(token, turma, listaAlunos);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
    }
	
	public void validarAluno(String[] token, Turma turma, List<Aluno> lista) {
		for(Aluno item : lista) {
			if(item.getCpf().equals(token[6])) {
				item.getTurmas().add(turma);
				getFacade().setAlunoAltera(item);
				getFacade().update();
				return;
			}
		}
		saveAluno(token, turma);
	}
	
	/*private Turma saveTurma(String[] token) {
		Turma turma = new Turma();
		turma.setNome(token[2]);
		turma.setNomeCurso(token[1]);
		turma.setTurno(token[3]);
		getFacadeTurma().setTurma(turma);
		getFacadeTurma().save();
		return turma;
	}*/
	
	//Matricula - Nome Curso - Nome Turma - Turno - Nome Aluno - Status do Aluno - CPF - Sexo
	private void saveAluno(String[] token, Turma turma) {
		Aluno aluno = new Aluno();
		List<Turma> listaTurma = new ArrayList<Turma>();
		listaTurma.add(turma);
		aluno.setCpf(token[6]);
		aluno.setMatricula(token[0]);
		aluno.setNome(token[4]);
		aluno.setSenha("12345678");
		aluno.setSexo(token[7]);
		aluno.setStatus(token[5]);
		aluno.setPerfil(getPerfil());
		aluno.setTurmas(listaTurma);
		getFacade().setAluno(aluno);
		getFacade().save();
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

	public Aluno getAlunoAssociado() {
		return alunoAssociado == null ? alunoAssociado = new Aluno() : alunoAssociado;
	}

	public void setAlunoAssociado(Aluno alunoAssociado) {
		this.alunoAssociado = alunoAssociado;
	}
}