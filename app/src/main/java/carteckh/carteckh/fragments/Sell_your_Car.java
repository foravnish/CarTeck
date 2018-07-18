package carteckh.carteckh.fragments;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.guna.libmultispinner.MultiSelectionSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class Sell_your_Car extends Fragment {

public Spinner spmonth,spyear,brandlist,sellerlocation,ownerstatus,statelist,insurance,transmission;
    MultiSelectionSpinner fuel;
    String[] _items = null;
    Button savedata;
    TextView modelist,versionlist,kmslist,pincodelist,pricelist,tvterm,notifi;
    AppCompatCheckBox ch_terms2;
    WebView webview;
    ProgressBar progressBar;
    List<String> brand=new ArrayList<String>();
    ArrayList<Constants> brand_id=new ArrayList<Constants>();
    ArrayList<String> State =new ArrayList<String>();
    ArrayList<Constants> State_id =new ArrayList<>();
    Dialog dialog;
    String b_id;
    String s_id;
    String l_id;
    //int bool=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.sell_your_car, container, false);
        final View view1=inflater.inflate(R.layout.webview,container, false);
        dialog = new Dialog(Sell_your_Car.this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Constants.sharedPreferences = getActivity().getSharedPreferences(Constants.Myprefrence, getActivity().MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

        spyear = (Spinner)view.findViewById(R.id.year);
        spmonth=(Spinner)view.findViewById(R.id.month);
        sellerlocation=(Spinner)view.findViewById(R.id.sellerlocation);
        brandlist=(Spinner)view.findViewById(R.id.brandlist);
        ownerstatus=(Spinner)view.findViewById(R.id.ownerstatus);
        statelist=(Spinner)view.findViewById(R.id.statelist);
        insurance=(Spinner)view.findViewById(R.id.insurance);
        fuel=(MultiSelectionSpinner) view.findViewById(R.id.fuel);
        transmission=(Spinner)view.findViewById(R.id.transmission);
        savedata=(Button)view.findViewById(R.id.save);
        modelist=(TextView)view.findViewById(R.id.modelist);
        versionlist=(TextView)view.findViewById(R.id.versionlist);
        kmslist=(TextView)view.findViewById(R.id.kmslist);
        pincodelist=(TextView)view.findViewById(R.id.pincodelist);
        pricelist=(TextView)view.findViewById(R.id.pricelist);
        tvterm=(TextView)view.findViewById(R.id.tvterm);
        webview=(WebView)view1.findViewById(R.id.webview);
        ch_terms2=(AppCompatCheckBox)view.findViewById(R.id.ch_terms2);



//        if(Constants.sharedPreferences.contains("branlist_position")==true){
//            String t = (Constants.sharedPreferences.getString("branlist_position",""));
//            brandlist.setSelection(Integer.parseInt(t));
//            Log.d("newrs",t+"");
//            Toast.makeText(getActivity(),"Value Available",Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getActivity(),"Value Not Selected",Toast.LENGTH_LONG).show();
//        }
        Bundle bundle1=getArguments();
        String val=null;


//        else if(val.equals("avnish_edit"))
//        {
//
//            modelist.setText(Constants.sharedPreferences.getString("model_list", ""));
//            versionlist.setText(Constants.sharedPreferences.getString("version_list", ""));
//            kmslist.setText(Constants.sharedPreferences.getString("kms_list", ""));
//            pincodelist.setText(Constants.sharedPreferences.getString("pin_list", ""));
//            pricelist.setText(Constants.sharedPreferences.getString("price_list", ""));
//            //int aa= Integer.parseInt(Constants.sharedPreferences.getString("bran",""));
//
//
//        }


        tvterm.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Terms & Conditions");
                builder.setMessage("I agree that by clicking the ‘Save & Continue' button above I am explicitly soliciting a OPT(One Time Password) from CarTeckh on my 'Mobile Number' provided above to assist me in completing this transaction. I understand that CarTeckh respects my privacy and will share my contact details only with genuine and interested buyers.\n" +
                        "I certify that the above facts are true and correct to the best of my knowledge and belief and that I have verified its contents. I understand that information and uploads furnished on this form are subject to verification. I understand that I subject my offer to rejection in the event that the above facts are found to be falsified.");

                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("More", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                          Fragment fragment=new TermConditionFragment();
                          FragmentManager fm=getFragmentManager();
                          FragmentTransaction ft=fm.beginTransaction();
                          ft.replace(R.id.container,fragment).addToBackStack(null).commit();
//                        TextView notifi=(TextView)view1.findViewById(R.id.noti_tv);
//                        ProgressBar  progressBar=(ProgressBar)view1.findViewById(R.id.progressBarweb);
//                        webview.setWebViewClient(new WebViewClient());
//                        webview.getSettings().setJavaScriptEnabled(true);
//                        webview.getSettings().setLoadsImagesAutomatically(true);
//                        webview.setScrollBarStyle(view.SCROLLBARS_INSIDE_OVERLAY);
//                        webview.loadUrl("https://www.carteckh.com/term-condition/");




                    }
                });
                builder.show();


            }
        });
        ArrayList<String> year_array = new ArrayList<String>();
        //ArrayList<String> months_array=new ArrayList<String>();
        String months_array[]={"Month","January","February","March","April","May","June","July","August","September","October","November","December"};
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        year_array.add("Year");
        for(int i=year;i>=1970;i--){

            year_array.add(i+"");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, year_array);
        dataAdapter.setDropDownViewResource(R.layout.spinnertext);
        spyear.setAdapter(dataAdapter);
