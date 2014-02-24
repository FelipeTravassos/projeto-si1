package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.NotSupportedException;

import managers.GerenciadorDeCadeiras;

/**
 * Entidade que representa o Plano de Curso do sistema.
 */
public class PlanoDeCurso {

	// TODO PADRÃO DE PROJETO: ALTA COESÃO - so haverá informações coerentes com
	// a classe
	private List<Periodo> periodos;
	public Map<String, Cadeira> mapaDeCadeiras;
	public static final int PRIMEIRO_PERIODO = 1;
	public static final int MAXIMO_CREDITOS = 28;

	public PlanoDeCurso() {
		// TODO Responsabilidade Atribuita seguindo o padrão Creator
		// O plano de curso ficou responsável por criar os períodos.
		this.periodos = new ArrayList<Periodo>();
		this.periodos.add(new Periodo(PRIMEIRO_PERIODO));
		this.mapaDeCadeiras = GerenciadorDeCadeiras.getMapaDeCadeiras();
		for (int i = 2; i <= 10; i++){
			periodos.add(new Periodo(i));
		}
	}

	/**
	 * Adiciona um periodo à lista de períodos, de acordo com o tamanho da
	 * lista.
	 * 
	 * Seguindo o padrão creator.
	 */
	public void addPeriodo() {
		this.periodos.add(new Periodo(this.periodos.size() + 1));
	}

	/**
	 * Retorna o período passado como argumento.
	 * 
	 * @param numPeriodo
	 *            número relativo ao periodo 1,2,3...
	 */
	public Periodo getPeriodo(int numPeriodo) {
		return this.periodos.get(numPeriodo - 1);
	}

	public List<Periodo> getPeriodos() {
		return this.periodos;
	}

	/**
	 * Retorna o Map de cadeiras já alocadas no plano de curso.
	 */
	public Map<String, Cadeira> getMapCadeirasAlocadas() {
		Map<String, Cadeira> alocadas = new HashMap<String, Cadeira>();
		for (Periodo periodo : periodos) {
			alocadas.putAll(periodo.getMapCadeiras());
		}
		return alocadas;
	}

	/**
	 * Retorna o Map de cadeiras ainda não alocadas no plano de curso.
	 */
	public Map<String, Cadeira> getMapCadeirasDisponiveis() {
		Map<String, Cadeira> disponiveis = new HashMap<String, Cadeira>();
		Map<String, Cadeira> alocadas = getMapCadeirasAlocadas();
		for (String nomeCadeira : mapaDeCadeiras.keySet()) {
			if (!alocadas.containsKey(nomeCadeira)) {
				disponiveis.put(nomeCadeira, mapaDeCadeiras.get(nomeCadeira));
			}
		}
		return disponiveis;
	}
	
	/**
	 * Retorna a lista de cadeira disponíveis para alocação ordenadas em ordem
	 * alfabética.
	 */
	public List<Cadeira> getCadeiraDispniveisOrdenadas(){
		List<Cadeira> cadeirasOrdenadas = new ArrayList<Cadeira>();
		cadeirasOrdenadas.addAll(getMapCadeirasDisponiveis().values());
		Collections.sort(cadeirasOrdenadas);
		return cadeirasOrdenadas;
	}

	/**
	 * Adiciona uma {@code cadeira} ao {@code periodo}
	 * 
	 * @throws Exception
	 */
	public void addCadeira(String cadeiraNome, int periodo) throws Exception {
		// TODO PADRÃO DE PROJETO: CONTROLLER - para manter o baixo acoplamento
		// essa classe vai ser a responsável por adicionar um cadeira ao periodo
		Cadeira cadeira = mapaDeCadeiras.get(cadeiraNome);
		if (periodo == PRIMEIRO_PERIODO) {
			throw new IllegalArgumentException(
					"você não pode adicionar cadeiras ao 1º periodo!");
		} else if (getPeriodo(periodo).getCreditos() + cadeira.getCreditos() > MAXIMO_CREDITOS) {
			throw new NotSupportedException("limite de créditos ultrapassado!");
		}
		for (Cadeira cadeiraDoPeriodo : getPeriodo(periodo).getCadeiras()) {
			if (cadeira.isPreRequisito(cadeiraDoPeriodo)) {
				throw new NotSupportedException(
						"Você não pode adicionar essa cadeira junto com seu(s) pré-requisitos");
			}
		}
		verificaPreRequisitos(cadeira, periodo);
		periodos.get(periodo - 1).addCadeira(cadeira);
	}

	/**
	 * Verifica se os pre-requisitos de uma certa cadeira já foram concluídos.
	 */
	private void verificaPreRequisitos(Cadeira cadeira, int periodo)
			throws NotSupportedException {
		Map<String, Cadeira> alocadas = getMapCadeirasAlocadas();
		for (Cadeira cadeiraPeriodo : cadeira.getPreRequisitos()) {
			if (!alocadas.containsKey(cadeiraPeriodo.getNome())) {
				throw new NotSupportedException("Pre Requisito: "
						+ cadeiraPeriodo.getNome() + " não concluido");
			}
		}
		// verifica se a cadeira tem algum pre-requisito em um periodo posterior
		// ao que está sendo adicionado
		for (int i = periodo; i < periodos.size(); i++) {
			for (Cadeira c : periodos.get(i).getCadeiras()) {
				if (cadeira.getPreRequisitos().contains(c)) {
					throw new NotSupportedException("Pre Requisito: "
							+ c.getNome() + " não concluido");
				}
			}
		}
	}

	/**
	 * Remove o período e todos os posteriores
	 */
	public void removePeriodo(int periodo) {
		this.periodos = periodos.subList(0, periodo - 1);
	}

	public void removeCadeira(String cadeira) throws Exception {
		// TODO PADRÃO DE PROJETO: CONTROLLER - para manter o baixo acoplamento
		// essa classe vai ser a responsável por remover uma cadeira ao periodo
		if (getMapCadeirasAlocadas().get(cadeira) == null) {
			throw new Exception("Essa Cadeira não está alocada!");
		} else if (GerenciadorDeCadeiras.getCadeirasPrimeiro().get(cadeira) != null) {
			throw new Exception("Você não pode remover cadeiras do 1º período!");
		}
		Cadeira removida = getMapCadeirasAlocadas().get(cadeira);
		// procura pela cadeira entre os periodos.
		for (Periodo periodo : periodos) {
			// remove a cadeira
			if (periodo.getMapCadeiras().get(cadeira) != null) {
				periodo.removerCadeira(removida);
			}
			// verifica as cadeiras que tem a cadeira a ser removida como
			// pre-requisito e remove
			for (Cadeira c : periodo.getCadeiras()) {
				if (c.getPreRequisitos().contains(removida)) {
					removeCadeira(c.getNome());
				}
			}
		}
	}

	/**
	 * Retorna true caso a {@code cadeira} seja pre-requisito de alguma outra
	 * nos {@code periodos}.
	 */
	public boolean isPreRequisito(String cad) {
		Cadeira cadeira = mapaDeCadeiras.get(cad);
		// procura pela cadeira entre os periodos.
		for (Periodo periodo : periodos) {
			// verifica as cadeiras que tem a cadeira a ser removida como
			// pre-requisito
			for (Cadeira cadeiraDoPeriodo : periodo.getCadeiras()) {
				if (cadeiraDoPeriodo.getPreRequisitos().contains(cadeira)) {
					return true;
				}
			}
		}
		return false;
	}

}