package alunounifacs.com.br.scee.helper;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.activity.EquipamentoActivity;
import alunounifacs.com.br.scee.dao.DepartamentoDAO;
import alunounifacs.com.br.scee.dialog.DepartamentoDialog;
import alunounifacs.com.br.scee.model.Departamento;
import alunounifacs.com.br.scee.model.Equipamento;

/**
 * Created by omesquita on 02/05/16.
 */
public class EquipamentoHelper {

    private EditText edtDescricao;
    private EditText edtPotencia;
    private EditText edtHorasDia;
    private EditText edtDiasMes;
    private Spinner spDepartamento;
    private ArrayList<Departamento> departamentos;
    private

    EquipamentoActivity activity;

    public EquipamentoHelper(final EquipamentoActivity activity) {
        this.activity = activity;
        edtDescricao = (EditText) activity.findViewById(R.id.edtDescricao);
        edtPotencia = (EditText) activity.findViewById(R.id.edtPotencia);
        edtHorasDia = (EditText) activity.findViewById(R.id.edtHorasDia);
        edtDiasMes = (EditText) activity.findViewById(R.id.edtDiasMes);
        spDepartamento = (Spinner) activity.findViewById(R.id.spDepartamentos);
        ImageButton btnNovoDepartamento = (ImageButton) activity.
                findViewById(R.id.ibtnAdicionarDepartamento);
        btnNovoDepartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DepartamentoDialog().show(activity.getSupportFragmentManager(), null,
                        new DepartamentoDialog.Callback() {
                            @Override
                            public void onClickSalvar(Departamento departamento) {
                                preencherSpinner();
                            }
                        });
            }
        });
        preencherSpinner();
    }

    public Equipamento getEquipamento(Equipamento equipamento) {
        if (validaCampos()) {
            equipamento.setDescricao(edtDescricao.getText().toString());
            equipamento.setPotencia(Double.parseDouble(edtPotencia.getText().toString()));
            equipamento.setHorasDia(Integer.parseInt(edtHorasDia.getText().toString()));
            equipamento.setDiasMes(Integer.parseInt(edtDiasMes.getText().toString()));
            equipamento.setDepartamento((Departamento) spDepartamento.getSelectedItem());
            return equipamento;
        }
        return null;
    }

    private boolean validaCampos() {
        if (edtDescricao.getText().length() <= 0) {
            edtDescricao.requestFocus();
            edtDescricao.setError(activity.getString(R.string.campo_obrigatorio));
            return false;
        } else if (edtPotencia.getText().length() <= 0) {
            edtPotencia.requestFocus();
            edtPotencia.setError(activity.getString(R.string.campo_obrigatorio));
            return false;
        } else if (edtHorasDia.getText().length() <= 0) {
            edtHorasDia.requestFocus();
            edtHorasDia.setError(activity.getString(R.string.campo_obrigatorio));
            return false;
        } else if (edtDiasMes.getText().length() <= 0) {
            edtDiasMes.requestFocus();
            edtDiasMes.setError(activity.getString(R.string.campo_obrigatorio));
            return false;
        }
        return true;
    }

    public void setEquipamento(Equipamento equipamento) {
        edtDescricao.setText(equipamento.getDescricao());
        edtPotencia.setText(String.valueOf(equipamento.getPotencia()));
        edtHorasDia.setText(String.valueOf(equipamento.getHorasDia()));
        edtDiasMes.setText(String.valueOf(equipamento.getDiasMes()));
        spDepartamento.setSelection(getItemPositionSpinner(equipamento.getDepartamento()));
    }

    private void preencherSpinner() {
        departamentos = (ArrayList<Departamento>) new DepartamentoDAO(
                activity.getBaseContext()).getAll();

        ArrayAdapter<String> spAdapter = new ArrayAdapter(activity,
                android.R.layout.simple_spinner_item, departamentos);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartamento.setAdapter(spAdapter);
    }

    private int getItemPositionSpinner(Departamento departamento) {
        for (Departamento d : departamentos) {
            if (d.getId() == departamento.getId()) {
                return departamentos.indexOf(d);
            }
        }
        return 0;
    }

    public void setDepartamento(Departamento departamento){
        spDepartamento.setSelection(getItemPositionSpinner(departamento));
    }
}

