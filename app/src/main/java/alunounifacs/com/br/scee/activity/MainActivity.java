package alunounifacs.com.br.scee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import alunounifacs.com.br.scee.App;
import alunounifacs.com.br.scee.fragment.EquipamentoFragment;
import alunounifacs.com.br.scee.R;
import alunounifacs.com.br.scee.fragment.DashboardFragment;
import alunounifacs.com.br.scee.fragment.DepartamentoFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean inEquipamento = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();
        replaceFragment(new DashboardFragment()); /**Seleciona o Fragmento Principal */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (App.getInstance().isDepartToEquip()){
            replaceFragment(new DepartamentoFragment());
            App.getInstance().changedDepartToEquip();
        } else {
            App.getInstance().changedDepartToEquip();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {
            replaceFragment(new DashboardFragment());
        } else if (id == R.id.nav_departamento) {
            replaceFragment(new DepartamentoFragment());
        } else if (id == R.id.nav_equipamento) {
            replaceFragment(new EquipamentoFragment());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, fragment).commit();
    }
}
