package br.edu.fumep.eep.cc.subman.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class Materia implements Entidade {
    private int id;
    private String nome;
    private String professor;
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    public Materia(){
    }

    public Materia(int id) {
        this.id = id;
    }

    public Materia(int id, String nome, String professor) {
        this.id = id;
        this.nome = nome;
        this.professor = professor;
    }

    public Materia(String nome, String professor) {
        this(0, nome, professor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Set<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Set<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void addAvaliacao(int tipo, Date data, float peso, Float nota){
        Avaliacao avaliacao = new Avaliacao(this);

        avaliacao.setTipo(tipo);
        avaliacao.setData(data);
        avaliacao.setPeso(peso);
        avaliacao.setNota(nota);

        this.avaliacoes.add(avaliacao);
    }
}
