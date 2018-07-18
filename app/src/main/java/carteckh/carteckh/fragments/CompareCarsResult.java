package carteckh.carteckh.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by deep on 12/5/2015.
 */

public class CompareCarsResult extends Fragment {
    @InjectView(R.id.campare_car_view)
    LinearLayout compare_car_view;
    @InjectView(R.id.h_s_features)
    Button h_s_features;
    @InjectView(R.id.ll_view_potrait)
    LinearLayout ll_view_potrait;
    @InjectView(R.id.ll_view_potrait_stcky)
    LinearLayout ll_view_potrait_stcky;
    @InjectView(R.id.ll_view_land)
    LinearLayout ll_view_land;
    @InjectView(R.id.txt2)
    TextView txt2;
    @InjectView(R.id.txt3)
    TextView txt3;
    @InjectView(R.id.txt4)
    TextView txt4;
    @InjectView(R.id.select_first_image_layout)
    LinearLayout select_first_image_layout;
    @InjectView(R.id.first_carname)
    TextView first_carname;
    @InjectView(R.id.first_carprice)
    TextView first_carprice;
    @InjectView(R.id.first_carimage)
    NetworkImageView first_carimage;
    @InjectView(R.id.second_carname)
    TextView second_carname;
    @InjectView(R.id.second_carprice)
    TextView second_carprice;
    @InjectView(R.id.second_carimage)
    NetworkImageView second_carimage;
    @InjectView(R.id.first_carname1)
    TextView first_carname1;
    @InjectView(R.id.first_carprice1)
    TextView first_carprice1;
    @InjectView(R.id.second_carname2)
    TextView second_carname2;
    @InjectView(R.id.second_carprice2)
    TextView second_carprice2;
    ImageLoader imageLoader;
    private Activity activity;
    private String[] keys;
    private Dialog dialog;
    private String url;
    private boolean isGreaterThan2 = false;
    private ArrayList<View> views = new ArrayList<>();
    private boolean isHideFeatures;
    private TextView colorsHeading;
    private ArrayList<JSONArray> jsonArrayColorOfCars = new ArrayList<>();
    private ArrayList<JSONObject> mainInformation = new ArrayList<>();
    Typeface typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        dialog = new Dialog(activity);

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = getArguments();
        keys = bundle.getStringArray("keys");
        Log.d("fdgdgdfgdfhd", Arrays.deepToString(keys));
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        Constants.flag = true;


        //Checking For car Comparison
        if (keys.length > 2) {
            isGreaterThan2 = true;
            url = Constants.Url + "task=CarCompareResult&car_id=" + keys[0] + "@" + keys[1] + "@" + keys[2];
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //Toast.makeText(getActivity(),"OK TEsted",Toast.LENGTH_SHORT).show();
        } else {
            url = Constants.Url + "task=CarCompareResult&car_id=" + keys[0] + "@" + keys[1];
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comparecar_result, null);
        ButterKnife.inject(this, view);
        Constants.sharedPreferences = getActivity().getSharedPreferences(Constants.Myprefrence, getActivity().MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

        imageLoader = AppController.getInstance().getImageLoader();
        initView();
        sendRequest();
        evevtListeners();
        view.setFocusableInTouchMode(true);

        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    activity.onBackPressed();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    private void initView() {
        if (isGreaterThan2) {
            ll_view_potrait.setVisibility(View.GONE);
            ll_view_land.setVisibility(View.VISIBLE);
            ll_view_land.setTag("sticky");
        } else {
            ll_view_potrait_stcky.setTag("sticky");
            ll_view_land.setVisibility(View.GONE);
            ll_view_potrait.setVisibility(View.VISIBLE);
        }
    }

    private void evevtListeners() {
        h_s_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHideFeatures) {
                    isHideFeatures = false;
                    h_s_features.setText("Hide");
                    manageViews(View.VISIBLE);
                } else {
                    h_s_features.setText("Visible");
                    isHideFeatures = true;
                    manageViews(View.GONE);
                }
            }

