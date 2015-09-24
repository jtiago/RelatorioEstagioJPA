package br.com.clogos.estagio.enums;

public enum ModuloEnum {
	Modulo_I("I M�dulo", "FICHA DE RELATӓRIO ښNICO"), 
	Modulo_II("II M�dulo", "FICHA DE RELATӓRIO POR CAMPO"), 
	Modulo_III("III M�dulo", "FICHA DE RELATӓRIO POR CAMPO"), 
	Modulo_II_III("II e III M�dulo", "FICHA DE RELATӓRIO SEMANAL");
	
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
		if(str.equalsIgnoreCase("I M�dulo"))  {
			return Modulo_I;
		} else if (str.equalsIgnoreCase("II M�dulo")) {
			return Modulo_II;
		} else if(str.equalsIgnoreCase("III M�dulo")) {
			return Modulo_III;
		} else if(str.equalsIgnoreCase("II e III M�dulo")) {
			return Modulo_II_III;
		} else {
			return null;
		}
	}
}
