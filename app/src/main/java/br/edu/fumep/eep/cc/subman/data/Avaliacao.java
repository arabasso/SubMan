package br.edu.fumep.eep.cc.subman.data;

import android.support.annotation.Nullable;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by arabasso on 10/11/2016.
 *
 */

public class Avaliacao implements Entidade {
    private int id;
    private String descricao;
    private int tipo;
    private LocalDate data;
    private Float peso;
    private Float nota;
    private boolean concluido;
    private Materia materia;

    public Avaliacao(Materia materia) {
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDataSimplesmenteFormatada() {
        return getDataSimplesmenteFormatada("yyyy-MM-dd");
    }

    public String getDataSimplesmenteFormatada(String formato) {
        return this.data.toString(DateTimeFormat.forPattern(formato));
    }

    public void setDataSimplesmenteFormatada(String valor) {
        setDataSimplesmenteFormatada(valor, "yyyy-MM-dd");
    }

    public void setDataSimplesmenteFormatada(String valor, String formato) {
        this.data = DateTimeFormat.forPattern(formato).parseLocalDate(valor);
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

    public String getPesoFormatado() {
        return getPeso(NumberFormat.getNumberInstance(Locale.US));
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
        Float peso;

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
        setPeso(valor, NumberFormat.getNumberInstance(Locale.US));
    }

    public String getNotaFormatada() {
        return getNota(NumberFormat.getNumberInstance(Locale.US));
    }

    public void setNotaFormatada(String valor) {
        setNota(valor, NumberFormat.getNumberInstance(Locale.US));
    }

    public boolean foiConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public String getDataFormatada() {
        return getData(DateTimeFormat.longDate());
    }

    public String getData(DateTimeFormatter dateTimeFormatter) {
        if (getData() == null){
            return "";
        }

        return getData().toString(dateTimeFormatter);
    }

    public void setDataFormatada(String valor, DateTimeFormatter dateTimeFormatter) {
        if (valor == null || valor.equals("")){
            setData(null);
        } else{
            setData(dateTimeFormatter.parseLocalDate(valor));
        }
    }

    public double calcularNota() {
        Float peso = getPeso();
        Float nota = getNota();

        if (peso == null){
            peso = 0.0f;
        }

        if (nota == null){
            nota = 0.0f;
        }

        return peso * nota;
    }
}
