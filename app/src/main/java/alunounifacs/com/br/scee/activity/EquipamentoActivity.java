package alunounifacs.com.br.scee.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.dao.EquipamentoDAO;
import alunounifacs.com.br.scee.helper.EquipamentoHelper;
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
        if(equipamento != null) {
            helper.setEquipamento(equipamento);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_equipamento, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_salvar) {
            if( equipamento == null)
                equipamento = new Equipamento();
            if(helper.getEquipamento(equipamento) != null) {
                new EquipamentoDAO(this).salvar(equipamento);
                toast(getString(R.string.equipamento_s_salvo, equipamento.getDescricao()));
                setResult(1);
                finish();
            }
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
