package carteckh.carteckh.util;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.util.Stack;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.R;

/**
 * Created by developer on 20-Nov-15.
 */
public class Constants {
    public static final String Url = "https://www.carteckh.com/appdata/jsondata.php?";

    public static final String STATE_LIST = Url+"task=State_List";  //
    public static final String ROAD_ASSISTANCE_URL = Url+"task=roadsideAssistance";

    public static Boolean flag2=true;
    public static Boolean music_stats=true;
    public static Boolean sound=true;
    public static Stack<String> stringStack;
    static AlertDialog alertDialog;
    public static ProgressDialog progressDialog;
    public static final String Myprefrence = "MyPrefrence";
    public static final String Myprefrence1 = "MyPrefrence1";
    public static final String Myprefrence_choice = "MyPrefrence2";
    public static final String Myprefrence_did = "MyPrefrence3";
    public static final String Myprefrence_newlaunches = "MyPrefrence4";
    public static final String Myprefrence_upcomming = "MyPrefrence5";
    public static final String Myprefrence_usedcar = "MyPrefrence6";
    public static final String Myprefrence_Advance = "MyPrefrence_Advance";
    public static final String Myprefrence_new1 = "MyPrefrence_new1";
    public static final String Myprefrence_new2 = "MyPrefrence_new2";
    public static final String Myprefrence_new3 = "MyPrefrence_new3";

    public static final String Myprefrence_noti = "MyPrefrence_noti";


    public static boolean snacbar=true;

    public static boolean bol_check = false, bol_search = false;

    public Constants(String str_Brandid) {
        this.bol_check = bol_check;
        this.str_Brandid = str_Brandid;
    }




    public static void showProgressDialog(Activity ctx, String msg) {
        progressDialog = new ProgressDialog(ctx.getWindow().getContext());
        progressDialog.getLayoutInflater();
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public static void dismissDialog() {
        progressDialog.dismiss();
    }

    public static void handleVolleyError(VolleyError error,Activity actvity) {
        //if any error occurs in the network operations, show the TextView that contains the error message
        String str;
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            str = "Oops! Your Connection Timed Out, Seems there is no Network !!!";
            Log.e("VolleyError"," volly "+str);
            if (actvity!=null)
            {
                Crouton.makeText(actvity, "Oops! Your Connection Timed Out May be Network Messed Up", Style.ALERT).show();
            }
            //getResources(R.string.error_timeout).toString();

        } else if (error instanceof AuthFailureError) {
            str = "Oops! carteckh Says It Doesn\'t Recognize You";
            Log.e("VolleyError"," volly "+str);
            //mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof ServerError) {
            str = "Oops! carteckh Server Just Messed Up";
            Log.e("VolleyError"," volly "+str);
            // mTextError.setText(R.string.error_auth_failure);
            //TODO
        } else if (error instanceof NetworkError) {
            str = "Oops! Your Connection Timed Out May be Network Messed Up";
            Log.e("VolleyError"," volly "+str);
            //TODO
        } else if (error instanceof ParseError) {
            str = "Oops! Data Received Was An Unreadable Mess";
            Log.e("VolleyError"," volly "+str);
            //TODO
        }

    }
    public boolean isBol_check() {

        return bol_check;
    }

    public void setBol_check(boolean bol_check) {
        this.bol_check = bol_check;
    }

    public static String str_brandid = "", str_modelid = "", str_versionid = "";
    public static boolean flag = false, first_car = false, second_car = false, third_car = false;
    public static String comparcarurl = "", comparcarurl2 = "", comparcarurl3 = "";


    public static String brand_id = "", model_id = "";


    int countint=0;

    public int getCountint() {
        return countint;
    }

    public void setCountint(int countint) {
        this.countint = countint;
    }

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    public static SharedPreferences sharedPreferences1;
    public static SharedPreferences.Editor editor1;

    public static SharedPreferences sharedPreferences_choice;
    public static SharedPreferences.Editor editor_choice;

    public static SharedPreferences sharedPreferences_did;
    public static SharedPreferences.Editor editor_did;

    public static SharedPreferences sharedPreferences_newlaunch;
    public static SharedPreferences.Editor editor_newlaunch;

    public static SharedPreferences sharedPreferences_upcoming;
    public static SharedPreferences.Editor editor_upcoming;

    public static SharedPreferences sharedPreferences_usedcar;
    public static SharedPreferences.Editor editor_usedcar;

    public static SharedPreferences sharedPreferences_advance;
    public static SharedPreferences.Editor editor_advance;
    public static SharedPreferences sharedPreferences_noti;
    public static SharedPreferences.Editor editor_noti;

//    sharedPreferences1=Constants.this.getSharedPreferences(Constants.Myprefrence1,MODE_PRIVATE);
//    editor1=Constants.sharedPreferences1.edit();
//
//    editor1.putString("value","on");
//    editor1.commit();

//    Constants.editor1.putBoolean("value",true);

    public String str_Brandid;
    String str_BrandName;
    public String str_roadsideNumber;
    public String str_icon;
    public String str_postdate;
    public String sid,sstate;
    String str_counter;
    String id, brand, model, version, oofer_from, price, offertitle, dealer_id, dealer_name, dealer_email, dealer_city, dealer_area, dealer_mobile;
    String p_id,p_name,nod,nol,p_price;

//    public  Constants(String sid,String sstate)
//    {
//        this.sid=sid;
//        this.sstate=sstate;
//    }
//    public  String getsid()
//    {
//        return  sid;
//    }
//    public String getSstate()
//    {
//        return sstate;
//    }
//    public Constants(String p_id, String p_name, String nod, String nol, String p_price){
//        this.p_id=p_id;
//        this.p_name=p_name;
//        this.nod=nod;
//        this.nol=nol;
//        this.p_price=p_price;
//    }

