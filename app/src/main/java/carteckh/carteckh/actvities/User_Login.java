package carteckh.carteckh.actvities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 20-Nov-15.
 */
public class User_Login extends AppCompatActivity  {


    @InjectView(R.id.et_username)
    EditText et_username;
    @InjectView(R.id.et_password)
    EditText et_password;
    @InjectView(R.id.btn_login)
    Button btn_login;
    @InjectView(R.id.btn_registration)
    Button btn_reg;
    @InjectView(R.id.showpass)
    Button showpass;
//    @InjectView(R.id.btnfb)
//    Button btnfb;
//
//    @InjectView(R.id.btntwitter)
//    Button btntwitter;

    @InjectView(R.id.btn_forgotpassword)
    Button btn_forgotpassword;
    @InjectView(R.id.skip)
    Button skip;
    Button btn_go;
    TextView tvterm,tv_view;
    Fragment fragment;

    CheckBox ch_terms;
    Dialog dialog,dialog1,dialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing the dialog
        dialog = new Dialog(User_Login.this);
        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_logn);
        ButterKnife.inject(this);

        Constants.sharedPreferences = User_Login.this.getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

        Constants.sharedPreferences1=User_Login.this.getSharedPreferences(Constants.Myprefrence1,MODE_PRIVATE);
        Constants.editor1=Constants.sharedPreferences1.edit();

//        Constants.editor1.putString("value","on");
//        Constants.editor1.commit();

        dialog1= new Dialog(User_Login.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_terms_n_conditions);
        dialog1.setCancelable(false);

//        dialog2= new Dialog(User_Login.this);
//        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog2.setContentView(R.layout.dialog_view_tnc);




        btn_go=(Button)dialog1.findViewById(R.id.btn_go);

        tvterm=(TextView)dialog1.findViewById(R.id.tvterm);
//        tv_view=(TextView)dialog2.findViewById(R.id.tv_view);
        ch_terms=(CheckBox)dialog1.findViewById(R.id.ch_terms);


        tvterm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.carteckh.com/term-condition/"));
                startActivity(intent2);
            }
        });
        String text = "<font color=#808080>I Accept to all the</font> <font color=#ED1C24>Term & Conditions</font> <font color=#808080>of Carteckh.com </font>";
        tvterm.setText(Html.fromHtml(text));




//        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://www.carteckh.com/appdata/jsondata.php?task=contentType&type=TermAndCondition", null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    JSONArray jsonArray= response.getJSONArray("ContentInfo");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                        String text_tv=jsonObject11.optString("content");
//                        tv_view.setText(Html.fromHtml(text_tv));
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//                //tv_view.setText(Html.fromHtml(text_tv));
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Crouton.makeText(User_Login.this, "" + "Some problem occured pls try again", Style.ALERT).show();
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

//        tvterm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(User_Login.this, "ok", Toast.LENGTH_SHORT).show();
//
//                dialog2.show();
//
////                Fragment fragment= new TermConditionFragment();
////                FragmentManager fm=getSupportFragmentManager();
////                FragmentTransaction ft=fm.beginTransaction();
////                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
//
//                //startActivity(new Intent(User_Login.this,Testing.class));
//
//
//
//                //startActivity(new Intent(User_Login.this,Testing.class));

//
//            }
//        });
        if (ch_terms.isChecked()==true){
            ch_terms.setBackgroundColor(Color.RED);

        }
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ch_terms.isChecked()==true) {

                    dialog1.dismiss();
                    Constants.editor1.putString("value","off");
                    //Constants.editor1.putString("status","yes");
                    Constants.editor1.commit();
                }

                else
                {
                    Crouton.makeText(User_Login.this, "Please Accept Terms & Conditions", Style.ALERT).show();
                }

                //finish();
