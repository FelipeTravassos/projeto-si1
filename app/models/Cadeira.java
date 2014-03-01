package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.google.common.base.Objects;

/**
 * Entidade que representa uma Cadeira.
 */
@Entity 						   //É UMA ENTIDADE DO BANCO DE DADOS
@Table(name = "Cadeiras_do_Curso") //Nome da minha tabela
public class Cadeira extends Model implements Comparable<Cadeira>{

	/* 
	 * PADRÃO DE PROJETO: ALTA COESÃO - so haverá informações coerentes com
	 * a classe
	 */

	public static Finder<Long,Cadeira> find = new Finder<Long,Cadeira>(Long.class, Cadeira.class); 
	private static final long serialVersionUID = 1L;
	
	@Id 											//TODA ENTIDADE TEM QUE TER SUA ID
	@GeneratedValue(strategy = GenerationType.AUTO) //VALOR DA ID GERADO AUTOMATICAMENTE
	long id;
	
	@Constraints.Required
	@Column(name = "Nome_da_Disciplina")
	private String nome;
	
	@Column(name = "Qt_de_Creditos")
	private int creditos;
	
	@ManyToMany(cascade=CascadeType.ALL) //vai salvando todas suas dependencias
	@JoinTable(name = "cadeira_requisito", joinColumns = {@JoinColumn (name = "fk_cadeira")}, inverseJoinColumns = {@JoinColumn(name = "fk_requisito")})
	private List<Cadeira> preRequisitos;
	
	@Column(name = "Periodo_da_Disciplina")
	private int periodo;
	
	@Column(name = "Dificuldade_da_Disciplina")
	private int dificuldade; // dificuldade de 1 - 10
		
	/**
	 * Construtor default
	 * Disciplinas são inicializadas a partir do métodos sets()
	 */
	public Cadeira() {
		setPreRequisitos(new ArrayList<Cadeira>());
	}

	/**
	 * 
	 * @return quantidade de créditos da cadeira
	 */
	public int getCreditos() {
		return this.creditos;
	}
	
	/**
	 * seta a quantidade de créditos da cadeira
	 * @param creditos
	 */
	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	/**
	 * 
	 * @return nome da cadeira
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * seta o nome da cadeira
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * 
	 * @return dificuldade da cadeira
	 */
	public int getDificuldade() {
		return dificuldade;
	}
	
	/**
	 * seta dificuldade da cadeira
	 * @param dificuldade
	 */
	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}

	/**
	 * 
	 * @return lista de pré requisitos de uma cadeira, se ela tiver, se não tiver, será vazia
	 */
	public List<Cadeira> getPreRequisitos() {
		return preRequisitos;
	}
	
	/**
	 * altera os pre requisitos
	 * @param preRequisitos é uma lista que é passada para alterar os pre requisitos antigos
	 */
	public void setPreRequisitos(List<Cadeira> preRequisitos) {
		this.preRequisitos = preRequisitos;
	}
	
	/**
	 * 
	 * @return periodo da cadeira
	 */
	public int getPeriodo(){
		return this.periodo;
	}
	
	/**
	 * seta periodo da cadeira
	 * @param periodo
	 */
	public void setPeriodo(int periodo){
		this.periodo = periodo;
	}

	/* 
	 * PADRÃO DE PROJETO: INFORMATION EXPERT - a classe cadeira é a
	 * responsável por guardar e adicionar pre-requisitos
	 */
	/**
	 * Adiciona um pré-requisito a discipina
	 * @param c (cadeira a se torna pré-requisito)
	 */
	public void addPreRequisito(Cadeira... c) {
		Cadeira[] lista = c;
		for (Cadeira cadeira : lista) {
			getPreRequisitos().add(cadeira);
		}
	}

	/**
	 * Retorna verdadeiro caso a cadeira {@code c} seja pre-requisito, Seguindo
	 * o padrão Information Expert, quem deve saber se uma cadeira é
	 * pre-requisito é a mesma.
	 */
	public boolean isPreRequisito(Cadeira c) {
		return this.getPreRequisitos().contains(c);
	}

	/**
	 * hashcode sobrescrito da superclass usando atributos da cadeira
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getId(), getNome(), creditos,
				getPreRequisitos());
	}

	/**
	 * Compara se duas cadeiras são iguais a partir de seus atributos
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cadeira other = (Cadeira) obj;
		return Objects.equal(this.getCreditos(), other.getCreditos())
				&& Objects.equal(this.getNome(), other.getNome())
				&& Objects.equal(this.getPreRequisitos(),
						other.getPreRequisitos());
	}

	/**
	 * 
	 * @return id da cadeira
	 */
	public long getId() {
		return id;
	}

	/**
	 * seta id da cadeira
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Compara duas disciplina pelo nome
	 */
	@Override
	public int compareTo(Cadeira c) {
		return getNome().compareTo(c.getNome());
	}
	
	/**
	 * Salva uma cadeira no bd
	 * @param cadeira
	 */
	public static void create(Cadeira cadeira) {
		cadeira.save();
	}

	/**
	 * deleta uma cadeira pela sua id
	 * @param id
	 */
	public static void delete(Long id) {
		find.ref(id).delete();
	}

	/**
	 * Atualiza as informações da cadeira
	 * @param id
	 */
	public static void atualizar(Long id) {
		Cadeira cadeira = find.ref(id);
		cadeira.update();
	}
	
	/**
	 * toString da cadeira que mostra id, nome, periodo e creditos da cadeira
	 */
	@Override
	public String toString() {
		return "Nome = " + this.nome + ", ID = " + this.id  + ", Periodo = " + 
	this.periodo + ", Creditos = " + this.creditos;
	}
}