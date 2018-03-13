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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.StreamedContent;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.jasper.FichaAvaliacaoJRDataSource;
import br.com.clogos.estagio.jpa.controller.AlunoController;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.util.Util;
import br.com.clogos.estagio.vo.CampoEstagioFichaVO;
import br.com.clogos.estagio.vo.FichaAvaliacaoVO;
import br.com.clogos.estagio.vo.GrupoFichaVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	
	public StreamedContent geraRelatorio(Aluno aluno) {
		StreamedContent streamedContent = null;
		this.alunoDados = aluno;
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Map<String, Object> paramentros = new HashMap<String, Object>();
		pesquisarDadosFicha();
		
		try {
			File fileJasper = new File(context.getRealPath("/relatorio/FichaAvaliacaoAluno.jasper"));
			File fileJasperSub = new File(context.getRealPath("/relatorio/"));
			File fileLogo = new File(context.getRealPath("/images/logoV2.jpg"));
			BufferedImage logo = ImageIO.read(fileLogo);
			
			// Atribuição as parametros do relatório
			paramentros.put("LOGO", logo);
			paramentros.put("TITULO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeCurso()+" "+getFichaAvaliacaoVO().getAlunoFichaVO().getModulo().getLabel().toUpperCase());
			paramentros.put("SUBTITULO", retornaSubTitulo(getFichaAvaliacaoVO().getAlunoFichaVO().getModulo()));
			paramentros.put("NOMEALUNO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeAluno());
			paramentros.put("NOMETURMA", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeTurma());
			paramentros.put("NOMEGRUPO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeGrupo());
			paramentros.put("CARGAHORARIA", getFichaAvaliacaoVO().getAlunoFichaVO().getCargaHoraria());
			//paramentros.put("SITUACAOFINAL", retornaSituacaoFinal(getFichaAvaliacaoVO().getListaGrupoCampo()));
			paramentros.put("SITUACAOFINAL", "H");
			paramentros.put("SUBREPORT_DIR", fileJasperSub.getAbsolutePath());
			paramentros.put("listaCampoEstagio", new JRBeanCollectionDataSource(verificaSeERadiologia(fichaAvaliacaoVO)));
			
			String nomeReportPDF = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+getFichaAvaliacaoVO().getAlunoFichaVO().getNomeAluno().replaceAll(" ", "")+".pdf";
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();   
		    ServletOutputStream servletOutputStream = response.getOutputStream();
		    byte[] bytes = JasperRunManager.runReportToPdf(fileJasper.getAbsolutePath(), paramentros, new FichaAvaliacaoJRDataSource(getFichaAvaliacaoVO().getListaGrupoCampo())); 

		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "filename=\""+nomeReportPDF+"\""); 
		    response.setContentLength(bytes.length);

		    servletOutputStream.write(bytes, 0, bytes.length);
		    servletOutputStream.flush();
		    servletOutputStream.close();
		    FacesContext.getCurrentInstance().renderResponse();
		    FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return streamedContent;
	}
	
	private String retornaSituacaoFinal(List<GrupoFichaVO> lista) {
		for(GrupoFichaVO g : lista) {
			if(g.getRelEnviado().contains("NH")) {
				return "NH";
			}
		}
		return "H";
	}
	
	private String retornaSubTitulo(ModuloEnum modulo) {
		if(modulo.compareTo(ModuloEnum.Modulo_I) == 0) {
			return "";
		} else if (modulo.compareTo(ModuloEnum.Modulo_II) == 0) {
			return "SAÚDE PÚBLICA, GERIATRIA E NEUROPSIQUIATRIA";
		} else if (modulo.compareTo(ModuloEnum.Modulo_III) == 0) {
			return "HOSPITALAR";
		} else {
			return "";
		}
	}
	
	private List<CampoEstagioFichaVO> verificaSeERadiologia(FichaAvaliacaoVO ficha) {
		if(ficha.getAlunoFichaVO().getNomeCurso().contains("RADIOLOGIA")) {
			CampoEstagioFichaVO campo = new CampoEstagioFichaVO();
			campo.setIdCampoEstagio(5L);
			campo.setNomeCampoEstagio("C&F-X Prestadora de serviços Radiológicos LTDA - ME");
			ficha.getListaCampoEstagio().add(campo);
		}
		
		return ficha.getListaCampoEstagio();
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
