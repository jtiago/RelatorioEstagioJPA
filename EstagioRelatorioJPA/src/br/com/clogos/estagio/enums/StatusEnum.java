package br.com.clogos.estagio.enums;

public enum StatusEnum {
	ABERTO(0, "ABERTO"),
	REVISAO(1, "REVISÃO"),
	VALIDADO(2, "VALIDADO");
	
	private Integer codigo;
	private String label;
	
	private StatusEnum(Integer codigo, String label) {
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
