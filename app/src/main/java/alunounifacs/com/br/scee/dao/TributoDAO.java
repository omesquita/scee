package alunounifacs.com.br.scee.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import alunounifacs.com.br.scee.model.Tipo;
import alunounifacs.com.br.scee.model.Tributo;

/**
 * Created by omesquita on 02/05/16.
 */
public class TributoDAO {
    private static final String TABLE_NAME = "tributo";

    private ConexaoDB conn;

    public TributoDAO(Context context) {
        this.conn = new ConexaoDB(context);
    }

    public TributoDAO(ConexaoDB conn) {
        this.conn = conn;
    }

    public Tributo getById(int id){
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] selectionArgs = new String[] {String.valueOf(id)};
        String selection = "id = ?";
        Tributo tributo = null;
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                tributo = new Tributo();
                tributo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tributo.setIcms(cursor.getDouble(cursor.getColumnIndex("icms")));
                tributo.setPis(cursor.getDouble(cursor.getColumnIndex("pis")));
                tributo.setCofins(cursor.getDouble(cursor.getColumnIndex("cofins")));
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return tributo;
    }
}
