package br.edu.fumep.eep.cc.subman.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by arabasso on 09/11/2016.
 *
 */

public class SqliteDbHelper extends SQLiteOpenHelper {
    public static final String BANCO_NOME = "provas";
    public static final int BANCO_VERSAO = 1;

    public static final String TABELA_AVALIACAO = "avaliacao";
    public static final String TABELA_MATERIA = "materia";

    public SqliteDbHelper(Context context) {
        super(context, BANCO_NOME, null, BANCO_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE materia (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nome TEXT, professor TEXT);");
        db.execSQL("CREATE TABLE avaliacao (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, descricao TEXT, tipo INTEGER, data DATE, peso FLOAT, nota FLOAT, concluido BOOLEAN, materia_id INTEGER NOT NULL, FOREIGN KEY(materia_id) REFERENCES materia(id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS nota");
        db.execSQL("DROP TABLE IF EXISTS materia");
        onCreate(db);
    }
}
