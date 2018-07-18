package carteckh.carteckh.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */

public class Car_Version_Summary extends Fragment {


    private Activity activity;


    @InjectView(R.id.parentview)
    LinearLayout parentview;


    public Car_Version_Summary() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.car_version_summary2, null);
        ButterKnife.inject(this, view);

        UpadateUi();

        return view;
    }

    private void UpadateUi() {
        if (Car_Version_Overview.SummaryBlock != null) {
            try {

                //if (Car_Version_Overview.SummaryBlock.length() > 0) {
                Log.d("fgdgdfgdfg",Car_Version_Overview.SummaryBlock.toString());

                    for (int i = 0; i < Car_Version_Overview.SummaryBlock.length(); i++) {


                        final JSONObject jsonObject = Car_Version_Overview.SummaryBlock.getJSONObject(i);
                        Log.d("fdsgdfgdf",jsonObject.toString());

                        Iterator<String> iterator = jsonObject.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();

                            LinearLayout linearLayoutParent = new LinearLayout(activity);
                            linearLayoutParent.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
                            linearLayoutParent.setPadding(10, 10, 10, 10);
                            linearLayoutParent.setBackgroundColor(Color.GRAY);
                            linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);

                            CustomTextView customTextViewTitle = new CustomTextView(activity);
                            customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            customTextViewTitle.setTextColor(Color.RED);
                            customTextViewTitle.setTag(i);
                            customTextViewTitle.setText(key);
                            customTextViewTitle.setTextSize(16);

                            CustomTextView customTextViewTitle1 = new CustomTextView(activity);
                            customTextViewTitle1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                            customTextViewTitle1.setTextColor(Color.WHITE);
                            customTextViewTitle1.setTag(i);
                            customTextViewTitle1.setText(jsonObject.getString(key));
                            customTextViewTitle1.setTextSize(16);


                            View view = new View(activity);
                            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
                            view.setBackgroundColor(Color.WHITE);


                            linearLayoutParent.addView(customTextViewTitle);
                            linearLayoutParent.addView(customTextViewTitle1);

                            parentview.addView(linearLayoutParent);
                            parentview.addView(view);
                        }


                    }

               // }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
