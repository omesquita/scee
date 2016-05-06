package alunounifacs.com.br.scee.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import alunounifacs.com.br.scee.R;

/**
 * Created by omesquita on 28/04/16.
 */
public class BaseActivity extends AppCompatActivity {
    private Toolbar toolbar;

    protected void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected void setTitleToolbar(String title) {
        toolbar.setTitle(title);
    }

    protected void setHomeAsUpIndicatorClose() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
