package carteckh.carteckh.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by deep on 12/22/2015.
 */

public class UsedCar_Summary extends Fragment {


    private Activity activity;


    TextView year,kms,price,status,fuel,trans,insurance,brand;
    TextView year1,kms1,price1,status1,fuel1,trans1,insurance1,brand1,heading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.car_version_summary, null);

        year=(TextView)view.findViewById(R.id.year);
        kms=(TextView)view.findViewById(R.id.kms);
        price=(TextView)view.findViewById(R.id.price);
        status=(TextView)view.findViewById(R.id.status);
        fuel=(TextView)view.findViewById(R.id.fuel);
        trans=(TextView)view.findViewById(R.id.trans);
        insurance=(TextView)view.findViewById(R.id.insurance);
        brand=(TextView)view.findViewById(R.id.brand);


        year1=(TextView)view.findViewById(R.id.year1);
        kms1=(TextView)view.findViewById(R.id.kms1);
        price1=(TextView)view.findViewById(R.id.price1);
        status1=(TextView)view.findViewById(R.id.status1);
        fuel1=(TextView)view.findViewById(R.id.fuel1);
        trans1=(TextView)view.findViewById(R.id.trans1);
        insurance1=(TextView)view.findViewById(R.id.insurance1);
        brand1=(TextView)view.findViewById(R.id.brand1);

        heading=(TextView)view.findViewById(R.id.heading);

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        year.setTypeface(typeface);
        kms.setTypeface(typeface);
        price.setTypeface(typeface);
        status.setTypeface(typeface);
        fuel.setTypeface(typeface);
        trans.setTypeface(typeface);
        insurance.setTypeface(typeface);
        brand.setTypeface(typeface);

        year1.setTypeface(typeface);
        kms1.setTypeface(typeface);
        price1.setTypeface(typeface);
        status1.setTypeface(typeface);
        fuel1.setTypeface(typeface);
        trans1.setTypeface(typeface);
        insurance1.setTypeface(typeface);
        brand1.setTypeface(typeface);

        heading.setTypeface(typeface);

        heading.setText(Constants.sharedPreferences.getString("brand","")+" "+Constants.sharedPreferences.getString("model","")+" "+Constants.sharedPreferences.getString("version",""));
        //JSONObject jsonObject= UsedCar_Deatail.Summary.getJSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=usedCarDetail&id="+Constants.sharedPreferences.getString("usedcarid",""), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //Constants.exitdialog(dialog);
                JSONObject jsonObject1 = jsonObject.optJSONObject("Summary");

                Log.d("gfdgd",jsonObject1.toString());
                            //Log.d("dgfdgf",jsonObject11.optString("icon"));
                brand.setText(UsedCar_Deatail.MainPartInfo.optString("Heading"));
                year.setText(jsonObject1.optString("Model Year"));
                kms.setText(jsonObject1.optString("Kms Driven")+" Kms");
                price.setText("₹ "+jsonObject1.optString("Expected Selling Price"));
                status.setText(jsonObject1.optString("Owner Status"));
                fuel.setText(jsonObject1.optString("Fuel Type"));
                trans.setText(jsonObject1.optString("Transmission"));
                insurance.setText(jsonObject1.optString("Insurance"));

                }




        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
         //       Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "" + "Some problem occured Please try again", Style.ALERT).show();

            }
        });
       // Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);



        return view;
    }

}
