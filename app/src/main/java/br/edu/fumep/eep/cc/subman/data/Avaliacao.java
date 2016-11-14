package br.edu.fumep.eep.cc.subman.data;

import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by arabasso on 10/11/2016.
 *
 */

public class Avaliacao implements Entidade {
    private int id;
    private String descricao;
    private int tipo;
    private DateTime data;
    private Float peso;
    private Float nota;
    private Materia materia;
    private String notaFormatada;
    private String notaFormatado;

    public Avaliacao(Materia materia) {
        this.materia = materia;
    }

    public Avaliacao(int id, String descricao, int tipo, DateTime data, Float peso, Float nota, Materia materia) {
        this.id = id;
        this.descricao = descricao;
        this.tipo = tipo;
        this.data = data;
        this.peso = peso;
        this.nota = nota;
        this.materia = materia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public DateTime getData() {
        return data;
    }

    public void setData(DateTime data) {
        this.data = data;
    }

    public String getDataFormatada() {
        return getDataFormatada("yyyy-MM-dd");
    }


    public String getDataFormatada(String formato) {
        return this.data.toString(DateTimeFormat.forPattern(formato));
    }

    public void setDataFormatada(String valor) {
        setDataFormatada(valor, "yyyy-MM-dd");
    }

    public void setDataFormatada(String valor, String formato) {
        this.data = DateTimeFormat.forPattern(formato).parseDateTime(valor);
    }

    public Float getPeso() {
        return peso;
    }

    public String getPeso(NumberFormat numberFormat) {
        if (getPeso() == null) {
            return "";
        }

        return numberFormat.format(getPeso());
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getNota() {
        return nota;
    }

    public String getNota(NumberFormat numberFormat) {
        if (getNota() == null) {
            return "";
        }

        return numberFormat.format(getNota());
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getPesoFormatado() {
        return getPeso(NumberFormat.getNumberInstance());
    }

    public void setPeso(String valor, NumberFormat numberFormat) {
        Float peso = getFloat(valor, numberFormat);

        setPeso(peso);
    }

    public void setNota(String valor, NumberFormat numberFormat) {
        Float nota = getFloat(valor, numberFormat);

        setNota(nota);
    }

    @Nullable
    private Float getFloat(String valor, NumberFormat numberFormat) {
        Float peso = null;

        if (valor == null || valor.equals("")){
            peso = null;
        } else{
            try {
                peso = numberFormat.parse(valor).floatValue();
            } catch (ParseException e) {
                peso = null;
            }
        }
        return peso;
    }

    public void setPesoFormatado(String valor) {
        setPeso(valor, NumberFormat.getNumberInstance());
    }

    public String getNotaFormatada() {
        return getNota(NumberFormat.getNumberInstance());
    }

    public void setNotaFormatada(String valor) {
        setNota(valor, NumberFormat.getNumberInstance());
    }
}
