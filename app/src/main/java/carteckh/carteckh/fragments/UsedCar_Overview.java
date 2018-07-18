package carteckh.carteckh.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 04-Jan-16.
 */
public class UsedCar_Overview extends Fragment {

    View view;

    TextView heading1,heading2;
    TextView kms,status,year,price;

//    @InjectView(R.id.parentview)
//    LinearLayout parentview;

//    @InjectView(R.id.kms)
//    TextView kms;
//
//    @InjectView(R.id.status)
//    TextView status;
//
//    @InjectView(R.id.year)
//    TextView year;
//
//    @InjectView(R.id.price)
//    TextView price;
//
    @InjectView(R.id.slider)
    SliderLayout sliderLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.usecardetail, container, false);
        ButterKnife.inject(this, view);

        heading1=(TextView)view.findViewById(R.id.heading1);
        heading2=(TextView)view.findViewById(R.id.heading2);

        kms=(TextView)view.findViewById(R.id.kms);
        status=(TextView)view.findViewById(R.id.status);
        year=(TextView)view.findViewById(R.id.year);
        price=(TextView)view.findViewById(R.id.price);

        heading1.setText(Constants.sharedPreferences.getString("brand",""));
        heading2.setText(Constants.sharedPreferences.getString("model",""));

        try {
            GettingData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }

    private void GettingData() throws JSONException {
        if (UsedCar_Deatail.PhotoList != null) {
            JSONArray jsonArray = UsedCar_Deatail.PhotoList.optJSONArray("Photos");
            for (int i = 0; i < jsonArray.length(); i++) {


                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView.description(UsedCar_Deatail.MainPartInfo.optString("Heading")+" "+Constants.sharedPreferences.getString("model",""))
                        .image(jsonArray.getString(i))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);


                sliderLayout.addSlider(textSliderView);


            }
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setCustomAnimation(new DescriptionAnimation());
            sliderLayout.setDuration(4000);
        }


        Diplay_Text(UsedCar_Deatail.RightSideBlock);


    }


    private void Diplay_Text(JSONObject rightSideBlock) {


        price.setText("₹ "+rightSideBlock.optString("Expected Selling Price: "));
        year.setText(rightSideBlock.optString("Model Year: "));
        kms.setText(rightSideBlock.optString("Kms Driven: "));
        status.setText(rightSideBlock.optString("Owner Status: "));

        Log.d("gfdgdf",rightSideBlock.optString("Expected Selling Price: "));
        Log.d("gfdghfhfg","true");
//        Iterator<String> stringIterator = rightSideBlock.keys();
//        while (stringIterator.hasNext()) {
//            String key = stringIterator.next();
//
//
//            LinearLayout linearLayout = new LinearLayout(getActivity());
//            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//
//            CustomTextView customTextView = new CustomTextView(getActivity());
//            customTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//
//            customTextView.setText(key);
//            customTextView.setTextSize(15);
//            customTextView.setTextColor(Color.RED);
//
//
//            CustomTextView customTextView1 = new CustomTextView(getActivity());
//            customTextView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//
//            customTextView1.setText(rightSideBlock.optString(key));
//            customTextView1.setTextSize(17);
//            customTextView1.setTextColor(Color.WHITE);
//            linearLayout.addView(customTextView);
//            linearLayout.addView(customTextView1);
//
//            parentview.addView(linearLayout);
//
//
//        }


    }

    @Override
    public void onStop() {
        super.onStop();
        sliderLayout.stopAutoCycle();
    }
}
