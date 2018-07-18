package carteckh.carteckh.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomButton;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */
public class C_Version extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener {
    private static CarOverView carOverView;
    @InjectView(R.id.c_version_campare_car)
    CustomButton c_version_campare_car;
    @InjectView(R.id.c_version_view)
    LinearLayout c_version_view;
    private Activity activity;
    private JSONArray versionListDataInfoJsonArray;
    private ArrayList<String> selectedIds = new ArrayList<>();

    public C_Version() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carOverView = new CarOverView();
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.c_version, null);
        ButterKnife.inject(this, view);
        c_version_campare_car.setOnClickListener(this);
        updateView();


        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getTag() instanceof Integer) {
            int position = (Integer) buttonView.getTag();
            try {
                String id = versionListDataInfoJsonArray.getJSONObject(position).getString("ActualId");
                if (isChecked) {
                    if (selectedIds.size() >= 3) {
                        buttonView.setChecked(false);
                        Toast.makeText(activity, "You can compare maximum 3 cars at a time.", Toast.LENGTH_SHORT).show();
                    } else {
                        selectedIds.add(id);
                    }
                } else
                    selectedIds.remove(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.c_version_campare_car) {
            Fragment fragment = null;
            fragment = new CompareCarsResult();
            Bundle bundle = new Bundle();


            if (selectedIds.size() == 2) {
                bundle.putStringArray("keys", new String[]{selectedIds.get(0), selectedIds.get(1)});
                fragment.setArguments(bundle);
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
                }


            } else if (selectedIds.size() == 3) {
                bundle.putStringArray("keys", new String[]{selectedIds.get(0), selectedIds.get(1), selectedIds.get(2)});
                fragment.setArguments(bundle);
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
                }


            } else {
                Toast.makeText(getActivity(), "Please Select Atleast Two cars", Toast.LENGTH_LONG).show();

            }

        } else if (v.getTag() instanceof Integer) {
            int position = (Integer) v.getTag();
            try {
                String id = versionListDataInfoJsonArray.getJSONObject(position).getString("Id");


                Bundle bundle = new Bundle();
                bundle.putString("brand_id", Constants.brand_id);
                bundle.putString("model_id", Constants.model_id);
                bundle.putString("version_id", id);

                Fragment fragment = new Car_Version_Overview();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("test").commit();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateView() {
        if (carOverView.VersionListData != null) {
            try {


                final JSONObject versionListDataInfoJsonObject = carOverView.VersionListData.getJSONObject("VersionListDataInfo1");
                versionListDataInfoJsonArray = versionListDataInfoJsonObject.getJSONArray("VersionListDataInfo1");

                Log.d("fgfdfgfgf",versionListDataInfoJsonArray.toString());
                for (int i = 0; i < versionListDataInfoJsonArray.length(); i++) {

                    final JSONObject jsonObject = versionListDataInfoJsonArray.getJSONObject(i);
                    LinearLayout linearLayoutParent = new LinearLayout(activity);
                    linearLayoutParent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayoutParent.setPadding(5, 10, 5, 10);
                    linearLayoutParent.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout linearLayoutChild = new LinearLayout(activity);
                    linearLayoutChild.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                    linearLayoutChild.setOrientation(LinearLayout.VERTICAL);
                    linearLayoutChild.setTag(i);
                    linearLayoutChild.setOnClickListener(this);

                    CustomTextView customTextViewTitle = new CustomTextView(activity);
                    customTextViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    customTextViewTitle.setTextAppearance(activity, R.style.TextViewStyle_Heading);
//                    customTextViewTitle.setSingleLine(true);
                    customTextViewTitle.setText(jsonObject.getString("Heading"));

                    CustomTextView customTextViewDes = new CustomTextView(activity);
                    customTextViewDes.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    customTextViewDes.setTextAppearance(activity, R.style.TextViewStyle);
                    customTextViewDes.setGravity(Gravity.LEFT);
                    customTextViewDes.setTextSize(13);
//                    customTextViewDes.setSingleLine(true);
                    customTextViewDes.setText(jsonObject.getString("VersionDetail") + "  ₹ " + jsonObject.getString("Price"));

                    linearLayoutChild.addView(customTextViewTitle);
                    linearLayoutChild.addView(customTextViewDes);


                    CheckBox checkBox = new CheckBox(activity);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    checkBox.setTag(i);

                    checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    checkBox.setOnCheckedChangeListener(this);
                    checkBox.setGravity(Gravity.RIGHT);
                    linearLayoutParent.addView(linearLayoutChild);
                    linearLayoutParent.addView(checkBox);


//                    CheckBox checkBox = new CheckBox(activity);
//                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
//                    checkBox.setTag(i);
//                    checkBox.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    checkBox.setOnCheckedChangeListener(this);
//
//                    linearLayoutParent.addView(linearLayoutChild);
//                    linearLayoutParent.addView(checkBox);

                    View view1 = new View(activity);
                    view1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    view1.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    c_version_view.addView(linearLayoutParent);
                    c_version_view.addView(view1);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
