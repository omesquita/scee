package alunounifacs.com.br.scee.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alunounifacs.com.br.scee.model.Tarifa;
import alunounifacs.com.br.scee.model.Tipo;

/**
 * Created by omesquita on 02/05/16.
 */

public class TarifaDAO {
    private static final String TABLE_NAME = "tarifa";

    private ConexaoDB conn;
    private Context context;

    public TarifaDAO(Context context) {
        this.conn = new ConexaoDB(context);
        this.context = context;
    }

    public TarifaDAO(ConexaoDB conn) {
        this.conn = conn;
    }

    public List<Tarifa> getAllByTipo (Tipo tipo) {
            SQLiteDatabase db = conn.getReadableDatabase();
            List<Tarifa> tarifas = new ArrayList<>();

            Cursor cursor = null;
            String[] selectionArgs = new String[] {String.valueOf(tipo.getId())};
            String selection = "id_tipo = ?";

            try {

                cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
                while (cursor.moveToNext()) {
                    Tarifa tarifa= new Tarifa();
                    tarifa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    tarifa.setConsumoMaximo(cursor.getDouble(cursor.getColumnIndex("consumo_maximo")));
                    tarifa.setTarifaBase(cursor.getDouble(cursor.getColumnIndex("tarifa_base")));
                    tarifa.setTarifaFinal(cursor.getDouble(cursor.getColumnIndex("tarifa_final")));
                    tarifa.setTributo(new TributoDAO(conn).getById(
                            cursor.getInt(cursor.getColumnIndex("id_tributo"))));
                    tarifa.setTipo(tipo);
                    tarifas.add(tarifa);
                }
            } finally {
                db.close();
                if (cursor != null) cursor.close();
            }

            return tarifas;
    }

    public Tarifa getById(int id) {
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] selectionArgs = new String[] {String.valueOf(id)};
        String selection = "id = ?";
        Tarifa tarifa = null;
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                tarifa = new Tarifa();
                tarifa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tarifa.setConsumoMaximo(cursor.getDouble(cursor.getColumnIndex("consumo_maximo")));
                tarifa.setTarifaBase(cursor.getDouble(cursor.getColumnIndex("tarifa_base")));
                tarifa.setTarifaFinal(cursor.getDouble(cursor.getColumnIndex("tarifa_final")));
                tarifa.setTributo(new TributoDAO(context).getById(
                        cursor.getInt(cursor.getColumnIndex("id_tributo"))));
                tarifa.setTipo(new TipoDAO(context).getById(
                        cursor.getInt(cursor.getColumnIndex("id_tipo"))));
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return tarifa;
    }
}
