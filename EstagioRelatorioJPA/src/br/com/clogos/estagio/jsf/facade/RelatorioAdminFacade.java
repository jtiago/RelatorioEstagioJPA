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
import br.com.clogos.estagio.jasper.RelatorioJRDataSource;
import br.com.clogos.estagio.jpa.controller.RelatorioController;
import br.com.clogos.estagio.jpa.controller.TurmaController;
import br.com.clogos.estagio.model.Relatorio;
import br.com.clogos.estagio.util.Util;

public class RelatorioAdminFacade implements Serializable {
	private static final long serialVersionUID = 8659107450710545395L;
	private Relatorio relatorio;
	private Relatorio relatorioValidar;
	private Relatorio relatorioImprimir;
	private List<Relatorio> listaRelatorios;
	private List<Relatorio> listaRelatoriosFilter;
	private RelatorioController relatorioController;
	private TurmaController turmaController;
	private String status;
	
	public List<Relatorio> getListaRelatorios() {
		return listaRelatorios;
	}
	
	public void pesquisaRelatorio() {
		vertificarStatus();
		getRelatorio().setIdSemestre(Util.getUsuarioSessao().getIdSemestre());
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
	
	private void vertificarStatus() {
		if(getStatus().equals("v")) {
			getRelatorio().setValidado(true);
		} else if(getStatus().equals("a")) {
			getRelatorio().setValidado(false);
		} else if(getStatus().equals("r")) {
			getRelatorio().setRevisao(true);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
