package br.edu.fumep.eep.cc.subman.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.fumep.eep.cc.subman.data.Avaliacao;
import br.edu.fumep.eep.cc.subman.data.DbHelper;
import br.edu.fumep.eep.cc.subman.data.Materia;

/**
 * Created by arabasso on 10/11/2016.
 *
 */

public class SqliteAvaliacaoRepositorio implements Repositorio<Avaliacao> {
    public static final String TABELA_AVALIACAO = "avaliacao";
    private DbHelper dbHelper;
    private String[] campos = new String[]{
            "id", "tipo", "data", "peso", "nota", "materia_id"
    };

    public SqliteAvaliacaoRepositorio(Context context) {
        this.dbHelper = new DbHelper(context);
    }

    @Override
    public Avaliacao carregar(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String [] p = new String[]{
                Integer.toString(id)
        };

        Cursor cursor = db.query(TABELA_AVALIACAO, campos,"id=?",p,null,null,null);

        Avaliacao avaliacao = null;

        if (cursor.moveToFirst()) {
            avaliacao = getAvaliacao(cursor);
        }

        db.close();

        return avaliacao;
    }

    public void salvar(Avaliacao av){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        valores.put("tipo",av.getTipo());
        valores.put("data",dateFormat.format(av.getData()));
        valores.put("peso",av.getPeso());
        valores.put("nota",av.getNota());

        if (av.getId() <= 0){
            db.insert(TABELA_AVALIACAO, null, valores);
        } else{
            String [] p = new String[]{
                    Integer.toString(av.getId())
            };

            db.update(TABELA_AVALIACAO,valores,"id=?",p);
        }

        db.close();
    }

    public List<Avaliacao> listar() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABELA_AVALIACAO, campos, null, null, null, null, null);

        List<Avaliacao> av = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                Avaliacao a = getAvaliacao(cursor);

                av.add(a);
            } while(cursor.moveToNext());
        }

        db.close();

        return av;
    }

    @NonNull
    private Avaliacao getAvaliacao(Cursor cursor) {

        Avaliacao a = new Avaliacao(new Materia(cursor.getInt(5)));

        a.setId(cursor.getInt(0));
        a.setTipo(cursor.getInt(1));
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            a.setData(dateFormat.parse(cursor.getString(2)));
        }catch(Exception e){
            a.setData(new Date(Long.MIN_VALUE));
        }
        a.setPeso(cursor.getFloat(3));
        a.setNota(cursor.getFloat(4));

        return a;
    }

    public void excluir(Avaliacao avaliacao){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] id = new String[]{
                Integer.toString(avaliacao.getId())
        };

        db.delete(TABELA_AVALIACAO,"id=?",id);

        db.close();
    }
}
