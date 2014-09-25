package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import br.com.clogos.estagio.jsf.facade.AlunoFacade;
import br.com.clogos.estagio.jsf.facade.UsuarioFacade;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Perfil;
import br.com.clogos.estagio.model.Usuario;

@ManagedBean(name="autenticarBean")
@SessionScoped
public class AutenticarBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private AlunoFacade alunoFacade;
	private UsuarioFacade facadeUsuario; 
	private Aluno aluno;
	private Perfil perfil;
	private String senha;
	private String cpf;
	private Boolean mensagem;
	
	public AutenticarBean() {
		this.mensagem = false;
	}

	public UsuarioFacade getFacadeUsuario() {
		return facadeUsuario == null ? new UsuarioFacade() : facadeUsuario;
	}
	
	public AlunoFacade getAlunoFacade() {
		return alunoFacade == null ? alunoFacade = new AlunoFacade() : alunoFacade;
	}
	
	public void login(ActionEvent event) {
		if(getPerfil().getId() == 1 || getPerfil().getId() == 3) {
			Usuario usuario = new Usuario();
			usuario.setCpf(getAluno().getCpf());
			usuario.setSenha(getAluno().getSenha());
			usuario.getPerfil().setId(getPerfil().getId());
			getFacadeUsuario().login(usuario); 
		} else {
			getAlunoFacade().login(getAluno());
		}
	}
	
	public void mudarSenha(ActionEvent event) {
		if(getPerfil().getId() == 1) {
			getFacadeUsuario().updateSenha(getCpf(), getSenha());
		} else {
			getAlunoFacade().updateSenha(getCpf(), getSenha());
		}
		this.mensagem = true;
	}
	
	public void logout(ActionEvent event) {
		getFacadeUsuario().logout();
	}

	public Aluno getAluno() {
		return aluno == null ? aluno = new Aluno() : aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Perfil getPerfil() {
		return perfil == null ? perfil = new Perfil() : perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getMensagem() {
		return mensagem;
	}

	public void setMensagem(Boolean mensagem) {
		this.mensagem = mensagem;
	}
}
