package alunounifacs.com.br.scee;

import android.app.Application;

/**
 * Created by omesquita on 01/05/16.
 */
public class App extends Application{

    private static App instance = null;
    private boolean departToEquip = false;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }

    public void changedDepartToEquip() {
        this.departToEquip = !departToEquip;
    }

    public boolean isDepartToEquip() {
        return this.departToEquip;
    }


}
