package alunounifacs.com.br.scee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alunounifacs.com.br.scee.dao.ConexaoDB;
import alunounifacs.com.br.scee.model.Departamento;
import alunounifacs.com.br.scee.model.Equipamento;
import alunounifacs.com.br.scee.model.Tipo;

/**
 * Created by omesquita on 02/05/16.
 */
public class TipoDAO {
    private static final String TABLE_NAME = "tipo";

    private ConexaoDB conn;
    private Context context;

    public TipoDAO(Context context) {
        this.conn = new ConexaoDB(context);
        this.context = context;
    }

    public void salvar(Tipo tipo) {
        ContentValues values = new ContentValues();
        values.put("descricao", tipo.getDescricao());
        values.put("consumo_maximo", tipo.getConsumoMaximo());

        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            if (tipo.getId() == 0) {
                db.insert(TABLE_NAME, null, values);
            } else {
                String whereClause = "id = ?";
                String[] whereArgs = new String[]{String.valueOf(tipo.getId())};
                db.update(TABLE_NAME, values, whereClause, whereArgs);
            }
        } finally {
            db.close();
        }
    }

    public List<Tipo> getAll() {
        SQLiteDatabase db = conn.getReadableDatabase();
        List<Tipo> tipos = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Tipo tipo= new Tipo();
                tipo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tipo.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                tipo.setConsumoMaximo(cursor.getDouble(cursor.getColumnIndex("consumo_maximo")));
                tipo.setTarifas(new TarifaDAO(context).getAllByTipo(tipo));
                tipos.add(tipo);
            }
        } finally {
            db.close();
            if (cursor != null) cursor.close();
        }

        return tipos;
    }

    public Tipo getById(int id_tipo) {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = null;
        Tipo tipo = null;
        String selection = "id = ?";
        String selectionArgs[] = new String[] {String.valueOf(id_tipo)};

        try {
            cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                tipo = new Tipo();
                tipo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tipo.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                tipo.setConsumoMaximo(cursor.getDouble(cursor.getColumnIndex("consumo_maximo")));
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return tipo;
    }


}
