package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 04-Dec-15.
 */
public class UpComingCars_ViewPager extends Fragment {

    @InjectView(R.id.pager)
    ViewPager pager;
    View view;

    JSONArray jsonArray;
    Dialog dialog;
    List<Constants> RoadAssistance = new ArrayList<Constants>();
    //List<Constants> RoadAssistance_text = new ArrayList<Constants>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialog = new Dialog(UpComingCars_ViewPager.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.upcomingviewpagte, container, false);

        ButterKnife.inject(this, view);



        //Log.d("dfgdfh1",getArguments().getString("id"));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=UpcomingCarDetail&carId="+ getArguments().getString("id"), null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                //RoadAssistance.clear();
                try {
                    Log.d("dfgdfh1",getArguments().getString("id"));
                    Log.d("dfgdfh",jsonObject.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }



                    //Toast.makeText(getActivity(), str_json, Toast.LENGTH_SHORT).show();
                    //Log.d("rssb",str_json);
                    //text.setText("" + jsonObject.optString("heading").toString());

                    JSONArray jsonArray = jsonObject.optJSONArray("UpcomingCar");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            JSONObject jsonObject1 = jsonObject11.getJSONObject("PhotoList");

                            JSONArray jsonArray1=jsonObject1.optJSONArray("Photos");
                            for (int j=0;j<jsonArray1.length();j++){

                                JSONObject jsonObject2 = jsonArray1.optJSONObject(j);

                                Log.d("dfgdfhdh",jsonObject2.optString("photo"));
                                //Log.d("dfgdfhdh",jsonObject2.optString(j));

                                RoadAssistance.add(new Constants(jsonObject11.optString("CarName"),jsonObject11.optString("launch_date"),jsonObject11.optString("min_price")+" - ₹ "+jsonObject11.optString("max_price"),jsonObject11.optString("short_desc"), jsonObject2.optString("photo")));
                                pager.setAdapter(new ViewPagerAdapter());

                                CirclePageIndicator indicator = (CirclePageIndicator)view.findViewById(R.id.indicat);
                                indicator.setViewPager(pager);
                                //pager.setCurrentItem(getArguments().getInt("pos"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try
                {
                    Constants.exitdialog(dialog);
                    Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
                }catch (Exception e){e.printStackTrace();}

            }
        });

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        //Log.d("dfgdfh1",getArguments().getString("id"));
//        try {
//            JSONObject jsonObject = new JSONObject(getArguments().getString("str_json"));
//            jsonArray = jsonObject.getJSONArray("UpcomingCar");
//
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                try {
//                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                    RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("car_photo"), jsonObject11.optString("CarName"), jsonObject11.optString("launch_date"), jsonObject11.optString("min_price") + " - ₹ " + jsonObject11.optString("max_price"), jsonObject11.optString("short_desc"),0));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }




        return view;
    }

    public class ViewPagerAdapter extends PagerAdapter {

        LayoutInflater layoutInflater;


        @Override
        public int getCount() {
            return RoadAssistance.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            NetworkImageView networkImageView;
            TextView text_Launchdate, text_description,text_price,name,description;


            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.custom_viewpager, container, false);

            networkImageView = (NetworkImageView) view.findViewById(R.id.networkimg);
            text_Launchdate = (TextView) view.findViewById(R.id.launchdate);
            text_description = (TextView) view.findViewById(R.id.description);
            text_price = (TextView) view.findViewById(R.id.price);
            name = (TextView) view.findViewById(R.id.name);
            description=(TextView)view.findViewById(R.id.description);



            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            name.setTypeface(typeface);
            text_description.setTypeface(typeface);
            text_Launchdate.setTypeface(typeface);
            description.setTypeface(typeface);
            text_price.setTypeface(typeface);;

            name.setText(RoadAssistance.get(position).getId().toString());
            //text_description.setText("Description " + RoadAssistance.get(position).getVersion().toString());
            text_Launchdate.setText("Expected Launch: " + RoadAssistance.get(position).getBrand().toString());
            text_price.setText("Expected Price: ₹ " + RoadAssistance.get(position).getModel().toString());
            if (RoadAssistance.get(position).getVersion().toString()!=null) {
                description.setText("" + RoadAssistance.get(position).getVersion().toString());
            }


            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            String s1=RoadAssistance.get(position).getOofer_from().toString();
            String s2 = s1.replace(" ","%20");
            Log.d("dfgdfghd","https://www.carteckh.com/upload_images/upcoming/"+s2);
            networkImageView.setImageUrl("https://www.carteckh.com/upload_images/upcoming/"+s2, imageLoader);


            (container).addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((ScrollView) object);
        }
    }
}
