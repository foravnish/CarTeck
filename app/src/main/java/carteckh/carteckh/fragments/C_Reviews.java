package carteckh.carteckh.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */
public class C_Reviews extends Fragment {
    private static CarOverView carOverView;
    @InjectView(R.id.c_reviews_view)
    LinearLayout c_reviews_view;
    @InjectView(R.id.c_reviews_heading)
    CustomTextView c_reviews_heading;
    private Activity activity;

    public C_Reviews() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carOverView = new CarOverView();
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.c_reviews, null);
        ButterKnife.inject(this, view);
        if (carOverView.ReviewData != null) {
            try {
                final JSONObject reviewDataJsonObject = carOverView.ReviewData.getJSONObject("ReviewDetail");
                final JSONArray reviewDataJsonArray = reviewDataJsonObject.getJSONArray("Review");
                if (reviewDataJsonArray.length() > 0) {
                    for (int i = 0; i < reviewDataJsonArray.length(); i++) {
                        final JSONObject jsonObject = reviewDataJsonArray.getJSONObject(i);

                        LinearLayout linearLayoutParent = new LinearLayout(activity);
                        linearLayoutParent.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                        linearLayoutParent.setPadding(5, 10, 5, 10);
                        linearLayoutParent.setGravity(Gravity.CENTER_VERTICAL);
                        linearLayoutParent.setOrientation(LinearLayout.VERTICAL);

                        CustomTextView customTextViewTitle = new CustomTextView(activity);
                        customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        customTextViewTitle.setTextAppearance(activity, R.style.TextViewStyle);
                        customTextViewTitle.setSingleLine(true);
                        customTextViewTitle.setTypeface(customTextViewTitle.getTypeface(), Typeface.BOLD);
                        customTextViewTitle.setText((Math.floor(Integer.parseInt(jsonObject.getString("review")) / 5)) + " star out of 5");

                        CustomTextView customTextViewDes = new CustomTextView(activity);
                        customTextViewDes.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        customTextViewDes.setTextAppearance(activity, R.style.TextViewStyle);
                        customTextViewDes.setSingleLine(true);
                        customTextViewDes.setPadding(5, 5, 0, 0);
                        customTextViewDes.setText(jsonObject.getString("reviewby"));

                        linearLayoutParent.addView(customTextViewTitle);
                        linearLayoutParent.addView(customTextViewDes);

                        View view1 = new View(activity);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                        layoutParams.setMargins(5, 5, 5, 5);
                        view1.setLayoutParams(layoutParams);
                        view1.setBackgroundColor(Color.parseColor("#FFFFFF"));

                        c_reviews_view.addView(linearLayoutParent);
                        c_reviews_view.addView(view1);

                    }
                    c_reviews_heading.setText(reviewDataJsonObject.getString("Heading2"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

}
