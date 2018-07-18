package carteckh.carteckh.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by deep on 12/22/2015.
 */
public class C_OverView2 extends Fragment {
    private static CarOverView2 carOverView;
    @InjectView(R.id.c_overview_iv)
    NetworkImageView c_overview_iv;
    @InjectView(R.id.c_overview_tv)
    TextView c_overview_tv;
    @InjectView(R.id.c_overview_tv_heading)
    CustomTextView c_overview_tv_heading;

    @InjectView(R.id.price)
    TextView price;

    @InjectView(R.id.location)
    TextView location;

    @InjectView(R.id.msg)
    TextView msg;

    @InjectView(R.id.emitext)
    TextView emitext;

    @InjectView(R.id.roadside)
    TextView roadside;

    @InjectView(R.id.dealer)
    TextView dealer;


    private Activity activity;
    private ImageLoader imageLoader;
    ImageView dis;

    String col="car dealer.";

    public C_OverView2() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carOverView = new CarOverView2();
        activity = getActivity();
        imageLoader = AppController.getInstance().getImageLoader();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.c_overview1, null);
        dis=(ImageView) view.findViewById(R.id.dis);

        ButterKnife.inject(this, view);
        updateView();
        return view;
    }

    public void updateView() {
//        if (carOverView.PhotoSection != null && carOverView.BrandModelName != null && carOverView.rightSideInfo != null && carOverView.EmiSection != null) {
        if (carOverView.PhotoSection != null && carOverView.BrandModelName != null && carOverView.rightSideInfo != null) {
            try {
                final JSONObject informationJsonObject = carOverView.rightSideInfo.getJSONObject("Information");
                final String minPrice = informationJsonObject.optString("MinPrice");
                final String maxPrice = informationJsonObject.optString("MaxPrice");
                final String exShowRoom_msg = informationJsonObject.optString("ExShowRoomMsg");
                final String exShowRoom = informationJsonObject.optString("ExShowRoom");
                final String status = informationJsonObject.optString("Status");

                Log.d("dgfdgdf","true1");

                //if (exShowRoom)

                if (status.equals("Discontinued")){
                    Log.d("dgfdgdf2","True2");

                    final String toalFreeno = carOverView.RoadSideAssisData.getJSONObject("RoadSideAssisInfo").optString("HelpNo");
                    dis.setVisibility(View.VISIBLE);
                    if (carOverView.EmiSection != null) {
                        final JSONObject emiInfoJsonObject = carOverView.EmiSection.optJSONObject("EmiInfo");
                        final String emi = emiInfoJsonObject.optString("Emi");
                        final String forloanOf = emiInfoJsonObject.optString("ForloanOf");
                        if (maxPrice.equals("")) {

//                            String span1 = "<h4> Last recorded price: ₹ <b> " + minPrice + " \n"+ exShowRoom_msg + "" + "</b></h4>";
//                            price.setText(Html.fromHtml(span1));

                            price.setText("Last recorded price: ₹ "+minPrice);
                            location.setText(exShowRoom_msg);

                            msg.setText(exShowRoom.toString());

                            String span = "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + forloanOf + "</p>" + "<h6> Roadside Assistance: " + toalFreeno;
                            c_overview_iv.setImageUrl(carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"), imageLoader);
                            c_overview_tv.setText(Html.fromHtml(span));
                        } else {
//                            String span1 = "<h4> Last recorded price: ₹ <b> " + minPrice + " \n"+ exShowRoom_msg + "" + "</b></h4>";
//                            price.setText(Html.fromHtml(span1));

                            price.setText("Last recorded price: ₹ "+minPrice);
                            location.setText(exShowRoom_msg);


                            msg.setText(exShowRoom.toString());
                            String span = "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + forloanOf + "</p>" + "<h6> Roadside Assistance: " + toalFreeno;
                            c_overview_iv.setImageUrl(carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"), imageLoader);
                            c_overview_tv.setText(Html.fromHtml(span));
                        }

                    } else {
//                        String span1 = "<h4> Last recorded price: ₹ <b> " + minPrice + "\n "+ exShowRoom_msg + "" + "</b></h4>";
//                        price.setText(Html.fromHtml(span1));

                        price.setText("Last recorded price: ₹ "+minPrice);
                        location.setText(exShowRoom_msg);

                        msg.setText(exShowRoom.toString());
                        String span = "<h6> Roadside Assistance:  " + toalFreeno;
                        c_overview_iv.setImageUrl(carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"), imageLoader);
                        c_overview_tv.setText(Html.fromHtml(span));
                    }
                }

                else
                {


                    final String toalFreeno = carOverView.RoadSideAssisData.getJSONObject("RoadSideAssisInfo").optString("HelpNo");

                    if (carOverView.EmiSection != null) {
                        final JSONObject emiInfoJsonObject = carOverView.EmiSection.optJSONObject("EmiInfo");
                        final String emi = emiInfoJsonObject.optString("Emi");
                        final String ForloanOf = emiInfoJsonObject.optString("ForloanOf");
                        String ss=ForloanOf.substring(14);
                        if (maxPrice.equals("")) {


//                            String span1 = "<h4> Starting at ₹ <b> " + minPrice + "\n "+ exShowRoom_msg + "" + "</b></h4>";
//                            price.setText(Html.fromHtml(span1));
                            price.setText("Starting at ₹ "+minPrice);
                            location.setText(exShowRoom_msg);

                            //msg.setText(exShowRoom.toString());
                            msg.setText("For On-Road prices contact your nearest");
                            dealer.setText("Car dealer.");
                            emitext.setText("EMI: ₹ "+emi);
                            String span = "For a loan of ₹  " + ss;
                            roadside.setText("Roadside Assistance: " + toalFreeno);
                            c_overview_tv.setText(Html.fromHtml(span));
                            c_overview_iv.setImageUrl(carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"), imageLoader);
                            hyperlink();
                            Log.d("gbfdhgfhgf1","true");
                            Log.d("gbfdhgfhgf",carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"));
                        } else {
//                            String span1 = "<h4> Starting at ₹ <b> " + minPrice + " - ₹ " + maxPrice +  " "+ "\n "+ exShowRoom_msg + "" + "</b></h4>";
//                            price.setText(Html.fromHtml(span1));

                            price.setText("Starting at ₹ "+minPrice  + " - ₹ " + maxPrice);
                            location.setText(exShowRoom_msg);


                            //msg.setText(exShowRoom.toString());
                            msg.setText("For On-Road prices contact your nearest");
                            dealer.setText("Car dealer.");
                            emitext.setText("EMI: ₹ "+emi);
                            String span = "For a loan of ₹  " + ss ;
                            roadside.setText("Roadside Assistance: " + toalFreeno);
                            c_overview_tv.setText(Html.fromHtml(span));
                            hyperlink();
                            c_overview_iv.setImageUrl(carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"), imageLoader);
                            Log.d("gbfdhgfhgf1","true");
                            Log.d("gbfdhgfhgf",carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"));
                        }

                    } else {
//                        String span1 = "<h4> Starting at ₹ <b> " + minPrice + "\n "+ exShowRoom_msg + "" + "</b></h4>";
//                        price.setText(Html.fromHtml(span1));

                        price.setText("Starting at ₹ "+minPrice);
                        location.setText(exShowRoom_msg);


                        //msg.setText(exShowRoom.toString());
                        msg.setText("For On-Road prices contact your nearest");
                        dealer.setText("Car dealer.");
                        String span =  "<h6> Roadside Assistance:  " + toalFreeno;
                        roadside.setText("Roadside Assistance: " + toalFreeno);
                        c_overview_tv.setText(Html.fromHtml(span));
                        c_overview_iv.setImageUrl(carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"), imageLoader);
                        hyperlink();
                        Log.d("gbfdhgfhgf1","true");
                        Log.d("gbfdhgfhgf",carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"));
                    }
                }

//                class MyLovelyOnClickListener implements View.OnClickListener
//                {
//
//                    int myLovelyVariable;
//                    public MyLovelyOnClickListener(int myLovelyVariable) {
//                        this.myLovelyVariable = myLovelyVariable;
//                    }
//
//                    @Override
//                    public void onClick(View v)
//                    {
//                        //read your lovely variable
//                    }
//
//                };


                Log.d("fgdfgdfhgdfh",carOverView.BrandModelName);
                c_overview_tv_heading.setText(carOverView.BrandModelName);
                C_Features.summary.setText(carOverView.BrandModelName);

                c_overview_iv.setImageUrl(carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"), imageLoader);
                Log.d("gbfdhgfhgf1","true");
                Log.d("gbfdhgfhgf",carOverView.PhotoSection.optJSONObject("Photoinfo").optString("Photo"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void hyperlink() {
        dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Locate_Dealer();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("test").add(R.id.container, fragment).commit();
            }
        });

    }
}
