package models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import managers.GerenciadorDeCadeiras;
import play.db.ebean.Model;

/**
 * Entidade que representa um período
 */
@Entity
public class Periodo extends Model{
	private static final long serialVersionUID = 1L;
	public static Finder<Long,Periodo> find = new Finder<Long,Periodo>(
			Long.class, Periodo.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@ManyToMany(cascade=CascadeType.ALL) //Cascade vai salvando todas suas dependencias
	@JoinTable(name = "periodo_cadeira", joinColumns = {@JoinColumn (name = "fk_periodo")}, inverseJoinColumns = {@JoinColumn(name = "fk_cadeira")})
	private Map<String, Cadeira> cadeiras;
	
	@Column(name="Numero_do_Periodo")
	private int numeroDoPeriodo;
	
	/**
	 * Construtor do Período
	 * @param numeroDoPeriodo
	 */
	public Periodo(int numeroDoPeriodo) {
		this.numeroDoPeriodo = numeroDoPeriodo;
		cadeiras = new HashMap<String, Cadeira>();
		if (numeroDoPeriodo == PlanoDeCurso.PRIMEIRO_PERIODO) {
			addCadeiraPrimeiroPeriodo();
		}
	}

	/**
	 * 
	 * @return o numero do periodo
	 */
	public int getNumero() {
		return numeroDoPeriodo;
	}

	/**
	 * adiciona as cadeiras do primeiro periodo
	 */
	private void addCadeiraPrimeiroPeriodo() {
		cadeiras = GerenciadorDeCadeiras.getCadeirasPrimeiro();
	}

	/**
	 * 
	 * @return uma coleção das cadeiras do periodo
	 */
	public Collection<Cadeira> getCadeiras() {
		return cadeiras.values();
	}

	/**
	 * 
	 * @return mapa de cadeiras do periodo
	 */
	public Map<String, Cadeira> getMapCadeiras() {
		return cadeiras;
	}

	/**
	 * 
	 * @param cadeira
	 * @return o Objeto Cadeira a partir da String cadeira
	 */
	public Cadeira getCadeira(String cadeira) {
		return cadeiras.get(cadeira);
	}

	/**
	 * adiciona uma cadeira ao periodo
	 * @param cadeira
	 */
	public void addCadeira(Cadeira cadeira){
		cadeiras.put(cadeira.getNome(), cadeira);
	}

	/**
	 * Remove uma cadeira do periodo
	 * @param cadeira
	 */
	public void removerCadeira(Cadeira cadeira) {
		cadeiras.remove(cadeira.getNome());
	}
	
	/**
	 * 
	 * @return o total da dificuldade do periodo
	 */
	public int getDificuldadeTotal() {
		int dificuldade = 0;
		for (Cadeira c : getCadeiras()) {
			dificuldade += c.getDificuldade();
		}
		return dificuldade;
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
	
	/**
	 * 
	 * @return id do periodo
	 */
	public long getId(){
		return id;
	}
	
	/**
	 * seta id do periodo
	 * @param id
	 */
	public void setId(long id){
		this.id = id;
	}
	
	/**
	 * salva o periodo no bd
	 * @param p
	 */
	public static void create(Periodo p) {
		p.save();
	}

	/**
	 * deleta um periodo pela sua id
	 * @param id
	 */
	public static void delete(Long id) {
		find.ref(id).delete();
	}

	/**
	 * atualiza o periodo
	 * @param id
	 */
	public static void atualizar(Long id) {
		Periodo p = find.ref(id);
		p.update();
	}
}