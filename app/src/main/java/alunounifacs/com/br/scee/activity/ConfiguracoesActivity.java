package alunounifacs.com.br.scee.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import alunounifacs.com.br.scee.R;

public class ConfiguracoesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        setUpToolbar();
        setHomeAsUp(true);

    }
}
