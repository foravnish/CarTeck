package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 19-Dec-15.
 */
public class Change_Password extends Fragment {


    View view;


    @InjectView(R.id.old_password)
    EditText old_passwoed;
    @InjectView(R.id.new_password)
    EditText new_password;
    @InjectView(R.id.reenter_password)
    EditText reenter_password;

    @InjectView(R.id.save)
    Button save;

    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialog = new Dialog(Change_Password.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.changepassword, container, false);


        ButterKnife.inject(this, view);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        old_passwoed.setTypeface(typeface);
        new_password.setTypeface(typeface);
        reenter_password.setTypeface(typeface);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (old_passwoed.getText().length() != 0) {

                    if (new_password.getText().length() != 0) {
                        if (reenter_password.getText().length() != 0) {
                            Constants.showdialog(dialog);
                            SendData();

                        } else {
                            Crouton.makeText(getActivity(), "Re-enter New Password", Style.ALERT).show();
                            Toast.makeText(getActivity(), "Re-enter New Password", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Crouton.makeText(getActivity(), "Enter New Password", Style.ALERT).show();
                        Toast.makeText(getActivity(), "Enter New Password", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Crouton.makeText(getActivity(), "Enter Old Password", Style.ALERT).show();
                    Toast.makeText(getActivity(), "Enter Old Password", Toast.LENGTH_SHORT).show();

                }


            }
        });


        return view;
    }

    private void SendData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.Url + "task=changePassword&userid=" + Constants.sharedPreferences.getString("user_id", "") + "&currentPassword=" + old_passwoed.getText().toString() + "&newPassword=" + new_password.getText().toString() + "&reEnterNewPassword=" + reenter_password.getText().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                Constants.exitdialog(dialog);

                try {

                    if (jsonObject.getString("success").equals("1")) {
                        Crouton.makeText(getActivity(), "" + jsonObject.getString("message"), Style.ALERT).show();
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {

                        Crouton.makeText(getActivity(), "" + jsonObject.getString("message"), Style.ALERT).show();
                        Toast.makeText(getActivity(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
