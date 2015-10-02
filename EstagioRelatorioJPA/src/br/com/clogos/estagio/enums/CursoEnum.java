package br.com.clogos.estagio.enums;

public enum CursoEnum {
	ENFERMAGME(0, "ENFERMAGEM"),
	RADIOLOGIA(1, "RADILOGIA"),
	ANALISE(3, "ANÁLISES CLÍNICAS");
	
	private Integer codigo;
	private String label;
	
	private CursoEnum(Integer codigo, String label) {
		this.codigo = codigo;
		this.label = label;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getLabel() {
		return label;
	}
}
