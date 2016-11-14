package br.edu.fumep.eep.cc.subman.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.Materia;

/**
 * Created by arabasso on 10/11/2016.
 *
 */

public class SqliteMateriaRepositorio implements Repositorio<Materia> {
    SqliteDbHelper dbHelper;
    private String[] campos = new String[]{
            "id", "nome", "professor"
    };
    private final AvaliacaoRepositorio avaliacaoRepositorio;
    private Context context;

    private class MateriaProxy extends Materia{
        private AvaliacaoRepositorio avaliacaoRepositorio;

        private boolean carregado;

        public MateriaProxy(Context context, int id, String nome, String professor) {
            super(id, nome, professor);

            this.avaliacaoRepositorio = new SqliteAvaliacaoRepositorio(context);
        }

        @Override
        public List<Avaliacao> getAvaliacoes() {
            if (!carregado){
                setAvaliacoes(avaliacaoRepositorio.listarPelaMateria(this));

                carregado = true;
            }

            return super.getAvaliacoes();
        }
    }

    public SqliteMateriaRepositorio(Context context) {
        this.dbHelper = new SqliteDbHelper(context);
        this.context = context;
        this.avaliacaoRepositorio = new SqliteAvaliacaoRepositorio(context);
    }

    @Override
    public Materia carregar(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String [] p = new String[]{
                Integer.toString(id)
        };

        Cursor cursor = db.query(SqliteDbHelper.TABELA_MATERIA, campos,"id=?",p,null,null,null);

        Materia materia = null;

        if (cursor.moveToFirst()) {
            materia = getMateria(cursor);
        }

        db.close();

        return materia;
    }

    public void salvar(Materia materia){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("nome", materia.getNome());
        valores.put("professor", materia.getProfessor());

        if (materia.getId() <= 0){
            int id = (int)db.insert(SqliteDbHelper.TABELA_MATERIA, null, valores);

            materia.setId(id);
        } else{
            String [] p = new String[]{
                    Integer.toString(materia.getId())
            };

            db.update(SqliteDbHelper.TABELA_MATERIA, valores, "id=?", p);
        }

        db.close();

        for (Avaliacao a : materia.getAvaliacoes()) {
            avaliacaoRepositorio.salvar(a);
        }

        excluirAvaliacoesOrfas(materia);
    }

    public List<Materia> listar(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(SqliteDbHelper.TABELA_MATERIA, campos, null, null, null, null, "nome ASC");

        List<Materia> m = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Materia materia = getMateria(cursor);

                m.add(materia);
            } while(cursor.moveToNext());
        }

        db.close();

        return m;
    }

    @NonNull
    private Materia getMateria(Cursor cursor) {
        return new MateriaProxy(context, cursor.getInt(0), cursor.getString(1), cursor.getString(2));
    }

    public void excluir(Materia materia){

        for (Avaliacao a : materia.getAvaliacoes()) {
            avaliacaoRepositorio.excluir(a);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] id = new String[]{
            Integer.toString(materia.getId())
        };

        db.delete(SqliteDbHelper.TABELA_MATERIA,"id = ?",id);

        db.close();
    }

    private void excluirAvaliacoesOrfas(Materia materia){
        List<Integer> ids = new ArrayList<>();

        ids.add(0);

        for (Avaliacao a : materia.getAvaliacoes()) {
            ids.add(a.getId());
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(SqliteDbHelper.TABELA_AVALIACAO, "NOT id IN (" + TextUtils.join(",", ids) + ")", null);

        db.close();
    }
}
