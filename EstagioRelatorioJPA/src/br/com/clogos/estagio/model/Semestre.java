package br.com.clogos.estagio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.clogos.estagio.jpa.dao.ObjectModel;

@Entity
@Table(name="SEMESTRE")
public class Semestre implements ObjectModel {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idsemestre")
	private Long id;
	
	@Column(name="nomeSemestre", length=10)
	private String nomeSemestre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeSemestre() {
		return nomeSemestre;
	}

	public void setNomeSemestre(String nomeSemestre) {
		this.nomeSemestre = nomeSemestre;
	}
}
