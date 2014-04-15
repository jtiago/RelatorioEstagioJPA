package br.com.clogos.estagio.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIBERARRELATORIO")
public class LiberarRelatorio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idliberar")
	private Long id;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fkturma")
	private Aluno turma;
	
	

}
