package carteckh.carteckh.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */

public class UsedCar_Specificattion extends Fragment {


    private Activity activity;


    @InjectView(R.id.parentview)
    LinearLayout parentview;
    ImageLoader imageLoader;


    public UsedCar_Specificattion() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        imageLoader = AppController.getInstance().getImageLoader();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.car_version_summary, null);
        ButterKnife.inject(this, view);

//        try {
////            UpadateUi();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return view;
    }

//    private void UpadateUi() throws JSONException {
//        if (UsedCar_Deatail.Specification != null) {
//
//            if (UsedCar_Deatail.Specification.length() > 0) {
//
//
//                Iterator<String> iterator = UsedCar_Deatail.Specification.keys();
//                Heading();
//                while (iterator.hasNext()) {
//                    String key = iterator.next();
//
//                    JSONObject jsonObject = UsedCar_Deatail.Specification.optJSONObject(key);
//
//
//                    if (!key.equals("Heading")) {
//
//
//                        LinearLayout linearLayoutParent = new LinearLayout(activity);
//                        linearLayoutParent.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
//                        linearLayoutParent.setPadding(10, 10, 10, 10);
//
//                        linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);
//
//                        CustomTextView customTextViewTitle = new CustomTextView(activity);
//                        customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                        customTextViewTitle.setTextColor(Color.RED);
//
//                        customTextViewTitle.setText(key);
//                        customTextViewTitle.setTextSize(14);
//
//
//                        NetworkImageView networkImageView = new NetworkImageView(getActivity());
//
//                        networkImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                        networkImageView.setImageUrl(jsonObject.getString("Excellent"), imageLoader);
//
//
//                        NetworkImageView networkImageView1 = new NetworkImageView(getActivity());
//
//                        networkImageView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                        networkImageView1.setImageUrl(jsonObject.getString("Good"), imageLoader);
//
//
//                        NetworkImageView networkImageView2 = new NetworkImageView(getActivity());
//
//                        networkImageView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                        networkImageView2.setImageUrl(jsonObject.getString("Fair"), imageLoader);
//
//
//                        NetworkImageView networkImageView3 = new NetworkImageView(getActivity());
//
//                        networkImageView3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                        networkImageView3.setImageUrl(jsonObject.getString("Poor"), imageLoader);
//
//
//                        View view = new View(activity);
//                        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
//                        view.setBackgroundColor(Color.WHITE);
//
//
//                        linearLayoutParent.addView(customTextViewTitle);
//                        linearLayoutParent.addView(networkImageView);
//                        linearLayoutParent.addView(networkImageView1);
//                        linearLayoutParent.addView(networkImageView2);
//                        linearLayoutParent.addView(networkImageView3);
//
//
//                        parentview.addView(linearLayoutParent);
//                        parentview.addView(view);
//                    }
//                }
//
//            }
//        }
//
//    }

    private void Heading() {


        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        linearLayout.setBackgroundColor(Color.GRAY);
        linearLayout.setPadding(10,10,10,10);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);


        CustomTextView customTextViewTitle = new CustomTextView(activity);
        customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        customTextViewTitle.setTextColor(Color.RED);

        customTextViewTitle.setText("");
        customTextViewTitle.setTextSize(14);


        CustomTextView customTextViewTitle1 = new CustomTextView(activity);
        customTextViewTitle1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        customTextViewTitle1.setTextColor(Color.WHITE);

        customTextViewTitle1.setText("Excellent");
        customTextViewTitle1.setTextSize(14);

        CustomTextView customTextViewTitle2 = new CustomTextView(activity);
        customTextViewTitle2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        customTextViewTitle2.setTextColor(Color.WHITE);

        customTextViewTitle2.setText("Good");
        customTextViewTitle2.setTextSize(14);

        CustomTextView customTextViewTitle3 = new CustomTextView(activity);
        customTextViewTitle3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        customTextViewTitle3.setTextColor(Color.WHITE);

        customTextViewTitle3.setText("Fair");
        customTextViewTitle3.setTextSize(14);


        CustomTextView customTextViewTitle4 = new CustomTextView(activity);
        customTextViewTitle4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        customTextViewTitle4.setTextColor(Color.WHITE);

        customTextViewTitle4.setText("Poor");
        customTextViewTitle4.setTextSize(14);

        linearLayout.addView(customTextViewTitle);
        linearLayout.addView(customTextViewTitle1);
        linearLayout.addView(customTextViewTitle2);
        linearLayout.addView(customTextViewTitle3);
        linearLayout.addView(customTextViewTitle4);
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
        view.setBackgroundColor(Color.WHITE);
        parentview.addView(linearLayout);
        parentview.addView(view);


    }
}
