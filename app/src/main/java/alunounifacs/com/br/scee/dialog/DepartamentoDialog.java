package alunounifacs.com.br.scee.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.dao.DepartamentoDAO;
import alunounifacs.com.br.scee.model.Departamento;

/**
 * Created by omesquita on 27/04/16.
 */
public class DepartamentoDialog extends DialogFragment {

    private Departamento departamento;
    private Callback mCallback;

    public void show(FragmentManager manager, Departamento departamento, Callback mCallback) {
        this.mCallback = mCallback;
        this.departamento = departamento;
        super.show(manager, "departamentoDialog");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_departamento, null);
        final EditText edtDescricao = (EditText) view.findViewById(R.id.edt_nome);

        /** O usuário clicou em Editar um departamento */
        if (departamento != null) {
            TextView txvTitulo = (TextView) view.findViewById(R.id.txv_title);
            txvTitulo.setText(R.string.editar_departamento);
            edtDescricao.setText(departamento.getDescricao());
        }

        builder.setView(view)
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (departamento != null) {
                            departamento.setDescricao(edtDescricao.getText().toString());
                        } else {
                            departamento = new Departamento();
                            departamento.setDescricao(edtDescricao.getText().toString());
                        }
                        new DepartamentoDAO(getContext()).salvar(departamento);
                        mCallback.onClickSalvar(departamento);
                    }
                })
                .setNegativeButton("Cancelar", null);

        return builder.create();
    }

    public interface Callback {
        void onClickSalvar(Departamento departamento);
    }
}
