package alunounifacs.com.br.scee.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import alunounifacs.com.br.scee.dao.utils.ScriptSQLite;

/**
 * Created by omesquita on 28/04/16.
 */
public class ConexaoDB extends SQLiteOpenHelper{

    private static final String DB_NAME = "scee.db";
    private static final int DB_VERSION = 1;

    public ConexaoDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] sqls = new String[] {
                ScriptSQLite.departamentoV1(),
                ScriptSQLite.equipamentoV1(),
                ScriptSQLite.tributoV1(),
                ScriptSQLite.tipoV1(),
                ScriptSQLite.tarifaV1(),
                ScriptSQLite.faturaV1(),
                ScriptSQLite.faturahasTarifaV1(),
                ScriptSQLite.departamentoHasFaturaV1()
        };

        for(String s : sqls){
            db.execSQL(s);
        }

        for(String s : ScriptSQLite.populateTipo()){
            db.execSQL(s);
        }

        for(String s : ScriptSQLite.populateTributo()){
            db.execSQL(s);
        }

        for(String s : ScriptSQLite.populateTarifa()){
            db.execSQL(s);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