//        if(val.equals("avnish_edit")) {
//            String t = (Constants.sharedPreferences.getString("year_position", ""));
//            spyear.setSelection(Integer.parseInt(t));
//
//        }
//        else
//            spyear.setSelection(0);
////        int months= (calendar.get(Calendar.MONTH));
////        for(int j=months;j<=12;j++) {
////            months_array.add(j+"");
////        }

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, months_array);
        dataAdapter2.setDropDownViewResource(R.layout.spinnertext);
        spmonth.setAdapter(dataAdapter2);
//        if(val.equals("avnish_edit")) {
//            String t = (Constants.sharedPreferences.getString("month_position", ""));
//            spmonth.setSelection(Integer.parseInt(t));
//
//        }
//        else
//            spmonth.setSelection(0);


        final String finalVal1 = val;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=roadsideAssistance", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //Constants.exitdialog(dialog);
                Constants.exitdialog(dialog);

                JSONObject jsonObject1 = jsonObject.optJSONObject("RoadSideAssistance");
                if (jsonObject1.optString("success").equals("1")) {
                    brand.clear();
                    brand_id.clear();
                    brand.add("Select Brand");
                    brand_id.add(new Constants("0000"));
                    JSONArray jsonArray = jsonObject1.optJSONArray("brand_data");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                            brand.add( Constants.(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName"), jsonObject11.optString("roadSideNumber"), jsonObject11.optString("icon")));
                            brand.add(jsonObject11.optString("getBrandName"));
                            brand_id.add(new Constants(jsonObject11.optString("getBrandId ")));
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, brand);
                            dataAdapter.setDropDownViewResource(R.layout.spinnertext);
                            brandlist.setAdapter(dataAdapter);
                            int a=dataAdapter.getPosition(jsonObject11.optString("getBrandName"));
                            //Constants.editor.putInt("bran",a);
//                            if(finalVal1.equals("avnish_edit")) {
//                                String t = (Constants.sharedPreferences.getString("branlist_position", ""));
//                                brandlist.setSelection(Integer.parseInt(t));
//
//                            }
//                            else
//                                brandlist.setSelection(0);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                    Constants.exitdialog(dialog);

//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();
                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(25000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        //Constants.showdialog(dialog);
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        final String finalVal = val;
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=StateList", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //Constants.exitdialog(dialog);
                State.clear();
                State_id.clear();
                JSONArray jsonArray = jsonObject.optJSONArray("state_list");
                State_id.add(new Constants("0000", "Select State"));
                State.add("Select State");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                            brand.add( Constants.(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName"), jsonObject11.optString("roadSideNumber"), jsonObject11.optString("icon")));
                            State.add(jsonObject11.optString("state"));
                            State_id.add(new Constants(jsonObject11.optString("id"),jsonObject11.optString("state")));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, State);
                    dataAdapter.setDropDownViewResource(R.layout.spinnertext);
                    sellerlocation.setAdapter(dataAdapter);
                    statelist.setAdapter(dataAdapter);

                //if(finalVal.equals("avnish_edit")) {
//                    String t = (Constants.sharedPreferences.getString("sellerlocation_position", ""));
//                    sellerlocation.setSelection(Integer.parseInt(t));
                //}
//                else
//                    sellerlocation.setSelection(0);

//                if(finalVal.equals("avnish_edit")) {
//                    String t = (Constants.sharedPreferences.getString("statelist_position", ""));
//                    statelist.setSelection(Integer.parseInt(t));
//
//                }
//                else
//                    statelist.setSelection(0);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                //State.add("Select State");
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();
                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(25000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        //Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest2);


        String owner[]={"Select Owner","I bought it New","2nd Owner","3rd Owner","4th & Above"};
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, owner);
        dataAdapter3.setDropDownViewResource(R.layout.spinnertext);;
        ownerstatus.setAdapter(dataAdapter3);
//        if(val.equals("avnish_edit")) {
//            String t = (Constants.sharedPreferences.getString("ownerstatus_position", ""));
//            ownerstatus.setSelection(Integer.parseInt(t));
//
//        }
//        else
//            ownerstatus.setSelection(0);

        String insurancelist[]={"Select Insurance","Comprehensive","Third Party","No Insurance","Don't Know"};
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, insurancelist);
        dataAdapter4.setDropDownViewResource(R.layout.spinnertext);
        insurance.setAdapter(dataAdapter4);
//            if(val.equals("avnish_edit")) {
//                String t = (Constants.sharedPreferences.getString("insurance_position", ""));
//                insurance.setSelection(Integer.parseInt(t));
//
//            }
//        else
//            insurance.setSelection(0);

        String fuellist[]={"Petrol","Diesel","CNG","Electric","LPG"};
        fuel.setItems(fuellist);


//        ArrayAdapter<String> dataAdapter5= new ArrayAdapter<String>(getActivity(), R.layout.multi_spinner, fuellist);
//        //dataAdapter5.setDropDownViewResource(R.layout.spinnertext);
//        fuel.setAdapter(dataAdapter5);
//            if(val.equals("avnish_edit")) {
//                String t = (Constants.sharedPreferences.getString("fuel_position", ""));
//                fuel.setSelection(Integer.parseInt(t));
//            }
//        else
//            fuel.setSelection(0);

        String transmissionlist[]={"Select Transmission","Manual","Automatic"};
        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, transmissionlist);
        dataAdapter6.setDropDownViewResource(R.layout.spinnertext);
        transmission.setAdapter(dataAdapter6);

//        if(val.equals("avnish_edit")) {
//            String t = (Constants.sharedPreferences.getString("transmission_position", ""));
//            transmission.setSelection(Integer.parseInt(t));
//
//        }
//        else
//            transmission.setSelection(0);


        savedata.setOnClickListener(new View.OnClickListener() {
//            Log.d("RSSB brand",brandlist.getSelectedItem().toString());
//            Log.d("RSSB model",modelist.getText().toString());
//            Log.d("RSSB ver",versionlist.getText().toString());
//            Log.d("RSSB month",spmonth.getSelectedItem().toString());
//            Log.d("RSSB year",spyear.getSelectedItem().toString());
//            Log.d("RSSB kms",kmslist.getText().toString());
//            Log.d("RSSB locatinon",sellerlocation.getSelectedItem().toString());
//            Log.d("RSSB pin",pincodelist.getText().toString());
//            Log.d("RSSB price",pricelist.getText().toString());
//            Log.d("RSSB owner",ownerstatus.getSelectedItem().toString());
//            Log.d("RSSB seller",statelist.getSelectedItem().toString());
//            Log.d("RSSB Insurance",insurance.getSelectedItem().toString());
//            Log.d("RSSB fuel",fuel.getSelectedItem().toString());
//            Log.d("RSSB transmission",transmission.getSelectedItem().toString());
            @Override
            public void onClick(View v) {
             if(ch_terms2.isChecked()){
                 validatation();


                // Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
//                 Crouton.makeText(getActivity(), "Enter Price", Style.CONFIRM).show();

             }
                else
             {
                 //Toast.makeText(getActivity(), "Please Check Term & Condition", Toast.LENGTH_SHORT).show();
                 Crouton.makeText(getActivity(), "Please Check Terms & Condition", Style.ALERT).show();

             }





            }


        });

        brandlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("branlist_position",position+"");
                Log.d("asdf",id+"");
                Log.d("qwert",position+"");
                Log.d("zxcv",view+"");
                Constants.editor.commit();
                String selectionarea = String.valueOf(parent.getItemIdAtPosition(position));
                b_id=brand_id.get(position).getStr_Brandid();
                Log.d("gdfgdf",b_id+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spmonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("month_position",position+"");
                Constants.editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("year_position",position+"");
                Constants.editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sellerlocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("sellerlocation_position",position+"");

                s_id=State_id.get(position).getStr_Brandid().toString();
                Constants.editor.commit();
                Log.d("fdvgd",s_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ownerstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("ownerstatus_position",position+"");
                Constants.editor.putString("ownerstatus_position1", String.valueOf(ownerstatus.getSelectedItemPosition()));
                Constants.editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        statelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("statelist_position",position+"");
                l_id=State_id.get(position).getStr_Brandid().toString();
                Constants.editor.commit();
                Log.d("dfvgdfh",l_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        insurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("insurance_position",position+"");
                Constants.editor.putString("insurance_position1", String.valueOf(insurance.getSelectedItemPosition()));
                Constants.editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("fuel_position",position+"");
                Constants.editor.putString("fuel_position1", String.valueOf(fuel.getSelectedItemPosition()));
                Constants.editor.commit();

//                ArrayList<String> fuel_array=  new ArrayList<String>();
//                fuel_array.add(String.valueOf(fuel.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        transmission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Constants.editor.putString("transmission_position",position+"");
                Constants.editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
   }

    private void validatation() {


        final String modeldata=modelist.getText().toString();
        final String versiondata=versionlist.getText().toString();
        final String kmsdata=kmslist.getText().toString();
        final String pincodedata=pincodelist.getText().toString();
        final String pricedata=pricelist.getText().toString();

        if((brandlist.getSelectedItemPosition() ==0)){
            Crouton.makeText(getActivity(), "Select Brand", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Brand", Toast.LENGTH_SHORT).show();

        }
//        else if(!(brandlist.getSelectedItemPosition() ==0) && modeldata.isEmpty()){
//            Crouton.makeText(getActivity(), "Enter Model", Style.ALERT).show();
//
//        }
//        else if((brandlist.getSelectedItemPosition() >=0) && !modeldata.isEmpty() && versiondata.isEmpty()){
//            Crouton.makeText(getActivity(), "Enter Version", Style.ALERT).show();
//
//        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()==0){
            Crouton.makeText(getActivity(), "Select Month", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Month", Toast.LENGTH_SHORT).show();

        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()==0 ){
            Crouton.makeText(getActivity(), "Select Year", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Year", Toast.LENGTH_SHORT).show();
        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()>=0 &&
                spyear.getSelectedItemPosition()>0 && kmsdata.isEmpty()){
            Crouton.makeText(getActivity(), "Enter Kms Driven", Style.ALERT).show();
            Toast.makeText(getActivity(), "Enter Kms Driven", Toast.LENGTH_SHORT).show();


        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()>=0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()==0){
            Crouton.makeText(getActivity(), "Select Location", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Location", Toast.LENGTH_SHORT).show();
        }
        else if(!(brandlist.getSelectedItemPosition() ==0) &&  spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && pincodedata.isEmpty()){
            Crouton.makeText(getActivity(), "Enter Pin Code", Style.ALERT).show();
            Toast.makeText(getActivity(), "Enter Pin Code", Toast.LENGTH_SHORT).show();

        }
//        else if(!(brandlist.getSelectedItemPosition() ==0) && !modeldata.isEmpty() && !versiondata.isEmpty() && spmonth.getSelectedItemPosition()>0 &&
//                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && !pincodedata.isEmpty() &&
//                pincodedata.length()==6 && pricedata.isEmpty()){
//            Crouton.makeText(getActivity(), "Enter 6 Digit Pin Code", Style.ALERT).show();
//
//        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && !pincodedata.isEmpty() &&
                pricedata.isEmpty()){
            Crouton.makeText(getActivity(), "Enter Price", Style.ALERT).show();
            Toast.makeText(getActivity(), "Enter Price", Toast.LENGTH_SHORT).show();

        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && !pincodedata.isEmpty() &&
                !pricedata.isEmpty()&& ownerstatus.getSelectedItemPosition()==0){
            Crouton.makeText(getActivity(), "Select Owner", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Owner", Toast.LENGTH_SHORT).show();
        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && !pincodedata.isEmpty() &&
                !pricedata.isEmpty()&& ownerstatus.getSelectedItemPosition()>0 && statelist.getSelectedItemPosition()==0){
            Crouton.makeText(getActivity(), "Select State", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select State", Toast.LENGTH_SHORT).show();
        }
        else if(!(brandlist.getSelectedItemPosition() ==0)  && spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && !pincodedata.isEmpty() &&
                !pricedata.isEmpty()&& ownerstatus.getSelectedItemPosition()>0 && statelist.getSelectedItemPosition()>0 && insurance.getSelectedItemPosition()==0){
            Crouton.makeText(getActivity(), "Select Insurance", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Insurance", Toast.LENGTH_SHORT).show();
        }
        else if(!(brandlist.getSelectedItemPosition() ==0)  && spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && !pincodedata.isEmpty() &&
                !pricedata.isEmpty()&& ownerstatus.getSelectedItemPosition()>0 && statelist.getSelectedItemPosition()>0 && insurance.getSelectedItemPosition()>0 &&
                fuel.getSelectedItemsAsString().equals("")) {
            Crouton.makeText(getActivity(), "Select Fuel", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Fuel", Toast.LENGTH_SHORT).show();
        }
        else if(!(brandlist.getSelectedItemPosition() ==0) && spmonth.getSelectedItemPosition()>0 &&
                spyear.getSelectedItemPosition()>0 && !kmsdata.isEmpty()&& sellerlocation.getSelectedItemPosition()>0 && !pincodedata.isEmpty() &&
                !pricedata.isEmpty()&& ownerstatus.getSelectedItemPosition()>0 && statelist.getSelectedItemPosition()>0 && insurance.getSelectedItemPosition()>0 &&
                !fuel.getSelectedIndices().equals("") && transmission.getSelectedItemPosition()==0) {
            Crouton.makeText(getActivity(), "Select Transmission", Style.ALERT).show();
            Toast.makeText(getActivity(), "Select Transmission", Toast.LENGTH_SHORT).show();
        }
        else if(!(pincodedata.length() ==6)){
            Crouton.makeText(getActivity(), "Enter Pin Code 6 Digit", Style.ALERT).show();
            Toast.makeText(getActivity(), "Enter Pin Code 6 Digit", Toast.LENGTH_SHORT).show();
        }
        else
        {

            try {
                Fragment fragment=new Contact_Detail();
                Bundle bundle=new Bundle();
                bundle.putString("b_brandlist",brandlist.getSelectedItem().toString());
                Log.d("b_brandlist",brandlist.getSelectedItem().toString());
                bundle.putString("b_brandid", b_id);
                bundle.putString("b_modellist",modelist.getText().toString());
                bundle.putString("b_verlist",versionlist.getText().toString());
                bundle.putString("b_monthlist",spmonth.getSelectedItem().toString());
                bundle.putString("b_yearlist",spyear.getSelectedItem().toString());
                bundle.putString("b_kmslist",kmslist.getText().toString());
                bundle.putString("b_locationlist",sellerlocation.getSelectedItem().toString());
                Log.d("b_locationlist",sellerlocation.getSelectedItem().toString());
                bundle.putString("b_pinlist",pincodelist.getText().toString());
                bundle.putString("b_pricelist",pricelist.getText().toString());
                bundle.putString("b_ownerlist",ownerstatus.getSelectedItem().toString());
                bundle.putString("b_sellerlist",statelist.getSelectedItem().toString());
                Log.d("b_sellerlist",statelist.getSelectedItem().toString());
                bundle.putString("b_insuranceist",insurance.getSelectedItem().toString());
                bundle.putString("b_fuellist", fuel.getSelectedItem()+"");

                Log.d("gfdgdfgdfhb",fuel.getSelectedItem()+"");
//                bundle.putString("b_transmissionlist",transmission.getSelectedItem().toString());
                bundle.putInt("b_transmissionlist",transmission.getSelectedItemPosition());
                Log.d("dfgdfgdf", String.valueOf(transmission.getSelectedItemPosition()));
                Log.d("dfgdfgdf", String.valueOf(transmission.getSelectedItemId()));


                bundle.putString("b_s_id",s_id);
                bundle.putString("b_l_id",l_id);
                bundle.putString("b_owner_id",Constants.sharedPreferences.getString("ownerstatus_position1",""));
                bundle.putString("b_insu_id",Constants.sharedPreferences.getString("insurance_position1",""));
//                bundle.putString("b_fuel_id",Constants.sharedPreferences.getString("fuel_position1",""));
                //bundle.putString("b_fuel_id",Constants.sharedPreferences.getString("fuel_position1",""));


                //bundle.putInt("b_term",bool);
                fragment.setArguments(bundle);
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack("test").commit();

                //Constants.editor.putString("brand_list", String.valueOf(brandlist.getSelectedItemPosition()));
                Constants.editor.putString("model_list",modelist.getText().toString());
                Constants.editor.putString("version_list",versionlist.getText().toString());
//                Constants.editor.putString("month_list",spmonth.getSelectedItem().toString());
//                Constants.editor.putString("year_list",spyear.getSelectedItem().toString());
                Constants.editor.putString("kms_list",kmslist.getText().toString());
                //Constants.editor.putString("loc_list",sellerlocation.getSelectedItem().toString());
                Constants.editor.putString("pin_list",pincodelist.getText().toString());
                Constants.editor.putString("price_list",pricelist.getText().toString());
                //Constants.editor.putString("owner_list",ownerstatus.getSelectedItem().toString());
                //Constants.editor.putString("seller_list",statelist.getSelectedItem().toString());
                //Constants.editor.putString("ins_list",insurance.getSelectedItem().toString());
                //Constants.editor.putString("fuel_list",fuel.getSelectedItem().toString());
                //Constants.editor.putString("trnas_list",transmission.getSelectedItem().toString());


                Constants.editor.commit();
            } catch (Exception e) {
                e.printStackTrace();

            }
            //Crouton.makeText(getActivity(), "Successfully.......", Style.ALERT).show();

            Log.d("RSSB brand",brandlist.getSelectedItem().toString());
            Log.d("RSSB brand_id",b_id.toString());
            Log.d("RSSB model",modelist.getText().toString());
            Log.d("RSSB ver",versionlist.getText().toString());
            Log.d("RSSB month",spmonth.getSelectedItem().toString());
            Log.d("RSSB year",spyear.getSelectedItem().toString());
            Log.d("RSSB kms",kmslist.getText().toString());
            Log.d("RSSB locatinon",sellerlocation.getSelectedItem().toString());
            Log.d("RSSB pin",pincodelist.getText().toString());
            Log.d("RSSB price",pricelist.getText().toString());
            Log.d("RSSB owner",ownerstatus.getSelectedItem().toString());
            Log.d("RSSB seller",statelist.getSelectedItem().toString());
            Log.d("RSSB Insurance",insurance.getSelectedItem().toString());
            Log.d("RSSB fuel",fuel.getSelectedItem().toString());
            Log.d("RSSB transmission",transmission.getSelectedItem().toString());

        }

    }



}
