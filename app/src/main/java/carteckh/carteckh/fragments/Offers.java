package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 30-Nov-15.
 */
public class Offers extends Fragment {

    @InjectView(R.id.spin_brand)
    Spinner spin_brand;
    @InjectView(R.id.spin_state)
    Spinner spin_state;

    @InjectView(R.id.listView)
    ListView listview;

    @InjectView(R.id.text)
    TextView text;

    @InjectView(R.id.linear)
    LinearLayout linear;


    @InjectView(R.id.linerlayout)
    RelativeLayout linerlayout;

    @InjectView(R.id.search)
    Button Search;

    @InjectView(R.id.ImageUsedCar)
    ImageView imageView;

    View view;

    List<Constants> Offers_list = new ArrayList<Constants>();

    List<Constants> States = new ArrayList<Constants>();
    List<String> State_Names = new ArrayList<String>();

    List<Constants> Brand = new ArrayList<Constants>();
    List<String> Brand_Names = new ArrayList<String>();

    Dialog dialog;
    Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        dialog = new Dialog(Offers.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.offers, container, false);

        ButterKnife.inject(this, view);

         adapter=new Adapter();
        GettingOfferslist();
        text.setText("OFFER LIST");

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=Offer_List", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Constants.exitdialog(dialog);
                try {

                    Offers_list.clear();
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("success").equals("1")) {


                        JSONArray jsonArray = jsonObject.optJSONArray("offer_list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);

                            listview.setVisibility(View.VISIBLE);
                            Log.d("", jsonArray.toString());

                            Offers_list.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("brand"), jsonObject1.optString("model"), jsonObject1.optString("offer_from"), jsonObject1.optString("valid_till"), jsonObject1.optString("offertitle"), jsonObject1.optString("photo"), jsonObject1.optString("dealer_id"), jsonObject1.optString("dealer_name"), jsonObject1.optString("dealer_email"), jsonObject1.optString("dealer_mobile")));


                        }
                        listview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                    else
                    {
                        //Crouton.makeText(getActivity(), "" + "not", Style.ALERT).show();

                        linerlayout.setBackgroundColor(Color.WHITE);
                        imageView.setVisibility(view.VISIBLE);
                        listview.setVisibility(View.INVISIBLE);
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                if (spin_brand.getSelectedItemPosition() != 0) {
                    map.put("brand_id", Brand.get(spin_brand.getSelectedItemPosition()).getStr_Brandid().toString());
                } else {
                    map.put("brand_id", "");
                }

                map.put("state_id", States.get(spin_state.getSelectedItemPosition()).getStr_Brandid().toString());

                return map;
            }
        };

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);

        Search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               // Crouton.makeText(getActivity(), "" + spin_state.getSelectedItem()+" "+spin_brand.getSelectedItem(), Style.INFO).show();
                imageView.setVisibility(view.GONE);
//                if (spin_state.getSelectedItemPosition()>=0){
//                    Search.setEnabled(true);
//                    Toast.makeText(getActivity(), "0  "+spin_state.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Search.setEnabled(false);
//                    Toast.makeText(getActivity(), "1  "+spin_state.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
//                }

                //Toast.makeText(getActivity(), ""+spin_state.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();

                                Constants.showdialog(dialog);

                AppController.getInstance().addToRequestQueue(stringRequest);

            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=StateList", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {


                JSONArray jsonArray = jsonObject.optJSONArray("state_list");
                States.add(new Constants("0000", "Select State"));
                State_Names.add("Select State");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("", jsonArray.toString());
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    States.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("state")));

                    State_Names.add(jsonObject1.optString("state"));

                }
                ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,    State_Names);
                adapter_state1.setDropDownViewResource(R.layout.spinnertext);
                spin_state.setAdapter(adapter_state1);

