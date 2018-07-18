package carteckh.carteckh.fragments;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_Detail extends Fragment {

    TextView name,email;
    EditText mobile;
    Button getotp;
    Dialog dialog;
    String otpvalue;
    List<String> items = new ArrayList<String>();
    List<String> item_id = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dialog = new Dialog(Contact_Detail.this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view= inflater.inflate(R.layout.contact_detail, container, false);


        Constants.sharedPreferences = getActivity().getSharedPreferences(Constants.Myprefrence, Context.MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

        name=(TextView)view.findViewById(R.id.name);
        email=(TextView)view.findViewById(R.id.email);
        mobile=(EditText)view.findViewById(R.id.mobile);
        getotp=(Button) view.findViewById(R.id.getotp);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=dealerSettings&userid=" + Constants.sharedPreferences.getString("user_id", ""), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);


                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("DealerInfo");
                    Iterator<String> iterator = jsonObject1.keys();

                    while (iterator.hasNext()) {
                        if (iterator.next().equals("success")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray(iterator.next());
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                            name.setText(jsonObject2.getString("FirstName")+" "+jsonObject2.getString("LastName"));
                            email.setText(jsonObject2.getString("Email"));
                            mobile.setText(jsonObject2.getString("Mobile"));
//                            et_mobilenumber.setText(jsonObject2.getString("Mobile"));
//                            et_pinnumber.setText(jsonObject2.getString("Pincode"));
//                            et_address.setText(jsonObject2.getString("Address"));
//                            state = jsonObject2.getString("State");

//                            final ArrayAdapter<Constants> adapter = new ArrayAdapter<Constants>(Edit_Profile.this.getContext(), R.layout.spinner_item, State1);
//                            adapter.setDropDownViewResource(R.layout.spinnertext);
//                            spin_state.setAdapter(adapter);
//                            for (int i = 0; i < State1.size(); i++) {
//                                if (state.trim().equals(State1.get(i).getStr_BrandName().trim().toString())) {
//                                    spin_state.setSelection(i);
//                                }
//
//                            }


                        }
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

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);




        //name.setText(val_brad);
        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.Url+"task=sendOtp&mobileNumber="+mobile.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject=null;
                        try {
                            jsonObject=new JSONObject(response);
                            if(jsonObject.optString("success").equals("1")){
                                Log.d("fdgdfgdf",jsonObject.optString("message"));
                                otpvalue=jsonObject.optString("message");
                                //Toast.makeText(getActivity(), "send "+otpvalue+" ", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                otpvalue=jsonObject.optString("message");
//                                Toast.makeText(getActivity(), "not "+otpvalue, Toast.LENGTH_SHORT).show();
                                Crouton.makeText(getActivity(), "" + otpvalue, Style.ALERT).show();
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
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(27000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                stringRequest.setShouldCache(false);


////////////****************change this /////////
                AppController.getInstance().addToRequestQueue(stringRequest);
                    PopUpDialog();
//                Fragment fragment =new PickImage();
//                Bundle bundle1=new Bundle();
//                bundle1.putString("b1_brandlist",val_brad+"");
//                bundle1.putString("b1_modellist",val_model);
//                bundle1.putString("b1_verlist",val_version);
//                bundle1.putString("b1_monthlist",val_months);
//                bundle1.putString("b1_yearlist",val_year);
//                bundle1.putString("b1_kmslist",val_kms);
//                bundle1.putString("b1_locationlist",val_location);
//                bundle1.putString("b1_pinlist",val_pin);
//                bundle1.putString("b1_pricelist",val_price);
//                bundle1.putString("b1_ownerlist",val_owner);
//                bundle1.putString("b1_sellerlist",val_seller);
//                bundle1.putString("b1_insuranceist",val_insurance);
//                bundle1.putString("b1_fuellist",val_fuel);
//                bundle1.putString("b1_transmissionlist",val_transmission);
//                //bundle1.putString("image", String.valueOf(image1.getDrawable()));
//                fragment.setArguments(bundle1);

//                FragmentManager fm=getFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                ft.replace(R.id.container,fragment).addToBackStack("test").commit();
            }
        });

        return  view;


    }
    private void PopUpDialog() {
        final Bundle bundle=getArguments();
        final String val_brad=bundle.getString("b_brandlist");
        final String val_brad_id=bundle.getString("b_brandid");
        final String val_model=bundle.getString("b_modellist");
        final String val_version=bundle.getString("b_verlist");
        final String val_months=bundle.getString("b_monthlist");
        final String val_year=bundle.getString("b_yearlist");
        final String val_kms=bundle.getString("b_kmslist");
        final String val_location=bundle.getString("b_locationlist");
        final String val_pin=bundle.getString("b_pinlist");
        final String val_price=bundle.getString("b_pricelist");
        final String val_owner=bundle.getString("b_ownerlist");
        final String val_seller=bundle.getString("b_sellerlist");
        final String val_insurance=bundle.getString("b_insuranceist");
        final String val_fuel=bundle.getString("b_fuellist");
        final int val_transmission=bundle.getInt("b_transmissionlist");
        Log.d("gdfgdghfhdfh", String.valueOf(val_transmission));
        final String val_s_id=bundle.getString("b_s_id");
        final String val_l_id=bundle.getString("b_l_id");

        final String val_owner_id=bundle.getString("b_owner_id");
        final String val_insu_id=bundle.getString("b_insu_id");

        //final String val_fuel_id=bundle.getString("b_fuel_id");






        //final  int val_tern= bundle.getInt("b_term");
        final EditText mobile_otp;
        Button verify;

        final Dialog popupdialog = new Dialog(getActivity());
        popupdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupdialog.setContentView(R.layout.sms_verify1);


        verify = (Button)popupdialog.findViewById(R.id.verify);
        mobile_otp=(EditText) popupdialog.findViewById(R.id.mobile_otp);



        verify.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                items = Arrays.asList(val_fuel.split("\\s*,\\s*"));

                Log.d("dfgdgdfhgfhf",items.toString());

                for(int i=0;i<items.size();i++) {
//                    Petrol","Diesel","CNG","Electric","LPG";
                    if (items.get(i).equals("CNG")) {
                        item_id.add("1");
                    } else if (items.get(i).equals("Diesel")) {
                        item_id.add("2");
                    } else if (items.get(i).equals("Petrol")) {
                        item_id.add("3");
                    } else if (items.get(i).equals("Electric")) {
                        item_id.add("4");
                    } else if (items.get(i).equals("LPG")) {
                        item_id.add("5");
                    }
                }

                Log.d("testingdata",items+"");


                if(mobile_otp.getText().toString().isEmpty()){
                    //Crouton.makeText(getActivity(), "" + "Enter Mobile Number", Style.ALERT).show();
                    Toast toast= Toast.makeText(getActivity(), "Please Enter OTP", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                    Crouton.makeText(getActivity(), "Please Enter OTP", Style.ALERT).show();

                }

                ////********** change this *******///
                //else if(mobile_otp.getText().toString().equals("aa")){
                else if(mobile_otp.getText().toString().equals(otpvalue)){

                    //JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, Constants.Url + "task=sellUsedCar&user_id=" + Constants.sharedPreferences.getString("user_id", "") +"&package_id=5" + "&brand_id="+val_brad+"&model_id="+val_model+"&version_id="+val_version+"&make_month="+val_months+"&make_year="+val_year+"&kms_driven="+val_kms+"&state_id="+val_location+"&pincode="+val_pin+"&user_mobile="+mobile.getText().toString()+"&expected_price="+val_price+"&owner="+val_owner+"&car_insu="+val_insurance.toString()+"&reg_num="+val_seller+"&add_fuel="+val_fuel+"&transmission="+val_transmission+"&term_condition=1", null, new Response.Listener<JSONObject>() {

                    // https://www.carteckh.com/appdata/jsondata.php?task=sellUsedCar&user_id=133&package_id=5&brand_id=aaa&model_id=sss&version_id=ww&make_month=jan&make_year=200&kms_driven=2000&state_id=qq&pincode=2251002&user_mobile=8791193679&expected_price=200000&owner=ee&car_insu=tttt&reg_num=gggg&add_fuel=rrr&transmission=yyyy&term_condition=1
                    final StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.Url + "task=sellUsedCar", new Response.Listener<String>() {
                        JSONObject jsonObject = null;
                        @Override
                        public void onResponse(String s) {

                            try {
                                jsonObject = new JSONObject(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Constants.exitdialog(dialog);

                            if (jsonObject.optString("Success").equals("1")) {

                                Log.d("RSSB",s+"");
                                Log.d("fdgdfg",val_fuel);
                                Constants.editor.putString("id",jsonObject.optString("message"));
                                //Crouton.makeText(getActivity(), "" + jsonObject.optString("message"), Style.CONFIRM).show();
                               // Toast.makeText(getActivity(), "inserted" , Toast.LENGTH_SHORT).show();
                                Constants.editor.commit();


                            } else {
                                Crouton.makeText(getActivity(), "" + jsonObject.optString("message"), Style.ALERT).show();
                                //Toast.makeText(getActivity(), "not inerted" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Constants.exitdialog(dialog);
                            Constants.handleVolleyError(volleyError, getActivity());

                            Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.INFO).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("user_id", Constants.sharedPreferences.getString("user_id", ""));
                            params.put("package_id", Constants.sharedPreferences.getString("packageid",""));

                            params.put("brand_id", val_brad_id);
                            Log.d("brand_id", val_brad_id);
                            params.put("model_id", val_model);
                            params.put("version_id", val_version);
                            params.put("make_month", val_months);
                            params.put("make_year", val_year);
                            params.put("kms_driven", val_kms);
                            params.put("state_id", val_s_id);
                            params.put("pincode", val_pin);
                            params.put("user_mobile", mobile.getText().toString());
                            params.put("expected_price", val_price);
                            params.put("owner", val_owner_id);
                            params.put("car_insu", val_insu_id);
                            params.put("reg_num", val_l_id);
//                            params.put("add_fuel", val_fuel);
                            params.put("transmission", String.valueOf(val_transmission));
                            params.put("term_condition", "1");

                            for(int i = 0;i<item_id.size();i++)
                            {
                                params.put("add_fuel[" + i + "]", item_id.get(i));
                            }


                            return params;

                        }
                    };
                    stringRequest2.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest2.setShouldCache(false);

                    AppController.getInstance().addToRequestQueue(stringRequest2);

                    Fragment fragment =new PickImage();

                        Bundle bundle1=new Bundle();
                        bundle1.putString("b1_brandlist",val_brad);
                        bundle1.putString("b1_modellist",val_model);
                        bundle1.putString("b1_verlist",val_version);
                        bundle1.putString("b1_monthlist",val_months);
                        bundle1.putString("b1_yearlist",val_year);
                        bundle1.putString("b1_kmslist",val_kms);
                        bundle1.putString("b1_locationlist",val_location);
                        bundle1.putString("b1_pinlist",val_pin);
                        bundle1.putString("b1_pricelist",val_price);
                        bundle1.putString("b1_ownerlist",val_owner);
                        bundle1.putString("b1_sellerlist",val_seller);
                        bundle1.putString("b1_insuranceist",val_insurance);
                        bundle1.putString("b1_fuellist",val_fuel);
                        bundle1.putInt("b1_transmissionlist",val_transmission);
                        bundle1.putString("b1_mobile",mobile.getText().toString());
                        //bundle1.putString("image", String.valueOf(image1.getDrawable()));
                        fragment.setArguments(bundle1);

                        FragmentManager fm=getFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        ft.replace(R.id.container,fragment).addToBackStack("test").commit();
                        popupdialog.dismiss();
                        Crouton.makeText(getActivity(),  "Successfully Verified...", Style.CONFIRM).show();
//                    Toast toast= Toast.makeText(getActivity(), "Sucessfully Varified...", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
//                    toast.show();

                    }
                    else
                    {
                        Crouton.makeText(getActivity(), "Please Enter Valid OTP", Style.ALERT).show();
                        Toast toast= Toast.makeText(getActivity(), "Please Enter Valid OTP", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                        toast.show();

                    }


                }



        });

        popupdialog.show();




    }

}
