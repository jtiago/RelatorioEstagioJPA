package br.com.clogos.estagio.jsf.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import br.com.clogos.estagio.jsf.facade.SupervisorFacade;
import br.com.clogos.estagio.model.Supervisor;

@ManagedBean
@ViewScoped
public class SupervisorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private SupervisorFacade facade;
	private UploadedFile uploadedFile;
	private Supervisor supervisor;
	private static final String DIRETORIO = "C:/";
	
	public SupervisorFacade getFacade() {
		return facade == null ? new SupervisorFacade() : facade;
	}
	
	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}
	
	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
	public Supervisor getSupervisor() {
		return supervisor == null ? new Supervisor() : supervisor;
	}
	
	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	public void save(ActionEvent event) {
		try {
			String nomeArquivo = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date())+getSupervisor().getNome();
			File file = new File(DIRETORIO+nomeArquivo+".png");
			IOUtils.copyLarge(getUploadedFile().getInputstream(), new FileOutputStream(file));
			
			System.out.println(file.getName());
		
//			getArquivo().setCaminho(DIRETORIO);
//			getArquivo().setTamanho((int) getUpload().getSize());
//			getArquivo().setNome(file.getName());
//			getFacade().save(getArquivo());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}                    
	}
	
	
}
