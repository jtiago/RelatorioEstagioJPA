package br.com.clogos.estagio.enums;

public enum ModuloEnum {
	Modulo_I("I Módulo", "FICHA DE RELATÓRIO ÚNICO", "105 Horas"), 
	Modulo_II("II Módulo", "FICHA DE RELATÓRIO POR CAMPO", "240 Horas"), 
	Modulo_III("III Módulo", "FICHA DE RELATÓRIO POR CAMPO", "250 Horas"),
	Modulo_IV("IV Módulo", "FICHA DE RELATÓRIO POR CAMPO", "250 Horas"),
	Modulo_II_III("II e III Módulo", "FICHA DE RELATÓRIO MENSAL", "535 Horas");
	
	private String label;
	private String ficha;
	private String cargaHoraria;
	
	ModuloEnum(String label, String ficha, String cargaHoraria) {
		this.label=label;
		this.ficha=ficha;
		this.cargaHoraria=cargaHoraria;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getFicha() {
		return ficha;
	}
	
	public String getCargaHoraria() {
		return cargaHoraria;
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
		} else if(str.equalsIgnoreCase("IV Módulo")) {
			return Modulo_IV;
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
		} else if(str.equalsIgnoreCase("Modulo_IV")) {
			return Modulo_IV;
		} else {
			return null;
		}
	}
}
