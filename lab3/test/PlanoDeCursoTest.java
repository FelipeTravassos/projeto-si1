import models.Cadeira;
import models.Periodo;
import models.PlanoDeCurso;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Simple (JUnit) tests that can call all parts of a play app. If you are
 * interested in mocking a whole application, see the wiki for more details.
 * 
 */
public class PlanoDeCursoTest {

	@Test
	public void testaListarPrimeiroPeriodo() {
		Periodo periodo = new Periodo(1);
		Assert.assertEquals(6, periodo.getCadeiras().size());
		// testar cadeiras
		// testar creditos
	}

	@Test
	public void testaAdicionarCadeira() {
		PlanoDeCurso plano = new PlanoDeCurso();
		plano.addPeriodo();

		try {
			plano.addCadeira("Programação II", 2);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		Assert.assertEquals(1, plano.getPeriodo(2).getCadeiras().size());
	}

	@Test
	public void testaRemoverCadeira() throws Exception {

		// teste simples
		Cadeira cadeira = new Cadeira("Programação 2", 5);
		Periodo periodo = new Periodo(2);

		periodo.addCadeira(cadeira);
		Assert.assertEquals(1, periodo.getCadeiras().size());

		periodo.removerCadeira(cadeira);
		Assert.assertEquals(0, periodo.getCadeiras().size());

		// teste real
		PlanoDeCurso plano = new PlanoDeCurso();
		plano.addPeriodo(); // periodo 11
		plano.addPeriodo(); // periodo 12
		plano.addCadeira("Cálculo II", 2);
		plano.addCadeira("Probabilidade e Est.", 3);

		Assert.assertEquals(true, plano.isPreRequisito("Cálculo II"));
		Assert.assertEquals(8, plano.getMapCadeirasAlocadas().size());
		plano.removeCadeira("Cálculo II");

		Assert.assertEquals(6, plano.getMapCadeirasAlocadas().size());
	}

	@Test
	public void testaAdicionarPeriodo() {
		PlanoDeCurso plano = new PlanoDeCurso(); //são add 10 por default
		plano.addPeriodo(); // add periodo (11º periodo)
		Assert.assertEquals(11, plano.getPeriodos().size());
	}

	@Test
	public void testaCreditosDoPeriodo() throws Exception {
		PlanoDeCurso plano = new PlanoDeCurso();

		plano.addPeriodo(); // add periodo (2º periodo)
		plano.addCadeira("Programação II", 2);

		Assert.assertEquals(4, plano.getPeriodo(2).getCreditos());
	}

	@Test
	public void testaUltrapassarLimiteDeCreditos() throws Exception {
		PlanoDeCurso plano = new PlanoDeCurso();
		Cadeira cadeira = new Cadeira("Programação 2", 5);
		Cadeira cadeira2 = new Cadeira("Calculo 2", 5);
		Cadeira cadeira3 = new Cadeira("Linear", 5);
		Periodo periodo = new Periodo(2);

		plano.addPeriodo(); // add periodo (2º periodo)
		cadeira.setCreditos(10);
		cadeira2.setCreditos(10);

		periodo.addCadeira(cadeira);
		periodo.addCadeira(cadeira2);

		Assert.assertEquals(20, periodo.getCreditos());

		cadeira3.setCreditos(10);
		try {
			periodo.addCadeira(cadeira3);
		} catch (Exception e) {
			Assert.assertEquals("Limite de Créditos Ultrapassado",
					e.getMessage());
		}
	}

	@Test
	public void testaAddCadeiraSemPreRequisitoConcluido() {
		PlanoDeCurso plano = new PlanoDeCurso();

		plano.addPeriodo(); // add periodo (2º periodo)
		try {
			plano.addCadeira("Estrutura de Dados", 2); // cadeira e periodo
		} catch (Exception e) {
			Assert.assertEquals("Pre Requisito: Programação II não concluido",
					e.getMessage());
		}
	}

	@Test
	public void testaRemoverPeriodo() {
		PlanoDeCurso plano = new PlanoDeCurso();
		plano.addPeriodo();
		try {
			plano.addCadeira("Algebra Linear", 2);
		} catch (Exception e) {
			Assert.fail("Não devia ter falhado");
		}
		Assert.assertEquals(plano.getMapCadeirasAlocadas().size(), 7);
		Assert.assertEquals(plano.getMapCadeirasDisponiveis().size(), 48);
		plano.removePeriodo(2);

		Assert.assertEquals(plano.getMapCadeirasAlocadas().size(), 6);
		Assert.assertEquals(plano.getMapCadeirasDisponiveis().size(), 49);
	}

	@Test
	public void testaDificuldade() {
		PlanoDeCurso plano = new PlanoDeCurso();

		plano.addPeriodo();
		try {
			plano.addCadeira("Algebra Linear", 2); // dificuldade 9
			Assert.assertEquals(9, plano.getPeriodo(2).getDificuldadeTotal());

			plano.addCadeira("Cálculo II", 2); // dificuldade 7
			Assert.assertEquals(16, plano.getPeriodo(2).getDificuldadeTotal());
		} catch (Exception e) {

			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testaAddCadeiraComPreRequisitoEmPeriodoPosterior() {
		PlanoDeCurso plano = new PlanoDeCurso();
		plano.addPeriodo(); // periodo 2
		plano.addPeriodo(); // periodo 3

		try {
			plano.addCadeira("Cálculo II", 3);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		try {
			plano.addCadeira("Probabilidade e Est.", 2);
			Assert.fail("Devia ter falhado");
			// cálculo 2 é seu pre-requisito
		} catch (Exception e) {
			Assert.assertEquals("Pre Requisito: Cálculo II não concluido",
					e.getMessage());
		}
	}
}
