package br.com.clogos.estagio.filter;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.Perfil;
import br.com.clogos.estagio.model.Supervisor;
import br.com.clogos.estagio.model.Usuario;


public class SecurityFilter implements Filter {

    public SecurityFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		String url = servletRequest.getRequestURL().toString();
		Boolean permissaoURL = false;
		Object usuarioLogado = servletRequest.getSession().getAttribute("usuarioLogado");
		
		if(url.endsWith("jsf")){
			permissaoURL = validaURL(servletRequest.getRequestURL().toString(), usuarioLogado, servletRequest, servletResponse);
		}
		
		if(usuarioLogado == null || !permissaoURL) {
			servletResponse.sendRedirect(servletRequest.getContextPath()+"/");
			servletRequest.getSession().invalidate();
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private static Boolean validaURL(String url, Object obj, HttpServletRequest request, HttpServletResponse response) {
		try {
			String page = url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));
			
			if(obj instanceof Usuario && !page.equals("home")) {
				Usuario usuario = (Usuario) obj;
				Perfil perfil = usuario.getPerfil();
				Field field = perfil.getClass().getDeclaredField(page);
				field.setAccessible(true);

				if(!Boolean.valueOf(field.get(perfil).toString())) 
					return false;
				
			} else if (obj instanceof Aluno && !page.equals("home")) {
					Aluno aluno = (Aluno) obj;
					Perfil perfilAluno = aluno.getPerfil();
					Field fieldAluno = perfilAluno.getClass().getDeclaredField(page);
					fieldAluno.setAccessible(true);

					if(!Boolean.valueOf(fieldAluno.get(perfilAluno).toString())) 
						return false;
				} else if (obj instanceof Supervisor && !page.equals("home")) {
					Supervisor supervisor = (Supervisor) obj;
					Perfil perfilSupervisor = supervisor.getPerfil();
					Field fieldSupervisor = perfilSupervisor.getClass().getDeclaredField(page);
					fieldSupervisor.setAccessible(true);

					if(!Boolean.valueOf(fieldSupervisor.get(perfilSupervisor).toString())) 
						return false;
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
