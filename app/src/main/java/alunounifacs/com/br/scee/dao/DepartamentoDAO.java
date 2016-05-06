package alunounifacs.com.br.scee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alunounifacs.com.br.scee.model.Departamento;

/**
 * Created by omesquita on 28/04/16.
 */
public class DepartamentoDAO {
    private static final String TABLE_NAME = "departamento";

    private ConexaoDB conn;

    public DepartamentoDAO(Context context) {
        this.conn = new ConexaoDB(context);
    }

    public DepartamentoDAO(ConexaoDB conn) {
        this.conn = conn;
    }

    public void salvar(Departamento departamento) {
        ContentValues values = new ContentValues();
        values.put("descricao", departamento.getDescricao());
        values.put("consumo", departamento.getConsumo());
        values.put("valorConsumo", departamento.getValor());

        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            if (departamento.getId() == 0) {
                db.insert(TABLE_NAME, "", values);
            } else {
                String where = "id = ?";
                String[] args = new String[]{String.valueOf(departamento.getId())};
                db.update(TABLE_NAME, values, where, args);
            }
        } finally {
            db.close();
        }
    }

    public boolean deletar(Departamento departamento) {
        if (departamento.getEquipamentos().size() == 0) {
            SQLiteDatabase db = conn.getReadableDatabase();
            try {
                String whereClause = "id = ?";
                String[] whereArgs = {String.valueOf(departamento.getId())};

                db.delete(TABLE_NAME, whereClause, whereArgs);
            } finally {
                db.close();
            }
            return true;
        }

        return false;
    }

    public List<Departamento> getAll() {
        SQLiteDatabase db = conn.getReadableDatabase();
        List<Departamento> departamentos = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Departamento departamento = new Departamento();
                departamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                departamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                departamento.setConsumo(cursor.getDouble(cursor.getColumnIndex("consumo")));
                departamento.setValor(cursor.getDouble(cursor.getColumnIndex("valorConsumo")));
                departamento.setEquipamentos(new EquipamentoDAO(conn).getAllByDepartamento(departamento));
                departamentos.add(departamento);
            }
        } finally {
            db.close();
            if (cursor != null) cursor.close();
        }

        return departamentos;
    }

    public Departamento getById(int id_departamento) {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = null;
        Departamento departamento = null;
        String selection = "id = ?";
        String selectionArgs[] = new String[] {String.valueOf(id_departamento)};

        try {
            cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            if (cursor.moveToFirst()) {
                departamento = new Departamento();
                departamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                departamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                departamento.setConsumo(cursor.getDouble(cursor.getColumnIndex("consumo")));
                departamento.setValor(cursor.getDouble(cursor.getColumnIndex("valorConsumo")));
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return departamento;
    }
}
