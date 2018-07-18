package carteckh.carteckh.actvities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 21-Nov-15.
 */
public class Forgot_Password extends AppCompatActivity {
    @InjectView(R.id.et_email)
    EditText et_email;

    @InjectView(R.id.btn_submit)
    Button btn_submit;

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the dialog
        dialog = new Dialog(Forgot_Password.this);

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgot_password);
        ButterKnife.inject(this);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constants.Check_Email(et_email.getText().toString())) {
                    Constants.showdialog(dialog);
                    SendData();


                } else {

                    Crouton.makeText(Forgot_Password.this, "Enter Registered Email Id", Style.ALERT).show();
                    Toast.makeText(Forgot_Password.this, "Enter Registered Email Id", Toast.LENGTH_SHORT).show();


                }
            }
        });

    }

    private void SendData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=forgotPassword", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Constants.exitdialog(dialog);

                try {
                    JSONObject jsonObject1 = new JSONObject(s);

                    if (jsonObject1.optString("success").equals("1")) {
                        Crouton.makeText(Forgot_Password.this, "" + jsonObject1.optString("message"), Style.ALERT).show();
                        Toast.makeText(Forgot_Password.this, ""+ jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Crouton.makeText(Forgot_Password.this, "" + jsonObject1.optString("message"), Style.ALERT).show();
                        Toast.makeText(Forgot_Password.this, ""+ jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", et_email.getText().toString());
                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
    }


}
