package carteckh.carteckh.fragments;

import android.app.Dialog;
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
import android.widget.Spinner;

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

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 19-Jan-16.
 */
public class UsedCarSearch extends Fragment {
    Dialog dialog;
    View view;

    @InjectView(R.id.search)
    Button search;


    @InjectView(R.id.ageoftehcar)
    MultiSelectionSpinner ageofcar;

    @InjectView(R.id.pricerange)
    MultiSelectionSpinner budget;

    @InjectView(R.id.kilometers)
    MultiSelectionSpinner kms;


    @InjectView(R.id.state)
    Spinner state;

    @InjectView(R.id.fueltype)
    MultiSelectionSpinner fueltype;


    @InjectView(R.id.owners)
    MultiSelectionSpinner owners;


    @InjectView(R.id.transmission)
    MultiSelectionSpinner transmission;

    @InjectView(R.id.brands)
    MultiSelectionSpinner brands;
    int year;

    String Petrol[] = {"CNG", "Diesel", "Petrol","Electric", "LPG"};
    String Transmission[] = {"Manual", "Automatic"};


    String Owners[] = {"First Owner", "Second Owner", "Third Owner", "4th & Above"};

    String PriceRange[] = {"Upto 3 Lakh", "3 Lakh to 10 Lakh",
            "10 Lakh to 20 Lakh", "20 Lakh to 30 Lakh",
            "30 Lakh to 50 Lakh",
            "50 Lakh to 1 Crore",
            "1Crore & Above"};


//    String AgeofCar[] = {"2016 - 2015", "2014 - 2013", "2012 - 2011", "2010 - 2009", "2008 - 2007", "2006 & before"};
    String AgeofCar [];


    String KmsDriven[] = {"0 to 50 Thousand", "50 to 1 Lakh", "1 to 2 Lakh", "2 to 4 Lakh", "4 & Above"};

    List<Constants> List_BodyType = new ArrayList<Constants>();
    List<String> str_bodytype = new ArrayList<String>();

    List<Constants> List_Brand = new ArrayList<Constants>();
    List<String> str_brand = new ArrayList<String>();

    ArrayList<Constants> List_State = new ArrayList<Constants>();
    List<String> str_state = new ArrayList<String>();

    ArrayList<Constants> List_Color = new ArrayList<Constants>();
    List<String> str_color = new ArrayList<String>();

    String[] checkString = {"null"};
    ArrayList<Integer> ageofcar1 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initializing the dialog
        dialog = new Dialog(UsedCarSearch.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.usedcar_filter, container, false);

        ButterKnife.inject(this, view);


        fueltype.setItems(Petrol);
        transmission.setItems(Transmission);
        owners.setItems(Owners);
        budget.setItems(PriceRange);

        year = Calendar.getInstance().get(Calendar.YEAR);

        Log.d("fdgdfghd", String.valueOf(year));
        //String AgeofCar[] = {"2016 - 2015", "2014 - 2013", "2012 - 2011", "2010 - 2009", "2008 - 2007", "2006 & before"};
        String AgeofCar[] = {String.valueOf(year)+" - "+String.valueOf(year-1), String.valueOf(year-2)+ " - "+ String.valueOf(year-3), String.valueOf(year-4)+" - "+String.valueOf(year-5), String.valueOf(year-6)+" - "+String.valueOf(year-7), String.valueOf(year-8)+" - "+String.valueOf(year-9), String.valueOf(year-10)+" & before"};

//        year = Calendar.getInstance().get(Calendar.YEAR);
//
//        int i;
//        for (i=year; i> 2004;i-=2) {
//            ageofcar1.add(i);
//
//        }
//           Log.d("dfgvdfhbfg", ageofcar1.toString());
//
//        AgeofCar[i] = ageofcar1.toString();
//        ageofcar.setItems(new String[]{AgeofCar[i]});

        ageofcar.setItems(AgeofCar);
        kms.setItems(KmsDriven);

