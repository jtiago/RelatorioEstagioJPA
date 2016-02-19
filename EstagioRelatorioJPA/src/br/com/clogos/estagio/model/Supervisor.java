package br.com.clogos.estagio.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.clogos.estagio.enums.CursoEnum;
import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name="SUPERVISOR", schema="uniweb")
public class Supervisor implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idsupervisor")
	private Long id;
	
	@Column(name="nomesupervisor", length=100, nullable=false)
	private String nome;
	
	@Column(name="cpfsupervisor", nullable=false)
	private String cpf;
	
	@Enumerated(EnumType.ORDINAL)
	private CursoEnum curso;
	
	@Column(name="codSituacao", nullable=false)
	private Integer codigoSituacao;
	
	@Column(name="senhasupervisor", nullable=false)
	private String senha;
	
	@OneToMany(mappedBy = "supervisor")
	private List<Relatorio> Relatorios;
	
	@OneToOne(cascade=CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name="fkimagem", referencedColumnName="idimagem")
	private ImagemAssinatura imagem;
	
	@ManyToOne
	@JoinColumn(name= "fkcampoEstagio")
	private CampoEstagio campoEstagio;
	
	@ManyToOne
	@JoinColumn(name = "fkPerfil")
	private Perfil perfil;
	
	@Transient
	private Long idSemestre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public CursoEnum getCurso() {
		return curso;
	}

	public void setCurso(CursoEnum curso) {
		this.curso = curso;
	}

	public ImagemAssinatura getImagem() {
		return imagem;
	}

	public void setImagem(ImagemAssinatura imagem) {
		this.imagem = imagem;
	}

	public Integer getCodigoSituacao() {
		return codigoSituacao;
	}

	public void setCodigoSituacao(Integer codigoSituacao) {
		this.codigoSituacao = codigoSituacao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Relatorio> getRelatorios() {
		return Relatorios;
	}

	public void setRelatorios(List<Relatorio> relatorios) {
		Relatorios = relatorios;
	}

	public CampoEstagio getCampoEstagio() {
		return campoEstagio == null ? campoEstagio = new CampoEstagio() : campoEstagio;
	}

	public void setCampoEstagio(CampoEstagio campoEstagio) {
		this.campoEstagio = campoEstagio;
	}

	public Perfil getPerfil() {
		return perfil == null ? perfil = new Perfil() : perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Long getIdSemestre() {
		return idSemestre;
	}

	public void setIdSemestre(Long idSemestre) {
		this.idSemestre = idSemestre;
	}
}
