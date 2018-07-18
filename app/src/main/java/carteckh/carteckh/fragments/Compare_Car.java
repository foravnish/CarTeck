package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 11-Dec-15.
 */
public class Compare_Car extends Fragment {
    View view;

    @InjectView(R.id.comapare_cars)
    Button btn_compareCars;


    String str_car1 = "", str_car2 = "", str_car3 = "";
    String str_car1_id = "", str_car2_id = "", str_car3_id = "";


    @InjectView(R.id.first_emptycar)
    ImageView first_emptycar;

    @InjectView(R.id.select_first_image_layout)
    LinearLayout select_first_image_layout;
    @InjectView(R.id.first_carname)
    TextView first_carname;
    @InjectView(R.id.first_carprice)
    TextView first_carprice;
    @InjectView(R.id.first_image)
    NetworkImageView first_image;


    @InjectView(R.id.third_emptycar)
    ImageView third_emptycar;

    @InjectView(R.id.select_third_image_layout)
    LinearLayout select_third_image_layout;
    @InjectView(R.id.third_carname)
    TextView third_carname;
    @InjectView(R.id.third_carprice)
    TextView third_carprice;
    @InjectView(R.id.third_image)
    NetworkImageView third_image;


    @InjectView(R.id.second_emptycar)
    ImageView second_emptycar;

    @InjectView(R.id.select_second_image_layout)
    LinearLayout select_second_image_layout;
    @InjectView(R.id.second_carname)
    TextView second_carname;
    @InjectView(R.id.second_carprice)
    TextView second_carprice;
    @InjectView(R.id.second_image)
    NetworkImageView second_image;

    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.compare_cars, container, false);
        ButterKnife.inject(this, view);


        //Initializing the dialog
        dialog = new Dialog(Compare_Car.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Constants.flag) {
            if (Constants.first_car || (!str_car1.equals(""))) {

                Constants.first_car = false;
                str_car1 = Constants.comparcarurl;
                //   Constants.comparcarurl = "";
                FirstCarDisplay();

            }
            if (Constants.second_car || (!str_car2.equals(""))) {

                Constants.second_car = false;
                str_car2 = Constants.comparcarurl2;
                // Constants.comparcarurl2 = "";


                SecondCarDisplay();
            }
            if (Constants.third_car || (!str_car3.equals(""))) {

                Constants.third_car = false;
                str_car3 = Constants.comparcarurl3;
                //   Constants.comparcarurl3 = "";
                ThirdCarDisplay();
            }


            if (Constants.second_car || (!str_car2.equals("")) && (str_car3.equals(""))) {
                third_emptycar.setVisibility(View.VISIBLE);
            } else {

            }

        }


        first_emptycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constants.first_car = true;


                Fragment fragment = null;
                fragment = new Brand_List();


                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });

        select_first_image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.first_car = true;
                Fragment fragment = null;
                fragment = new Brand_List();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });


        third_emptycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constants.third_car = true;


                Fragment fragment = null;
                fragment = new Brand_List();


                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });

        select_third_image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.third_car = true;
                Fragment fragment = null;
                fragment = new Brand_List();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });


        second_emptycar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constants.second_car = true;
                Fragment fragment = null;
                fragment = new Brand_List();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });

        select_second_image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.second_car = true;
                Fragment fragment = null;
                fragment = new Brand_List();

                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });

        btn_compareCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (str_car1_id.equals("") && str_car2_id.equals("")) {
                    Crouton.makeText(getActivity(), "Please Select Atleast One Car", Style.ALERT).show();

                } else {

                    if (str_car1_id.equals(str_car2_id) || str_car1_id.equals(str_car3_id) || str_car2_id.equals(str_car3_id)) {

                        Crouton.makeText(getActivity(), "Please select different car to compare.", Style.ALERT).show();
                    } else {
                        Fragment fragment = null;
                        fragment = new CompareCarsResult();
                        Bundle bundle = new Bundle();


                        if (str_car3_id.equals(""))
                            bundle.putStringArray("keys", new String[]{str_car1_id, str_car2_id});
                        else {
                            bundle.putStringArray("keys", new String[]{str_car1_id, str_car2_id, str_car3_id});
                        }
                        fragment.setArguments(bundle);
                        if (fragment != null) {
                            Constants.exitdialog(dialog);
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

                        }
                    }
                }
            }
        });

        return view;
    }


    private void FirstCarDisplay() {
        first_emptycar.setVisibility(View.GONE);
        select_first_image_layout.setVisibility(View.VISIBLE);

        try {

            JSONObject jsonObject = new JSONObject(str_car1);

            JSONObject jsonObject1 = jsonObject.getJSONObject("carRecord");
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            first_image.setImageUrl(jsonObject1.optString("photo"), imageLoader);
            first_carname.setText("" + jsonObject1.optString("name"));
            first_carprice.setText("" + jsonObject1.optString("price"));

            str_car1_id = jsonObject1.optString("id");
            Constants.flag = false;


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void ThirdCarDisplay() {
        third_emptycar.setVisibility(View.GONE);
        select_third_image_layout.setVisibility(View.VISIBLE);

        try {

            JSONObject jsonObject = new JSONObject(str_car3);

            JSONObject jsonObject1 = jsonObject.getJSONObject("carRecord");
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            third_image.setImageUrl(jsonObject1.optString("photo"), imageLoader);
            third_carname.setText("" + jsonObject1.optString("name"));
            third_carprice.setText("" + jsonObject1.optString("price"));
            Constants.flag = false;
            str_car3_id = jsonObject1.optString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void SecondCarDisplay() {
        second_emptycar.setVisibility(View.GONE);
        select_second_image_layout.setVisibility(View.VISIBLE);

        try {

            JSONObject jsonObject = new JSONObject(str_car2);

            JSONObject jsonObject1 = jsonObject.getJSONObject("carRecord");
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            second_image.setImageUrl(jsonObject1.optString("photo"), imageLoader);
            second_carname.setText("" + jsonObject1.optString("name"));
            second_carprice.setText("" + jsonObject1.optString("price"));
            Constants.flag = false;
            str_car2_id = jsonObject1.optString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
