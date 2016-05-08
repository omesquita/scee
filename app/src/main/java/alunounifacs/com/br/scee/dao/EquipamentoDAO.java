package alunounifacs.com.br.scee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import alunounifacs.com.br.scee.model.Departamento;
import alunounifacs.com.br.scee.model.Equipamento;

/**
 * Created by omesquita on 28/04/16.
 */
public class EquipamentoDAO {
    private static final String TABLE_NAME = "equipamento";

    private ConexaoDB conn;

    public EquipamentoDAO(Context context) {
        this.conn = new ConexaoDB(context);
    }

    public EquipamentoDAO(ConexaoDB conn) {
        this.conn = conn;
    }

    public void salvar(Equipamento equipamento) {
        ContentValues values = new ContentValues();
        values.put("descricao", equipamento.getDescricao());
        values.put("potencia", equipamento.getPotencia());
        values.put("horas_dias", equipamento.getHorasDia());
        values.put("dias_mes", equipamento.getDiasMes());
        values.put("consumo", equipamento.getConsumo());
        values.put("valorConsumo", equipamento.getValorConsumo());
        values.put("id_departamento", equipamento.getDepartamento().getId());

        SQLiteDatabase db = conn.getWritableDatabase();

        try {
            if (equipamento.getId() == 0) {
                db.insert(TABLE_NAME, null, values);
            } else {
                String whereClause = "id = ?";
                String[] whereArgs = new String[]{String.valueOf(equipamento.getId())};
                db.update(TABLE_NAME, values, whereClause, whereArgs);
            }
        } finally {
            db.close();
        }
    }

    public void deletar(Equipamento equipamento) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String whereClause = "id = ?";
        String whereArgs[] = {String.valueOf(equipamento.getId())};
        try {
            db.delete(TABLE_NAME, whereClause, whereArgs);
        } finally {
            db.close();
        }
    }

    public List<Equipamento> getAll() {
        SQLiteDatabase db = conn.getReadableDatabase();
        List<Equipamento> equipamentos = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            equipamentos = toList(cursor);
        } finally {
            db.close();
            if(cursor != null) cursor.close();
        }

        return equipamentos;
    }

    public List<Equipamento> getAllByDepartamento(Departamento departamento) {
        SQLiteDatabase db = conn.getReadableDatabase();
        List<Equipamento> equipamentos = new ArrayList<>();

        String selection = "id_departamento = ?";
        String[] selectionArgs = new String[] {String.valueOf(departamento.getId())};
        Cursor cursor = null;

        try {
            cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            equipamentos = toList(cursor);
        } finally {
            db.close();
            if(cursor != null) cursor.close();
        }

        return equipamentos;
    }

    private List<Equipamento> toList(Cursor cursor) {
        List<Equipamento> equipamentos = new ArrayList<>();

        while (cursor.moveToNext()) {
            Equipamento equipamento = new Equipamento();
            equipamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
            equipamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            equipamento.setPotencia(cursor.getDouble(cursor.getColumnIndex("potencia")));
            equipamento.setConsumo(cursor.getDouble(cursor.getColumnIndex("consumo")));
//            equipamento.setValorConsumo(cursor.getDouble(cursor.getColumnIndex("valorConsumo")));
            equipamento.setHorasDia(cursor.getInt(cursor.getColumnIndex("horas_dias")));
            equipamento.setDiasMes(cursor.getInt(cursor.getColumnIndex("dias_mes")));
            Departamento temp = new DepartamentoDAO(conn).
                    getById(cursor.getInt(cursor.getColumnIndex("id_departamento")));
            equipamento.setDepartamento(temp);
            equipamentos.add(equipamento);
        }
        return equipamentos;
    }
}
