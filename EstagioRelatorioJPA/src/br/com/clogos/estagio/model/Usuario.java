package br.com.clogos.estagio.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name="USUARIO")
public class Usuario implements ObjectModel {
	private static final long serialVersionUID = -5281352613901692899L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idusuario")
	private Long id;
	
	@Column(name="cpfusuario", length=14, nullable=false)
	private String cpf;
	
	@Column(name="nomeusuario", length=50)
	private String nome;
	
	@Column(name="senhausuario", length=100)
	private String senha;
	
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="fkperfil")
	private Perfil perfil;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil == null ? perfil = new Perfil() : perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
