package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import managers.GerenciadorDeCadeiras;

/**
 * Entidade que representa um período
 */
public class Periodo {

	private Map<String, Cadeira> cadeiras;
	private int numeroDoPeriodo;

	public Periodo(int numeroDoPeriodo) {
		this.numeroDoPeriodo = numeroDoPeriodo;
		cadeiras = new HashMap<String, Cadeira>();
		if (numeroDoPeriodo == PlanoDeCurso.PRIMEIRO_PERIODO) {
			addCadeiraPrimeiroPeriodo();
		}
	}

	public int getNumero() {
		return numeroDoPeriodo;
	}

	private void addCadeiraPrimeiroPeriodo() {
		cadeiras = GerenciadorDeCadeiras.getCadeirasPrimeiro();
	}

	public Collection<Cadeira> getCadeiras() {
		return cadeiras.values();
	}

	public Map<String, Cadeira> getMapCadeiras() {
		return cadeiras;
	}

	public Cadeira getCadeira(String cadeira) {
		return cadeiras.get(cadeira);
	}

	public void addCadeira(Cadeira cadeira) throws Exception {
		cadeiras.put(cadeira.getNome(), cadeira);
	}

	public void removerCadeira(Cadeira cadeira) {
		cadeiras.remove(cadeira.getNome());
	}

	public int getDificuldadeTotal() {
		int difi = 0;
		for (Cadeira c : getCadeiras()) {
			difi += c.getDificuldade();
		}
		return difi;
	}

	/**
	 * Calcula o total de Créditos do Periodo
	 * 
	 * Responsabilidade Atribuída seguindo o padrão Information Expert
	 */
	public int getCreditos() {
		int sum = 0;
		for (Cadeira c : cadeiras.values()) {
			sum += c.getCreditos();
		}
		return sum;
	}

}