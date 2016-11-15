package br.edu.fumep.eep.cc.subman.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.Materia;

/**
 * Created by arabasso on 10/11/2016.
 *
 */

public class SqliteAvaliacaoRepositorio implements AvaliacaoRepositorio {
    private SqliteDbHelper dbHelper;
    private Context context;
    private String[] campos = new String[]{
            "id",
            "descricao",
            "tipo",
            "data",
            "peso",
            "nota",
            "concluido",
            "materia_id",
    };

    public SqliteAvaliacaoRepositorio(Context context) {
        this.dbHelper = new SqliteDbHelper(context);
        this.context = context;
    }

    @Override
    public Avaliacao carregar(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String [] p = new String[]{
                Integer.toString(id)
        };

        Cursor cursor = db.query(SqliteDbHelper.TABELA_AVALIACAO, campos, "id = ?", p, null, null, null);

        Avaliacao avaliacao = null;

        if (cursor.moveToFirst()) {
            avaliacao = getAvaliacao(cursor);
        }

        db.close();

        return avaliacao;
    }

    public void salvar(Avaliacao avaliacao){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = getValues(avaliacao);

        if (avaliacao.getId() <= 0){
            int id = (int)db.insert(SqliteDbHelper.TABELA_AVALIACAO, null, valores);

            avaliacao.setId(id);
        } else{
            String [] p = new String[]{
                    Integer.toString(avaliacao.getId())
            };

            db.update(SqliteDbHelper.TABELA_AVALIACAO, valores, "id = ?", p);
        }

        db.close();
    }

    @NonNull
    private ContentValues getValues(Avaliacao avaliacao) {
        ContentValues valores = new ContentValues();

        valores.put("descricao", avaliacao.getDescricao());
        valores.put("tipo", avaliacao.getTipo());
        valores.put("data", avaliacao.getDataSimplesmenteFormatada());
        valores.put("peso", avaliacao.getPeso());
        valores.put("nota", avaliacao.getNota());
        valores.put("concluido", avaliacao.foiConcluido());
        valores.put("materia_id", avaliacao.getMateria().getId());

        return valores;
    }

    public List<Avaliacao> listar() {
        return getAvaliacoes(null, null);
    }

    @Override
    public List<Avaliacao> listarPelaMateria(Materia materia) {
        String [] p = new String[]{
                Integer.toString(materia.getId())
        };

        return getAvaliacoes("materia_id = ?", p);
    }

    @Override
    public List<Avaliacao> listarPendentes() {
        String [] p = new String[]{
                Integer.toString(0)
        };

        return getAvaliacoes("concluido = ?", p);
    }

    @NonNull
    private List<Avaliacao> getAvaliacoes(String where, String [] p) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(SqliteDbHelper.TABELA_AVALIACAO, campos, where, p, null, null, "data ASC, descricao ASC");

        List<Avaliacao> av = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Avaliacao avaliacao = getAvaliacao(cursor);

                av.add(avaliacao);
            } while(cursor.moveToNext());
        }

        db.close();

        return av;
    }

    private class AvaliacaoProxy extends Avaliacao{
        private final Repositorio<Materia> materiaRepositorio;
        private int materiaId;

        public AvaliacaoProxy(int materiaId, Context context) {
            super(null);

            this.materiaId = materiaId;
            materiaRepositorio = new SqliteMateriaRepositorio(context);
        }

        @Override
        public Materia getMateria() {
            if (super.getMateria() == null){
                super.setMateria(materiaRepositorio.carregar(materiaId));
            }

            return super.getMateria();
        }
    }

    @NonNull
    private Avaliacao getAvaliacao(Cursor cursor) {

        Avaliacao avaliacao = new AvaliacaoProxy(cursor.getInt(7), context);

        avaliacao.setId(cursor.getInt(0));
        avaliacao.setDescricao(cursor.getString(1));
        avaliacao.setTipo(cursor.getInt(2));
        avaliacao.setDataSimplesmenteFormatada(cursor.getString(3));
        avaliacao.setPesoFormatado(cursor.getString(4));
        avaliacao.setNotaFormatada(cursor.getString(5));
        avaliacao.setConcluido(cursor.getInt(6) == 1);

        return avaliacao;
    }

    public void excluir(Avaliacao avaliacao){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] id = new String[]{
                Integer.toString(avaliacao.getId())
        };

        db.delete(SqliteDbHelper.TABELA_AVALIACAO, "id = ?", id);

        db.close();
    }
}
