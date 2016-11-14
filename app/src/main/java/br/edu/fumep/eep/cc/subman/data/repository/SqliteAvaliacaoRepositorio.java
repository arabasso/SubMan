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
    private String[] campos = new String[]{
            "id", "descricao", "tipo", "data", "peso", "nota", "materia_id"
    };

    public SqliteAvaliacaoRepositorio(Context context) {
        this.dbHelper = new SqliteDbHelper(context);
    }

    @Override
    public Avaliacao carregar(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String [] p = new String[]{
                Integer.toString(id)
        };

        Cursor cursor = db.query(SqliteDbHelper.TABELA_AVALIACAO, campos,"id=?",p,null,null,null);

        Avaliacao avaliacao = null;

        if (cursor.moveToFirst()) {
            avaliacao = getAvaliacao(cursor);
        }

        db.close();

        return avaliacao;
    }

    public void salvar(Avaliacao avaliacao){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("descricao", avaliacao.getDescricao());
        valores.put("tipo", avaliacao.getTipo());
        valores.put("data", avaliacao.getDataFormatada());
        valores.put("peso", avaliacao.getPeso());
        valores.put("nota", avaliacao.getNota());
        valores.put("materia_id", avaliacao.getMateria().getId());

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

    @NonNull
    private List<Avaliacao> getAvaliacoes(String where, String [] p) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(SqliteDbHelper.TABELA_AVALIACAO, campos, where, p, null, null, "data ASC");

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

    @NonNull
    private Avaliacao getAvaliacao(Cursor cursor) {

        Avaliacao a = new Avaliacao(new Materia(cursor.getInt(6)));

        a.setId(cursor.getInt(0));
        a.setDescricao(cursor.getString(1));
        a.setTipo(cursor.getInt(2));
        a.setDataFormatada(cursor.getString(3));
        a.setPesoFormatado(cursor.getString(4));
        a.setNotaFormatada(cursor.getString(5));

        return a;
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
