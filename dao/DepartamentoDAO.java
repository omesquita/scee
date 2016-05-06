package osnirmesquita.com.br.scee.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import osnirmesquita.com.br.scee.model.Departamento;

/**
 * Created by omesquita on 28/04/16.
 */
public class DepartamentoDAO {

    private ConexaoDB conn;

    public DepartamentoDAO(Context context) {
        this.conn = new ConexaoDB(context);
    }

    public void salvar(Departamento departamento) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("descricao", departamento.getDescricao());
        values.put("consumo", departamento.getConsumo());

        if (departamento.getId() != 0) {
            String[] args = new String[]{departamento.getId() + ""};
            db.update("departamento", values, "id = ", args);
        } else {
            db.insert("departamento", "", values);

        }

        db.close();


    }

    public boolean deletar(Departamento departamento) {
//        O departamento só pode ser apagado se não houver equipamento dentro dele.
        if (departamento.getEquipamentos().size() == 0) {
            SQLiteDatabase db = conn.getReadableDatabase();
            String listaParametros[] = {String.valueOf(departamento.getId())};
            db.delete("departamentos", "id = ?", listaParametros);

        }
        return true;
    }

    public List<Departamento> getAll() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String sql = "SELECT * FROM departamento";
        Cursor cursor = db.rawQuery(sql, null);
        List<Departamento> departamentos = new ArrayList<>();

        while (cursor.moveToNext()) {
            Departamento departamento = new Departamento();
            departamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
            departamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            departamento.setConsumo(cursor.getDouble(cursor.getColumnIndex("consumo")));
            departamentos.add(departamento);


        }
        cursor.close();
        db.close();

        return departamentos;
    }

}
