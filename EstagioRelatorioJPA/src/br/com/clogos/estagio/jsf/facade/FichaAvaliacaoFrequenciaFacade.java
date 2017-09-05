package br.com.clogos.estagio.jsf.facade;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import br.com.clogos.estagio.jasper.FichaAvaliacaoJRDataSource;
import br.com.clogos.estagio.jpa.controller.AlunoController;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.util.Util;
import br.com.clogos.estagio.vo.FichaAvaliacaoVO;
import br.com.clogos.estagio.vo.GrupoFichaVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class FichaAvaliacaoFrequenciaFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FichaAvaliacaoVO fichaAvaliacaoVO;
	private RelatorioController relatorioController;
	private AlunoController alunoController;
	private FichaAvaliacaoVO fichaAvaliacaoImprimir; 
	
	private List<Aluno> listaAlunoPorTurma;
	private	Aluno alunoDados;
	private Long idTurma;
	
	public FichaAvaliacaoVO getFichaAvaliacaoVO() {
		if(fichaAvaliacaoVO == null) {
			fichaAvaliacaoVO = new FichaAvaliacaoVO();
		}
		
		if(alunoDados != null && alunoDados.getCpf() != null) {
			fichaAvaliacaoVO = getRelatorioController().findFichaAvaliacao(getAlunoDados().getId(), getAlunoDados().getTurmaT().getId(), Util.getIdSemestre());
			alunoDados = null;
		}
		return fichaAvaliacaoVO;
	}
	
	public void pesquisarAlunoPorTurma() {
		listaAlunoPorTurma = getAlunoController().findPorTurmaGrupo(getIdTurma(), Util.getIdSemestre());
	}
	
	public void pesquisarDadosFicha() {
		fichaAvaliacaoVO = getRelatorioController().findFichaAvaliacao(getAlunoDados().getId(), getAlunoDados().getTurmaT().getId(), Util.getIdSemestre());
	}
	
	public void geraRelatorio() {
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Map<String, Object> paramentros = new HashMap<String, Object>();
		pesquisarDadosFicha();
		
		try {
			File fileJasper = new File(context.getRealPath("/relatorio/FichaAvaliacao.jasper"));
			File fileLogo = new File(context.getRealPath("/images/logo.gif"));
			BufferedImage logo = ImageIO.read(fileLogo);
			paramentros.put("LOGO", logo);
			paramentros.put("TITULO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeCurso()+" "+getFichaAvaliacaoVO().getAlunoFichaVO().getModulo().getLabel().toUpperCase());
			paramentros.put("NOMEALUNO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeAluno());
			paramentros.put("NOMETURMA", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeTurma());
			paramentros.put("NOMEGRUPO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeGrupo());
			paramentros.put("SITUACAOFINAL", retornaSituacaoFinal(getFichaAvaliacaoVO().getListaGrupoCampo()));
			
		
			JasperPrint jasperPrint = JasperFillManager.fillReport(fileJasper.getAbsolutePath(), paramentros, new FichaAvaliacaoJRDataSource(getFichaAvaliacaoVO().getListaGrupoCampo()));
			//JasperViewer.viewReport(jasperPrint,false);
			String dataReport = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+getFichaAvaliacaoVO().getAlunoFichaVO().getNomeAluno().replaceAll(" ", "")+".pdf";
			String diretorio = System.getProperty ("java.io.tmpdir");
			JasperExportManager.exportReportToPdfFile(jasperPrint, diretorio+dataReport);
			Runtime.getRuntime().exec("cmd /c start "+diretorio+dataReport);
			
			File fileTemp = new File(dataReport);
			fileTemp.deleteOnExit();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String retornaSituacaoFinal(List<GrupoFichaVO> lista) {
		for(GrupoFichaVO g : lista) {
			if(g.getRelEnviado().contains("NH")) {
				return "NH";
			}
		}
		return "H";
	}
	
	public List<Aluno> getListaAlunoPorTurma() {
		return listaAlunoPorTurma;
	}
	
	public RelatorioController getRelatorioController() {
		return relatorioController == null ? relatorioController = new RelatorioController() : relatorioController;
	}
	
	public AlunoController getAlunoController() {
		return alunoController == null ? alunoController = new AlunoController() : alunoController;
	}

	public Aluno getAlunoDados() {
		return alunoDados == null ? alunoDados = new Aluno() : alunoDados;
	}

	public void setAlunoDados(Aluno alunoDados) {
		this.alunoDados = alunoDados;
	}
	
	public Long getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(Long idTurma) {
		this.idTurma = idTurma;
	}

	public FichaAvaliacaoVO getFichaAvaliacaoImprimir() {
		return fichaAvaliacaoImprimir == null ? fichaAvaliacaoImprimir = new FichaAvaliacaoVO() : fichaAvaliacaoImprimir;
	}

	public void setFichaAvaliacaoImprimir(FichaAvaliacaoVO fichaAvaliacaoImprimir) {
		this.fichaAvaliacaoImprimir = fichaAvaliacaoImprimir;
	}
}
