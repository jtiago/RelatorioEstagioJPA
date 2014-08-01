package br.com.clogos.estagio.jasper;

import java.util.Iterator;
import java.util.List;

import br.com.clogos.estagio.model.Relatorio;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class RelatorioJRDataSource implements JRDataSource {
	
	private Iterator<Relatorio> iterator;
	private Relatorio cursor;
	
	public RelatorioJRDataSource(List<Relatorio> relatorios) {
		super();
		iterator = relatorios.iterator();
	}

	@Override
	public Object getFieldValue(JRField nome) throws JRException {
		Relatorio relatorio = cursor;
		if(nome.getName().equals("NOMEALUNO")) {
			return relatorio.getAluno().getNome();
		}
		if(nome.getName().equals("NOMETURMA")) {
			return relatorio.getAluno().getNomeTurma();
		}
		if(nome.getName().equals("CAMPO")) {
			return relatorio.getCampoEstagio().getNome();
		}
		if(nome.getName().equals("SUPERVISOR")) {
			return relatorio.getCampoEstagio().getSupervisor().getNome();
		} 
		if(nome.getName().equals("DATAINICIO")) {
			return relatorio.getDataInicio();
		}
		if(nome.getName().equals("DATATERMINO")) {
			return relatorio.getDataTerminio();
		}
		return null;
	}

	@Override
	public boolean next() throws JRException {
		boolean retorno = iterator.hasNext();
		if(retorno) {
			cursor=iterator.next();
		}
		return retorno;
	}

}
