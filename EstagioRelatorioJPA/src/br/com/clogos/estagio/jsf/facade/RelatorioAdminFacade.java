package br.com.clogos.estagio.jsf.facade;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import br.com.clogos.estagio.enums.StatusEnum;
import br.com.clogos.estagio.jasper.RelatorioJRDataSource;
import br.com.clogos.estagio.jpa.controller.GenericController;
import br.com.clogos.estagio.jpa.controller.GrupoCampoEstagioController;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.jpa.controller.TurmaController;
import br.com.clogos.estagio.model.GrupoCampoEstagio;
import br.com.clogos.estagio.model.Relatorio;
import br.com.clogos.estagio.util.Util;

public class RelatorioAdminFacade implements Serializable {
	private static final long serialVersionUID = 8659107450710545395L;
	private Relatorio relatorio;
	private Relatorio relatorioValidar;
	private Relatorio relatorioImprimir;
	private List<Relatorio> listaRelatorios;
	private List<Relatorio> listaRelatoriosFilter;
	private List<GrupoCampoEstagio> listaGrupoCampoEstagio;
			
	private GrupoCampoEstagioController grupoCampoEstagioController;
	private RelatorioController relatorioController;
	private TurmaController turmaController;
	private GenericController genericController;
	
	public List<Relatorio> getListaRelatorios() {
		return listaRelatorios;
	}
	
	public List<GrupoCampoEstagio> getListaGrupoCampoEstagio() {
		if(relatorioValidar != null) {
			listaGrupoCampoEstagio = getGrupoCampoEstagioController().find(getRelatorioValidar().getGrupoCampoEstagio());
		}
		return listaGrupoCampoEstagio;
	}
	
	public StatusEnum[] getStatus() {
		return StatusEnum.getStatusRelAdmin();
	}
	
	public void pesquisaRelatorio() {
		getRelatorio().setIdSemestre(Util.getIdSemestre());
		listaRelatorios = getRelatorioController().findRelatoriosAdmin(getRelatorio());
	}
	
	public void validarRelatorio() {
		if(getRelatorioValidar() != null) {
			if(getRelatorioController().updateValidarRelatorio(getRelatorioValidar().getId(), getRelatorioValidar().getObservacao())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Relatório validado com sucesso.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Problemas ao validar relatório", ""));
			}
		}
		listaRelatorios=null;relatorioController=null;relatorioValidar=null;
	}
	
	public void remover() {
		try {
			if(getRelatorioValidar() != null) {
				getGenericController().remove(getRelatorioValidar());
				relatorioValidar = null; genericController = null; listaRelatorios = null; listaRelatoriosFilter = null;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Relatório removido com sucesso.", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Problemas ao remover Relatório.", ""));
		}
	}
	
	public void revisarRelatorio() {
		if(getRelatorioValidar() != null) {
			if(getRelatorioController().updateRevisaoRelatorio(getRelatorioValidar().getId(), getRelatorioValidar().getObservacao())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Relatório enviado para Revisão com sucesso.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Problemas ao enviar relatório para revisão", ""));
			}
		}
		relatorioValidar=null; listaRelatorios=null; relatorioController=null; 
	}
	
	public void alterarDataInicioTerminioRelatorio() {
		if(getRelatorioValidar() != null) {
			if(getRelatorioController().alterarDataInicioTerminioRelatorio(getRelatorioValidar())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Alteração das datas efetuado com sucesso.", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Problemas ao alterar ass datas do relatório", ""));
			}
		}
	}
	
	public void geraRelatorio() {
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		Map<String, Object> paramentros = new HashMap<String, Object>();
		List<Relatorio> lista = new LinkedList<Relatorio>();
		lista.add(getRelatorioImprimir());
		
		try {
			File fileJasper = new File(context.getRealPath("/relatorio/RelatorioEstagio.jasper"));
			File fileLogo = new File(context.getRealPath("/images/logo.gif"));
			BufferedImage logo = ImageIO.read(fileLogo);
			paramentros.put("LOGO", logo);
			//paramentros.put("NOMECURSO", getTurmaController().obterCurso(getRelatorioImprimir().getAluno().getNomeTurma()).getNomeCurso());
			paramentros.put("TITULO", "ESTÁGIO SUPERVISIONADO "+getRelatorioImprimir().getModulo().getLabel().toUpperCase());
			paramentros.put("IMGSUPERVISOR", getRelatorioImprimir().getSupervisor(). getImagem().getCaminho()+
					getRelatorioImprimir().getSupervisor().getImagem().getNome());
		
			JasperPrint jasperPrint = JasperFillManager.fillReport(fileJasper.getAbsolutePath(), paramentros, new RelatorioJRDataSource(lista));
			//JasperViewer.viewReport(jasperPrint,false);
			String dataReport = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+getRelatorioImprimir().getAluno().getNome().replaceAll(" ", "")+".pdf";
			JasperExportManager.exportReportToPdfFile(jasperPrint, "c:/IMGESTAGIO/"+dataReport);
			Runtime.getRuntime().exec("cmd /c start c:/IMGESTAGIO/"+dataReport);
			
			File fileTemp = new File(dataReport);
			fileTemp.deleteOnExit();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Relatorio getRelatorio() {
		return relatorio == null ? relatorio = new Relatorio() : relatorio;
	}
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
	public Relatorio getRelatorioValidar() {
		return relatorioValidar == null ? relatorioValidar = new Relatorio() : relatorioValidar;
	}

	public void setRelatorioValidar(Relatorio relatorioValidar) {
		this.relatorioValidar = relatorioValidar;
	}

	public Relatorio getRelatorioImprimir() {
		return relatorioImprimir == null ? relatorioImprimir = new Relatorio() : relatorioImprimir;
	}

	public void setRelatorioImprimir(Relatorio relatorioImprimir) {
		this.relatorioImprimir = relatorioImprimir;
	}

	public List<Relatorio> getListaRelatoriosFilter() {
		return listaRelatoriosFilter;
	}

	public void setListaRelatoriosFilter(List<Relatorio> listaRelatoriosFilter) {
		this.listaRelatoriosFilter = listaRelatoriosFilter;
	}

	public RelatorioController getRelatorioController() {
		return relatorioController == null ? relatorioController = new RelatorioController() : relatorioController;
	}
	public TurmaController getTurmaController() {
		return turmaController == null ? turmaController = new TurmaController() : turmaController ;
	}
	
	public GrupoCampoEstagioController getGrupoCampoEstagioController() {
		return grupoCampoEstagioController == null ? grupoCampoEstagioController = new GrupoCampoEstagioController() : grupoCampoEstagioController;
	}
	
	public GenericController getGenericController() {
		return genericController == null ? genericController = new GenericController() : genericController;
	}
}