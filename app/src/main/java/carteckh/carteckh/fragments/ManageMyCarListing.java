package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 06-Jan-16.
 */
public class ManageMyCarListing extends Fragment {
    View view;

    @InjectView(R.id.parentview)
    LinearLayout parentview;

    Dialog dialog;


    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new Dialog(getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.managemycarlisting, container, false);

        ButterKnife.inject(this, view);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=myCarListing&userid=" + Constants.sharedPreferences.getString("user_id", ""), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);

                if (jsonObject.optString("success").equals("1")) {
                    UpdateUI(jsonObject);
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

    private void UpdateUI(JSONObject jsonObject) {


        JSONArray jsonArray = jsonObject.optJSONArray("Listing");

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject1 = jsonArray.optJSONObject(i);


            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setTag(i);
            linearLayout.setPadding(2, 5, 2, 5);


            NetworkImageView networkImageView = new NetworkImageView(getActivity());
            networkImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 200, 1));
            networkImageView.setImageUrl(jsonObject1.optString("photo"), imageLoader);
            networkImageView.setTag(i);
            networkImageView.setScaleType(ImageView.ScaleType.FIT_XY);



            LinearLayout sublinearlayout = new LinearLayout(getActivity());
            sublinearlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            sublinearlayout.setOrientation(LinearLayout.VERTICAL);
            sublinearlayout.setTag(i);


            TextView Heading = new TextView(getActivity());
            Heading.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//               String span = "<h1>" + jsonObject1.optString("Heading") + "</h1>";
            Heading.setText(jsonObject1.optString("Heading"));
            Heading.setTag(i);
            Heading.setTextSize(18);
            Heading.setPadding(2, 2, 2, 2);


            TextView Price = new TextView(getActivity());
            Price.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // span = "<h6> Price  :" + jsonObject1.optString("Price") + "</h1>";
            Price.setText("Price :" + jsonObject1.optString("Price"));
            Price.setTag(i);
            Price.setPadding(2, 2, 2, 2);


            TextView Kilometers = new TextView(getActivity());
            Kilometers.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //  span = "<h6> Kilometers  :" + jsonObject1.optString("Kilometers") + "</h1>";
            Kilometers.setText("Kilometers :" + jsonObject1.optString("Kilometers"));
            Kilometers.setTag(i);
            Kilometers.setPadding(2, 2, 2, 2);

            TextView ModelYear = new TextView(getActivity());
            ModelYear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // span = "<h6> Model Year  :" + jsonObject1.optString("Model Year") + "</h1>";
            ModelYear.setText("Model Year :" + jsonObject1.optString("Model Year"));
            ModelYear.setTag(i);
            ModelYear.setPadding(2, 2, 2, 2);

            TextView listedon = new TextView(getActivity());
            listedon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // span = "<h6> Car Listed On  :" + jsonObject1.optString("Car Listed On") + "</h1>";
            listedon.setText("Car Listed On :" + jsonObject1.optString("Car Listed On"));
            listedon.setTag(i);
            listedon.setPadding(2, 2, 2, 2);


            sublinearlayout.addView(Heading);
            sublinearlayout.addView(Price);
            sublinearlayout.addView(Kilometers);
            sublinearlayout.addView(ModelYear);
            sublinearlayout.addView(listedon);

            linearLayout.addView(networkImageView);
            linearLayout.addView(sublinearlayout);
            View view = new View(getActivity());
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
            view.setBackgroundColor(Color.parseColor("#000000"));

            parentview.addView(linearLayout);


            parentview.addView(view);


        }


    }
}
