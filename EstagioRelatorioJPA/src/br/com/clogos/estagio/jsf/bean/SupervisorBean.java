package br.com.clogos.estagio.jsf.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import br.com.clogos.estagio.jsf.facade.SupervisorFacade;
import br.com.clogos.estagio.model.ImagemAssinatura;

@ManagedBean
public class SupervisorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private SupervisorFacade facade;
	private UploadedFile uploadedFile;
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
	
	public void save(ActionEvent event) {
		try {
			String nomeArquivo = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
			File file = new File(DIRETORIO+nomeArquivo+".png");
			IOUtils.copyLarge(getUploadedFile().getInputstream(), new FileOutputStream(file));
			
			Object obj = event.getComponent().getAttributes().get("idNome"); 
			System.out.println(obj);
			
			ImagemAssinatura assinatura = new ImagemAssinatura();
			assinatura.setNome(nomeArquivo);
			assinatura.setCaminho(DIRETORIO);
			getFacade().save(assinatura);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}                    
	}
	
	
}
