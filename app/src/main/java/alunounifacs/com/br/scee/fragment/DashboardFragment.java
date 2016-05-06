package alunounifacs.com.br.scee
        .fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import alunounifacs.com.br.scee.App;
import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.dao.DepartamentoDAO;
import alunounifacs.com.br.scee.model.Fatura;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private Fatura fatura;
    private TextView txvConsumoTotal;
    private TextView txvQtdDepartamentos;
    private TextView txvQtdEquipamentos;
    private TextView txvValorTotalFatura;
    private TextView txvTipoTarifa;
    
    public DashboardFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        fatura = new Fatura();
        App.getInstance().setFatura(fatura);
        txvConsumoTotal = (TextView) view.findViewById(R.id.txvConsumoTotal);
        txvQtdDepartamentos = (TextView) view.findViewById(R.id.txvQtdDepartamentos);
        txvQtdEquipamentos = (TextView) view.findViewById(R.id.txvQtdEquipamentos);
        txvValorTotalFatura = (TextView) view.findViewById(R.id.txvValotTotalFatura);
        txvTipoTarifa = (TextView) view.findViewById(R.id.txvTipoTarifa);

        fatura.setData(Calendar.getInstance().getTime());
        fatura.setDepartamentos(new DepartamentoDAO(getContext()).getAll());
        fatura.calculaFatura(getContext());
        atualizaCards();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void atualizaCards() {
        /**Conteúdo do CardFatura*/
        txvQtdDepartamentos.setText(String.valueOf(fatura.getQtdDepartamentos()));
        txvQtdEquipamentos.setText(String.valueOf(fatura.getQtdEquipamentos()));
        txvConsumoTotal.setText(String.format("%.2f", fatura.getConsumoKWh()));
        txvValorTotalFatura.setText(String.format("%.2f", fatura.getValorFinal()));
        txvTipoTarifa.setText(fatura.getTipoDeTarifa().getDescricao());
    }
}
