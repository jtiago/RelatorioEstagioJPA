package br.com.clogos.estagio.enums;

public enum ModuloEnum {
	Modulo_I("I Módulo"), 
	Modulo_II("II Módulo"), 
	Modulo_III_1("III Módulo 1ª Fase"), 
	Modulo_III_2("III Módulo 2ª Fase");
	
	private String label;
	
	ModuloEnum(String label) {
		this.label=label;
	}
	
	public String getLabel() {
		return label;
	}
}
