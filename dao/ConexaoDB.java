package osnirmesquita.com.br.scee.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String sql = "CREATE TABLE IF NOT EXISTS equipamento( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                "descricao TEXT NOT NULL, " +
                "potencia REAL, " +
                "hora INTERGER, " +
                "dias INTERGER, " +
                "consumo_kwh REAL, " +
                "valor_consumo REAL);";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS departamento(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "descricao TEXT, " +
                "consumo_kwh REAL, " +
                "valor_consumo REAL );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
