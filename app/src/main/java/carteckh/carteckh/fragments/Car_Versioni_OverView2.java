package carteckh.carteckh.fragments;

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
public class Car_Versioni_OverView2 extends Fragment {

    @InjectView(R.id.c_overview_iv)
    NetworkImageView c_overview_iv;
    @InjectView(R.id.c_overview_tv)
    TextView c_overview_tv;
    @InjectView(R.id.c_overview_tv_heading)
    CustomTextView c_overview_tv_heading;

    private ImageLoader imageLoader;
    ImageView dis;
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
    private static CarOverView2 carOverView;
    public Car_Versioni_OverView2() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        if (Car_Version_Overview2.PhotoSection != null && Car_Version_Overview2.rightSideInfo != null && Car_Version_Overview2.EmiSection != null) {
            try {
                final String toalFreeno = carOverView.RoadSideAssisData.getJSONObject("RoadSideAssisInfo").optString("HelpNo");
                final JSONObject informationJsonObject = Car_Version_Overview2.rightSideInfo.getJSONObject("Information");
                final String status = informationJsonObject.getString("Status");
                final String exShowRoom_msg = informationJsonObject.optString("ExShowRoomMsg");
                final String minPrice = informationJsonObject.getString("Price");
                final String exShowRoom = informationJsonObject.getString("ExShowRoom");
                String col="car dealer.";
                Log.d("fdgdgdfghfhgfh",status);
                if (status.equals("Discontinued")){
                    final JSONObject emiInfoJsonObject = Car_Version_Overview2.EmiSection.getJSONObject("EmiInfo");
                    final String emi = emiInfoJsonObject.getString("Emi");
                    final String forloanOf = emiInfoJsonObject.getString("ForloanOf");
                    String ss=forloanOf.substring(14);
                    dis.setVisibility(View.VISIBLE);

//                    String span1 = "<h4> Last recorded price: ₹ <b> " + minPrice + " \n"+ exShowRoom_msg + "" + "</b></h4>";
//                    price.setText(Html.fromHtml(span1));

                    price.setText("Last recorded price: ₹ "+minPrice);
                    location.setText(exShowRoom_msg);

                    msg.setText(exShowRoom.toString());
                    String span = "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + forloanOf + "</p>" + "<h6> Roadside Assistance: " + toalFreeno;

                    //String span = "<h4> Price:₹<b> " + minPrice + "</b></h4>" + exShowRoom + "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + forloanOf + "</p>";
                    //String span = "<h4> Last recorded price: ₹ <b> " + minPrice + " "+ "" + "</b></h4>" + exShowRoom + "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + "For a loan of ₹  " + ss + "</p>" ;
                    c_overview_tv_heading.setText(Car_Version_Overview2.PhotoSection.getJSONObject("Photoinfo").getString("Heading"));
                    //c_overview_tv.setText(Html.fromHtml(span));
                    c_overview_iv.setImageUrl(Car_Version_Overview2.PhotoSection.getJSONObject("Photoinfo").getString("Photo").replace(" ","%20"), imageLoader);
                }
                else {
                    final JSONObject emiInfoJsonObject = Car_Version_Overview2.EmiSection.getJSONObject("EmiInfo");
                    final String emi = emiInfoJsonObject.getString("Emi");
                    final String forloanOf = emiInfoJsonObject.getString("ForloanOf");

                    String ss = forloanOf.substring(14);

                    //String span = "<h4> Price:₹<b> " + minPrice + "</b></h4>" + exShowRoom + "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + forloanOf + "</p>";
//                    String span1 = "<h4> Starting at ₹ <b> " + minPrice + "\n "+ exShowRoom_msg + "" + "</b></h4>";
//                    price.setText(Html.fromHtml(span1));

                    price.setText("Starting at ₹ "+minPrice);
                    location.setText(exShowRoom_msg);

                    msg.setText("For On-Road prices contact your nearest");
                    dealer.setText("Car dealer.");
                    emitext.setText("EMI: ₹ "+emi);
                    String span = "For a loan of ₹  " + ss ;
                    roadside.setText("Roadside Assistance: " + toalFreeno);
                    c_overview_tv.setText(Html.fromHtml(span));
                    //String span = "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + "For a loan of ₹  " + ss + "</p>" + "<h6> Roadside Assistance: " + toalFreeno;


                    //String span = "<h4> Starting at ₹ <b> " + minPrice + " "+ exShowRoom_msg + "" + "</b></h4>" + exShowRoom +"<b> <font color='#76B9EC'>"+col +"</b> </font> "+ "<p><b>EMI: ₹ </b>" + emi + "</p><p>" + "For a loan of ₹  " + ss + "</p>";
                    c_overview_tv_heading.setText(Car_Version_Overview2.PhotoSection.getJSONObject("Photoinfo").getString("Heading"));
                    hyperlink();
                    c_overview_tv.setText(Html.fromHtml(span));
                    c_overview_iv.setImageUrl(Car_Version_Overview2.PhotoSection.getJSONObject("Photoinfo").getString("Photo").replace(" ","%20"), imageLoader);
                }
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
