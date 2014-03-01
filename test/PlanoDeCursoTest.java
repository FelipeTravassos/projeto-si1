import java.util.Collection;

import managers.GerenciadorDeCadeiras;
import models.Cadeira;
import models.PlanoDeCurso;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Simple (JUnit) tests that can call all parts of a play app. If you are
 * interested in mocking a whole application, see the wiki for more details.
 * 
 */
public class PlanoDeCursoTest {
	private PlanoDeCurso plano;
	
	private int PRIMEIRO_PERIODO;
	private int SEGUNDO_PERIODO;
	private int TERCEIRO_PERIODO;
	private int QUARTO_PERIODO;
	private int QUINTO_PERIODO;
	private int SETIMO_PERIODO;

	
	@Before
	public void start(){
		plano = new PlanoDeCurso();
		
		PRIMEIRO_PERIODO = 1;
		SEGUNDO_PERIODO = 2;
		TERCEIRO_PERIODO = 3;
		QUARTO_PERIODO = 4;
		QUINTO_PERIODO = 5;
		SETIMO_PERIODO = 7;
	}
	
	@Test
	public void deveVerificarSePeriodosEstaAlocadosCorretamente(){
		Collection<Cadeira> listaDisciplinaPrimeiroPeriodo = plano.getPeriodo(PRIMEIRO_PERIODO).getCadeiras();
		String toStringPrimeiroPeriodo = "[Nome = Int. à Computacação, ID = 0, Periodo = 1, Creditos = 4, "
										+ "Nome = Programação I, ID = 0, Periodo = 1, Creditos = 4, "
										+ "Nome = Lab. de Programação I, ID = 0, Periodo = 1, Creditos = 4, "
										+ "Nome = Leitura e Prod. de Textos, ID = 0, Periodo = 1, Creditos = 4, "
										+ "Nome = Algebra Vetorial, ID = 0, Periodo = 1, Creditos = 4, "
										+ "Nome = Cáĺculo I , ID = 0, Periodo = 1, Creditos = 4]";
		Assert.assertEquals(toStringPrimeiroPeriodo, listaDisciplinaPrimeiroPeriodo.toString());
		
		
		Collection<Cadeira> listaDisciplinaSegundoPeriodo = plano.getPeriodo(SEGUNDO_PERIODO).getCadeiras();
		String toStringSegundoPeriodo = "[Nome = Teoria dos Grafos, ID = 0, Periodo = 2, Creditos = 2, "
										+ "Nome = Programação II, ID = 0, Periodo = 2, Creditos = 4, "
										+ "Nome = Cálculo II, ID = 0, Periodo = 2, Creditos = 4, "
										+ "Nome = Matemática Discreta, ID = 0, Periodo = 2, Creditos = 4, "
										+ "Nome = Fund. de Física Clássica, ID = 0, Periodo = 2, Creditos = 4, "
										+ "Nome = Lab. de Programação II, ID = 0, Periodo = 2, Creditos = 4, "
										+ "Nome = Metodologia Científica, ID = 0, Periodo = 2, Creditos = 4]";
		Assert.assertEquals(toStringSegundoPeriodo, listaDisciplinaSegundoPeriodo.toString());
		
		
		Collection<Cadeira> listaDisciplinaTerceiroPeriodo = plano.getPeriodo(TERCEIRO_PERIODO).getCadeiras();
		String toStringTerceiroPeriodo = "[Nome = Algebra Linear, ID = 0, Periodo = 3, Creditos = 4, "
										+ "Nome = Teoria da Computação, ID = 0, Periodo = 3, Creditos = 4, "
										+ "Nome = Lab. de Estrutura de Dados, ID = 0, Periodo = 3, Creditos = 4, "
										+ "Nome = Estrutura de Dados, ID = 0, Periodo = 3, Creditos = 4, "
										+ "Nome = Probabilidade e Est., ID = 0, Periodo = 3, Creditos = 4, "
										+ "Nome = Gerência da Informação, ID = 0, Periodo = 3, Creditos = 4, "
										+ "Nome = Fund. de Física Moderna, ID = 0, Periodo = 3, Creditos = 4]";
		Assert.assertEquals(toStringTerceiroPeriodo, listaDisciplinaTerceiroPeriodo.toString());

		
		Collection<Cadeira> listaDisciplinaQuartoPeriodo = plano.getPeriodo(QUARTO_PERIODO).getCadeiras();
		String toStringQuartoPeriodo = "[Nome = Paradigmas de Linguagens de Programação, ID = 0, Periodo = 4, Creditos = 2, "
										+ "Nome = Lógica Matemática, ID = 0, Periodo = 4, Creditos = 4, "
										+ "Nome = Métodos Estatísticos, ID = 0, Periodo = 4, Creditos = 4, "
										+ "Nome = Sistemas de Informação I, ID = 0, Periodo = 4, Creditos = 4, "
										+ "Nome = Lab. de Org. e Arquitetura de Computadores, ID = 0, Periodo = 4, Creditos = 4, "
										+ "Nome = Org. e Arquitetura de Computadores I, ID = 0, Periodo = 4, Creditos = 4, "
										+ "Nome = Engenharia de Software I, ID = 0, Periodo = 4, Creditos = 4]";
		Assert.assertEquals(toStringQuartoPeriodo, listaDisciplinaQuartoPeriodo.toString());

		
		
		
		Collection<Cadeira> listaDisciplinaQuintoPeriodo = plano.getPeriodo(QUINTO_PERIODO).getCadeiras();
		String toStringQuintoPeriodo = "[Nome = Laboratório de Engenharia de Software, ID = 0, Periodo = 5, Creditos = 4, "
										+ "Nome = Análise e Técnicas de Algoritmos, ID = 0, Periodo = 5, Creditos = 4, "
										+ "Nome = Banco de Dados I, ID = 0, Periodo = 5, Creditos = 4, "
										+ "Nome = Redes de Computadores, ID = 0, Periodo = 5, Creditos = 4, "
										+ "Nome = Compiladores, ID = 0, Periodo = 5, Creditos = 4, "
										+ "Nome = Sistemas de Informação II, ID = 0, Periodo = 5, Creditos = 4, "
										+ "Nome = Informática e Sociedade, ID = 0, Periodo = 5, Creditos = 2]";
		Assert.assertEquals(toStringQuintoPeriodo, listaDisciplinaQuintoPeriodo.toString());
	}
	

