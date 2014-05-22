package br.com.clogos.estagio.jsf.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import br.com.clogos.estagio.jsf.facade.SupervisorFacade;
import br.com.clogos.estagio.model.ImagemAssinatura;

@ManagedBean
@ViewScoped
public class SupervisorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private SupervisorFacade facade;
	private UploadedFile file;
	private String nome;
	private static final String DIRETORIO = "C:/";
	
	public SupervisorFacade getFacade() {
		return facade == null ? new SupervisorFacade() : facade;
	}
	
	public void save() {
		try {
			String nomeArquivo = new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new Date());
			File file = new File(DIRETORIO+nomeArquivo+".png");
			IOUtils.copyLarge(getFile().getInputstream(), new FileOutputStream(file));
			
			ImagemAssinatura assinatura = new ImagemAssinatura();
			assinatura.setNome(nomeArquivo);
			assinatura.setCaminho(DIRETORIO);
			getFacade().save(assinatura, getNome());
			file = null;

		} catch (Exception e) {
			e.printStackTrace();
		}                    
	}
	
	public void remove(ActionEvent event) {
		getFacade().remover();
	}
	
	public UploadedFile getFile() {
		return file;
	}
	public void setFile(UploadedFile file) {
		this.file = file;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