        budget.setSelection(checkString);
        ageofcar.setSelection(checkString);
        kms.setSelection(checkString);
        transmission.setSelection(checkString);
        fueltype.setSelection(checkString);
        owners.setSelection(checkString);




        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=usedCarBrand", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                JSONObject jsonObject1 = jsonObject.optJSONObject("UsedCarBrandList");
                if (jsonObject1.optString("success").equals("1")) {

                    JSONArray jsonArray = jsonObject1.optJSONArray("brand_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            List_Brand.add(new Constants(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName")));
                            str_brand.add(jsonObject11.optString("getBrandName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    brands.setItems(str_brand);
                    brands.setSelection(checkString);


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "" + "Some problem occured please try again", Style.ALERT).show();

            }
        });

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest1);


        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=usedCarState", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                if (jsonObject.optInt("success") == 1) {
                    JSONArray jsonArray = jsonObject.optJSONArray("stateList");

                    List_State.add(new Constants("0000", "Select State"));
                    str_state.add("Select State");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            List_State.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("state")));
                            str_state.add(jsonObject1.optString("state"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, str_state);
                adapter.setDropDownViewResource(R.layout.spinnertext);
                state.setAdapter(adapter);

                if (Constants.sharedPreferences.contains("state_id")) {
                    for (int i = 0; i < List_State.size(); i++) {
                        if (Constants.sharedPreferences.getString("state_id", "").equals(List_State.get(i).getStr_Brandid().toString())) {
                            state.setSelection(i);
                        }

                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest2);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                String str_price = "";

              //  for (int i = 0; i < budget.getSelectedIndices().size(); i++) {

                    if (str_price.equals("")) {
                        str_price = ""+ budget.getSelectedIndices3();
                    } else {
                        str_price = str_price + "," + budget.getSelectedIndices3();
                    }

                //}
                String price1=str_price.replace("[","");
                String price2=price1.replace("]","");
                String price3=price2.replace(" ","");

                Log.d("dfsdfsdgdgdfg",price3);
                bundle.putString("price_range", price3);


                String str_carage = "";

//                for (int i = 0; i < ageofcar.getSelectedIndices().size(); i++) {
//
//                    if (str_carage.equals("")) {
//                        str_carage = String.valueOf(ageofcar.getSelectedItemsAsString());
//                    } else {
//                        str_carage = str_carage + "," + String.valueOf(ageofcar.getSelectedItemsAsString());
//                    }
//
//                }
                str_carage =  String.valueOf(ageofcar.getSelectedItemsAsString());
                String str_carage1=str_carage.replace(" - ","_");
                String str_carage2=str_carage1.replace(" & before","_1966");

                bundle.putString("car_age", str_carage2);

                Log.d("fdgdgdfgdf",str_carage2);
                String str_kms = "";

//                for (int i = 0; i < kms.getSelectedIndices().size(); i++) {
//
//                    if (str_kms.equals("")) {
//                        str_kms = String.valueOf(kms.getSelectedIndices().get(i));
//                    } else {
//                        str_kms = str_kms + "," + String.valueOf(kms.getSelectedIndices().get(i));
//                    }
//
//                }
                str_kms =  String.valueOf(kms.getSelectedItemsAsString());
                String str_kms2=str_kms.replace("50 to 1 Lakh","50_1");
                String str_kms3=str_kms2.replace("0 to 50 Thousand","0_50");
                String str_kms4=str_kms3.replace("1 to 2 Lakh","1_2");
                String str_kms5=str_kms4.replace("2 to 4 Lakh","2_4");
                String str_kms6=str_kms5.replace("4 & Above","4_200");
                bundle.putString("kms_driven", str_kms6);

                Log.d("dfgdfghfdghfhgf",str_kms6);

                str_kms = "";

                for (int i = 0; i < fueltype.getSelectedIndices().size(); i++) {

                    if (str_kms.equals("")) {
                        str_kms = String.valueOf(fueltype.getSelectedIndices().get(i) + 1);
                    } else {
                        str_kms = str_kms + "," + String.valueOf(fueltype.getSelectedIndices().get(i) + 1);
                    }

                }

                Log.d("gfdgdfgdfgdftrgr",str_kms);
                bundle.putString("fuel_type", str_kms);

                if (state != null && List_State.size()>0 ) {
                    if (state.getSelectedItemPosition() == 0) {
                        bundle.putString("state_id", "");
                    } else {
                        bundle.putString("state_id", List_State.get(state.getSelectedItemPosition()).getStr_Brandid());
                    }
                } else {
                    bundle.putString("state_id", "");
                }


                str_kms = "";

                for (int i = 0; i < owners.getSelectedIndices().size(); i++) {

                    if (str_kms.equals("")) {
                        str_kms = String.valueOf(owners.getSelectedIndices().get(i) + 1);
                    } else {
                        str_kms = str_kms + "," + String.valueOf(owners.getSelectedIndices().get(i) + 1);
                    }

                }


                Log.d("fsdgfdgertgferfsdas",str_kms);
                bundle.putString("owner", str_kms);


                String str_brands = "";

               if (List_Brand.size()>0)
               {
                   for (int i = 0; i < brands.getSelectedIndices().size(); i++) {

                       if (str_brands.equals("")) {
                           str_brands = List_Brand.get(brands.getSelectedIndices().get(i)).getStr_Brandid();
                       } else {
                           str_brands = str_brands + "," + List_Brand.get(brands.getSelectedIndices().get(i)).getStr_Brandid();
                       }

                   }
               }

                bundle.putString("brands", str_brands);
                str_kms = "";

                for (int i = 0; i < transmission.getSelectedIndices().size(); i++) {

                    if (str_kms.equals("")) {
                        str_kms = String.valueOf(transmission.getSelectedIndices().get(i) + 1);
                    } else {
                        str_kms = str_kms + "," + String.valueOf(transmission.getSelectedIndices().get(i) + 1);
                    }

                }
                bundle.putString("transmission", str_kms);
                //bundle.putString("usedforstate", "state");

                Log.d("fgdfgrfsdage",str_kms);

                Fragment fragment = new Used_CarList();
                FragmentManager fragmentManager = getFragmentManager();
                fragment.setArguments(bundle);
                Constants.bol_search = true;

                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();


            }
        });


        return view;
    }
}
