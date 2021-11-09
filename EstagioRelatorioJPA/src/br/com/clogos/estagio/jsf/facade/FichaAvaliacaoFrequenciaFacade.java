package br.com.clogos.estagio.jsf.facade;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import br.com.clogos.estagio.vo.AlunoFichaVO;
import br.com.clogos.estagio.vo.CampoEstagioFichaVO;
import br.com.clogos.estagio.vo.FichaAvaliacaoVO;
import br.com.clogos.estagio.vo.GrupoFichaVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FichaAvaliacaoFrequenciaFacade implements Serializable {

	private static final String FINAL_DE_SEMANA = " - FDS";
	private static final String RADIOLOGIA = "RADIOLOGIA";
	private static final String ENFERMAGEM = "ENFERMAGEM";
	
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
			File fileJasper = new File(context.getRealPath(recuperarArquivoJasper(this.fichaAvaliacaoVO)));
			File fileJasperSub = new File(context.getRealPath("/relatorio/"));
			File fileLogo = new File(context.getRealPath("/images/logonova2021.jpeg"));
			BufferedImage logo = ImageIO.read(fileLogo);
			
						// Atribuição as parametros do relatório
			paramentros.put("LOGO", logo);
			paramentros.put("TITULO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeCurso());
			// Para a ficha de Radiologia foi retirado por pedido da Tatiane
			paramentros.put("SUBTITULO", getFichaAvaliacaoVO().getAlunoFichaVO().getModulo().getLabel().toUpperCase()+retornaSubTitulo(getFichaAvaliacaoVO().getAlunoFichaVO().getModulo()));
			paramentros.put("NOMEALUNO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeAluno());
			paramentros.put("NOMETURMA", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeTurma());
			paramentros.put("NOMEGRUPO", getFichaAvaliacaoVO().getAlunoFichaVO().getNomeGrupo());
			paramentros.put("CARGAHORARIA", getFichaAvaliacaoVO().getAlunoFichaVO().getCargaHoraria());
			paramentros.put("SITUACAOFINAL", "");
			paramentros.put("SUBREPORT_DIR", fileJasperSub.getAbsolutePath());
			// Verificar se as data do estágio foi no final de semana para alterar a descrição da sigla do campo
			this.verificarDataFinalSemana(this.fichaAvaliacaoVO.getListaGrupoCampo(), this.fichaAvaliacaoVO.getAlunoFichaVO());
			paramentros.put("listaCampoEstagio", new JRBeanCollectionDataSource(verificaSeERadiologiaAssinaturaRelatorio(fichaAvaliacaoVO)));
			
			String nomeReportPDF = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+getFichaAvaliacaoVO().getAlunoFichaVO().getNomeAluno().replaceAll(" ", "")+".pdf";
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();   
		    ServletOutputStream servletOutputStream = response.getOutputStream();
		    byte[] bytes = JasperRunManager.runReportToPdf(fileJasper.getAbsolutePath(), paramentros, new FichaAvaliacaoJRDataSource(
		    		verificarListaGrupoCampoEstagioSeRadilogia(fichaAvaliacaoVO))); 

		    response.setContentType("application/pdf");
		    response.setHeader("Content-disposition", "filename=\""+nomeReportPDF+"\""); 
		    response.setContentLength(bytes.length);

		    servletOutputStream.write(bytes, 0, bytes.length);
		    servletOutputStream.flush();
		    servletOutputStream.close();
		    FacesContext.getCurrentInstance().renderResponse();
		    FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
		return streamedContent;
	}
	
	private String retornaSubTitulo(ModuloEnum modulo) {
		if(modulo.compareTo(ModuloEnum.Modulo_I) == 0) {
			return "";
		} else if (modulo.compareTo(ModuloEnum.Modulo_II) == 0) {
			return " - SAÚDE PÚBLICA, GERIATRIA E NEUROPSIQUIATRIA";
		} else if (modulo.compareTo(ModuloEnum.Modulo_III) == 0) {
			return " - HOSPITALAR";
		} else {
			return "";
		}
	}
	
	/**
	 * Verificar se as datas finais é final de semana para mudar a Sigla no relatório. 
	 * Definido pela Lucilene de deixar somente para Enfermagem.
	 * Os dois layout do Jasper já está ajustado para o tamanho correto. 
	 */
	private void verificarDataFinalSemana(List<GrupoFichaVO> listaCampo, AlunoFichaVO alunoFicha) {
		if (alunoFicha.getNomeCurso().contains(ENFERMAGEM)) {
			for (GrupoFichaVO campo : listaCampo) {
				Calendar calendarInicial = Calendar.getInstance();
				calendarInicial.setTime(campo.getDataInicial());
				
				Calendar calendarFinal = Calendar.getInstance();
				calendarFinal.setTime(campo.getDataFinal());
				
				if ((calendarInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendarInicial.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) &&
						(calendarFinal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendarFinal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
					campo.setSiglaCampoEstagio(campo.getSiglaCampoEstagio()+FINAL_DE_SEMANA);
				}
			}
		}
	}
	
	private List<CampoEstagioFichaVO> verificaSeERadiologiaAssinaturaRelatorio(FichaAvaliacaoVO ficha) {
		if(ficha.getAlunoFichaVO().getNomeCurso().contains(RADIOLOGIA)) {
			CampoEstagioFichaVO campo = new CampoEstagioFichaVO();
			campo.setIdCampoEstagio(5L);
			campo.setNomeCampoEstagio("RN MANUTENCOES E SERVICOS - ME");
			ficha.getListaCampoEstagio().add(campo);
		}
		
		return ficha.getListaCampoEstagio();
	}
	
	private String recuperarArquivoJasper(FichaAvaliacaoVO ficha) {
		if(ficha.getAlunoFichaVO().getNomeCurso().contains(RADIOLOGIA)) {
			return "/relatorio/FichaAvaliacaoAlunoRadiologia.jasper";
		} else {
			return "/relatorio/FichaAvaliacaoAluno.jasper";
		}
	}
	
	/**
	 * Para Radilogia os campos iguais deve apresentar em um só linha no relatório. 
	 * As datas inicial e final será capiturado a menor no inicial e maior para final. 
	 * @param ficha
	 * @return
	 */
	private List<GrupoFichaVO> verificarListaGrupoCampoEstagioSeRadilogia(FichaAvaliacaoVO ficha) {
		List<GrupoFichaVO> lista = new ArrayList<>();
		GrupoFichaVO grupo = new GrupoFichaVO();
		
		if(ficha.getAlunoFichaVO().getNomeCurso().contains(RADIOLOGIA) && !ficha.getListaGrupoCampo().isEmpty()) {
			grupo.setSiglaCampoEstagio(ficha.getListaGrupoCampo().get(0).getSiglaCampoEstagio());
			grupo.setRelEnviado(ficha.getListaGrupoCampo().get(0).getRelEnviado());
			
			Date dataInicialMenor = null;
			for (GrupoFichaVO item : ficha.getListaGrupoCampo()) {
				if (dataInicialMenor == null || item.getDataInicial().compareTo(dataInicialMenor) < 0) {
					dataInicialMenor = item.getDataInicial();
					grupo.setDataInicial(dataInicialMenor);
				}
			}
				
			Date dataFinalMaior = null;
			for (GrupoFichaVO item : ficha.getListaGrupoCampo()) {
				if (dataFinalMaior == null || item.getDataFinal().compareTo(dataFinalMaior) > 0) {
					dataFinalMaior = item.getDataFinal();
				}
				grupo.setDataFinal(dataFinalMaior);
			}
			
			lista.add(grupo);
			return lista;
		} else {
			return ficha.getListaGrupoCampo();
		}
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
