package br.edu.fumep.eep.cc.subman.data.media;

import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;

/**
 * Created by arabasso on 14/11/2016.
 *
 */

public class CalculoMediaPonderada implements CalculoMedia {
    @Override
    public double calcular(List<Avaliacao> avaliacoes) {
        double media = 0.0;

        for (Avaliacao a : avaliacoes) {
            media += a.calcularNota();
        }

        return media;
    }
}
