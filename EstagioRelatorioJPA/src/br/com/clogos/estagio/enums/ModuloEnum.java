package br.com.clogos.estagio.enums;

public enum ModuloEnum {
	Modulo_I("I M骴ulo", "FICHA DE RELAT覴IO 脷NICO"), 
	Modulo_II("II M骴ulo", "FICHA DE RELAT覴IO POR CAMPO"), 
	Modulo_III("III M骴ulo", "FICHA DE RELAT覴IO POR CAMPO"), 
	Modulo_II_III("II e III M骴ulo", "FICHA DE RELAT覴IO SEMANAL");
	
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
		if(str.equalsIgnoreCase("I M贸dulo"))  {
			return Modulo_I;
		} else if (str.equalsIgnoreCase("II M贸dulo")) {
			return Modulo_II;
		} else if(str.equalsIgnoreCase("III M贸dulo")) {
			return Modulo_III;
		} else if(str.equalsIgnoreCase("II e III M贸dulo")) {
			return Modulo_II_III;
		} else {
			return null;
		}
	}
	
	public static ModuloEnum getModuloTipo(String str) {
		if(str.equalsIgnoreCase("Modulo_I"))  {
			return Modulo_I;
		} else if (str.equalsIgnoreCase("Modulo_II")) {
			return Modulo_II;
		} else if(str.equalsIgnoreCase("Modulo_III")) {
			return Modulo_III;
		} else if(str.equalsIgnoreCase("Modulo_II_III")) {
			return Modulo_II_III;
		} else {
			return null;
		}
	}
}
