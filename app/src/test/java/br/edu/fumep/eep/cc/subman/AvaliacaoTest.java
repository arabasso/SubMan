package br.edu.fumep.eep.cc.subman;

import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

    public static final double EPSILON = 0.00001;
    private Materia materia;
    private Avaliacao avaliacao;

    @Before
    public void setUp(){
        materia = new Materia(1, "Cálculo", "José da Silva");
        avaliacao = materia.addAvaliacao(0, "P", LocalDate.now(), 0.5f, 10.0f);
    }

    @Test
    public void calcularNotaFinal(){
        assertThat(avaliacao.calcularNota(), Matchers.closeTo(5.0, EPSILON));
    }

    @Test
    public void calcularNotaFinalPesoNulo(){
        avaliacao.setPeso(null);

        assertThat(avaliacao.calcularNota(), Matchers.closeTo(0.0, EPSILON));
    }

    @Test
    public void calcularNotaFinalNotaNula(){
        avaliacao.setNota(null);

        assertThat(avaliacao.calcularNota(), Matchers.closeTo(0.0, EPSILON));
    }

    @Test
    public void calcularNotaFinalPesoNotaNula(){
        avaliacao.setPeso(null);
        avaliacao.setNota(null);

        assertThat(avaliacao.calcularNota(), Matchers.closeTo(0.0, 0.00001));
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
        avaliacao.setPesoFormatado("0.5");

        assertThat(avaliacao.getPesoFormatado(), equalTo("0.5"));
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
        avaliacao.setNotaFormatada("8.5");

        assertThat(avaliacao.getNotaFormatada(), equalTo("8.5"));
    }

    @Test
    public void definirDataComoNuloRetornaStringVazia(){
        avaliacao.setData(null);

        assertThat(avaliacao.getDataFormatada(), equalTo(""));
    }

    @Test
    public void definirDataComoStringNula(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.fullDate();

        avaliacao.setDataFormatada("", dateTimeFormatter);

        assertThat(avaliacao.getData(), is(nullValue()));
    }

    @Test
    public void definirDataStringComoData14_11_2016(){
        avaliacao.setDataFormatada("14/11/2016", DateTimeFormat.shortDate());

        assertThat(avaliacao.getData(), equalTo(new LocalDate(2016, 11, 14)));
    }

    @Test
    public void definirDataComoString14_11_2016(){
        avaliacao.setData(new LocalDate(2016, 11, 14));

        assertThat(avaliacao.getDataFormatada(), equalTo("14 de Novembro de 2016"));
    }

    @Test
    public void definirValorNotaConcluiAvaliacao(){
        avaliacao.setNota(10.0f);

        assertThat(avaliacao.foiConcluido(), is(true));
    }

    @Test
    public void anularNotaDeixaAvaliacaoPendente(){
        avaliacao.setNota(null);

        assertThat(avaliacao.foiConcluido(), is(false));
    }
}