            private void manageViews(int action) {
                for (int i = 0; i < views.size(); i++) {
                    views.get(i).setVisibility(action);
                }
            }
        });
    }

    private void sendRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override

            public void onResponse(JSONObject jsonObject) {
                try {

                    //Get the Array from jsonObject and get object from CarCompareResult
                    JSONObject jsonObject1 = jsonObject.getJSONArray("CarCompareResult").getJSONObject(0);

                    Log.d("test_ing", jsonObject1 + "");
                    Iterator<String> stringIterator = jsonObject1.keys();
                    Log.d("test_ing1", stringIterator + "");
//                    Constants.exitdialog(dialog);
                    if (isGreaterThan2)
                        updateUI(jsonObject1.getJSONObject(stringIterator.next()), jsonObject1.getJSONObject(stringIterator.next()), jsonObject1.getJSONObject(stringIterator.next()));
                    else {
                        updateUI(jsonObject1.getJSONObject(stringIterator.next()), jsonObject1.getJSONObject(stringIterator.next()));

                    }

                } catch (JSONException e) {
                    if (dialog!=null)
                    {
                        Constants.exitdialog(dialog);
                    }
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();

            }
        });
        if(getActivity()!= null && !getActivity().isFinishing()){
            try
            {
                //Constants.showdialog(dialog);
            }catch (Exception e){e.printStackTrace();}
        }
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void updateUI(JSONObject car1, JSONObject car2, JSONObject car3) {
        boolean isHeadingAdded, addheadingView;
        LinearLayout subRow;
        try {
            Iterator<String> stringIteratorCar1 = car1.keys();
            Iterator<String> stringIteratorCar2 = car2.keys();
            Iterator<String> stringIteratorCar3 = car3.keys();

            while (stringIteratorCar1.hasNext()) {
                isHeadingAdded = false;
                addheadingView = true;
                final String carKey1 = stringIteratorCar1.next();
                final String carKey2 = stringIteratorCar2.next();
                final String carKey3 = stringIteratorCar3.next();
                final TextView HEADING = new TextView(activity);
                HEADING.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                HEADING.setGravity(Gravity.CENTER);
                HEADING.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                HEADING.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                HEADING.setPadding(2, 15, 2, 15);
                String myString1 = carKey1.trim().replaceAll("_", " ");
                HEADING.setText(myString1);
                Log.d("dfgdfgdfghdf",myString1);
                if (carKey1.equals("Available_Colours")) {
                    colorsHeading = HEADING;
                    jsonArrayColorOfCars.add(car1.getJSONArray(carKey1));
                    jsonArrayColorOfCars.add(car2.getJSONArray(carKey2));
                    jsonArrayColorOfCars.add(car3.getJSONArray(carKey3));

                } else if (carKey1.equals("MainInformation")) {
                    mainInformation.add(car1.getJSONObject(carKey1));
                    mainInformation.add(car2.getJSONObject(carKey2));
                    mainInformation.add(car3.getJSONObject(carKey3));
                } else {
                    final JSONObject stringIteratorSubCar1Attrs = car1.getJSONObject(carKey1);
                    final JSONObject stringIteratorSubCar2Attrs = car2.getJSONObject(carKey2);
                    final JSONObject stringIteratorSubCar3Attrs = car3.getJSONObject(carKey3);

                    Iterator<String> stringIteratorSubCar1 = stringIteratorSubCar1Attrs.keys();
                    while (stringIteratorSubCar1.hasNext()) {
                        subRow = null;
                        final String subKey = stringIteratorSubCar1.next();
                        final String car1Attr = stringIteratorSubCar1Attrs.getString(subKey);
                        final String car2Attr = stringIteratorSubCar2Attrs.getString(subKey);
                        final String car3Attr = stringIteratorSubCar3Attrs.getString(subKey);

                        //if (car1Attr != null && car1Attr.length() > 0 && !car1Attr.equalsIgnoreCase("null") && car2Attr != null && car2Attr.length() > 0 && !car2Attr.equalsIgnoreCase("null") && car3Attr != null && car3Attr.length() > 0 && !car3Attr.equalsIgnoreCase("null")) {
                        if (car1Attr != null && car1Attr.length() > 0  &&  car2Attr.length() > 0  &&  car3Attr.length() > 0 ) {
                            LinearLayout ROW = new LinearLayout(activity);
                            ROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ROW.setGravity(Gravity.CENTER);
                            ROW.setOrientation(LinearLayout.VERTICAL);
                            if (car1Attr.equals("https://www.carteckh.com/images/check-img.png")|| car1Attr.equals("https://www.carteckh.com/images/n-a-img.png") ||
                                    car2Attr.equals("https://www.carteckh.com/images/check-img.png")|| car2Attr.equals("https://www.carteckh.com/images/n-a-img.png") ||
                                    car3Attr.equals("https://www.carteckh.com/images/check-img.png")|| car3Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {
                                String myString = subKey.trim().replaceAll("_", " ");
                                subRow = addImageView(myString, car1Attr, car2Attr, car3Attr);
                            } else {
                                subRow = addTextView(subKey, car1Attr, car2Attr, car3Attr);
                            }

                            View view = new View(activity);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
//                            layoutParams.setMargins(20, 0, 20, 0);
                            view.setLayoutParams(layoutParams);
                            view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                            if (!isHeadingAdded) {
                                compare_car_view.addView(HEADING);
                                isHeadingAdded = true;
                            } else
                                ROW.addView(view);

                            ROW.addView(subRow);
                            compare_car_view.addView(ROW);


                            if (car1Attr.equalsIgnoreCase(car2Attr))
                                views.add(ROW);
                            else
                                addheadingView = false;
                        }

                    }


                    if (addheadingView)

                        views.add(HEADING);
                }
            }


            addingColorsView();
            if (isGreaterThan2)
                addingStickyView();

            HeadingCarDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(activity!= null && !activity.isFinishing()){
            try
            {
                Constants.exitdialog(dialog);
            }catch (Exception e){e.printStackTrace();}
        }

    }

    private void HeadingCarDetails() throws JSONException {


        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        first_carimage.setImageUrl(mainInformation.get(0).getString("Photo"), imageLoader);
        first_carname.setText("" + mainInformation.get(0).getString("Brand") + " " + mainInformation.get(0).getString("Model"));
        first_carprice.setText("" + mainInformation.get(0).getString("Price"));

        ImageLoader imageLoader1 = AppController.getInstance().getImageLoader();
        second_carimage.setImageUrl(mainInformation.get(1).getString("Photo"), imageLoader1);
        second_carname.setText("" + mainInformation.get(1).getString("Brand") + " " + mainInformation.get(1).getString("Model"));
        second_carprice.setText("" + mainInformation.get(1).getString("Price"));


        first_carname1.setText("" + mainInformation.get(0).getString("Brand") + " " + mainInformation.get(0).getString("Model"));
        first_carprice1.setText("" + mainInformation.get(0).getString("Price"));

        second_carname2.setText("" + mainInformation.get(1).getString("Brand") + " " + mainInformation.get(1).getString("Model"));
        second_carprice2.setText("" + mainInformation.get(1).getString("Price"));

    }

    private void addingStickyView() {
        for (int i = 0; i < mainInformation.size(); i++) {
            JSONObject jsonObjectMainInformation = mainInformation.get(i);
            try {
                String name = jsonObjectMainInformation.getString("Brand") + " " + jsonObjectMainInformation.getString("Model") + " " + jsonObjectMainInformation.getString("Version");
                if (i == 0)
                    txt2.setText(name);
                else if (i == 1)
                    txt3.setText(name);
                else
                    txt4.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUI(JSONObject car1, JSONObject car2) {
        boolean isHeadingAdded, addheadingView;
        LinearLayout subRow;
        try {
            Iterator<String> stringIteratorCar1 = car1.keys();
            Iterator<String> stringIteratorCar2 = car2.keys();

            while (stringIteratorCar1.hasNext()) {
                isHeadingAdded = false;
                addheadingView = true;
                final String carKey1 = stringIteratorCar1.next();
                final String carKey2 = stringIteratorCar2.next();
                final TextView HEADING = new TextView(activity);
                HEADING.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                HEADING.setGravity(Gravity.CENTER);
                HEADING.setBackgroundColor(getResources().getColor(R.color.myColorRed));
                HEADING.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                HEADING.setPadding(2, 15, 2, 15);
                HEADING.setTypeface(typeface);
                HEADING.setTextColor(Color.WHITE);
                String myString1 = carKey1.trim().replaceAll("_", " ");
                HEADING.setText(myString1);
                //HEADING.setText(carKey1);

                if (carKey1.equals("Available_Colours")) {
                    colorsHeading = HEADING;
                    jsonArrayColorOfCars.add(car1.getJSONArray(carKey1));
                    jsonArrayColorOfCars.add(car2.getJSONArray(carKey2));
                } else if (carKey1.equals("MainInformation")) {
                    mainInformation.add(car1.getJSONObject(carKey1));
                    mainInformation.add(car2.getJSONObject(carKey2));
                } else {
                    final JSONObject stringIteratorSubCar1Attrs = car1.getJSONObject(carKey1);
                    final JSONObject stringIteratorSubCar2Attrs = car2.getJSONObject(carKey2);
                    Iterator<String> stringIteratorSubCar1 = stringIteratorSubCar1Attrs.keys();
                    while (stringIteratorSubCar1.hasNext()) {
                        subRow = null;
                        final String subKey = stringIteratorSubCar1.next();
                        final String car1Attr = stringIteratorSubCar1Attrs.getString(subKey);
                        final String car2Attr = stringIteratorSubCar2Attrs.getString(subKey);
                        //if (car1Attr != null && car1Attr.length() > 0 && !car1Attr.equalsIgnoreCase("null") && car2Attr != null && car2Attr.length() > 0 && !car2Attr.equalsIgnoreCase("null")) {
                        if (car1Attr != null && car1Attr.length() > 0  && car2Attr.length() > 0 ) {

                            LinearLayout ROW = new LinearLayout(activity);
                            ROW.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                            ROW.setGravity(Gravity.CENTER);
                            ROW.setOrientation(LinearLayout.VERTICAL);

                            if (car1Attr.equals("https://www.carteckh.com/images/check-img.png") || car1Attr.equals("https://www.carteckh.com/images/n-a-img.png")||
                                    car2Attr.equals("https://www.carteckh.com/images/check-img.png") || car2Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {
                                String myString = subKey.trim().replaceAll("_", " ");
                                subRow = addImageView2(myString, car1Attr, car2Attr, null);
                            }

                            else {
                                subRow = addTextView(subKey, car1Attr, car2Attr, null);
                            }

                            View view = new View(activity);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
//                            layoutParams.setMargins(20, 0, 20, 0);
                            view.setLayoutParams(layoutParams);
                            view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                            if (!isHeadingAdded) {
                                compare_car_view.addView(HEADING);
                                isHeadingAdded = true;
                            } else
                                ROW.addView(view);

                            ROW.addView(subRow);
                            compare_car_view.addView(ROW);
                            if (car1Attr.equalsIgnoreCase(car2Attr))
                                views.add(ROW);
                            else
                                addheadingView = false;
                        }


                    }
                    if (addheadingView)
                        views.add(HEADING);
                }
            }
            addingColorsView();

            HeadingCarDetails();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(activity!= null && !activity.isFinishing()){
            try
            {
                Constants.exitdialog(dialog);
            }catch (Exception e){e.printStackTrace();}
        }
    }


    private void addingColorsView() {
        try {
            compare_car_view.addView(colorsHeading);
            Log.d("dfgdfgdfh",colorsHeading.toString());
            for (int i = 0; i < jsonArrayColorOfCars.size(); i++) {
                JSONArray jsonArray = jsonArrayColorOfCars.get(i);
                JSONObject jsonObjectMainInformation = mainInformation.get(i);
                final TextView car_name = new TextView(activity);
                car_name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                car_name.setGravity(Gravity.CENTER);
                car_name.setSingleLine(true);
                car_name.setBackgroundColor(getResources().getColor(android.R.color.white));
//                car_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                car_name.setPadding(2, 15, 2, 15);
                car_name.setText(jsonObjectMainInformation.getString("Brand") + ", " + jsonObjectMainInformation.getString("Model") + ", " + jsonObjectMainInformation.getString("Version"));
                HorizontalScrollView horizontalScrollView = new HorizontalScrollView(activity);
                horizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                final LinearLayout linearLayout = new LinearLayout(activity);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    final String color = "#" + jsonObject.getString("colourcode");
                    View view = new View(activity);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
                    layoutParams.setMargins(10, 20, 10, 20);
                    view.setLayoutParams(layoutParams);
                    view.setBackgroundColor(Color.parseColor(color));
                    linearLayout.addView(view);
                }
                compare_car_view.addView(car_name);
                horizontalScrollView.addView(linearLayout);
                compare_car_view.addView(horizontalScrollView);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private LinearLayout addImageView(String subKey, String car1Attr, String car2Attr, String car3Attr) {
        final LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(2, 15, 2, 15);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(3);


        //Main Effective...
        TextView textView = new TextView(activity);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#73CD53"));
        textView.setText(subKey);
        linearLayout.addView(textView);

        if (car1Attr.equals("https://www.carteckh.com/images/check-img.png")|| car1Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {
            NetworkImageView imageView1 = new NetworkImageView(activity);
            imageView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            imageView1.setImageUrl(car1Attr, imageLoader);
            linearLayout.addView(imageView1);
        } else {
            TextView textView1 = new TextView(activity);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(40,LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            textView1.setGravity(Gravity.CENTER);
            textView1.setText(" " + car1Attr);
            textView1.setTextColor(Color.parseColor("#B07219"));
            linearLayout.addView(textView1);

        }

        if (car2Attr.equals("https://www.carteckh.com/images/check-img.png")|| car2Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {

            NetworkImageView imageView2 = new NetworkImageView(activity);
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            imageView2.setImageUrl(car2Attr, imageLoader);
            linearLayout.addView(imageView2);
        } else {
            TextView textView2 = new TextView(activity);
            textView2.setLayoutParams(new LinearLayout.LayoutParams(40,LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//            textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);
            textView2.setGravity(Gravity.CENTER);
            textView2.setText(" " + car2Attr);
            textView2.setTextColor(Color.parseColor("#B07219"));
            linearLayout.addView(textView2);
        }
        if (car3Attr != null) {
            linearLayout.setWeightSum(4);


            if (car3Attr.equals("https://www.carteckh.com/images/check-img.png")|| car3Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {
                NetworkImageView imageView3 = new NetworkImageView(activity);
                imageView3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                imageView3.setImageUrl(car3Attr, imageLoader);
                linearLayout.addView(imageView3);
            } else {
                TextView textView3 = new TextView(activity);
                textView3.setLayoutParams(new LinearLayout.LayoutParams(40,LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                textView3.setTypeface(textView3.getTypeface(), Typeface.BOLD);
                textView3.setGravity(Gravity.CENTER);
                textView3.setText(" " + car3Attr);
                textView3.setTextColor(Color.parseColor("#B07219"));
                linearLayout.addView(textView3);

            }

//
//
//           linearLayout.addView(textView);


        } else {
            linearLayout.setWeightSum(3);

            linearLayout.addView(textView);

        }


        return linearLayout;
    }



    private LinearLayout addImageView2(String subKey, String car1Attr, String car2Attr, String car3Attr) {
        final LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(2, 15, 2, 15);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setWeightSum(3);


        //Main Effective...

        Log.d("fgdgdfghd1",subKey.toString());
//        String string=subKey.toString();
        Constants.editor.putString("string",subKey.toString());
        Constants.editor.commit();

        if (car1Attr.equals("https://www.carteckh.com/images/check-img.png")|| car1Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {
            NetworkImageView imageView1 = new NetworkImageView(activity);
            imageView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            imageView1.setImageUrl(car1Attr, imageLoader);
            linearLayout.addView(imageView1);
        } else {
            TextView textView1 = new TextView(activity);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(40,LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//            textView1.setTypeface(textView1.getTypeface(), Typeface.BOLD);
            textView1.setText(" " + car1Attr);
            textView1.setGravity(Gravity.CENTER);
            textView1.setTextColor(Color.parseColor("#B07219"));
            linearLayout.addView(textView1);

        }

//
        TextView textView = new TextView(activity);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#73CD53"));
        textView.setText(subKey);
        linearLayout.addView(textView);

        if (car2Attr.equals("https://www.carteckh.com/images/check-img.png")|| car2Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {

            NetworkImageView imageView2 = new NetworkImageView(activity);
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            imageView2.setImageUrl(car2Attr, imageLoader);
            linearLayout.addView(imageView2);
        } else {
            TextView textView2 = new TextView(activity);
            textView2.setLayoutParams(new LinearLayout.LayoutParams(40,LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            //textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);
            textView2.setText(" " + car2Attr);
            textView2.setGravity(Gravity.CENTER);
            textView2.setTextColor(Color.parseColor("#B07219"));
            linearLayout.addView(textView2);
        }






        if (car3Attr != null) {
            linearLayout.setWeightSum(4);

            if (car3Attr.equals("https://www.carteckh.com/images/check-img.png")|| car3Attr.equals("https://www.carteckh.com/images/n-a-img.png")) {
                NetworkImageView imageView3 = new NetworkImageView(activity);
                imageView3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                imageView3.setImageUrl(car3Attr, imageLoader);
                linearLayout.addView(imageView3);
            } else {
                TextView textView3 = new TextView(activity);
                textView3.setLayoutParams(new LinearLayout.LayoutParams(40,LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                textView3.setTypeface(textView3.getTypeface(), Typeface.BOLD);
                textView3.setText(" " + car3Attr);
                textView3.setGravity(Gravity.CENTER);
                textView3.setTextColor(Color.parseColor("#B07219"));
                linearLayout.addView(textView3);

            }


//           linearLayout.addView(textView);


            Log.d("gfdgdfgd",Constants.sharedPreferences.getString("string",""));;

            Log.d("fgdgdfghd2",car1Attr.toString());
            Log.d("fgdgdfghd3",car2Attr.toString());
            Log.d("fgdgdfghd4",car3Attr.toString());



        } else {
            linearLayout.setWeightSum(3);



        }


        return linearLayout;
    }

    private LinearLayout addTextView(String subKey, String car1Attr, String car2Attr, String car3Attr) {
        final LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(2, 15, 2, 15);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView1 = new TextView(activity);
        textView1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//        textView1.setTypeface(textView1.getTypeface(), Typeface.BOLD);
        textView1.setGravity(Gravity.CENTER);
        textView1.setText(car1Attr);
        textView1.setTypeface(typeface);

        TextView textView2 = new TextView(activity);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//        textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD);
        textView2.setGravity(Gravity.CENTER);
        textView2.setTextColor(Color.parseColor("#73CD53"));
        String myString = subKey.trim().replaceAll("_", " ");
        Log.d("fgvdgdfhg",myString);
        textView2.setText(myString);


        textView2.setTypeface(typeface);

        TextView textView3 = new TextView(activity);
        textView3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//        textView3.setTypeface(textView3.getTypeface(), Typeface.BOLD);
        textView3.setGravity(Gravity.CENTER);
        textView3.setText(car2Attr);
        textView3.setTypeface(typeface);



        if (car3Attr != null) {
            linearLayout.setWeightSum(4);
            TextView textView4 = new TextView(activity);
            textView4.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//            textView4.setTypeface(textView4.getTypeface(), Typeface.BOLD);
            textView4.setGravity(Gravity.CENTER);
            textView4.setText(car3Attr);
            textView4.setTypeface(typeface);
            if (car1Attr.equals(car2Attr) && car1Attr.equals(car3Attr)) {
                textView1.setTextColor(Color.parseColor("#B07219"));
                textView3.setTextColor(Color.parseColor("#B07219"));
                textView4.setTextColor(Color.parseColor("#B07219"));
            } else {
                textView1.setTextColor(Color.parseColor("#B07219"));
                textView3.setTextColor(Color.parseColor("#B07219"));
                textView4.setTextColor(Color.parseColor("#B07219"));
            }


            linearLayout.addView(textView2);
            linearLayout.addView(textView1);
            linearLayout.addView(textView3);
            linearLayout.addView(textView4);

        } else {
            linearLayout.setWeightSum(3);
            if (car1Attr.equals(car2Attr)) {
                textView1.setTextColor(Color.parseColor("#B07219"));
                textView3.setTextColor(Color.parseColor("#B07219"));
            } else {
                textView1.setTextColor(Color.parseColor("#B07219"));
                textView3.setTextColor(Color.parseColor("#B07219"));
            }
            linearLayout.addView(textView1);
            linearLayout.addView(textView2);
            linearLayout.addView(textView3);
        }
        return linearLayout;

    }
}