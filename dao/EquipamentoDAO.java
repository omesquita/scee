package osnirmesquita.com.br.scee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import osnirmesquita.com.br.scee.model.Departamento;
import osnirmesquita.com.br.scee.model.Equipamento;

/**
 * Created by omesquita on 28/04/16.
 */
public class EquipamentoDAO {

    private ConexaoDB conn;

    public EquipamentoDAO(Context context) {
        this.conn = new ConexaoDB(context);
    }

    public void salvar(Equipamento equipamento) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("descricao", equipamento.getDescricao());
        values.put("potencia", equipamento.getPotencia());
        values.put("hora", equipamento.getHora());
        values.put("dias", equipamento.getDias());
        values.put("consumo", equipamento.getConsumo());
        values.put("valorConsumo", equipamento.getValorConsumo());

        if (equipamento.getId() != 0) {
            String[] args = new String[]{equipamento.getId() + ""};
            db.update("equipamento", values, "id = ", args);

        } else {
            db.insert("quipamento", "", values);
        }
        db.close();

    }

    public void deletar(Equipamento equipamento) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String listaParametros[] = {String.valueOf(equipamento.getId())};
        db.delete("equipamentos", "id = ?", listaParametros);
    }

    public List<Equipamento> getAll() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String sql = "SELECT * FROM equipamento";
        Cursor cursor = db.rawQuery(sql, null);
        List<Equipamento> equipamentos = new ArrayList<>();

        while (cursor.moveToNext()) {
            Equipamento equipamento = new Equipamento();
            equipamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
            equipamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            equipamento.setPotencia(cursor.getInt(cursor.getColumnIndex("potencia")));
            equipamento.setHora(cursor.getInt(cursor.getColumnIndex("hora")));
            equipamento.setDias(cursor.getInt(cursor.getColumnIndex("dias")));
            equipamento.setConsumo(cursor.getDouble(cursor.getColumnIndex("consumo")));
            equipamento.setValorConsumo(cursor.getDouble(cursor.getColumnIndex("valorConsumo")));

        }
        cursor.close();
        db.close();

        return equipamentos;
    }
}
