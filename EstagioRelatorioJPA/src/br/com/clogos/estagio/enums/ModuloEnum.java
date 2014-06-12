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
	
	public static ModuloEnum getModulo(String str) {
		switch (str) {
		case "I Módulo": return Modulo_I; 
		case "II Módulo": return Modulo_II;
		case "III Módulo 1ª Fase": return Modulo_III_1;
		case "III Módulo 2ª Fase": return Modulo_III_2;
		default: return null;
		}
	}
}
