package alunounifacs.com.br.scee.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.dao.EquipamentoDAO;
import alunounifacs.com.br.scee.helper.EquipamentoHelper;
import alunounifacs.com.br.scee.model.Departamento;
import alunounifacs.com.br.scee.model.Equipamento;

public class EquipamentoActivity extends BaseActivity {

    private EquipamentoHelper helper;
    private Equipamento equipamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipamento);
        setUpToolbar();
        setHomeAsUpIndicatorClose();

        helper = new EquipamentoHelper(EquipamentoActivity.this);

        equipamento = (Equipamento) getIntent().getSerializableExtra("equipamento");
        if (equipamento != null) {
            helper.setEquipamento(equipamento);
            setTitle(getString(R.string.title_editar_equipamento));
        } else {
            equipamento = new Equipamento();
        }

        Departamento departamento = (Departamento) getIntent().getSerializableExtra("departamento");
        if (departamento != null) {
            equipamento.setDepartamento(departamento);
            helper.setDepartamento(departamento);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_equipamento, menu);

        if (equipamento != null) {
            menu.findItem(R.id.action_excluir).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_salvar) {
            if (helper.getEquipamento(equipamento) != null) {
                equipamento.calcularConsumo();
                new EquipamentoDAO(this).salvar(equipamento);
                toast(getString(R.string.s_salvo, equipamento.getDescricao()));
                setResult(1);
                finish();
            }
        } else if (id == R.id.action_excluir) {
            new EquipamentoDAO(this).deletar(equipamento);
            toast(getString(R.string.s_excluido, equipamento.getDescricao()));
            setResult(1);
            finish();
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
