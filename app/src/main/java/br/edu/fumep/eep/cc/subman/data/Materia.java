package br.edu.fumep.eep.cc.subman.data;

import android.text.TextUtils;

import org.joda.time.LocalDate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.media.CalculoMedia;
import br.edu.fumep.eep.cc.subman.data.media.CalculoMediaPonderada;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class Materia implements Entidade {
    private int id;
    private String nome;
    private String professor;
    private List<Avaliacao> avaliacoes = new ArrayList<>();
    private CalculoMedia calculoMedia = new CalculoMediaPonderada();

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

    public void ordenarAvaliacoes(){
        Collections.sort(this.avaliacoes, new Comparator<Avaliacao>() {
            public int compare(Avaliacao o1, Avaliacao o2) {
                return o1.getData().compareTo(o2.getData());
            }
        });
    }

    public Avaliacao addAvaliacao(){
        Avaliacao avaliacao = new Avaliacao(this);

        getAvaliacoes().add(avaliacao);

        return avaliacao;
    }

    public Avaliacao addAvaliacao(int tipo, String descricao, LocalDate data, Float peso, Float nota){
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

    public double calcularMedia() {
        return calculoMedia.calcular(getAvaliacoes());
    }

    public String getSigla() {
        StringBuffer sb = new StringBuffer();

        for (String s : nome.split(" ")) {
            if (s.length() > 3){
                sb.append(Character.toUpperCase(s.charAt(0)));
            }
        }

        return sb.toString();
    }

    public String getNotas(String separador) {
        List<String> notas = new ArrayList<>();

        for (Avaliacao a : getAvaliacoes()) {
            if (!a.temNota()){
                continue;
            }

            notas.add(NumberFormat.getInstance().format(a.calcularNota()));
        }

        return TextUtils.join(separador, notas);
    }
}
