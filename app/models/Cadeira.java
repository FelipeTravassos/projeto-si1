package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;






import com.google.common.base.Objects;

/**
 * Entidade que representa uma Cadeira.
 */
@Entity //É UMA ENTIDADE DO BANCO DE DADOS
public class Cadeira extends Model implements Comparable<Cadeira>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* 
	 * PADRÃO DE PROJETO: ALTA COESÃO - so haverá informações coerentes com
	 * a classe
	 */
	
	@Id 											//TODA ENTIDADE TEM QUE TER SUA ID
	@GeneratedValue(strategy = GenerationType.AUTO) //VALOR DA ID GERADO AUTOMATICAMENTE
	private int id;
	
	@Constraints.Required
	@Column(nullable=false)
	private String nome;
	
	private int creditos;
	
//	@ManyToMany(cascade=CascadeType.ALL)
	private List<Cadeira> preRequisitos;
	
	private int periodo;
	private int dificuldade; // dificuldade de 1 - 10
	private PlanoDeCurso plano;
	
	// Construtor Default
	public Cadeira() {
		setPreRequisitos(new ArrayList<Cadeira>());
	}

//	public Cadeira(String nome, int dificuldade) {
//		this.setNome(nome);
//		this.creditos = 4;
//		this.dificuldade = dificuldade;
//		setPreRequisitos(new ArrayList<Cadeira>());
//	}
//
//	public Cadeira(String nome, int dificuldade, int creditos) {
//		this(nome, dificuldade);
//		this.creditos = creditos;
//	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public int getCreditos() {
		return this.creditos;
	}

	public String getNome() {
		return this.nome;
	}

	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public List<Cadeira> getPreRequisitos() {
		return preRequisitos;
	}
	
	public int getPeriodo(){
		return this.periodo;
	}
	
	public void setPeriodo(int periodo){
		this.periodo = periodo;
	}

	/* 
	 * PADRÃO DE PROJETO: INFORMATION EXPERT - a classe cadeira é a
	 * responsável por guardar e adicionar pre-requisitos
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

	@Override
	public int hashCode() {
		return Objects.hashCode(getId(), getNome(), creditos,
				getPreRequisitos());
	}

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

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPreRequisitos(List<Cadeira> preRequisitos) {
		this.preRequisitos = preRequisitos;
	}

	@Override
	public int compareTo(Cadeira c) {
		return getNome().compareTo(c.getNome());
	}
	
	public PlanoDeCurso getPlano() {
		return plano;
	}

	public void setPlano(PlanoDeCurso plano) {
		this.plano = plano;
	}
}