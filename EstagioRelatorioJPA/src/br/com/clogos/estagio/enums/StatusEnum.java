package br.com.clogos.estagio.enums;

public enum StatusEnum {
	ABERTO(0, "ABERTO"),
	REVISAO(1, "REVISÃO"),
	VALIDADO(2, "VALIDADO"),
	NAOENVIADO(3, "NÃO ENVIADO"),
	TRANCOU(4, "TRANCOU"),
	DESISTENTE(5, "DESISTENTE"),
	CURSANDO(6, "CURSANDO");
	
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
	
	public static String getLabelStatus(String id) {
		if(id.equals("0"))  {
			return ABERTO.label;
		} else if (id.equals("1")) {
			return REVISAO.label;
		} else if(id.equals("2")) {
			return VALIDADO.label;
		} else {
			return NAOENVIADO.label;
		}
	}
	
	public static StatusEnum[] getStatusRelAdmin() {
		return new StatusEnum[]{StatusEnum.ABERTO,StatusEnum.VALIDADO,StatusEnum.REVISAO};
		
	}
}
