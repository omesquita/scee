package alunounifacs.com.br.scee.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import alunounifacs.com.br.scee.App;
import alunounifacs.com.br.scee.dialog.DepartamentoDialog;
import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.adapter.DepartamentoAdapter;
import alunounifacs.com.br.scee.dao.DepartamentoDAO;
import alunounifacs.com.br.scee.model.Departamento;

public class DepartamentoFragment extends BaseFragment implements DepartamentoDialog.Callback
        , View.OnClickListener, DepartamentoAdapter.onClickListner {

    private RecyclerView recyclerViewDepartamento;
    private List<Departamento> departamentos;
    public DepartamentoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_departamentos, container, false);
        getActivity().setTitle("Departamentos");

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        Button btnCalcularConsumoTotal = (Button) view.findViewById(R.id.btnCalcularConsumoTotal);
        btnCalcularConsumoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Simular Fatura
            }
        });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDepartamento = (RecyclerView) view.findViewById(R.id.rvDepartamentos);
        recyclerViewDepartamento.setLayoutManager(mLinearLayoutManager);
        recyclerViewDepartamento.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDepartamento.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onClick(View v) {
        new DepartamentoDialog().show(getFragmentManager(), null, this);
    }

    @Override
    public void onItemClick(Departamento departamento) {
        EquipamentoFragment frag = new EquipamentoFragment();
        App.getInstance().changedDepartToEquip();
        frag.setDepartamento(departamento);
        getActivity().getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, frag).commit();
    }

    @Override
    public void onClickItemEdit(Departamento departamento) {
        new DepartamentoDialog().show(getFragmentManager(), departamento, this);
    }

    @Override
    public void onClickItemExcluir(Departamento departamento) {
        if(new DepartamentoDAO(getContext()).deletar(departamento)) {
            onResume();
            toast(getString(R.string.s_excluido, departamento.getDescricao()));
        } else {
            simpleDialog(getString(R.string.atencao),
                    getString(R.string.msg_departamento_not_excluido));
        }
    }

    @Override
    public void onClickSalvar(Departamento departamento) {
        onResume();
    }


    @Override
    public void onResume() {
        super.onResume();
        departamentos = new DepartamentoDAO(getContext()).getAll();
        DepartamentoAdapter adapter = new DepartamentoAdapter(getActivity(), departamentos, this);
        recyclerViewDepartamento.setAdapter(adapter);
    }
}