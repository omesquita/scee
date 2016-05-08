package alunounifacs.com.br.scee.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.List;

import alunounifacs.com.br.scee.App;
import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.dao.DepartamentoDAO;
import alunounifacs.com.br.scee.model.Fatura;
import alunounifacs.com.br.scee.model.Tarifa;

/**
 * Created by omesquita on 06/05/16.
 */
public class SimularFaturaDialog extends DialogFragment {
    private TextView txvQtdDepartamento;
    private TextView txvQtdEquipamentos;
    private TextView txvConsumoTotal;
    private TextView txvValotTotalFatura;
    private TextView txvTipoTarifa;

    private Callback callback;

    public void show(FragmentManager manager, Callback callback) {
        super.show(manager, "dialogFatura");
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_simular_fatura, null);


        Fatura fatura = new Fatura();
        fatura.setDepartamentos(new DepartamentoDAO(getContext()).getAll());
        fatura.setData(Calendar.getInstance().getTime());
        fatura.calculaFatura(getContext());
        List<Tarifa> tarifas = fatura.getTarifasAplicadas();

        inicializaComponentes(view);
        txvTipoTarifa.setText(fatura.getTipoDeTarifa().getDescricao());
        txvQtdDepartamento.setText(String.valueOf(fatura.getQtdDepartamentos()));
        txvQtdEquipamentos.setText(String.valueOf(fatura.getQtdEquipamentos()));
        txvConsumoTotal.setText(String.format("%.2f", fatura.getConsumoKWh()));
        txvValotTotalFatura.setText(String.format("%.2f", fatura.getValorFinal()));

        TableLayout table = (TableLayout) view.findViewById(R.id.tabelaFatura);

        for (Tarifa tarifa : tarifas) {
            if (tarifa.getQuantidadeKWh() != 0) {
                addItemFatura(tarifa, table);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Detalhes da Fatura");
        builder.setPositiveButton("OK", null);
        builder.setView(view);
        callback.finalizado();
        return builder.create();
    }

    private void addItemFatura(Tarifa tarifa, TableLayout table) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TableRow row = new TableRow(getContext());
        row.setLayoutParams(params);

        params.weight = 4.0f;
        TextView descricao = new TextView(getContext());
        descricao.setLayoutParams(params);
        descricao.setText("Descricao");

        params.weight = 0.4f;
        TextView quantidade = new TextView(getContext());
        quantidade.setText(String.valueOf(tarifa.getQuantidadeKWh()));
        quantidade.setLayoutParams(params);

        params.weight = 2.0f;
        TextView precoUnit = new TextView(getContext());
        precoUnit.setText(String.format("%.8f", tarifa.getTarifaFinal()));
        precoUnit.setLayoutParams(params);

        params.weight = 1.0f;
        TextView valor = new TextView(getContext());
        valor.setText(String.format("%.2f", tarifa.getValorTotal()));
        valor.setLayoutParams(params);

        row.addView(descricao);
        row.addView(quantidade);
        row.addView(precoUnit);
        row.addView(valor);
        table.addView(row);
    }

    private void inicializaComponentes(View view) {
        txvQtdDepartamento = (TextView) view.findViewById(R.id.txvQtdDepartamentos);
        txvQtdEquipamentos = (TextView) view.findViewById(R.id.txvQtdEquipamentos);
        txvConsumoTotal = (TextView) view.findViewById(R.id.txvConsumoTotal);
        txvValotTotalFatura = (TextView) view.findViewById(R.id.txvValotTotalFatura);
        txvTipoTarifa = (TextView) view.findViewById(R.id.txvTipoTarifa);
    }

    public interface Callback {
        void finalizado();
    }
}