	@Test
	public void deveAtualizarQuantidadeCreditos() {
		Assert.assertEquals(12, plano.getPeriodo(SETIMO_PERIODO).getCreditos());
		try {
			plano.addCadeira("Optativa 1", SETIMO_PERIODO);
			plano.addCadeira("Optativa 2", SETIMO_PERIODO);
			plano.addCadeira("Optativa 3", SETIMO_PERIODO);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(24, plano.getPeriodo(SETIMO_PERIODO).getCreditos());
		
		try {
			plano.removeCadeira("Optativa 1");
			plano.removeCadeira("Optativa 2");
			plano.removeCadeira("Optativa 3");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(12, plano.getPeriodo(SETIMO_PERIODO).getCreditos());
	}

	@Test
	public void deveCriarNovoPeriodoAdicionarCadeirasVerificar() {
		int DECIMO_PRIMEIRO_PERIODO = 11;
		plano.addPeriodo(); // add periodo (11º periodo)
		
		Assert.assertEquals(DECIMO_PRIMEIRO_PERIODO, plano.getPeriodos().size());
		Assert.assertEquals(0, plano.getPeriodo(DECIMO_PRIMEIRO_PERIODO).getCreditos());
		
		try {
			plano.addCadeira("Optativa 1", DECIMO_PRIMEIRO_PERIODO);
			plano.addCadeira("Optativa 2", DECIMO_PRIMEIRO_PERIODO);
			plano.addCadeira("Optativa 3", DECIMO_PRIMEIRO_PERIODO);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		Assert.assertEquals(12, plano.getPeriodo(DECIMO_PRIMEIRO_PERIODO).getCreditos());
	}

	@Test
	public void verificaDificuldadeDoPeriodo() {
		int somaDificuldades = 0;
		
		//Verifica Dificuldade do Primeiro Periodo
		for(Cadeira c : GerenciadorDeCadeiras.getCadeirasPrimeiro().values()){
				somaDificuldades += c.getDificuldade();			
		}
		Assert.assertEquals(somaDificuldades, plano.getPeriodo(PRIMEIRO_PERIODO).getDificuldadeTotal());

		somaDificuldades = 0;
		
		//Verifica Dificuldade do Segundo Periodo
		for(Cadeira c : GerenciadorDeCadeiras.getMapaDeCadeiras().values()){
			if(c.getPeriodo() == SEGUNDO_PERIODO){
				somaDificuldades += c.getDificuldade();
			}
		}
		Assert.assertEquals(somaDificuldades, plano.getPeriodo(SEGUNDO_PERIODO).getDificuldadeTotal());

		somaDificuldades = 0; 
		
		//Verifica Dificuldade do Terceiro Periodo
		for(Cadeira c : GerenciadorDeCadeiras.getMapaDeCadeiras().values()){
			if(c.getPeriodo() == TERCEIRO_PERIODO){
				somaDificuldades += c.getDificuldade();
			}
		}
		Assert.assertEquals(somaDificuldades, plano.getPeriodo(TERCEIRO_PERIODO).getDificuldadeTotal());
		
		
		somaDificuldades = 0; 
		
		//Verifica Dificuldade do Quarto Periodo
		for(Cadeira c : GerenciadorDeCadeiras.getMapaDeCadeiras().values()){
			if(c.getPeriodo() == QUARTO_PERIODO){
				somaDificuldades += c.getDificuldade();
			}
		}
		Assert.assertEquals(somaDificuldades, plano.getPeriodo(QUARTO_PERIODO).getDificuldadeTotal());
	}
}
