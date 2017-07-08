package br.com.clogos.estagio.jasper;

import java.util.Iterator;
import java.util.List;

import br.com.clogos.estagio.vo.GrupoFichaVO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class FichaAvaliacaoJRDataSource implements JRDataSource {
	
	private Iterator<GrupoFichaVO> iterator;
	private GrupoFichaVO cursor;
	
	public FichaAvaliacaoJRDataSource(List<GrupoFichaVO> relatorios) {
		super();
		iterator = relatorios.iterator();
	}

	@Override
	public Object getFieldValue(JRField nome) throws JRException {
		GrupoFichaVO grupoFichaVO = cursor;
		if(nome.getName().equals("CAMPO")) {
			return grupoFichaVO.getSiglaCampoEstagio();
		}
		if(nome.getName().equals("INICIO")) {
			return grupoFichaVO.getDataInicial();
		}
		if(nome.getName().equals("TERMINIO")) {
			return grupoFichaVO.getDataFinal();
		}
		if(nome.getName().equals("AVALIACAO")) {
			return grupoFichaVO.getRelEnviado();
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
