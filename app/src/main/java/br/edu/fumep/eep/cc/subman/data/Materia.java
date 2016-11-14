package br.edu.fumep.eep.cc.subman.data;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class Materia implements Entidade {
    private int id;
    private String nome;
    private String professor;
    private List<Avaliacao> avaliacoes = new ArrayList<>();

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

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Avaliacao addAvaliacao(){
        Avaliacao avaliacao = new Avaliacao(this);

        getAvaliacoes().add(avaliacao);

        return avaliacao;
    }

    public Avaliacao addAvaliacao(int tipo, String descricao, DateTime data, Float peso, Float nota){
        Avaliacao avaliacao = new Avaliacao(this);

        avaliacao.setDescricao(descricao);
        avaliacao.setTipo(tipo);
        avaliacao.setData(data);
        avaliacao.setPeso(peso);
        avaliacao.setNota(nota);

        getAvaliacoes().add(avaliacao);

        return avaliacao;
    }

    public Avaliacao getAvaliacao(int position) {
        if (position >= 0) {
            return getAvaliacoes().get(position);
        }

        return addAvaliacao();
    }
}
