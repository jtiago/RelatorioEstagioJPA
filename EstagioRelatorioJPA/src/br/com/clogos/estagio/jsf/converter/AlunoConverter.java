package br.com.clogos.estagio.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.clogos.estagio.jpa.controller.AlunoController;
import br.com.clogos.estagio.model.Aluno;

@FacesConverter("AlunoConverter")
public class AlunoConverter implements Converter {

	private AlunoController alunoController = new AlunoController();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if(value != null && value.trim().length() > 0 && !value.equalsIgnoreCase("Selecione Aluno")) {
			return alunoController.find(Long.valueOf(value));
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		 if(object != null) {
	            return String.valueOf(((Aluno) object).getId());
	        }
	        else {
	            return null;
	        }
	}
}
