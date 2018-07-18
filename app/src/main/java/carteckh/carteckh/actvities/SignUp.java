package carteckh.carteckh.actvities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
 * Created by developer on 20-Nov-15.
 */
public class SignUp extends AppCompatActivity {


    @InjectView(R.id.et_email)
    EditText et_email;
    @InjectView(R.id.et_firstname)
    EditText et_firstname;

    @InjectView(R.id.et_lastname)
    EditText et_lastname;
    @InjectView(R.id.et_mobile)
    EditText et_mobile;
    @InjectView(R.id.et_password)
    EditText et_password;
    @InjectView(R.id.et_cnfrmpass)
    EditText et_cnfrmpass;
    @InjectView(R.id.ch_terms)
    AppCompatCheckBox ch_terms;
    @InjectView(R.id.tvterm)
    TextView terms;

    @InjectView(R.id.btn_signup)
    Button btn_signup;
    StringRequest stringRequest1;

    Dialog dialog;
    EditText mobile_otp;
    Button verify;
    String otpvalue;
    boolean bol_terms = false;
    Dialog popupdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing the dialog
        dialog = new Dialog(SignUp.this);

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signup);
        ButterKnife.inject(this);

        Constants.sharedPreferences = SignUp.this.getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

//        if (Constants.sharedPreferences.contains("user_id")) {
//            Intent intent = new Intent(SignUp.this, Testing.class);
//            intent.putExtra("values","false");
//            startActivity(intent);
//            Constants.snacbar=true;
//            finish();
//        }

        terms.setOnClickListener(new View.OnClickListener() {
            //            LayoutInflater layoutInflater;
            @Override
            public void onClick(View v) {
//                Fragment fragment=new TermConditionFragment();
//                FragmentManager fm=getSupportFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.carteckh.com/term-condition/"));
                startActivity(browserIntent);
//                View view = layoutInflater.inflate(R.layout.webview, null);

            }
        });


        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        final String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Log.d("fdgvdbgdfgh",ip);

        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                    Toast.makeText(SignUp.this, "OK", Toast.LENGTH_SHORT).show();
                    JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=checkEmailId&email="+et_email.getText(), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {


                            if (response.optString("success").equals("1")){
                                //Toast.makeText(SignUp.this, "yes", Toast.LENGTH_SHORT).show();
                                btn_signup.setEnabled(true);
                            }
                            else if (response.optString("success").equals("0")){
                                Toast.makeText(SignUp.this,response.optString("message") , Toast.LENGTH_SHORT).show();
                                et_email.requestFocus();
                            }
                            Constants.exitdialog(dialog);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Constants.exitdialog(dialog);
                            Crouton.makeText(SignUp.this, "" + "Some problem occured pls try again", Style.ALERT).show();
                        }
                    });
                    Constants.showdialog(dialog);
                    AppController.getInstance().addToRequestQueue(jsonObjectRequest);
                }
            }
        });


        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=register", new Response.Listener<String>() {
            JSONObject jsonObject = null;
            @Override
            public void onResponse(String s) {

                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Constants.exitdialog(dialog);

                if (jsonObject.optString("success").equals("1")) {

                    Log.d("RSSB",s+"");


//                    popupdialog = new Dialog(SignUp.this);
//                    popupdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    popupdialog.setContentView(R.layout.sms_verify);
//                    popupdialog.setCancelable(false);
//                    verify = (Button)popupdialog.findViewById(R.id.verify);
//                    mobile_otp=(EditText) popupdialog.findViewById(R.id.mobile_otp);
//                    popupdialog.show();
                    //Crouton.makeText(SignUp.this, "" + jsonObject.optString("message"), Style.CONFIRM).show();
                    //Toast.makeText(SignUp.this, "" +jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    Constants.editor.putString("user_id", jsonObject.optString("newUserId"));
                    Constants.editor.putString("user_mail", et_email.getText().toString());
                    Constants.editor.putString("user_name", et_firstname.getText().toString()+" "+et_lastname.getText().toString());
                    //Constants.editor.putString("l_name", et_lastname.getText().toString());
                    Constants.editor.commit();


                } else {
                    Crouton.makeText(SignUp.this, "" + jsonObject.optString("message"), Style.ALERT).show();
                    Toast.makeText(SignUp.this, ""+ jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Constants.handleVolleyError(volleyError, SignUp.this);

                Crouton.makeText(SignUp.this, "" + "Connect to the Internet Please try again", Style.INFO).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("fname", et_firstname.getText().toString());
                params.put("lname", et_lastname.getText().toString());

                params.put("email", et_email.getText().toString());
                params.put("mobile", et_mobile.getText().toString());

                params.put("password", et_password.getText().toString());
                params.put("confirm_password", et_cnfrmpass.getText().toString());
                params.put("ip",ip);
                if (bol_terms)
                    params.put("terms", "1");
                else
                    params.put("terms", "0");

                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Constants.showdialog(dialog);
                if (ch_terms.isChecked()) {
                    bol_terms = true;

                } else {
                    bol_terms = false;

                }

                if (et_firstname.length() != 0) {
                    if (et_lastname.length() != 0) {
                        if (Constants.Check_Email(et_email.getText().toString())) {
                            if (ch_terms.isChecked()) {
                                    if(et_mobile.length()!=0 && et_mobile.length()==10){
                                            if(et_password.length()!=0 && et_password.length()>5){
                                                if(et_cnfrmpass.length()!=0 && et_cnfrmpass.length()>5){
                                                    if(et_password.getText().toString().equals(et_cnfrmpass.getText().toString())){

//                                                        AppController.getInstance().addToRequestQueue(stringRequest);
//                                                        AppController.getInstance().addToRequestQueue(stringRequest1);


                                                         stringRequest1 =new StringRequest(Request.Method.POST, Constants.Url+"task=sendOtp&mobileNumber="+et_mobile.getText().toString(), new Response.Listener<String>() {

                                                            JSONObject jsonObject1=null;
                                                            @Override
                                                            public void onResponse(String response) {

                                                                Constants.exitdialog(dialog);
                                                                try {

                                                                    jsonObject1=new JSONObject(response);
                                                                    if(jsonObject1.optString("success").equals("1")){

                                                                        Log.d("rrrr","working");

                                                                        popupdialog = new Dialog(SignUp.this);
                                                                        popupdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                        popupdialog.setContentView(R.layout.sms_verify);
                                                                        popupdialog.setCancelable(false);
                                                                        verify = (Button)popupdialog.findViewById(R.id.verify);
                                                                        mobile_otp=(EditText) popupdialog.findViewById(R.id.mobile_otp);
                                                                        popupdialog.show();

//                                                                        AppController.getInstance().addToRequestQueue(stringRequest);
                                                                        otpvalue=jsonObject1.optString("message");
//                                                                        final Dialog popupdialog = new Dialog(SignUp.this);
//                                                                        popupdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                                                        popupdialog.setContentView(R.layout.sms_verify);
//                                                                        popupdialog.setCancelable(false);

                                                                       // popupdialog.show();


                                                                        verify.setOnClickListener(new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {


                                                                                if(mobile_otp.getText().toString().isEmpty()){
                                                                                    //Crouton.makeText(getActivity(), "" + "Enter Mobile Number", Style.ALERT).show();
//                    Toast toast= Toast.makeText(getActivity(), "Please enter OTP", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
//                    toast.show();
                                                                                    Crouton.makeText(SignUp.this, "Please enter OTP", Style.CONFIRM).show();
                                                                                    Toast.makeText(SignUp.this, "Please enter OTP", Toast.LENGTH_SHORT).show();

                                                                                }

                                                                                else if(mobile_otp.getText().toString().equals(otpvalue)) {
//                                                                    AppController.getInstance().addToRequestQueue(stringRequest);

                                                                                    popupdialog.dismiss();

                                                                                    Crouton.makeText(SignUp.this, "Successfully verified Please Login", Style.CONFIRM).show();
                                                                                    Toast.makeText(SignUp.this, "Successfully verified Please Login", Toast.LENGTH_SHORT).show();
                                                                                    //Testing.tv_name.setText(Constants.sharedPreferences.getString("f_name","")+" "+Constants.sharedPreferences.getString("l_name",""));
//                                                                                    Testing.tv_name.setText(et_firstname.getText().toString()+" "+et_lastname.getText().toString());
//                                                                                    Crouton.makeText(SignUp.this, "" + jsonObject1.optString("message"), Style.ALERT).show();
                                                                                    //Toast.makeText(SignUp.this, ""+ jsonObject1.optString("message"), Toast.LENGTH_SHORT).show();
                                                                                    Intent intent = new Intent(SignUp.this, User_Login.class);
                                                                                    startActivity(intent);
                                                                                    Constants.snacbar=true;
                                                                                    finish();
                                                                                    //Constants.showdialog(dialog);

                                                                                    AppController.getInstance().addToRequestQueue(stringRequest);
                                                                                }
                                                                                else
                                                                                {
                                                                                    Crouton.makeText(SignUp.this, "Please Enter Valid OTP", Style.CONFIRM).show();
                                                                                    Toast.makeText(SignUp.this, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
//                        Toast toast= Toast.makeText(getActivity(), "Please Enter Valid OTP", Toast.LENGTH_SHORT);
//                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
//                        toast.show();

                                                                                }

                                                                            }
                                                                        });


                                                                        //Toast.makeText(SignUp.this, "send "+otpvalue+" ", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    else
                                                                    {
                                                                        otpvalue=jsonObject1.optString("message");
                                                                        Toast.makeText(SignUp.this, "not "+otpvalue, Toast.LENGTH_SHORT).show();
                                                                        Crouton.makeText(SignUp.this, "" + otpvalue, Style.CONFIRM).show();
                                                                        Log.d("rrrr",""+otpvalue);
                                                                    }

                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {

                                                            }
                                                        });
                                                        stringRequest1.setRetryPolicy(new DefaultRetryPolicy(27000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                                        stringRequest1.setShouldCache(false);
                                                        AppController.getInstance().addToRequestQueue(stringRequest1);
//                                                        final Dialog popupdialog = new Dialog(SignUp.this);
//                                                        popupdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                                                        popupdialog.setContentView(R.layout.sms_verify);
//
//                                                        verify = (Button)popupdialog.findViewById(R.id.verify);
//                                                        mobile_otp=(EditText) popupdialog.findViewById(R.id.mobile_otp);
//                                                        popupdialog.show();
//
//                                                        verify.setOnClickListener(new View.OnClickListener() {
//                                                            @Override
//                                                            public void onClick(View v) {
//
//
//                                                                if(mobile_otp.getText().toString().isEmpty()){
//                                                                    //Crouton.makeText(getActivity(), "" + "Enter Mobile Number", Style.ALERT).show();
////                    Toast toast= Toast.makeText(getActivity(), "Please enter OTP", Toast.LENGTH_SHORT);
////                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
////                    toast.show();
//                                                                    Crouton.makeText(SignUp.this, "Please enter OTP", Style.CONFIRM).show();
//
//                                                                }
//
//                                                                else if(mobile_otp.getText().toString().equals(otpvalue)) {
////                                                                    AppController.getInstance().addToRequestQueue(stringRequest);
//
//                                                                    popupdialog.dismiss();
//
//                                                                    Crouton.makeText(SignUp.this, "Sucessfully Varified...", Style.CONFIRM).show();
//                                                                    Testing.tv_name.setText(Constants.sharedPreferences.getString("f_name","")+" "+Constants.sharedPreferences.getString("l_name",""));
//
//                                                                    //Constants.showdialog(dialog);
//
//                                                                    //AppController.getInstance().addToRequestQueue(stringRequest1);
//                                                                }
//                                                                else
//                                                                {
//                                                                    Crouton.makeText(SignUp.this, "Please Enter Valid OTP", Style.CONFIRM).show();
////                        Toast toast= Toast.makeText(getActivity(), "Please Enter Valid OTP", Toast.LENGTH_SHORT);
////                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
////                        toast.show();
//
//                                                                }
//
//                                                            }
//                                                        });


                                                    }

                                                    else{
                                                        Crouton.makeText(SignUp.this, "Password Mismatch", Style.ALERT).show();
                                                        Toast.makeText(SignUp.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                                                        et_password.requestFocus();
                                                    }



                                             }
                                                else{
                                                    Crouton.makeText(SignUp.this, "Enter 6 length Confirm Password", Style.ALERT).show();
                                                    Toast.makeText(SignUp.this, "Enter 6 length Confirm Password", Toast.LENGTH_SHORT).show();
                                                    et_cnfrmpass.requestFocus();
                                                }
                                            }
                                            else{
                                                Crouton.makeText(SignUp.this, "Enter 6 length Password", Style.ALERT).show();
                                                Toast.makeText(SignUp.this, "Enter 6 length Password",Toast.LENGTH_SHORT).show();
                                                et_password.requestFocus();
                                            }


//
                                    }else {
                                        Crouton.makeText(SignUp.this, "Enter 10 Digit Mobile No..", Style.ALERT).show();
                                        Toast.makeText(SignUp.this, "Enter 10 Digit Mobile No..",Toast.LENGTH_SHORT).show();
                                        et_mobile.requestFocus();
                                    }
                            } else {
                                Crouton.makeText(SignUp.this, "Accept term & condition", Style.ALERT).show();
                                Toast.makeText(SignUp.this, "Accept term & condition",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Crouton.makeText(SignUp.this, "Enter Valid EmailId", Style.ALERT).show();
                            Toast.makeText(SignUp.this, "Enter Valid EmailId",Toast.LENGTH_SHORT).show();
                            et_email.requestFocus();
                        }
                    } else {
                        Crouton.makeText(SignUp.this, "Enter Last Name", Style.ALERT).show();
                        Toast.makeText(SignUp.this, "Enter Last Name",Toast.LENGTH_SHORT).show();
                        et_lastname.requestFocus();
                    }
                } else {
                    Crouton.makeText(SignUp.this, "Enter First Name", Style.ALERT).show();
                    Toast.makeText(SignUp.this, "Enter First Name",Toast.LENGTH_SHORT).show();
                    et_firstname.requestFocus();

                }
                Constants.exitdialog(dialog);
            }
        });

////                if (ch_terms.isChecked()) {
//                    bol_terms = true;
//                } else {
//                    bol_terms = false;
//                }
//
//                if (et_firstname.length() != 0) {
//                    if (et_lastname.length() != 0) {
//                        if (Constants.Check_Email(et_email.getText().toString())) {
//                            if (ch_terms.isChecked())
//                            {
//                                Constants.showdialog(dialog);
//                                AppController.getInstance().addToRequestQueue(stringRequest);
//
//                            }else
//                            {
//                                Crouton.makeText(SignUp.this, "Accept term & condition", Style.ALERT).show();
//                            }
//                        } else {
//                            Crouton.makeText(SignUp.this, "Enter Valid EmailId", Style.ALERT).show();
//                        }
//                    } else {
//                        Crouton.makeText(SignUp.this, "Enter Last Name", Style.ALERT).show();
//                    }
//                } else {
//                    Crouton.makeText(SignUp.this, "Enter First Name", Style.ALERT).show();
//                }


    }


    
}

