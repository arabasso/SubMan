package br.edu.fumep.eep.cc.subman.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.DbHelper;
import br.edu.fumep.eep.cc.subman.data.Materia;

/**
 * Created by arabasso on 10/11/2016.
 *
 */

public class SqliteMateriaRepositorio implements Repositorio<Materia> {
    public static final String TABELA_MATERIA = "materia";
    DbHelper dbHelper;
    private String[] campos = new String[]{
            "id", "nome", "professor"
    };

    public SqliteMateriaRepositorio(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    @Override
    public Materia carregar(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String [] p = new String[]{
                Integer.toString(id)
        };

        Cursor cursor = db.query(TABELA_MATERIA, campos,"id=?",p,null,null,null);

        Materia materia = null;

        if (cursor.moveToFirst()) {
            materia = getMateria(cursor);
        }

        db.close();

        return materia;
    }

    public void salvar(Materia m){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("nome",m.getNome());
        valores.put("professor",m.getProfessor());

        if (m.getId() <= 0){
            int id = (int)db.insert(TABELA_MATERIA, null, valores);

            m.setId(id);
        } else{
            String [] p = new String[]{
                    Integer.toString(m.getId())
            };

            db.update(TABELA_MATERIA,valores,"id=?",p);
        }

        db.close();
    }

    public List<Materia> listar(){

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABELA_MATERIA,campos,null,null,null,null,null);

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
        return new Materia(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
    }

    public void excluir(Materia materia){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] id = new String[]{
            Integer.toString(materia.getId())
        };

        db.delete(TABELA_MATERIA,"id=?",id);

        db.close();
    }
}
