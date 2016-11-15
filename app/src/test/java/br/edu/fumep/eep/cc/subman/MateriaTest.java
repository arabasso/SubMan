package br.edu.fumep.eep.cc.subman;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.edu.fumep.eep.cc.subman.data.Materia;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by arabasso on 14/11/2016.
 *
 */

public class MateriaTest {

    public static final float EPSILON = 0.00001f;
    private Materia materia;

    @Before
    public void setUp(){
        materia = new Materia("Cálulo", "José da Silva");
    }

    @Test
    public void calcularMediaPonderadaSemAvaliacoesDeveSerZero(){
        assertThat(materia.calcularMedia(), closeTo(0.0f, EPSILON));
    }

    @Test
    public void calcularMediaPonderada(){
        materia.addAvaliacao(0, "P1", LocalDate.now(), 0.5f, 10.0f);
        materia.addAvaliacao(0, "P2", LocalDate.now(), 0.5f, 10.0f);

        assertThat(materia.calcularMedia(), closeTo(10.0f, EPSILON));
    }

    @Test
    public void calcularMediaPonderadaComTrabalhoVazio(){
        materia.addAvaliacao(0, "P1", LocalDate.now(), 0.5f, 10.0f);
        materia.addAvaliacao(0, "P2", LocalDate.now(), 0.5f, 10.0f);
        materia.addAvaliacao(1, "T1", LocalDate.now(), null, null);

        assertThat(materia.calcularMedia(), closeTo(10.0f, EPSILON));
    }

    @Test
    public void calcularMediaPonderadaComTrabalho(){
        materia.addAvaliacao(0, "P1", LocalDate.now(), 0.4f, 10.0f);
        materia.addAvaliacao(0, "P2", LocalDate.now(), 0.4f, 10.0f);
        materia.addAvaliacao(1, "T1", LocalDate.now(), 0.2f, 10.0f);

        assertThat(materia.calcularMedia(), closeTo(10.0f, EPSILON));
    }

    @Test
    public void gerarSiglaCalculoIII(){
        Materia materia = new Materia("Cálculo III", "José da Silva");

        assertThat(materia.getSigla(), equalTo("C"));
    }

    @Test
    public void gerarSiglaArquiteturaSoftware(){
        Materia materia = new Materia("Arquitetura de Software", "José da Silva");

        assertThat(materia.getSigla(), equalTo("AS"));
    }
}
