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
import alunounifacs.com.br.scee.dao.EquipamentoDAO;
import alunounifacs.com.br.scee.model.Equipamento;

/**
 * Created by omesquita on 02/05/16.
 */
public class EquipamentoAdapter extends RecyclerView.Adapter<EquipamentoAdapter.ViewHolder> {

    private Context context;
    private List<Equipamento> equipamentos;
    private onClickListner onClickListner;

    public EquipamentoAdapter(Context context, List<Equipamento> equipamentos, onClickListner onClickListner) {
        this.context = context;
        this.equipamentos = equipamentos;
        this.onClickListner = onClickListner;
    }

    @Override
    public long getItemId(int position) {
        return equipamentos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return equipamentos == null ? 0 : equipamentos.size();
    }

    @Override
    public EquipamentoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_equipamento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Equipamento equipamento = equipamentos.get(holder.getAdapterPosition());

        holder.txvDescricao.setText(equipamento.getDescricao());
        holder.txvHorasDia.setText(String.valueOf(equipamento.getHorasDia()));
        holder.txvDiasMes.setText(String.valueOf(equipamento.getDiasMes()));
        holder.txvPotencia.setText(String.valueOf(equipamento.getPotencia()));
        holder.txvDepartamento.setText(equipamento.getDepartamento().getDescricao());
        holder.txvConsumo.setText(context.getString(R.string.kwh, equipamento.getConsumo()));
        holder.txvValor.setText(String.format("R$ %s", String.format("%.2f", equipamento.getValorConsumo())));
        holder.ibtnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipamento.calcularConsumo();
                holder.txvConsumo.setText(context.getString(R.string.kwh, equipamento.getConsumo()));
                new EquipamentoDAO(context).salvar(equipamento);
            }
        });


        if (onClickListner != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onItemClick(equipamento);
                }
            });

            holder.ibtnExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListner.onClickItemExcluir(equipamento);
                }
            });


        }
    }

    public interface onClickListner {
        void onItemClick(Equipamento equipamento);
        void onClickItemExcluir(Equipamento equipamento);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txvDescricao;
        private TextView txvHorasDia;
        private TextView txvDiasMes;
        private TextView txvPotencia;
        private TextView txvDepartamento;
        private TextView txvConsumo;
        private TextView txvValor;
        private ImageButton ibtnCalcular;
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
            txvHorasDia = (TextView) view.findViewById(R.id.txvHorasDias);
            txvDiasMes = (TextView) view.findViewById(R.id.txvDiasMes);
            txvPotencia = (TextView) view.findViewById(R.id.txvPotencia);
            txvDepartamento = (TextView) view.findViewById(R.id.txvDepartamento);
            txvConsumo = (TextView) view.findViewById(R.id.txvConsumo);
            txvValor = (TextView) view.findViewById(R.id.txvValor);
            ibtnCalcular = (ImageButton) view.findViewById(R.id.ibtnCalcular);
            ibtnExcluir = (ImageButton) view.findViewById(R.id.ibtnExcluir);
        }
    }
}
