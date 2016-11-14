package br.edu.fumep.eep.cc.subman;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.Materia;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by arabasso on 14/11/2016.
 *
 */

public class AvaliacaoTest {

    private Materia materia;
    private Avaliacao avaliacao;

    @Before
    public void setUp(){
        materia = new Materia(1, "Cálculo", "José da Silva");
        avaliacao = materia.addAvaliacao(0, "P", DateTime.now(), 0.5f, 10.0f);
    }

    @Test
    public void definirPesoComoNuloRetornaStringVazia(){
        avaliacao.setPeso(null);

        assertThat(avaliacao.getPesoFormatado(), equalTo(""));
    }

    @Test
    public void definirPesoComoStringNula(){
        avaliacao.setPesoFormatado("");

        assertThat(avaliacao.getPeso(), is(nullValue()));
    }

    @Test
    public void definirPesoComoString0_5(){
        avaliacao.setPesoFormatado("0,5");

        assertThat(avaliacao.getPesoFormatado(), equalTo("0,5"));
    }

    @Test
    public void definirNotaComoNuloRetornaStringVazia(){
        avaliacao.setNota(null);

        assertThat(avaliacao.getNotaFormatada(), equalTo(""));
    }

    @Test
    public void definirNotaComoStringNula(){
        avaliacao.setNotaFormatada("");

        assertThat(avaliacao.getNota(), is(nullValue()));
    }

    @Test
    public void definirNotaComoString8_5(){
        avaliacao.setNotaFormatada("8,5");

        assertThat(avaliacao.getNotaFormatada(), equalTo("8,5"));
    }
}
