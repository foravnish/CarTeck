package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 15-Dec-15.
 */
public class UserProfile extends Fragment {


    @InjectView(R.id.parentview)
    LinearLayout parentView;

    View view;
    Dialog dialog;
    JsonObjectRequest jsonObjectRequest;

    @InjectView(R.id.et_firstname)
    TextView et_firstname;
    @InjectView(R.id.et_lastname)
    TextView et_lastname;
    @InjectView(R.id.et_email)
    TextView et_email;
    @InjectView(R.id.et_mobilenumber)
    TextView et_mobilenumber;
    @InjectView(R.id.et_location)
    TextView et_location;
    @InjectView(R.id.et_pinnumber)
    TextView et_pinnumber;
    @InjectView(R.id.et_address)
    TextView et_address;
//public static   TextView et_firstname,et_lastname,et_email,et_mobilenumber,et_location,et_pinnumber,et_address;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        dialog = new Dialog(UserProfile.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.getWindow().setLayout(100,40);

        Constants.sharedPreferences=getActivity().getSharedPreferences(Constants.Myprefrence, Context.MODE_PRIVATE);
        Constants.editor=Constants.sharedPreferences.edit();

        view = inflater.inflate(R.layout.dynamicview, container, false);
         et_firstname=(TextView)view.findViewById(R.id.et_firstname);
         et_lastname=(TextView)view.findViewById(R.id.et_lastname);
         et_email=(TextView)view.findViewById(R.id.et_email);
         et_mobilenumber=(TextView)view.findViewById(R.id.et_mobilenumber);
         et_location=(TextView)view.findViewById(R.id.et_location);
         et_pinnumber=(TextView)view.findViewById(R.id.et_pinnumber);
         et_address=(TextView)view.findViewById(R.id.et_address);



        //ButterKnife.inject(this, view);


        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        et_firstname.setTypeface(typeface);
        et_pinnumber.setTypeface(typeface);
        et_address.setTypeface(typeface);
        et_mobilenumber.setTypeface(typeface);
        et_lastname.setTypeface(typeface);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=dealerSettings&userid=" + Constants.sharedPreferences.getString("user_id", ""), null, new Response.Listener<JSONObject>() {
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
                            et_location.setText(jsonObject2.getString("State"));
                            //et_location.setText(Constants.sharedPreferences.getString("state_name",""));
                            et_pinnumber.setText(jsonObject2.getString("Pincode"));
                            et_address.setText(jsonObject2.getString("Address"));
                            Log.d("rssb",jsonObject+"");

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                    UpdateUi(jsonObject1.getJSONObject(iterator.next()), jsonObject1.getJSONObject(iterator.next()));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "Sorry please try again later.", Style.ALERT).show();
            }
        });

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        return view;

    }



    //    private void UpdateUi(JSONObject Car1, JSONObject Car2) throws JSONException {
//
//
//        Iterator<String> Car1Iterator = Car1.keys();
//        Iterator<String> Car2Iterator = Car2.keys();
//
//        while (Car1Iterator.hasNext()) {
//
//            String mainkey = Car1Iterator.next();
//
//            if (!mainkey.equals("Available_Colours")) {
//
//                TextView MainHeading = new TextView(getActivity());
//                MainHeading.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                MainHeading.setTextColor(Color.RED);
//
//                MainHeading.setGravity(Gravity.CENTER);
//                MainHeading.setPadding(10, 10, 10, 10);
//                MainHeading.setBackgroundColor(Color.rgb(Color.CYAN, Color.DKGRAY, Color.GRAY));
//
//                MainHeading.setText(mainkey);
//                parentView.addView(MainHeading);
//
//
//                JSONObject Car1JSONObject = Car1.getJSONObject(mainkey);
//                JSONObject Car2JSONObject = Car2.getJSONObject(mainkey);
//
//                Iterator<String> CAR1ITERATOR = Car1JSONObject.keys();
//                Iterator<String> CAR2ITERATOR = Car2JSONObject.keys();
//
//                while (CAR1ITERATOR.hasNext()) {
//
//                    String key = CAR1ITERATOR.next();
//
//
//                    LinearLayout linearLayout = new LinearLayout(getActivity());
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    linearLayout.setLayoutParams(layoutParams);
//                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//
//                    TextView car1Textveiw = new TextView(getActivity());
//                    car1Textveiw.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
//                    car1Textveiw.setSingleLine(true);
//                    car1Textveiw.setText(Car1JSONObject.getString(key));
//
//                    TextView Heading = new TextView(getActivity());
//                    Heading.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
//                    Heading.setSingleLine(true);
//                    Heading.setText(key);
//
//
//                    TextView car2Textveiw = new TextView(getActivity());
//                    car2Textveiw.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
//                    car2Textveiw.setSingleLine(true);
//                    car2Textveiw.setText(Car2JSONObject.getString(key));
//
//
//                    linearLayout.addView(car1Textveiw);
//                    linearLayout.addView(Heading);
//                    linearLayout.addView(car2Textveiw);
//
//                    parentView.addView(linearLayout);
//                }
//            } else {
//            }
//
//        }
//
//
//    }
}
