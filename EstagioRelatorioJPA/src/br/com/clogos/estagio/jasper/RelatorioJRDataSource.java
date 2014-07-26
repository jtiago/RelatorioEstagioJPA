package br.com.clogos.estagio.jasper;

import java.util.Iterator;

import br.com.clogos.estagio.model.Relatorio;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class RelatorioJRDataSource implements JRDataSource {
	
	private Iterator<Relatorio> iterator;
	private Relatorio cursor;

	@Override
	public Object getFieldValue(JRField nome) throws JRException {
		Relatorio relatorio = cursor;
		if(nome.getName().equals("NOMEALUNO")) {
			return relatorio.getAluno().getNome();
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