//                Intent intent = new Intent(User_Login.this, Testing.class);
//                startActivity(intent);
//
//                Constants.snacbar=true;
//                finish();
            }
        });



        if (!Constants.sharedPreferences1.getString("value","").equals("off")){
            dialog1.show();
            Constants.editor1.putString("notioff","yes");
            Constants.editor1.commit();
          //  ExpandableListDrawerAdapter.toggleButton.setChecked(true);

            //Constants.editor1.putString("value","off");
        }

        Log.d("dfgdfg",Constants.sharedPreferences1.getString("value",""));


        if (Constants.sharedPreferences.contains("user_id")) {



            Intent intent = new Intent(User_Login.this, Testing.class);
            intent.putExtra("values","false");
            startActivity(intent);
//            Home.snackbar.show();

            Constants.snacbar=true;
            finish();
        }


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User_Login.this, Testing.class);
                 intent.putExtra("values","true");
//                Constants.editor.putString("values","true");
//                Constants.editor.commit();
                startActivity(intent);

                //Home.dialog2.dismiss();

                Constants.snacbar=true;
                finish();

            }
        });


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=login", new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Constants.exitdialog(dialog);


                if (jsonObject.optString("success").equals("1")) {

                    Log.d("jmd",s+"");
                    Crouton.makeText(User_Login.this, "" + jsonObject.optString("message"), Style.CONFIRM).show();
                    Constants.editor.putString("user_id", jsonObject.optString("cartech_user_id"));
                    Constants.editor.putString("user_name", jsonObject.optString("cartech_user_name"));
                    Constants.editor.putString("user_mail", jsonObject.optString("cartech_user_email"));
                    Constants.editor.putString("user_pass", et_password.getText().toString());
                    Constants.editor.commit();

                    Intent intent = new Intent(User_Login.this, Testing.class);
                    intent.putExtra("values","false");
                    startActivity(intent);
                    Constants.snacbar=true;
                    finish();
                } else {
                    Crouton.makeText(User_Login.this, "" + jsonObject.optString("message"), Style.ALERT).show();
                   // Toast.makeText(User_Login.this, ""+jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(User_Login.this);
                    builder.setCancelable(false);
                  //  builder.setTitle("State");
                    builder.setMessage(jsonObject.optString("message"));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //
                        }
                    });

                    // Create the AlertDialog object and return it
                    builder.create().show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(User_Login.this, "" + "Some problem occured pls try again", Style.INFO).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", et_username.getText().toString());
                params.put("password", et_password.getText().toString());
                return params;

            }
        };


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Constants.showdialog(dialog);
//                    AppController.getInstance().addToRequestQueue(stringRequest);

                if (isValidEmaillId(et_username.getText().toString())) {
                    Constants.showdialog(dialog);
                    AppController.getInstance().addToRequestQueue(stringRequest);
                }
                else {
                    AlertDialog.Builder  builder = new AlertDialog.Builder(User_Login.this);
                    builder.setCancelable(false);
                    //  builder.setTitle("State");
                    builder.setMessage("Please enter valid email id or password");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //
                        }
                    });
                    builder.create().show();
                }

                // Create the AlertDialog object and return it

                //Testing.tv_name.setText(Constants.sharedPreferences.getString("user_name",""));
                //Testing.tv_name.setText(Constants.sharedPreferences.getString("f_name","")+" "+Constants.sharedPreferences.getString("l_name",""));


            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Login.this, SignUp.class);
                startActivity(intent);
            }
        });


        btn_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(User_Login.this, Forgot_Password.class);
                startActivity(intent);

            }
        });
        showpass.setOnClickListener(new View.OnClickListener() {
            Boolean flag=true;
            @Override
            public void onClick(View view) {

                if (flag==true) {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    showpass.setText("Hide Password");
                     flag=false;
                } else {

                    et_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showpass.setText("Show Password");
                    flag=true;
                }
            }
        });


    }

    public boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        final Dialog dialog4;

        Button Yes_action,No_action;
        TextView heading;
        dialog4 = new Dialog(User_Login.this);
        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog4.setContentView(R.layout.update_state1);

        Yes_action=(Button)dialog4.findViewById(R.id.Yes_action);
        No_action=(Button)dialog4.findViewById(R.id.No_action);
        heading=(TextView)dialog4.findViewById(R.id.heading);


        heading.setText("Are you sure you want to exit?");
        Yes_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        No_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog4.dismiss();
            }
        });
        dialog4.show();


    }
}

