package carteckh.carteckh.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.actvities.Testing;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 08-Dec-15.
 */
public class Used_CarList extends Fragment {

    @InjectView(R.id.grid_view)
    ListView grid_view;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;
    @InjectView(R.id.linear)
    LinearLayout linear;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.ImageUsedCar)
    ImageView imageView;


    String str_json = "";

    CoordinatorLayout coordinatorLayout;
    @InjectView(R.id.filter)
    ImageView filter;


    EditText mobile_otp;
    Button verify;

    AlertDialog alertDialog;
    View view;
    Adapter adapter = null;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;

    EditText mobile,name;
    String otpvalue;
    TextView messages;



    String Noti_data;

    String sellerid=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        //Initializing the dialog
        dialog = new Dialog(Used_CarList.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.didyouknow, container, false);
        ButterKnife.inject(this, view);

        text.setText("Used Car List");
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UsedCarSearch();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

            }
        });

        Constants.sharedPreferences = getActivity().getSharedPreferences(Constants.Myprefrence, getActivity().MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();


//        seller_detail.setOnClickListener(new View.OnClickListener() {
//            EditText mobile,name;
//            Button send_sms;
//            @Override
//            public void onClick(View view) {
//
//
//                AlertDialog.Builder builder=new AlertDialog.Builder(context);
//                builder.setTitle("Get Seller Details");
//
//                LayoutInflater layoutInflater=LayoutInflater.from(context);
//                View view1= layoutInflater.inflate(R.layout.sellerdetails,null);
//                mobile=(EditText)view1.findViewById(R.id.et_mobile_no);
//                name=(EditText)view1.findViewById(R.id.et_name);
//
//                builder.setView(view1);
//
//                alertDialog=builder.create();
//                alertDialog.show();
//
//            }
//        });


        adapter = new Adapter();

        grid_view.setAdapter(adapter);


//        grid_view.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int mPosition = 0;
//            int mOffset = 0;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int position = grid_view.getFirstVisiblePosition();
//                View v = grid_view.getChildAt(0);
//                int offset = (v == null) ? 0 : v.getTop();
//
//                if (mPosition < position || (mPosition == position && mOffset < offset)) {
//                    // Scrolled up
//                    tv_relative.setVisibility(View.GONE);
//
//                } else {
//                    // Scrolled down
//                    tv_relative.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });


        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new UsedCar_Deatail();
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();



            }
        });


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=UsedCarListSearch&state_id="+Constants.sharedPreferences.getString("state_id", ""), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {


                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Constants.exitdialog(dialog);
                RoadAssistance.clear();


                if (jsonObject.optString("success").equals("1")) {

                    linear.setBackgroundResource(R.drawable.background_bg);
                    imageView.setVisibility(view.GONE);
                    str_json = jsonObject.toString();
                    text.setText("Used Car List");

                    JSONArray jsonArray = jsonObject.optJSONArray("UsedCarList");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            //owner_statusowner_status
                            RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("car_photo"), jsonObject11.optString("model_year"), jsonObject11.optString("owner_status"), jsonObject11.optString("brand_name"), jsonObject11.optString("expected_price"), jsonObject11.optString("km_driven"),jsonObject11.optString("sellerId"), jsonObject11.optString("model_name"),jsonObject11.optString("version_name"),null));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
                else if(jsonObject.optString("success").equals("0"))
                {

                    //Crouton.makeText(getActivity(), "" + "not", Style.ALERT).show();

                    linear.setBackgroundColor(Color.WHITE);
                    imageView.setVisibility(view.VISIBLE);
//                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Welcome to Carteckh", Snackbar.LENGTH_SHORT);
//                    snackbar.show();
                      Crouton.makeText(getActivity(), "" + "No Result Found", Style.ALERT).show();
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "" + "Some problem occured please try again", Style.ALERT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                if (Constants.bol_search) {
                    Constants.bol_search = false;

                    map.put("state_id", getArguments().getString("state_id"));
//                    if (getArguments().getString("usedforstate").equals("state")){
//                        map.put("state_id", getArguments().getString("state_id"));
//                    }
                    Log.d("dffffff",getArguments().getString("state_id"));
                    //map.put("state_id", Constants.sharedPreferences.getString("state_id", ""));
                    map.put("price_range", getArguments().getString("price_range"));
                    map.put("car_age", getArguments().getString("car_age"));
                    map.put("kms_driven", getArguments().getString("kms_driven"));
                    map.put("fuel_type", getArguments().getString("fuel_type"));
                    map.put("owner", getArguments().getString("owner"));
                    map.put("brands", getArguments().getString("brands"));
                    map.put("transmission", getArguments().getString("transmission"));


                    Log.d("state_id", getArguments().getString("state_id"));
                    Log.d("price_range", getArguments().getString("price_range"));
                    Log.d("car_age", getArguments().getString("car_age"));
                    Log.d("kms_driven", getArguments().getString("kms_driven"));
                    Log.d("fuel_type", getArguments().getString("fuel_type"));
                    Log.d("owner", getArguments().getString("owner"));
                    Log.d("brands", getArguments().getString("brands"));
                    Log.d("transmission", getArguments().getString("transmission"));
                }

                return map;
            }
        };

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    static class ViewHolder {

        TextView tv_title, tv_price, tv_launch, tv_driven,read_more;
        NetworkImageView img;
        private Button seller_detail;


    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Used_CarList.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return RoadAssistance.size();
        }

        @Override
        public Object getItem(int position) {
            return RoadAssistance.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_newcars, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.tv_price = (TextView) convertView.findViewById(R.id.price);
                viewHolder.img = (NetworkImageView) convertView.findViewById(R.id.network);
                viewHolder.tv_launch = (TextView) convertView.findViewById(R.id.launchdate);
                viewHolder.tv_driven = (TextView) convertView.findViewById(R.id.driven);
                viewHolder.read_more = (TextView) convertView.findViewById(R.id.read_more);
                viewHolder.seller_detail=(Button)convertView.findViewById(R.id.seller_Detail);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.seller_detail.setOnClickListener(new View.OnClickListener() {
//                EditText mobile,name;
//                Button send_sms;
                @Override
                public void onClick(View view) {
                    PopUpDialog();
                    Constants.editor.putString("usedcarid2", RoadAssistance.get(position).getId().toString());
                    Constants.editor.putString("seller_id", RoadAssistance.get(position).getDealer_id().toString());
                    sellerid= RoadAssistance.get(position).getDealer_id().toString();
                    Constants.editor.commit();
                    Log.d("fgdgdfdfdgf",Constants.sharedPreferences.getString("usedcarid2",""));
                    Log.d("dfgfgfgfgf",Constants.sharedPreferences.getString("seller_id",""));


                    Log.d("dfsddsgdf", RoadAssistance.get(position).getDealer_id().toString());

                    Noti_data=RoadAssistance.get(position).getDealer_id().toString();
                    //Toast.makeText(getActivity(), RoadAssistance.get(position).getDealer_id().toString() , Toast.LENGTH_SHORT).show();
                    Log.d("jmd","OkTested");
                }
            });
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Log.d("gfnhjgfjf",RoadAssistance.get(position).getId().toString());
                    bundle.putString("id", RoadAssistance.get(position).getId().toString());

                    Constants.editor.putString("brand", RoadAssistance.get(position).getOofer_from().toString());
                    Constants.editor.putString("model", RoadAssistance.get(position).getDealer_name().toString());
                    Constants.editor.putString("version", RoadAssistance.get(position).getDealer_email().toString());

                    Constants.editor.putString("usedcarid", RoadAssistance.get(position).getId().toString());
                    Constants.editor.commit();
                    Fragment fragment = new UsedCar_Deatail();
                    fragment.setArguments(bundle);
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
                }
            });

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_title.setTypeface(typeface);
            viewHolder.tv_price.setTypeface(typeface);
            viewHolder.tv_launch.setTypeface(typeface);
            viewHolder.tv_driven.setTypeface(typeface);
            viewHolder.read_more.setTypeface(typeface);

            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getOofer_from().toString());
            viewHolder.tv_price.setText("Expected Price: ₹ " + NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(RoadAssistance.get(position).getPrice().toString())));
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.img.setImageUrl(RoadAssistance.get(position).getBrand().toString(), imageLoader);
            viewHolder.read_more.setText("Model Year: "+RoadAssistance.get(position).getModel().toString());
            viewHolder.tv_launch.setText("Kms Driven: "+RoadAssistance.get(position).getOffertitle().toString()+" Km");            //viewHolder.read_more.setText();
            viewHolder.tv_driven.setText("Owner Status: "+RoadAssistance.get(position).getVersion().toString());
            return convertView;
        }
    }

    public void PopUpDialog() {

        Button bt_getDetail;
        final Dialog popupdialog = new Dialog(getActivity());
        popupdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupdialog.setContentView(R.layout.sellerdetails);

        bt_getDetail = (Button)popupdialog.findViewById(R.id.send_sms);
        mobile=        (EditText) popupdialog.findViewById(R.id.et_mobile_no);
        name=        (EditText) popupdialog.findViewById(R.id.et_name);
        messages=(TextView) popupdialog.findViewById(R.id.messages);


        Log.d("fgdfghfhhghg",Constants.sharedPreferences.getString("seller_id",""));

        messages.setText("For privacy concerns, we hide owner details. Please fill this form to get it.");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=dealerSettings&userid=" + Constants.sharedPreferences.getString("user_id", ""), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);


                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("DealerInfo");
                    Iterator<String> iterator = jsonObject1.keys();


                    while (iterator.hasNext()) {
                        if (iterator.next().equals("success")) {
                            JSONArray jsonArray = jsonObject1.getJSONArray(iterator.next());
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                            name.setText(jsonObject2.getString("FirstName")+" "+jsonObject2.getString("LastName"));

                            mobile.setText(jsonObject2.getString("Mobile"));
                            messages.setText("For privacy concerns, we hide owner details. Please fill this form to get it.");
//                            et_mobilenumber.setText(jsonObject2.getString("Mobile"));
//                            et_pinnumber.setText(jsonObject2.getString("Pincode"));
//                            et_address.setText(jsonObject2.getString("Address"));
//                            state = jsonObject2.getString("State");

//                            final ArrayAdapter<Constants> adapter = new ArrayAdapter<Constants>(Edit_Profile.this.getContext(), R.layout.spinner_item, State1);
//                            adapter.setDropDownViewResource(R.layout.spinnertext);
//                            spin_state.setAdapter(adapter);
//                            for (int i = 0; i < State1.size(); i++) {
//                                if (state.trim().equals(State1.get(i).getStr_BrandName().trim().toString())) {
//                                    spin_state.setSelection(i);
//                                }
//
//                            }


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
            }
        });

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        bt_getDetail.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if(mobile.getText().toString().isEmpty()){
                    //Crouton.makeText(getActivity(), "" + "Enter Mobile Number", Style.ALERT).show();
                   Toast toast= Toast.makeText(getActivity(), "Enter Mobile Number", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }
                else if(!mobile.getText().toString().isEmpty() && name.getText().toString().isEmpty()){
                    //Crouton.makeText(getActivity(), "" + "Enter Name", Style.ALERT).show();
                    Toast toast= Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }
                else if(!mobile.getText().toString().isEmpty() && !name.getText().toString().isEmpty() && mobile.getText().toString().length()<10){
                    //Crouton.makeText(getActivity(), "" + "Enter 10 Digit Mobile No.", Style.ALERT).show();
                    Toast toast= Toast.makeText(getActivity(), "Enter 10 Digit Mobile No.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();

                }
                else
                {

                    popupdialog.dismiss();
                    final Dialog popupdialog1 = new Dialog(getActivity());
                    popupdialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    popupdialog1.setContentView(R.layout.sms_verify);
                    popupdialog1.show();
                    verify = (Button)popupdialog1.findViewById(R.id.verify);
                    mobile_otp=(EditText) popupdialog1.findViewById(R.id.mobile_otp);



                    final StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.Url+"task=sendOtp&mobileNumber="+mobile.getText().toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject=null;
                            try {
                                jsonObject=new JSONObject(response);
                                if(jsonObject.optString("success").equals("1")){
                                    otpvalue=jsonObject.optString("message");
                                    //Toast.makeText(getActivity(), "send "+otpvalue+" ", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    otpvalue=jsonObject.optString("message");
//                                Toast.makeText(getActivity(), "not "+otpvalue, Toast.LENGTH_SHORT).show();
                                    Crouton.makeText(getActivity(), "" + otpvalue, Style.CONFIRM).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(27000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest.setShouldCache(false);


                    //***** change this ****/////

                    AppController.getInstance().addToRequestQueue(stringRequest);

                    verify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mobile_otp.getText().toString().isEmpty()){
                                //Crouton.makeText(getActivity(), "" + "Enter Mobile Number", Style.ALERT).show();
                    Toast toast= Toast.makeText(getActivity(), "Please enter OTP", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                                Crouton.makeText(getActivity(), "Please enter OTP", Style.CONFIRM).show();

                            }

                            //***** change this ****/////
                                else if(mobile_otp.getText().toString().equals(otpvalue)){
                               // else if(mobile_otp.getText().toString().equals("aa")){


                                ////**** Call  sellerDetailNow JSON ******************//////////////

                                Log.d("fdgdfg1",Constants.sharedPreferences.getString("user_id", ""));
                                Log.d("fdgdfg2",Constants.sharedPreferences.getString("seller_id",""));
                                Log.d("hgfhjgjg",Constants.sharedPreferences.getString("usedcarid2",""));

                                Log.d("gdfhgdfh","hgfhgfj");
                                Log.d("fdgdfg3",mobile.getText().toString());
                                Log.d("fdgdfg4",name.getText().toString());

                                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, Constants.Url +"task=sellerDetailResponse&userid=" +sellerid +"&cid="+ Constants.sharedPreferences.getString("usedcarid2","")+"&mobile_verify="+mobile.getText().toString() +"&name="+name.getText().toString(), null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject jsonObject) {

                                        Constants.exitdialog(dialog);

                                        if (jsonObject.optString("Success").equals("1")){

                                            Fragment fragment =new Congrats2();
                                            Bundle bundle1=new Bundle();
                                            bundle1.putString("keyname",name.getText().toString());

                                            fragment.setArguments(bundle1);

                                            FragmentManager fm=getFragmentManager();
                                            FragmentTransaction ft=fm.beginTransaction();
                                            ft.replace(R.id.container,fragment).addToBackStack("test").commit();
                                            popupdialog1.dismiss();
                                            //PopUpDialog
//
//                                            Toast toast= Toast.makeText(getActivity(), "Successfully  Verified", Toast.LENGTH_SHORT);
//                                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
//                                            toast.show();
                                            Crouton.makeText(getActivity(), "Successfully Verified...", Style.CONFIRM).show();

                                            Log.d("fgffhfghgjgf",Constants.sharedPreferences.getString("seller_id",""));


                                            if (Constants.sharedPreferences.getString("seller_id","").equals(Constants.sharedPreferences.getString("user_id",""))) {

                                                JsonObjectRequest notification_request = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=carResponseNotifiationAlert&sellerId=" + Constants.sharedPreferences.getString("seller_id", ""), null, new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject jsonObject) {
                                                        // Constants.exitdialog(dialog);
                                                        Log.d("frgtdfghytrfyu", Constants.sharedPreferences.getString("seller_id", ""));
                                                        Log.d("gdfgfhfh", "true");
                                                        // Toast.makeText(getActivity(), "val: "+Constants.sharedPreferences.getString("seller_id",""), Toast.LENGTH_SHORT).show();
                                                        if (jsonObject.optString("Success").equals("1")) {

                                                            Log.d("gfdghfhfg", Constants.sharedPreferences.getString("seller_id", ""));
                                                            Log.d("gfdghfhfg2", Constants.sharedPreferences.getString("user_id", ""));


                                                            Log.d("fghfgfgfgf", "used true");
                                                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                                                            builder.setSmallIcon(R.drawable.notification_icon);
                                                            builder.setContentTitle("Buyer: "+jsonObject.optString("name"));
                                                            builder.setTicker("Buyer: "+jsonObject.optString("name"));
                                                            //builder.setContentText(jsonObject.optString("message"));
                                                            builder.setContentText("For "+jsonObject.optString("brand") + " " + jsonObject.optString("model_id") + " " + jsonObject.optString("version_id"));
                                                            builder.setDefaults(Notification.DEFAULT_ALL);
                                                            builder.setAutoCancel(true);

                                                            builder.setStyle(new NotificationCompat.BigTextStyle().bigText("For "+jsonObject.optString("brand") + " " + jsonObject.optString("model_id") + " " + jsonObject.optString("version_id")));
                                                            builder.setContentText("For "+jsonObject.optString("brand") + " " + jsonObject.optString("model_id") + " " + jsonObject.optString("version_id")).build();

                                                            Intent intent2 = new Intent(getActivity(), Testing.class);
                                                            intent2.putExtra("values", "usedcarlist");
                                                            Constants.editor.putString("name", jsonObject.optString("name"));
                                                            Constants.editor.putString("brand", jsonObject.optString("brand"));
                                                            Constants.editor.putString("model_id", jsonObject.optString("model_id"));
                                                            Constants.editor.putString("version_id", jsonObject.optString("version_id"));
                                                            Constants.editor.putString("mobile", jsonObject.optString("mobile"));
                                                            Constants.editor.commit();
                                                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
                                                            stackBuilder.addParentStack(Testing.class);
                                                            stackBuilder.addNextIntent(intent2);
                                                            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
                                                            builder.setContentIntent(pendingIntent);
                                                            NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                                                            Random random = new Random();
                                                            int randomNumber = random.nextInt(9999 - 1000) + 1000;
                                                            nm.notify(randomNumber, builder.getNotification());

                                                        } else if (jsonObject.optString("Success").equals("0")) {

                                                            Log.d("dvfdgvdFGDFGfgdfere", "0");
                                                        }

                                                        Crouton.makeText(getActivity(), jsonObject.optString("message"), Style.CONFIRM).show();

                                                        //adapter.notifyDataSetChanged();

                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError volleyError) {
//                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

                                                    }
                                                });
                                                //Constants.showdialog(dialog);
                                                Crouton.makeText(getActivity(), jsonObject.optString("message"), Style.CONFIRM).show();
                                                AppController.getInstance().addToRequestQueue(notification_request);

                                            }

//----*****************End  Notification URL ***************************//

                                        }
                                        else if (jsonObject.optString("Success").equals("0"))
                                        {

                                            Fragment fragment =new Congrats3();
                                            Bundle bundle1=new Bundle();
                                            bundle1.putString("keyname",name.getText().toString());
                                            bundle1.putString("msg",jsonObject.optString("message").toString());
                                            fragment.setArguments(bundle1);
                                            FragmentManager fm=getFragmentManager();
                                            FragmentTransaction ft=fm.beginTransaction();
                                            ft.replace(R.id.container,fragment).addToBackStack("test").commit();
                                            popupdialog1.dismiss();
                                            Crouton.makeText(getActivity(),jsonObject.optString("message"), Style.CONFIRM).show();
                                            //Toast.makeText(getActivity(), jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                                        }

                                    }


                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Constants.exitdialog(dialog);
                                        Crouton.makeText(getActivity(),"Some Error occur,Please try Again", Style.CONFIRM).show();
                                    }
                                });

                                Constants.showdialog(dialog);
                                AppController.getInstance().addToRequestQueue(jsonObjectRequest2);

                                ////**** End sellerDetailNow JSON ******************//////////////



//

                            }
                            else
                            {
                                Crouton.makeText(getActivity(), "Please Enter Valid OTP", Style.CONFIRM).show();
                                Toast toast= Toast.makeText(getActivity(), "Please Enter Valid OTP", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                toast.show();

                            }

                        }
                    });


                }


            }
        });

        popupdialog.show();


    }


}

