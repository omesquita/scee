package alunounifacs.com.br.scee.fragment;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import alunounifacs.com.br.scee.R;

/**
 * Created by omesquita on 27/04/16.
 */
public class BaseFragment extends Fragment {

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void simpleDialog(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(getString(R.string.ok), null);
        builder.show();
    }

}
