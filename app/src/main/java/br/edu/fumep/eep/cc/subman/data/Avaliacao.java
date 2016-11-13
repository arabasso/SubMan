package br.edu.fumep.eep.cc.subman.data;

import java.util.Date;

/**
 * Created by arabasso on 10/11/2016.
 *
 */

public class Avaliacao implements Entidade {
    private int id;
    private int tipo;
    private Date data;
    private float peso;
    private Float nota;
    private Materia materia;

    public Avaliacao(Materia materia) {
        this.materia = materia;
    }

    public Avaliacao(int id, int tipo, Date data, float peso, Float nota, Materia materia) {
        this.id = id;
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }
}
