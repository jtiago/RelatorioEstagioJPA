package br.com.clogos.estagio.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.clogos.estagio.enums.ModuloEnum;
import br.com.clogos.estagio.model.Aluno;
import br.com.clogos.estagio.model.LiberarRelatorio;

public class Main {
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPA");
	
	public static void main(String[] args) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		/*entityManager.getTransaction().begin();
		
		Aluno aluno = new Aluno();
		aluno.setCpf("12345678978");
		aluno.setEmail("teste2teste.com");
		aluno.setMatricula("12345678");
		aluno.setNome("tesdte");
		aluno.setNomeCurso("teste");
		aluno.setTurma("ASD");
		entityManager.persist(aluno);		
		
		LiberarRelatorio liberarRelatorio = new LiberarRelatorio();
		liberarRelatorio.setModulo(ModuloEnum.II_III);
		liberarRelatorio.setTurma(aluno);
		entityManager.persist(liberarRelatorio);
		
		entityManager.getTransaction().commit();
		entityManager.close()*/;
		
	}

}
