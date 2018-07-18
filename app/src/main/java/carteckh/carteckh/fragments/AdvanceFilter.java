package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.guna.libmultispinner.MultiSelectionSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 12-Jan-16.
 */
public class AdvanceFilter extends Fragment {

    View view;
    @InjectView(R.id.fueltype)
    MultiSelectionSpinner fueltype;

    @InjectView(R.id.transmission)
    MultiSelectionSpinner transmission;

    @InjectView(R.id.diplacement)
    MultiSelectionSpinner diplacement;

    @InjectView(R.id.brands)
    MultiSelectionSpinner brands;

    @InjectView(R.id.bodytype)
    MultiSelectionSpinner bodytype;


//    @InjectView(R.id.pricerange)
//    public static MultiSelectionSpinner pricerange;

    public static MultiSelectionSpinner pricerange;
    @InjectView(R.id.seats)
    MultiSelectionSpinner seats;


    @InjectView(R.id.search)
    Button search;

    @InjectView(R.id.reset)
    Button reset;


    int left = 1, right = 100, left1 = 2, right1 = 7;


    String Petrol[] = {"CNG", "Diesel", "Petrol", "Electric", "LPG"};

    String Transmission[] = {"Manual", "Automatic"};


    String Dsiplacement[] = {"Upto 1000 cc", "1001 cc to 2000 cc", "2001 cc to 2500 cc", "2501 cc to 3000 cc", "3001 cc to 3500 cc", "Above 3500 cc"};


    String PriceRange[] = {
            "Upto 5 Lakh",
            "5 Lakh to 10 Lakh",
            "10 Lakh to 20 Lakh",
            "20 Lakh to 30 Lakh",
            "30 Lakh to 50 Lakh",
            "50 Lakh to 1 Crore",
            "Above 1 Crore"};
    String Seats[] = {"2 Seater", "3 Seater",
            "4 Seater", "5 Seater", "6 Seater", "7 Seater",
            "7 + Seater"};

    List<Constants> List_BodyType = new ArrayList<Constants>();
    List<String> str_bodytype = new ArrayList<String>();

    List<Constants> List_Brand = new ArrayList<Constants>();
    List<String> str_brand = new ArrayList<String>();


    Dialog dialog;

    public static String[] checkString = {"null"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing the dialog
        dialog = new Dialog(AdvanceFilter.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.advancefilter, container, false);
        ButterKnife.inject(this, view);

        Constants.sharedPreferences_advance=getActivity().getSharedPreferences(Constants.Myprefrence_Advance, Context.MODE_PRIVATE);
        Constants.editor_advance=Constants.sharedPreferences_advance.edit();

        pricerange=(MultiSelectionSpinner)view.findViewById(R.id.pricerange);
        pricerange.setItems(PriceRange);

        seats.setItems(Seats);
        diplacement.setItems(Dsiplacement);

        fueltype.setItems(Petrol);

        transmission.setItems(Transmission);

//        int[] aa={4,6};
        //checkString= new String[]{"50 Lakh to 1 Crore"};
        String [] price={Constants.sharedPreferences_advance.getString("price","")};
        String [] fuel={Constants.sharedPreferences_advance.getString("fuel","")};
        String [] seates={Constants.sharedPreferences_advance.getString("seate","")};
        String [] trans={Constants.sharedPreferences_advance.getString("trans","")};
        String [] disp={Constants.sharedPreferences_advance.getString("disp","")};
        final String [] brand={Constants.sharedPreferences_advance.getString("brand","")};
        final String [] body={Constants.sharedPreferences_advance.getString("body","")};







        pricerange.setSelection(price);
        fueltype.setSelection(fuel);
        seats.setSelection(seates);
        transmission.setSelection(trans);
        diplacement.setSelection(disp);

        Log.d("fgdgertgergtde", Arrays.deepToString(price));
//        bodytype.setSelection(checkString);




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=getBodyType", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Constants.exitdialog(dialog);
                    if (jsonObject.getString("success").equals("1")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Listing");
//                        str_bodytype.add("Select Body Type");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            List_BodyType.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("bodyType")));
                            str_bodytype.add(jsonObject1.optString("bodyType"));
                        }
                        bodytype.setItems(str_bodytype);
                        bodytype.setSelection(body);
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


        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=roadsideAssistance", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                JSONObject jsonObject1 = jsonObject.optJSONObject("RoadSideAssistance");
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
                    brands.setSelection(brand);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();

            }
        });

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest1);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.editor_advance.clear();
                Constants.editor_advance.commit();


                pricerange.setSelection(checkString);
                fueltype.setSelection(checkString);
                seats.setSelection(checkString);
                transmission.setSelection(checkString);
                diplacement.setSelection(checkString);
                brands.setSelection(checkString);
                bodytype.setSelection(checkString);

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
//                bundle.putString("minamount",pricerange.getSelectedItemsAsString());

                String price = "";
               // for (int i = 0; i < pricerange.getSelectedIndices().size(); i++) {

                    if (price.equals("")) {

                            price =  ""+ pricerange.getSelectedIndices3();
                        
                            //Log.d("fvdgdfgdf", String.valueOf(j0));

                    } else {

                            price = price + "," + pricerange.getSelectedIndices3();
                           //Log.d("fvdgdfgdf", String.valueOf(j));

                    }

                Log.d("dfdfdgdg1", String.valueOf(pricerange.getSelectedIndices()));



             //  Toast.makeText(getActivity(),pricerange.getSelectedItemsAsString() , Toast.LENGTH_SHORT).show();
                String price1=price.replace("[","");
                String price2=price1.replace("]","");
                String price3=price2.replace(" ","");
               // }

                Log.d("fdgdgvdfgdf",price3);
