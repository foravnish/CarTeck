package carteckh.carteckh.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */
public class C_Features2 extends Fragment {
    private static CarOverView2 carOverView;
    @InjectView(R.id.c_features_view)
    LinearLayout c_features_view;


    public  static CustomTextView summary;

    private Activity activity;

    public C_Features2() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carOverView = new CarOverView2();
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.c_features, null);
        summary=(CustomTextView)view.findViewById(R.id.summary);
        ButterKnife.inject(this, view);
        if (carOverView.SummaryData != null) {


            summary.setText(Constants.sharedPreferences.getString("brandname",""));

            try {
                final JSONObject summaryDataJsonObject = carOverView.SummaryData.getJSONObject("SummaryBlocl");

                //summary.setText("ss");

                Iterator<String> stringIterator = summaryDataJsonObject.keys();

                while (stringIterator.hasNext()) {
                    final String key = stringIterator.next();

                    CustomTextView customTextViewTitle = new CustomTextView(activity);
                    customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    customTextViewTitle.setTextAppearance(activity, R.style.TextViewStyle_Heading);
                    customTextViewTitle.setSingleLine(true);
                    customTextViewTitle.setPadding(5, 5, 5, 5);
                    customTextViewTitle.setTypeface(customTextViewTitle.getTypeface(), Typeface.BOLD);
                    customTextViewTitle.setText(key);
                    CustomTextView customTextViewDes = new CustomTextView(activity);

                    customTextViewDes.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    customTextViewDes.setTextAppearance(activity, R.style.TextViewStyle);
                    customTextViewDes.setSingleLine(true);
                    customTextViewDes.setPadding(10, 0, 10, 10);
                    customTextViewDes.setText(summaryDataJsonObject.getString(key));

                    View view1 = new View(activity);
                    view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    view1.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    c_features_view.addView(customTextViewTitle);
                    c_features_view.addView(customTextViewDes);
                    c_features_view.addView(view1);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

}
