package br.com.clogos.estagio.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(schema="uniweb")
public class GrupoCampoEstagio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fkgrupo")
	private Grupo grupo;
	
	@ManyToOne
	@JoinColumn(name = "fkcampoEstagio")
	private CampoEstagio campoEstagio;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicial;
	
	@Temporal(TemporalType.DATE)
	private Date dataFinal;
	
	@Transient
	private String dataInicialFormatada;
	@Transient
	private String dataFinalFormatada;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataInicialFormatada(String dataInicialFormatada) {
		this.dataInicialFormatada = dataInicialFormatada;
	}

	public void setDataFinalFormatada(String dataFinalFormatada) {
		this.dataFinalFormatada = dataFinalFormatada;
	}

	public Grupo getGrupo() {
		return grupo == null ? grupo = new Grupo() : grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public CampoEstagio getCampoEstagio() {
		return campoEstagio == null ? campoEstagio = new CampoEstagio() : campoEstagio;
	}

	public void setCampoEstagio(CampoEstagio campoEstagio) {
		this.campoEstagio = campoEstagio;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getDataInicialFormatada() {
		if(dataInicial != null) {
			dataInicialFormatada = new SimpleDateFormat("dd/MM").format(dataInicial);
		}
		return dataInicialFormatada;
	}
	public String getDataFinalFormatada() {
		if(dataFinal != null) {
			dataFinalFormatada = new SimpleDateFormat("dd/MM").format(dataFinal);
		}
		return dataFinalFormatada;
	}
}
