package br.edu.fumep.eep.cc.subman.data.media;

import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;

/**
 * Created by arabasso on 14/11/2016.
 *
 */

public interface CalculoMedia {
    public double calcular(List<Avaliacao> avaliacoes);
}
