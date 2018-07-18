package carteckh.carteckh.fragments;

import android.app.Activity;
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

/**
 * Created by deep on 12/22/2015.
 */

public class UsedCar_Features extends Fragment {


    private Activity activity;


    @InjectView(R.id.parentview)
    LinearLayout parentview;
    ImageLoader imageLoader;


    public UsedCar_Features() {


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

//        UpadateUi();

        return view;
    }

//    private void UpadateUi() {
//        if (UsedCar_Deatail.Features != null) {
//            try {
//
//                if (UsedCar_Deatail.Features.length() > 0) {
//
//                    Iterator<String> mainIterator = UsedCar_Deatail.Features.keys();
//
//                    while (mainIterator.hasNext()) {
//
//                        String mainkey = mainIterator.next();
//                        if (!mainkey.equals("Heading")) {
//
//
//                            CustomTextView customTextView = new CustomTextView(getActivity());
//                            customTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                            customTextView.setPadding(10, 10, 10, 10);
//                            customTextView.setBackgroundColor(Color.GRAY);
//                            customTextView.setTextColor(Color.RED);
//                            customTextView.setText(mainkey);
//                            parentview.addView(customTextView);
//                            JSONObject jsonObject = UsedCar_Deatail.Features.getJSONObject(mainkey);
//
//
//                            Iterator<String> iterator = jsonObject.keys();
//                            while (iterator.hasNext()) {
//                                String key = iterator.next();
//
//
//                                LinearLayout linearLayoutParent = new LinearLayout(activity);
//                                linearLayoutParent.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
//                                linearLayoutParent.setPadding(10, 10, 10, 10);
//
//                                linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);
//
//
//                                CustomTextView customTextViewTitle = new CustomTextView(activity);
//                                customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                                customTextViewTitle.setTextColor(Color.RED);
//
//                                customTextViewTitle.setText(key);
//                                customTextViewTitle.setTextSize(16);
//
//
//                                NetworkImageView networkImageView = new NetworkImageView(getActivity());
//                                networkImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                                networkImageView.setImageUrl(jsonObject.getString(key), imageLoader);
//
//
//                                View view = new View(activity);
//                                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
//                                view.setBackgroundColor(Color.WHITE);
//
//
//                                linearLayoutParent.addView(customTextViewTitle);
//                                linearLayoutParent.addView(networkImageView);
//
//                                parentview.addView(linearLayoutParent);
//                                parentview.addView(view);
//
//                            }
//
//                        }
//                    }
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
