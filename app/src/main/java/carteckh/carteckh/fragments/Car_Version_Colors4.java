package carteckh.carteckh.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */

public class Car_Version_Colors4 extends Fragment implements View.OnClickListener {

    @InjectView(R.id.c_colors_view)
    LinearLayout c_colors_view;
    @InjectView(R.id.c_colors_heading1)
    CustomTextView c_colors_heading1;
    @InjectView(R.id.c_colors_heading2)
    CustomTextView c_colors_heading2;
    private Activity activity;
    private int colorDimen;


    public Car_Version_Colors4() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
        colorDimen = (int) activity.getResources().getDimension(R.dimen.textview_colors);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.c_colors, null);
        ButterKnife.inject(this, view);
        if (Car_Version_Overview4.Available_Colours != null) {
            try {

                if (Car_Version_Overview4.Available_Colours.length() > 0) {
                    for (int i = 0; i < Car_Version_Overview4.Available_Colours.length(); i++) {
                        final JSONObject jsonObject = Car_Version_Overview4.Available_Colours.getJSONObject(i);

                        LinearLayout linearLayoutParent = new LinearLayout(activity);
                        linearLayoutParent.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                        linearLayoutParent.setPadding(5, 10, 5, 10);
                        linearLayoutParent.setGravity(Gravity.CENTER);
                        linearLayoutParent.setOrientation(LinearLayout.VERTICAL);

                        CustomTextView customTextViewTitle = new CustomTextView(activity);
                        customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(colorDimen, colorDimen));
                        customTextViewTitle.setBackgroundColor(Color.parseColor("#" + jsonObject.getString("colourcode")));
                        customTextViewTitle.setTag(i);
                        customTextViewTitle.setOnClickListener(this);

//                        CustomTextView customTextViewDes = new CustomTextView(activity);
//                        customTextViewDes.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                        customTextViewDes.setTextAppearance(activity, R.style.TextViewStyle);
//                        customTextViewDes.setSingleLine(true);
//                        customTextViewDes.setPadding(0, 5, 0, 0);
//                        customTextViewDes.setText(jsonObject.getString("colourname"));

                        linearLayoutParent.addView(customTextViewTitle);
//                        linearLayoutParent.addView(customTextViewDes);

                        View view1 = new View(activity);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
                        layoutParams.setMargins(5, 20, 5, 20);
                        view1.setLayoutParams(layoutParams);
                        view1.setBackgroundColor(Color.parseColor("#FFFFFF"));

                        c_colors_view.addView(linearLayoutParent);
                        c_colors_view.addView(view1);

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (Car_Version_Overview4.Available_Colours != null) {
            try {
                Toast.makeText(activity, Car_Version_Overview4.Available_Colours.getJSONObject((int) v.getTag()).getString("colourname"), Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
