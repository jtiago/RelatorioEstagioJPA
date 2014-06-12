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
		if(str.equalsIgnoreCase("I Módulo"))  {
			return Modulo_I;
		} else if (str.equalsIgnoreCase("II Módulo")) {
			return Modulo_II;
		} else if(str.equalsIgnoreCase("III Módulo 1ª Fase")) {
			return Modulo_III_1;
		} else if(str.equalsIgnoreCase("III Módulo 2ª Fase")) {
			return Modulo_III_2;
		} else {
			return null;
		}
	}
}
