package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.actvities.Testing;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 17-Dec-15.
 */
public class Edit_Profile extends Fragment {


    @InjectView(R.id.et_firstname)
    EditText et_firstname;
    @InjectView(R.id.et_lastname)
    EditText et_lastname;
    @InjectView(R.id.et_email)
    EditText et_email;
    @InjectView(R.id.et_mobilenumber)
    EditText et_mobilenumber;
    @InjectView(R.id.et_pinnumber)
    EditText et_pinnumber;
    @InjectView(R.id.et_address)
    EditText et_address;

    @InjectView(R.id.spin_state)
    Spinner spin_state;

    @InjectView(R.id.btn_save)
    Button btn_save;

    List<Constants> State1 = new ArrayList<Constants>();
    List<String> State_Names = new ArrayList<String>();


    Dialog dialog;
    View view;
    String state = "";


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        dialog = new Dialog(Edit_Profile.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.edit_profile, container, false);
        ButterKnife.inject(this, view);


        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        et_firstname.setTypeface(typeface);
        et_pinnumber.setTypeface(typeface);
        et_address.setTypeface(typeface);
        et_mobilenumber.setTypeface(typeface);
        et_lastname.setTypeface(typeface);

        Constants.sharedPreferences=getActivity().getSharedPreferences(Constants.Myprefrence, Context.MODE_PRIVATE);
        Constants.editor=Constants.sharedPreferences.edit();

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
                            et_firstname.setText(jsonObject2.getString("FirstName"));
                            et_lastname.setText(jsonObject2.getString("LastName"));
                            et_email.setText(jsonObject2.getString("Email"));
                            et_mobilenumber.setText(jsonObject2.getString("Mobile"));
                            Log.d("fsdfsdg",jsonObject2.getString("State"));
                            Log.d("ghfhfg",Constants.sharedPreferences.getString("state_id",""));
                            state = jsonObject2.getString("StateId");
//                            state = Constants.sharedPreferences.getString("state_id","");
                            //state = "37";
//                            if (Constants.sharedPreferences.contains("state_id")) {
                                for (int i = 0; i < State1.size(); i++) {
                                    if (state.equals(State1.get(i).getStr_Brandid().toString())) {
                                        Log.d("fdgdfgdfgh", String.valueOf(i));
                                        Log.d("fdgdfgdfgh2", state);
                                        spin_state.setSelection(i);
                                    }

                                }
//                            }
                            //spin_state.setSelection(Integer.parseInt(state));
                            et_pinnumber.setText(jsonObject2.getString("Pincode"));
                            et_address.setText(jsonObject2.getString("Address"));


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


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sendthedata();



//                View view1= inflater.inflate(R.layout.activity_testing,container,false);
//                TextView textView1=(TextView)view1.findViewById(R.id.edit1);
//                TextView textView2=(TextView)view1.findViewById(R.id.edit2);
//                textView1.setText(et_firstname.toString());
//                textView2.setText(et_lastname.toString());
            }
        });



        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=StateList", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);

                JSONArray jsonArray = jsonObject.optJSONArray("state_list");
                State1.add(new Constants("0000","Select State"));
                State_Names.add("Select State");
                for (int i = 1; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                        State1.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("state")));
                        State_Names.add(jsonObject1.optString("state"));
//                        final ArrayAdapter<Constants> adapter = new ArrayAdapter<Constants>(Edit_Profile.this.getContext(), R.layout.spinner_item, State1);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, State_Names);
                adapter.setDropDownViewResource(R.layout.spinnertext);
                spin_state.setAdapter(adapter);


//                if (Constants.sharedPreferences.contains("state_id")) {
//                    for (int i = 0; i < State1.size(); i++) {
//                        if (Constants.sharedPreferences.getString("state_id", "").equals(State1.get(i).getStr_Brandid().toString())) {
//                            spin_state.setSelection(i);
//                        }
//
//                    }
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "Sorry please try again later.", Style.ALERT).show();
            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest1);


        return view;
    }

    private void Sendthedata() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=dealerSettingsUpdate&userid=" + Constants.sharedPreferences.getString("user_id", ""), new Response.Listener<String>() {
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

                    try {

                        Crouton.makeText(getActivity(), jsonObject.getString("message"), Style.INFO).show();
                        Testing.tv_name.setText(et_firstname.getText().toString()+" "+et_lastname.getText().toString());
                        Testing.tv_mobile.setText(et_email.getText().toString());
//                        UserProfile.et_firstname.setText(et_firstname.getText().toString());
//                        UserProfile.et_lastname.setText(et_lastname.getText().toString());
//                        UserProfile.et_email.setText(et_email.getText().toString());
//                        UserProfile.et_mobilenumber.setText(et_mobilenumber.getText().toString());
//                        UserProfile.et_location.setText(spin_state.getSelectedItem().toString());
//                        UserProfile.et_pinnumber.setText(et_pinnumber.getText().toString());
//                        UserProfile.et_address.setText(et_address.getText().toString());
//                        Profile.tablayout.getTabAt(0).select();

//                        Profile.tablayout.refreshDrawableState();
                        Fragment fragment = null;
                        fragment = new Profile();
                        if (fragment != null) {

                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    try {
                        Crouton.makeText(getActivity(), jsonObject.getString("message"), Style.ALERT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "Some problem occured pls try again", Style.ALERT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("FirstName", et_firstname.getText().toString());
                params.put("LastName", et_lastname.getText().toString());
                params.put("Email", et_email.getText().toString());
                params.put("Mobile", et_mobilenumber.getText().toString());
                params.put("State", State1.get(spin_state.getSelectedItemPosition()).getStr_Brandid().toString());
                //params.put("State", "10");

                params.put("Pincode", et_pinnumber.getText().toString());
                params.put("Address", et_address.getText().toString());


                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}
