package alunounifacs.com.br.scee.fragment;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by omesquita on 27/04/16.
 */
public class BaseFragment extends Fragment {

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
