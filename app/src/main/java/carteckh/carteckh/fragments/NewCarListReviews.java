package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 05-Dec-15.
 */
public class NewCarListReviews extends Fragment {

    @InjectView(R.id.pager)
    ViewPager pager;
    View view;

    JSONArray jsonArray;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        dialog = new Dialog(NewCarListReviews.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.upcomingviewpagte, container, false);

        ButterKnife.inject(this, view);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://www.carteckh.com/appdata/jsondata.php?task=ReviewList&review_brand=" + getArguments().getString("review_brand") + "review_model=" + getArguments().getString("review_model"), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                if (jsonObject.optString("success").equals("1")) {
                    JSONArray jsonArray = jsonObject.optJSONArray("ReviewList");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            RoadAssistance.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("question"), jsonObject1.optString("name"), jsonObject1.optString("post_date"), jsonObject1.optString("review"), jsonObject1.optString("version"), jsonObject1.optString("Familiarity_with_the_car"), jsonObject1.optString("ExteriorStyle"), jsonObject1.optString("ComfortSpace"), jsonObject1.optString("Performance"), jsonObject1.optString("FuelEconomy"), jsonObject1.optString("Features"), jsonObject1.optString("ExpertReviews"), jsonObject1.optString("overview")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    pager.setAdapter(new ViewPagerAdapter());


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


            TextView tv_title, tv_name;
            RatingBar ratingBar, ratingstyle, rating_comfort, rating_performnce, rating_econimy, rating_value;


            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.custom_user_review, container, false);

            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
            ratingstyle = (RatingBar) view.findViewById(R.id.rating_style);
            rating_comfort = (RatingBar) view.findViewById(R.id.rating_comfort);
            rating_performnce = (RatingBar) view.findViewById(R.id.rating_performance);
            rating_econimy = (RatingBar) view.findViewById(R.id.rating_econimy);
            rating_value = (RatingBar) view.findViewById(R.id.rating_valueformone);


            tv_title.setText("" + RoadAssistance.get(position).str_postdate.toString());

            tv_name.setText("" + RoadAssistance.get(position).getStr_Brandid().toString() + " review on " + RoadAssistance.get(position).getStr_BrandName().toString());

            if (!RoadAssistance.get(position).getPrice().toString().equals("null")) {
                ratingstyle.setRating(Float.parseFloat(RoadAssistance.get(position).getPrice().toString()));
            } else {
                ratingstyle.setRating((float) 0.0);
            }


            if (!RoadAssistance.get(position).getOffertitle().toString().equals("null")) {
                rating_comfort.setRating(Float.parseFloat(RoadAssistance.get(position).getOffertitle().toString()));
            } else {
                rating_comfort.setRating((float) 0.0);
            }

            if (!RoadAssistance.get(position).getDealer_city().toString().equals("null")) {
                rating_performnce.setRating(Float.parseFloat(RoadAssistance.get(position).getDealer_city().toString()));
            } else {
                rating_performnce.setRating((float) 0.0);
            }

            if (!RoadAssistance.get(position).getDealer_email().toString().equals("")) {
                rating_econimy.setRating(Float.parseFloat(RoadAssistance.get(position).getDealer_email().toString()));
            } else {
                rating_econimy.setRating((float) 0.0);
            }

            if (!RoadAssistance.get(position).getDealer_area().toString().equals("null")) {
                rating_value.setRating(Float.parseFloat(RoadAssistance.get(position).getDealer_area().toString()));
            } else {
                rating_value.setRating((float) 0.0);
            }
            (container).addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            (container).removeView((LinearLayout) object);
        }
    }
}
