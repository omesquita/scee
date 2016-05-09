package alunounifacs.com.br.scee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.dao.DepartamentoDAO;
import alunounifacs.com.br.scee.dao.EquipamentoDAO;
import alunounifacs.com.br.scee.model.Departamento;

/**
 * Created by omesquita on 02/05/16.
 */
public class DepartamentoAdapter extends RecyclerView.Adapter<DepartamentoAdapter.ViewHolder>{

    private Context context;
    private List<Departamento> departamentos;
    private onClickListner onClickListner;

    public DepartamentoAdapter(Context context, List<Departamento> departamentos, onClickListner onClickListner) {
        this.context = context;
        this.departamentos = departamentos;
        this.onClickListner = onClickListner;
    }

    @Override
    public long getItemId(int position) {
        return departamentos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return departamentos == null ? 0 : departamentos.size();
    }

    @Override
    public DepartamentoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_departamento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DepartamentoAdapter.ViewHolder holder, int position) {
        final Departamento departamento = departamentos.get(holder.getAdapterPosition());
        departamento.calcularConsumo();

        holder.txvDescricao.setText(departamento.getDescricao());
        holder.txvQtdEquipamento.setText(
                departamento.getEquipamentos() == null ? "0" : departamento.getEquipamentos().size()+"");
        holder.txvConsumo.setText(String.format("%.2f", departamento.getConsumo()));
        holder.txvValor.setText(String.format("%.2f", departamento.getValorFinal()));


        if (onClickListner != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onItemClick(departamento);
                }
            });
            holder.ibtnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onClickItemEdit(departamento);
                }
            });

            holder.ibtnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onClickItemExcluir(departamento);
                }
            });
        }
    }

    public interface onClickListner {
        void onItemClick(Departamento departamento);
        void onClickItemEdit(Departamento departamento);
        void onClickItemExcluir(Departamento departamento);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvDescricao;
        private TextView txvQtdEquipamento;
        private TextView txvConsumo;
        private TextView txvValor;
        private ImageButton ibtnEditar;
        private ImageButton ibtnExcluir;

        public ViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLayoutPosition();
                }
            });

            txvDescricao = (TextView) view.findViewById(R.id.txvDescricao);
            txvQtdEquipamento = (TextView) view.findViewById(R.id.txvQtdEquipamento);
            txvConsumo = (TextView) view.findViewById(R.id.txvConsumo);
            txvValor = (TextView) view.findViewById(R.id.txvValor);
            ibtnEditar = (ImageButton) view.findViewById(R.id.ibtnEditar);
            ibtnExcluir = (ImageButton) view.findViewById(R.id.ibtnExcluir);
        }
    }
}
