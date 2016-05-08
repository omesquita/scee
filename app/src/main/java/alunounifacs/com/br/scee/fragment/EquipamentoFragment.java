package alunounifacs.com.br.scee.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.activity.EquipamentoActivity;
import alunounifacs.com.br.scee.adapter.EquipamentoAdapter;
import alunounifacs.com.br.scee.dao.EquipamentoDAO;
import alunounifacs.com.br.scee.dialog.SimularFaturaDialog;
import alunounifacs.com.br.scee.model.Departamento;
import alunounifacs.com.br.scee.model.Equipamento;

public class EquipamentoFragment extends BaseFragment implements View.OnClickListener,
        EquipamentoAdapter.onClickListner {

    private RecyclerView recyclerViewEquipamento;
    private List<Equipamento> equipamentos;
    private Departamento departamento;

    public EquipamentoFragment() {
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipamentos, container, false);
        getActivity().setTitle(getString(R.string.equipamentos));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        Button btnCalcularConsumoTotal = (Button) view.findViewById(R.id.btnCalcularConsumoTotal);
        btnCalcularConsumoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimularFaturaDialog().show(getFragmentManager(),
                        new SimularFaturaDialog.Callback() {
                            @Override
                            public void finalizado() {
                                toast("Foi chamado");
                                onResume();
                            }
                        });
            }
        });

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewEquipamento = (RecyclerView) view.findViewById(R.id.rvEquipamentos);
        recyclerViewEquipamento.setLayoutManager(mLinearLayoutManager);
        recyclerViewEquipamento.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEquipamento.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(getContext(), EquipamentoActivity.class);
        if (departamento != null) {
            it.putExtra("departamento", departamento);
        }
        startActivityForResult(it, 1);
    }

    @Override
    public void onItemClick(Equipamento equipamento) {
        Intent it = new Intent(getContext(), EquipamentoActivity.class);
        it.putExtra("equipamento", equipamento);
        startActivityForResult(it, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * Se o departamento for diferente de null, o fragment foi chamado
         * atraves da lista de departamento, por isso deve ser carregado
         * somente os equipamentos daquele departamento
         */
        if (departamento != null) {
            equipamentos = new EquipamentoDAO(getContext()).getAllByDepartamento(departamento);
        } else {
            equipamentos = new EquipamentoDAO(getContext()).getAll();
        }
        EquipamentoAdapter adapter = new EquipamentoAdapter(getActivity(), equipamentos, this);
        recyclerViewEquipamento.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            onResume();
        }
    }
}
