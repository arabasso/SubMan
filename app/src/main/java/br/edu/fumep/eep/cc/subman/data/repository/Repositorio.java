package br.edu.fumep.eep.cc.subman.data.repository;

import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Entidade;

/**
 * Created by arabasso on 12/11/2016.
 *
 */

public interface Repositorio<T extends Entidade> {
    public T carregar(int id);
    public void salvar(T entidade);
    public void excluir(T entidade);
    public List<T> listar();
}
