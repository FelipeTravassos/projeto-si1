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
@Entity //É UMA ENTIDADE DO BANCO DE DADOS
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
		
	// Construtor
	public Cadeira() {
		setPreRequisitos(new ArrayList<Cadeira>());
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPreRequisitos(List<Cadeira> preRequisitos) {
		this.preRequisitos = preRequisitos;
	}

	@Override
	public int compareTo(Cadeira c) {
		return getNome().compareTo(c.getNome());
	}
	
	public static void create(Cadeira cadeira) {
		cadeira.save();
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static void atualizar(Long id) {
		Cadeira cadeira = find.ref(id);
		cadeira.update();
	}
}