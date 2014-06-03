package br.com.clogos.estagio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name="PERFIL")
public class Perfil implements ObjectModel {
	private static final long serialVersionUID = -1232891701475637425L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idperfil")
	private Long id;
	@Column(name="nomeperfil", length=25)
	private String nome;

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
}
