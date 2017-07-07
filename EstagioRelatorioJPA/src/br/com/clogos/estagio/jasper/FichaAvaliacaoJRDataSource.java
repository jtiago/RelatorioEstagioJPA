package br.com.clogos.estagio.jasper;

import java.util.Iterator;
import java.util.List;

import br.com.clogos.estagio.vo.FichaAvaliacaoVO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class FichaAvaliacaoJRDataSource implements JRDataSource {
	
	private Iterator<FichaAvaliacaoVO> iterator;
	private FichaAvaliacaoVO cursor;
	
	public FichaAvaliacaoJRDataSource(List<FichaAvaliacaoVO> relatorios) {
		super();
		iterator = relatorios.iterator();
	}

	@Override
	public Object getFieldValue(JRField nome) throws JRException {
		FichaAvaliacaoVO fichaAvaliacaoVO = cursor;
		if(nome.getName().equals("NOMEALUNO")) {
			return fichaAvaliacaoVO.getAlunoFichaVO().getNomeAluno();
		}
		if(nome.getName().equals("NOMETURMA")) {
			return fichaAvaliacaoVO.getAlunoFichaVO().getNomeTurma();
		}
		if(nome.getName().equals("NOMEGRUPO")) {
			return fichaAvaliacaoVO.getAlunoFichaVO().getNomeGrupo();
		}
		if(nome.getName().equals("LISTACAMPO")) {
			return fichaAvaliacaoVO.getListaGrupoCampo();
		} 
//		if(nome.getName().equals("DATAINICIO")) {
//			return relatorio.getGrupoCampoEstagio().getDataInicial();
//		}
//		if(nome.getName().equals("DATATERMINO")) {
//			return relatorio.getGrupoCampoEstagio().getDataFinal();
//		}
//		if(nome.getName().equals("TEXTO")) {
//			return relatorio.getTexto();
//		}
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