//               if (price.equals("Upto 5 Lakh")){
//                   price="1";
//               }
//                else if (pricerange.getSelectedItemsAsString().equals("5 Lakh to 10 Lakh")){
//                   bundle.putString("minamount","2");
//               }
//               else if (pricerange.getSelectedItemsAsString().equals("10 Lakh to 20 Lakh")){
//                   bundle.putString("minamount","3");
//               }
//               else if (pricerange.getSelectedItemsAsString().equals("20 Lakh to 30 Lakh")){
//                   bundle.putString("minamount","4");
//               }
//               else if (pricerange.getSelectedItemsAsString().equals("30 Lakh to 50 Lakh")){
//                   bundle.putString("minamount","5");
//               }
//               else if (pricerange.getSelectedItemsAsString().equals("50 Lakh to 1 Crore")){
//                   bundle.putString("minamount","6");
//               }
//               else if (pricerange.getSelectedItemsAsString().equals("Above 1 Crore")){
//                   bundle.putString("minamount","7");
//               }

                bundle.putString("minamount", price3);
//                String[]pri={pricerange.getSelectedItemsAsString()};
//                bundle.putStringArray("pricerange",pri);


                String seates = "";
                // for (int i = 0; i < pricerange.getSelectedIndices().size(); i++) {

                if (seates.equals("")) {

                    seates =  ""+ seats.getSelectedIndices2();
                    //Log.d("fvdgdfgdf", String.valueOf(j0));

                } else {

                    seates = seates + "," + seats.getSelectedIndices2();
                    //Log.d("fvdgdfgdf", String.valueOf(j));

                }

                String seates1=seates.replace("[","");
                String seates2=seates1.replace("]","");
                String seates3=seates2.replace(" ","");
                // }

                bundle.putString("seats_type", seates3);

               // bundle.putInt("maxamount", right * 100000);
                String dis=diplacement.getSelectedItemsAsString();
                String dis1=dis.replace(" cc to ","_");
                String dis2=dis1.replace(" cc","");
                String dis3=dis2.replace("Upto ","0_");
                String dis4=dis3.replace(" Above 3500","3501_15000");

                bundle.putString("engine_displacement",dis4);

                bundle.putString("fuel_type", String.valueOf(fueltype.getSelectedItemsAsString()));
//                bundle.putInt("min-seat", left1);
//                bundle.putInt("max-seat", right1);
                bundle.putString("transmission_type", String.valueOf(transmission.getSelectedItemsAsString()));

                String brand_id = "";



                for (int i = 0; i < brands.getSelectedIndices().size(); i++) {

                    if (brand_id.equals("")) {
                        brand_id = List_Brand.get(brands.getSelectedIndices().get(i)).getStr_Brandid();
                    } else {
                        brand_id = brand_id + "," + List_Brand.get(brands.getSelectedIndices().get(i)).getStr_Brandid();
                    }

                }


                bundle.putString("brands", brand_id);

                String str_bodytype = "";

                for (int i = 0; i < bodytype.getSelectedIndices().size(); i++) {

                    if (str_bodytype.equals("")) {
                        str_bodytype = List_BodyType.get(bodytype.getSelectedIndices().get(i)).getStr_Brandid();
                    } else {
                        str_bodytype = str_bodytype + "," + List_BodyType.get(bodytype.getSelectedIndices().get(i)).getStr_Brandid();
                    }

                }


                bundle.putString("body_type", str_bodytype);



                Constants.editor_advance.putString("price",pricerange.getSelectedItemsAsString());
                Constants.editor_advance.putString("fuel",fueltype.getSelectedItemsAsString());
                Constants.editor_advance.putString("seate",seats.getSelectedItemsAsString());
                Constants.editor_advance.putString("trans",transmission.getSelectedItemsAsString());
                Constants.editor_advance.putString("disp",diplacement.getSelectedItemsAsString());
                Constants.editor_advance.putString("brand",brands.getSelectedItemsAsString());
                Constants.editor_advance.putString("body",bodytype.getSelectedItemsAsString());

                Constants.editor_advance.commit();



                Fragment fragment = new AdvancedCarSearch2();
                FragmentManager fragmentManager = getFragmentManager();
                fragment.setArguments(bundle);
                Constants.bol_search = true;

                Constants.editor.putString("amount1",price3);
                Constants.editor.putString("seats_type1",seates3);
                Constants.editor.putString("engine_displacement1",dis4);
                Constants.editor.putString("fuel_type1",String.valueOf(fueltype.getSelectedItemsAsString()));
                Constants.editor.putString("transmission_type1",String.valueOf(transmission.getSelectedItemsAsString()));
                Constants.editor.putString("brands1",brand_id);
                Constants.editor.putString("body_type1",str_bodytype);
                Constants.editor.commit();

                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();



            }
        });


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment fragment = new AdvancedCarSearch();

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("test").commit();
                    return true;
                }
                return false;
            }
        } );


        return view;
    }


}
