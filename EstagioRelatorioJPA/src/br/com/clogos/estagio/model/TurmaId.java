package br.com.clogos.estagio.model;

import java.io.Serializable;

public class TurmaId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nomeTurma;
	
	public TurmaId() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nomeTurma == null) ? 0 : nomeTurma.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TurmaId other = (TurmaId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeTurma == null) {
			if (other.nomeTurma != null)
				return false;
		} else if (!nomeTurma.equals(other.nomeTurma))
			return false;
		return true;
	}
}
