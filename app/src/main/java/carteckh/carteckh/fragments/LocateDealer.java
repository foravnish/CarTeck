package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
 * Created by developer on 01-Dec-15.
 */
public class LocateDealer extends Fragment {
    @InjectView(R.id.grid_view)
    ListView listView;
    @InjectView(R.id.linear)
    LinearLayout linear;

    @InjectView(R.id.filter)
    ImageView filter;
    @InjectView(R.id.tv_relative)
    RelativeLayout relativeLayout;
    @InjectView(R.id.text)
    TextView text;

    @InjectView(R.id.ImageUsedCar)
    ImageView ImageUsedCar;

    View view;

    List<Constants> Service = new ArrayList<Constants>();
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        dialog = new Dialog(LocateDealer.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.didyouknow, container, false);

        ButterKnife.inject(this, view);
        filter.setVisibility(View.GONE);
        text.setText("");

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
//                int position = listView.getFirstVisiblePosition();
//                View v = listView.getChildAt(0);
//                int offset = (v == null) ? 0 : v.getTop();
//
//                if (mPosition < position || (mPosition == position && mOffset < offset)) {
//                    // Scrolled up
//                    relativeLayout.setVisibility(View.GONE);
//
//                } else {
//                    // Scrolled down
//                    relativeLayout.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=DealerServiceCentre", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Constants.exitdialog(dialog);
                if (jsonObject.optString("success").equals("1")) {
//                    text.setText("" + jsonObject.optString("heading"));
                    text.setText("" + jsonObject.optString("heading"));
                    Log.d("rssb",jsonObject.optString("heading"));

                    JSONArray jsonArray = jsonObject.optJSONArray("DealerServiceCentre");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            Service.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("brand"), jsonObject1.optString("dealer_web_link"), jsonObject1.optString("dealer_contact"), jsonObject1.optString("dealer_city"), jsonObject1.optString("showroom"), jsonObject1.optString("address"), jsonObject1.optString("fax_no"),jsonObject1.optString("email"),jsonObject1.optString("lat"),jsonObject1.optString("lng")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    listView.setAdapter(new Adapter());
                }
                else {
                    text.setText("No Result Found");
                    linear.setBackgroundColor(Color.WHITE);
                    ImageUsedCar.setVisibility(View.VISIBLE);
                    //Crouton.makeText(getActivity(), "Not Found", Style.ALERT).show();
                    //getFragmentManager().popBackStack();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "Some problem occured pls try again", Style.ALERT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();

                if (!getArguments().getBoolean("status")) {
                    map.put("brand_id", getArguments().getString("id"));
                    map.put("state_id", Constants.sharedPreferences.getString("state_id", ""));
                } else {
                    map.put("brand_id", getArguments().getString("brand_id"));
                    map.put("state_id", getArguments().getString("state_id"));
                }
                map.put("listfor", "dealer_list");

                return map;
            }
        };

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);


        return view;
    }


    static class ViewHolder {


        TextView tv_servicecenter, tv_brand, tv_brand1, tv_weblink, tv_weblink1, tv_contact, tv_contact1, tv_city, tv_city1,tv_add,tv_add1,tv_email,tv_email1,tv_fax,tv_fax1;

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) LocateDealer.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return Service.size();
        }

        @Override
        public Object getItem(int position) {
            return Service.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_locate_dealer, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.tv_servicecenter = (TextView) convertView.findViewById(R.id.tv_servicecenter);
                viewHolder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brandname);
                viewHolder.tv_brand1 = (TextView) convertView.findViewById(R.id.tv_brandname1);
                viewHolder.tv_weblink = (TextView) convertView.findViewById(R.id.tv_dealerweb);
                viewHolder.tv_weblink1 = (TextView) convertView.findViewById(R.id.tv_dealerweb1);
                viewHolder.tv_contact = (TextView) convertView.findViewById(R.id.tv_address);
                viewHolder.tv_contact1 = (TextView) convertView.findViewById(R.id.tv_address1);
                viewHolder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);
                viewHolder.tv_city1 = (TextView) convertView.findViewById(R.id.tv_city1);

                viewHolder.tv_add = (TextView) convertView.findViewById(R.id.tv_add);
                viewHolder.tv_add1 = (TextView) convertView.findViewById(R.id.tv_add1);

                viewHolder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);
                viewHolder.tv_email1 = (TextView) convertView.findViewById(R.id.tv_email1);

                viewHolder.tv_fax = (TextView) convertView.findViewById(R.id.tv_fax);
                viewHolder.tv_fax1 = (TextView) convertView.findViewById(R.id.tv_fax1);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_servicecenter.setTypeface(typeface);
            viewHolder.tv_brand1.setTypeface(typeface);
            viewHolder.tv_weblink1.setTypeface(typeface);
            viewHolder.tv_contact1.setTypeface(typeface);
            viewHolder.tv_city1.setTypeface(typeface);
            viewHolder.tv_add1.setTypeface(typeface);
            viewHolder.tv_email1.setTypeface(typeface);
            viewHolder.tv_fax1.setTypeface(typeface);


            viewHolder.tv_servicecenter.setText("" + Service.get(position).getPrice().toString());
            viewHolder.tv_brand1.setText("" + Service.get(position).getBrand().toString());

                Log.d("aaafgdfhgdfh",Service.get(position).getBrand().toString());
                Log.d("bbfgdfhgdfh",Service.get(position).getModel().toString());

            if (Service.get(position).getModel().toString().equals("")){
                viewHolder.tv_weblink1.setVisibility(View.GONE);
                viewHolder.tv_weblink.setVisibility(View.GONE);
            }
            else
            {
            viewHolder.tv_weblink1.setText("" + Service.get(position).getModel().toString());
                viewHolder.tv_weblink1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str="<u> http://"+Service.get(position).getModel().toString()+"</u>";
                        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(Html.fromHtml(str))));
                        startActivity(intent);
                    }
                });
            }
            if (Service.get(position).getVersion().toString().equals("")){viewHolder.tv_contact1.setVisibility(View.GONE);viewHolder.tv_contact.setVisibility(View.GONE);}
            else
            {
            viewHolder.tv_contact1.setText("" + Service.get(position).getVersion().toString());}
            if (Service.get(position).getOofer_from().toString().equals("")){viewHolder.tv_city1.setVisibility(View.GONE);viewHolder.tv_city.setVisibility(View.GONE);}
            else{
            viewHolder.tv_city1.setText("" + Service.get(position).getOofer_from().toString());}
            if (Service.get(position).getOffertitle().toString().equals("")){ viewHolder.tv_add1.setVisibility(View.GONE); viewHolder.tv_add.setVisibility(View.GONE);}
            else{
            viewHolder.tv_add1.setText("" + Service.get(position).getOffertitle().toString());}

            if (Service.get(position).getDealer_id().toString().equals("")){viewHolder.tv_fax1.setVisibility(View.GONE);viewHolder.tv_fax.setVisibility(View.GONE);}
            else{
                viewHolder.tv_fax1.setText("" + Service.get(position).getDealer_id().toString());}

            if ( Service.get(position).getDealer_name().toString().equals("")){viewHolder.tv_email1.setVisibility(View.GONE);viewHolder.tv_email.setVisibility(View.GONE);}

            else{
            viewHolder.tv_email1.setText("" + Service.get(position).getDealer_name().toString());}



            return convertView;
        }
    }
}