    //************** 13 ****************
    public Constants(String id, String brand, String model, String version, String oofer_from, String price, String offertitle, String dealer_id, String dealer_name, String dealer_email, String dealer_city, String dealer_area, String dealer_mobile) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.version = version;
        this.oofer_from = oofer_from;
        this.price = price;
        this.offertitle = offertitle;
        this.dealer_id = dealer_id;
        this.dealer_name = dealer_name;
        this.dealer_email = dealer_email;
        this.dealer_city = dealer_city;
        this.dealer_area = dealer_area;
        this.dealer_mobile = dealer_mobile;
    }
//************** 11 ****************
    public Constants(String id, String brand, String name, String email, String mobile, String address, String fax_no, String landline_number, String service_center, String lat, String lng) {

        this.id = id;
        this.brand = brand;
        this.model = name;
        this.version = email;
        this.oofer_from = mobile;
        this.price = address;
        this.offertitle = fax_no;
        this.dealer_id = landline_number;
        this.dealer_name = service_center;
        this.dealer_email = lat;
        this.dealer_city = lng;


    }

    //// 5 /////
    public Constants(String principalAmount, String interestRate, String totalMonth, String emi, String interest) {
        this.id = principalAmount;
        this.brand = interestRate;
        this.model = totalMonth;
        this.version = emi;
        this.oofer_from = interest;
    }

    public Constants(String id, String brand, String name, String email, String mobile, String address, String fax_no, String landline_number) {

        this.id = id;
        this.brand = brand;
        this.model = name;
        this.version = email;
        this.oofer_from = mobile;
        this.price = address;
        this.offertitle = fax_no;
        this.dealer_id = landline_number;


    }

    public Constants(String id, String question, String name, String post_date, String review, String version, String familiarity_with_the_car, String exteriorStyle, String comfortSpace, String performance, String fuelEconomy, String features, String expertReviews, String overview) {

        this.id = id;
        this.str_postdate = question;
        this.str_Brandid = name;
        this.str_BrandName = post_date;
        this.str_counter = review;
        this.brand = version;
        this.oofer_from = familiarity_with_the_car;
        this.price = exteriorStyle;
        this.offertitle = comfortSpace;
        this.dealer_city = performance;
        this.dealer_email = fuelEconomy;
        this.dealer_area = features;
        this.dealer_mobile = expertReviews;
        this.dealer_id = overview;
    }

    public Constants(String id, String state) {
        this.str_Brandid = id;
        this.str_BrandName = state;
    }

    public Constants(String str_Brandid, String str_BrandName, String str_roadsideNumber, String str_icon, String str_postdate, String str_counter,int countint) {
        this.str_Brandid = str_Brandid;
        this.str_BrandName = str_BrandName;
        this.str_roadsideNumber = str_roadsideNumber;
        this.str_icon = str_icon;
        this.str_postdate = str_postdate;
        this.str_counter = str_counter;
        this.countint=countint;
    }

    public Constants(String str_Brandid, String str_BrandName, String str_roadsideNumber, String str_icon) {
        this.str_Brandid = str_Brandid;
        this.str_BrandName = str_BrandName;
        this.str_roadsideNumber = str_roadsideNumber;
        this.str_icon = str_icon;
    }

    public static void showdialog(Dialog dialog) {

        dialog.setContentView(R.layout.customprogress);
        dialog.setCanceledOnTouchOutside(false);
        ImageView img=(ImageView)dialog.findViewById(R.id.img);

        TranslateAnimation animation = new TranslateAnimation(-90.0f, 150.0f, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(1000);  // animation duration
        animation.setRepeatCount(Animation.INFINITE);  // animation repeat count
        animation.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img.startAnimation(animation);

        dialog.show();

    }

    public static void exitdialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    public static boolean Check_Email(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public String toString() {
        return str_BrandName;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOofer_from() {
        return oofer_from;
    }

    public void setOofer_from(String oofer_from) {
        this.oofer_from = oofer_from;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffertitle() {
        return offertitle;
    }

    public void setOffertitle(String offertitle) {
        this.offertitle = offertitle;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getDealer_email() {
        return dealer_email;
    }

    public void setDealer_email(String dealer_email) {
        this.dealer_email = dealer_email;
    }

    public String getDealer_city() {
        return dealer_city;
    }

    public void setDealer_city(String dealer_city) {
        this.dealer_city = dealer_city;
    }

    public String getDealer_area() {
        return dealer_area;
    }

    public void setDealer_area(String dealer_area) {
        this.dealer_area = dealer_area;
    }

    public String getDealer_mobile() {
        return dealer_mobile;
    }

    public void setDealer_mobile(String dealer_mobile) {
        this.dealer_mobile = dealer_mobile;
    }

    public String getStr_postdate() {

        return str_postdate;
    }

    public void setStr_postdate(String str_postdate) {
        this.str_postdate = str_postdate;
    }

    public String getStr_counter() {
        return str_counter;
    }

    public void setStr_counter(String str_counter) {
        this.str_counter = str_counter;
    }

    public String getStr_Brandid() {

        return str_Brandid;
    }

    public void setStr_Brandid(String str_Brandid) {
        this.str_Brandid = str_Brandid;
    }

    public String getStr_BrandName() {
        return str_BrandName;
    }

    public void setStr_BrandName(String str_BrandName) {
        this.str_BrandName = str_BrandName;
    }

    public String getStr_roadsideNumber() {
        return str_roadsideNumber;
    }

    public void setStr_roadsideNumber(String str_roadsideNumber) {
        this.str_roadsideNumber = str_roadsideNumber;
    }

    public String getStr_icon() {
        return str_icon;
    }

    public void setStr_icon(String str_icon) {
        this.str_icon = str_icon;
    }


}