//                if (Constants.sharedPreferences.contains("state_id")) {
//                    for (int i = 0; i < States.size(); i++) {
//                        if (Constants.sharedPreferences.getString("state_id", "").equals(States.get(i).getStr_Brandid().toString())) {
//                            Log.d("fdgdfgdfgh", String.valueOf(i));
//                            Log.d("fdgdfgdfgh2", Constants.sharedPreferences.getString("state_id", ""));
//                            Log.d("fdgdfgdfgh3", String.valueOf(Constants.sharedPreferences.contains("state_id")));
//
//                            spin_state.setSelection(i);
//                        }
//
//                    }
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, "http://www.carteckh.com/appdata/jsondata.php?task=roadsideAssistance", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                JSONObject jsonObject1 = jsonObject.optJSONObject("RoadSideAssistance");
                if (jsonObject1.optString("success").equals("1")) {
                    Log.d("", jsonObject.toString());
                    JSONArray jsonArray = jsonObject1.optJSONArray("brand_data");

                    Brand.add(new Constants("0000", "Select Brand"));
                    Brand_Names.add("Select Brand");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            Brand.add(new Constants(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName")));
                            Brand_Names.add(jsonObject11.optString("getBrandName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Brand_Names);
                    adapter_state.setDropDownViewResource(R.layout.spinnertext);
                    spin_brand.setAdapter(adapter_state);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest1);

//        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int mPosition = 0;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int position = listview.getFirstVisiblePosition();
////
//                if (mPosition == position) {
//                    // Scrolled up
//                    linear.setVisibility(View.VISIBLE);
//
//                } else {
//                    // Scrolled down
//                    linear.setVisibility(View.GONE);
//
//                }
//            }
//        });

        return view;
    }

    private void GettingOfferslist() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=Offer_List", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Constants.exitdialog(dialog);
                try {
                    //Offers_list.clear();
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.optString("success").equals("1")) {
                        JSONArray jsonArray = jsonObject.optJSONArray("offer_list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);

                            Log.d("", jsonArray.toString());

                            Offers_list.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("brand"), jsonObject1.optString("model"), jsonObject1.optString("offer_from"), jsonObject1.optString("valid_till"), jsonObject1.optString("offertitle"), jsonObject1.optString("photo"), jsonObject1.optString("dealer_id"), jsonObject1.optString("dealer_name"), jsonObject1.optString("dealer_email"), jsonObject1.optString("dealer_mobile")));

                        }
                        listview.setAdapter(adapter);

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

        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    static class ViewHolder {

        NetworkImageView networkImageView;

        TextView tv_brandname, tv_offersfrom1, tv_validtill1, tv_dealter1, tv_emailid1, tv_no1,name1, tv_price1, tv_dealer, tv_dealer1, tv_mailid, tv_mailid1, tv_city, tv_city1, tv_no;

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Offers.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return Offers_list.size();
        }

        @Override
        public Object getItem(int position) {
            return Offers_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_offers, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.networkImageView = (NetworkImageView) convertView.findViewById(R.id.networkimag);

                viewHolder.tv_brandname = (TextView) convertView.findViewById(R.id.tv_brandname);
                viewHolder.tv_offersfrom1 = (TextView) convertView.findViewById(R.id.tv_offersfrom1);
                viewHolder.tv_validtill1 = (TextView) convertView.findViewById(R.id.tv_validtill1);
                viewHolder.tv_dealter1 = (TextView) convertView.findViewById(R.id.tv_dealter1);
                viewHolder.tv_emailid1 = (TextView) convertView.findViewById(R.id.tv_emailid1);
                viewHolder.tv_no1 = (TextView) convertView.findViewById(R.id.tv_no1);
                viewHolder.name1 = (TextView) convertView.findViewById(R.id.name1);

//                viewHolder.tv_dealer = (TextView) convertView.findViewById(R.id.tv_dealter);
//                viewHolder.tv_dealer1 = (TextView) convertView.findViewById(R.id.tv_dealter1);
//                viewHolder.tv_mailid = (TextView) convertView.findViewById(R.id.tv_emailid);
//                viewHolder.tv_mailid1 = (TextView) convertView.findViewById(R.id.tv_emailid1);
//                viewHolder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
//                viewHolder.tv_city1 = (TextView) convertView.findViewById(R.id.tv_city1);
//                viewHolder.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
//                viewHolder.tv_no1 = (TextView) convertView.findViewById(R.id.tv_no1);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }



            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_brandname.setTypeface(typeface);
            viewHolder.tv_offersfrom1.setTypeface(typeface);
            viewHolder.tv_validtill1.setTypeface(typeface);
            viewHolder.tv_dealter1.setTypeface(typeface);
            viewHolder.tv_emailid1.setTypeface(typeface);
            viewHolder.tv_no1.setTypeface(typeface);
            viewHolder.name1.setTypeface(typeface);
//            viewHolder.offerby1.setTypeface(typeface);


            viewHolder.tv_brandname.setText(Offers_list.get(position).getBrand().toString());
            viewHolder.tv_offersfrom1.setText(Offers_list.get(position).getVersion().toString());
            viewHolder.tv_validtill1.setText(Offers_list.get(position).getOofer_from().toString());
            viewHolder.tv_dealter1.setText(Offers_list.get(position).getOffertitle().toString());
            viewHolder.tv_emailid1.setText(Offers_list.get(position).getDealer_name().toString());
            viewHolder.tv_no1.setText(Offers_list.get(position).getDealer_email().toString());
            viewHolder.name1.setText(Offers_list.get(position).getDealer_city().toString());
            //viewHolder.tv_no1.setText(Offers_list.get(position).getDealer_mobile());
            //viewHolder.offerby1.setText(Offers_list.get(position).getphoto());

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.networkImageView.setImageUrl(Offers_list.get(position).getPrice().toString().replace(" ","%20"), imageLoader);
            return convertView;
        }
    }

}
