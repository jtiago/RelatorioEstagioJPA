package br.com.clogos.estagio.enums;

public enum ModuloEnum {
	Modulo_I("I Módulo", "FICHA DE RELATÓ“RIO ÚšNICO"), 
	Modulo_II("II Módulo", "FICHA DE RELATÓ“RIO POR CAMPO"), 
	Modulo_III("III Módulo", "FICHA DE RELATÓ“RIO POR CAMPO"), 
	Modulo_II_III("II e III Módulo", "FICHA DE RELATÓ“RIO SEMANAL");
	
	private String label;
	private String ficha;
	
	ModuloEnum(String label, String ficha) {
		this.label=label;
		this.ficha=ficha;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getFicha() {
		return ficha;
	}
	
	public static ModuloEnum getModulo(String str) {
		if(str.equalsIgnoreCase("I Módulo"))  {
			return Modulo_I;
		} else if (str.equalsIgnoreCase("II Módulo")) {
			return Modulo_II;
		} else if(str.equalsIgnoreCase("III Módulo")) {
			return Modulo_III;
		} else if(str.equalsIgnoreCase("II e III Módulo")) {
			return Modulo_II_III;
		} else {
			return null;
		}
	}
}
