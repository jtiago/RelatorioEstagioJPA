package br.com.clogos.estagio.jsf.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.UploadedFile;

import br.com.clogos.estagio.jsf.facade.SupervisorFacade;
import br.com.clogos.estagio.model.ImagemAssinatura;

@ManagedBean(name="supervisorBean")
@ViewScoped
public class SupervisorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private SupervisorFacade facade;
	private UploadedFile file;
	private static final String DIRETORIO = "C:/IMGESTAGIO/";
	
	public SupervisorFacade getFacade() {
		if(facade == null) {
			facade = new SupervisorFacade();
		}
		return facade;
	}
	
	public void save() {
			
		String nomeArquivo = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
		// File file = new File(DIRETORIO+nomeArquivo+".jpg");
		// IOUtils.copyLarge(getFile().getInputstream(), new FileOutputStream(file));
		
		ImagemAssinatura assinatura = new ImagemAssinatura();
		assinatura.setNome(nomeArquivo);
		assinatura.setCaminho(DIRETORIO);
		getFacade().save(assinatura);

		                   
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
	}
	
	public void update(ActionEvent event) {
		getFacade().update();
	}
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
}
