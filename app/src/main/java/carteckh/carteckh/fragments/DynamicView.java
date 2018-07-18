package carteckh.carteckh.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;

/**
 * Created by developer on 19-Dec-15.
 */
public class DynamicView extends Fragment {

    @InjectView(R.id.parentview)
    LinearLayout parentview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamicview, container, false);
        ButterKnife.inject(this, view);
        parentview.setPadding(0, 10, 0, 10);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.carteckh.com/appdata/jsondata.php?task=modelDetailWithVersion", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    UpdateUi(new JSONObject(s));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(getActivity(), "Some problem occured pls try again"(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("brand_id", "2");
                map.put("model_id", "89");
                map.put("model_name", "r8");


                return map;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);


        return view;
    }

    private void UpdateUi(JSONObject jsonObject) throws JSONException {


        Iterator<String> iterator = jsonObject.keys();


        while (iterator.hasNext()) {

            String mainKey = iterator.next();
            JSONObject jsonObject1;
            Iterator<String> subIterator;
            switch (mainKey) {


                case "ReviewData":

                    break;


                case "VersionListData":

                    jsonObject1 = jsonObject.getJSONObject("VersionListData");


                    subIterator = jsonObject1.keys();
                    while (subIterator.hasNext()) {
                        String subkey = subIterator.next();


                        JSONObject jsonObject2 = jsonObject1.getJSONObject("VersionListDataInfo");
                        JSONArray jsonArray1 = jsonObject2.getJSONArray("VersionListDataInfo");

                        TextView textViewHeading = new TextView(getActivity());

                        textViewHeading.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        textViewHeading.setText("VersionList");
                        textViewHeading.setTextColor(Color.GREEN);
                        textViewHeading.setPadding(15, 15, 15, 15);
                        textViewHeading.setTextSize(20);
                        textViewHeading.setGravity(Gravity.CENTER);
                        textViewHeading.setBackgroundColor(Color.DKGRAY);

                        parentview.addView(textViewHeading);

                        for (int i = 0; i < jsonArray1.length(); i++) {

                            JSONObject jsonObject3 = jsonArray1.getJSONObject(i);


                            LinearLayout linearLayout = new LinearLayout(getActivity());
                            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.setPadding(10, 10, 10, 10);

                            LinearLayout sublinLinearLayout = new LinearLayout(getActivity());
                            sublinLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            sublinLinearLayout.setOrientation(LinearLayout.VERTICAL);


                            TextView heading = new TextView(getActivity());
                            heading.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            heading.setText(jsonObject3.getString("Heading"));
                            heading.setTextColor(Color.RED);
                            heading.setTextSize(16);

                            TextView version = new TextView(getActivity());
                            version.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            version.setText(jsonObject3.getString("VersionDetail"));
                            version.setTextSize(14);

                            sublinLinearLayout.addView(heading);
                            sublinLinearLayout.addView(version);
                            linearLayout.addView(sublinLinearLayout);

                            TextView price = new TextView(getActivity());
                            price.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            price.setText("Rs." + jsonObject3.getString("Price"));
                            price.setTextSize(14);
                            linearLayout.addView(price);


                            parentview.addView(linearLayout);


                        }

                        UpdateColor(jsonObject);

                    }


                    break;
            }


        }


    }

    private void UpdateColor(JSONObject jsonObject) throws JSONException {
        JSONObject jsonObject1 = (jsonObject.getJSONObject("ColourData")).getJSONObject("ColourDataInfo");


        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText("" + jsonObject1.getString("Heading1"));
        textView.setTextColor(Color.GREEN);
        textView.setPadding(15, 15, 15, 15);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.DKGRAY);
        parentview.addView(textView);


        TextView subheading = new TextView(getActivity());
        subheading.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        subheading.setText(jsonObject1.getString("Heading2"));
        subheading.setTextSize(14);
        subheading.setTextColor(Color.RED);
        parentview.addView(subheading);

        HorizontalScrollView scrollView = new HorizontalScrollView(getActivity());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        JSONArray jsonArray = jsonObject1.getJSONArray("CodeAndName");


        final LinearLayout linearLayoutscroll = new LinearLayout(getActivity());
        linearLayoutscroll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayoutscroll.setGravity(Gravity.CENTER);
        linearLayoutscroll.setPadding(15, 15, 15, 15);

        linearLayoutscroll.setOrientation(LinearLayout.HORIZONTAL);

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject2 = jsonArray.getJSONObject(i);


            View view = new View(getActivity());
            view.setLayoutParams(new LinearLayout.LayoutParams(40, 40));
            view.setBackgroundColor(Color.parseColor("#" + jsonObject2.getString("colourcode")));
            view.setPadding(5, 0, 5, 0);


            TextView textView1 = new TextView(getActivity());
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setText(jsonObject2.getString("colourname"));
            textView.setPadding(5, 0, 5, 0);
            textView1.setTextSize(12);


            linearLayoutscroll.addView(view);
            linearLayoutscroll.addView(textView1);


        }
        scrollView.addView(linearLayoutscroll);
        parentview.addView(scrollView);
    }
}
