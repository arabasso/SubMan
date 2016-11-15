package br.edu.fumep.eep.cc.subman.data.repository;

import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.Materia;

/**
 * Created by arabasso on 13/11/2016.
 *
 */

public interface AvaliacaoRepositorio extends Repositorio<Avaliacao> {
    public List<Avaliacao> listarPelaMateria(Materia materia);
    public List<Avaliacao> listarPendentes();
}
