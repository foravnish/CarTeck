package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class ServiceCenter extends Fragment {
    @InjectView(R.id.grid_view)
    ListView listView;
    @InjectView(R.id.linear)
    LinearLayout linear;
    @InjectView(R.id.tv_relative)
    RelativeLayout relativeLayout;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.filter)
    ImageView filter;
    @InjectView(R.id.ImageUsedCar)
    ImageView ImageUsedCar;
    View view;

    List<Constants> Service = new ArrayList<Constants>();
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        dialog = new Dialog(ServiceCenter.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.didyouknow, container, false);
        ButterKnife.inject(this, view);
//        filter=(FloatingActionButton)view.findViewById(R.id.filter);
        filter.setVisibility(View.GONE);


        text.setText("");

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int mpositon = 0;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                int pos = listView.getFirstVisiblePosition();
//                if (mpositon == pos) {
//                    relativeLayout.setVisibility(View.VISIBLE);
//                } else {
//                    relativeLayout.setVisibility(View.GONE);
//                }
//
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
                    text.setText("" + jsonObject.optString("heading"));

                    JSONArray jsonArray = jsonObject.optJSONArray("DealerServiceCentre");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        try {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            Service.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("brand"), jsonObject1.optString("name"), jsonObject1.optString("email"), jsonObject1.optString("mobile"), jsonObject1.optString("address"), jsonObject1.optString("fax_no"), jsonObject1.optString("landline_number"), jsonObject1.optString("service_center"), jsonObject1.optString("service_city"), jsonObject1.optString("web_link"), jsonObject1.optString("lat"), jsonObject1.optString("lng")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    listView.setAdapter(new Adapter());
                }
                else
                {
                    text.setText("No Result Found");
                    linear.setBackgroundColor(Color.WHITE);
                    ImageUsedCar.setVisibility(View.VISIBLE);
                    //Crouton.makeText(getActivity(), "Not Found", Style.ALERT).show();
//                    getFragmentManager().popBackStack();
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
                map.put("listfor", "service_center");
                return map;
            }
        };


        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);


        return view;
    }

    static class ViewHolder {


        TextView tv_servicecenter, tv_brand, tv_brand1, tv_email, tv_email1, tv_address, tv_address1, tv_landlinenumber, tv_landlinenuber1, tv_fax, tv_fax1,city,city1,tv_name,tv_name1,tv_mobile,tv_mobile1,web_link,web_link1;

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) ServiceCenter.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_service_center, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.tv_servicecenter = (TextView) convertView.findViewById(R.id.tv_servicecenter);
                viewHolder.tv_brand = (TextView) convertView.findViewById(R.id.tv_brandname);
                viewHolder.tv_brand1 = (TextView) convertView.findViewById(R.id.tv_brandname1);
                viewHolder.tv_email = (TextView) convertView.findViewById(R.id.tv_emailid);
                viewHolder.tv_email1 = (TextView) convertView.findViewById(R.id.tv_emailid1);
                viewHolder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
                viewHolder.tv_address1 = (TextView) convertView.findViewById(R.id.tv_address1);
                viewHolder.tv_landlinenumber = (TextView) convertView.findViewById(R.id.tv_land);
                viewHolder.tv_landlinenuber1 = (TextView) convertView.findViewById(R.id.tv_land1);
                viewHolder.tv_fax = (TextView) convertView.findViewById(R.id.tv_fax);
                viewHolder.tv_fax1 = (TextView) convertView.findViewById(R.id.tv_fax1);
                viewHolder.city = (TextView) convertView.findViewById(R.id.city);
                viewHolder.city1 = (TextView) convertView.findViewById(R.id.city1);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_name1 = (TextView) convertView.findViewById(R.id.tv_name1);
                viewHolder.tv_mobile = (TextView) convertView.findViewById(R.id.tv_mobile);
                viewHolder.tv_mobile1 = (TextView) convertView.findViewById(R.id.tv_mobile1);
                viewHolder.web_link = (TextView) convertView.findViewById(R.id.web_link);
                viewHolder.web_link1 = (TextView) convertView.findViewById(R.id.web_link1);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_servicecenter.setTypeface(typeface);
            viewHolder.tv_brand1.setTypeface(typeface);
            viewHolder.tv_email1.setTypeface(typeface);
            viewHolder.tv_address1.setTypeface(typeface);
            viewHolder.tv_landlinenuber1.setTypeface(typeface);
            viewHolder.tv_fax1.setTypeface(typeface);
            viewHolder.city1.setTypeface(typeface);
            viewHolder.tv_name1.setTypeface(typeface);
            viewHolder.tv_mobile1.setTypeface(typeface);
            viewHolder.web_link1.setTypeface(typeface);



            viewHolder.tv_servicecenter.setText("" + Service.get(position).getDealer_name().toString());
            viewHolder.tv_brand1.setText("" + Service.get(position).getBrand().toString());

            if (Service.get(position).getVersion().toString().equals(""))
            {
                viewHolder.tv_email1.setVisibility(View.GONE);
                viewHolder.tv_email.setVisibility(View.GONE);
            }
            else{
            viewHolder.tv_email1.setText("" + Service.get(position).getVersion().toString());}
            if (Service.get(position).getPrice().toString().equals("")){ viewHolder.tv_address1.setVisibility(View.GONE);
                viewHolder.tv_address.setVisibility(View.GONE);}
            else {
            viewHolder.tv_address1.setText("" + Service.get(position).getPrice().toString());}
            if (Service.get(position).getDealer_id().toString().equals("")){viewHolder.tv_landlinenuber1.setVisibility(View.GONE);viewHolder.tv_landlinenumber.setVisibility(View.GONE);}
            else{
            viewHolder.tv_landlinenuber1.setText("" + Service.get(position).getDealer_id().toString());}
            if (Service.get(position).getOffertitle().toString().equals("")){ viewHolder.tv_fax1.setVisibility(View.GONE); viewHolder.tv_fax.setVisibility(View.GONE);}
            else{
            viewHolder.tv_fax1.setText("" + Service.get(position).getOffertitle().toString());}
            if (Service.get(position).getDealer_email().toString().equals("")){viewHolder.city1.setVisibility(View.GONE); viewHolder.city.setVisibility(View.GONE);
            }
            else{
            viewHolder.city1.setText("" + Service.get(position).getDealer_email().toString());}
            if (Service.get(position).getModel().toString().equals("")){viewHolder.tv_name1.setVisibility(View.GONE); viewHolder.tv_name.setVisibility(View.GONE);}
            else{
            viewHolder.tv_name1.setText("" + Service.get(position).getModel().toString());}



            if (Service.get(position).getDealer_city().toString().equals("")){viewHolder.web_link1.setVisibility(View.GONE); viewHolder.web_link.setVisibility(View.GONE);}
            else{
                viewHolder.web_link1.setText("" + Service.get(position).getDealer_city().toString());}
            if (Service.get(position).getOofer_from().toString().equals("")){ viewHolder.web_link1.setVisibility(View.GONE); viewHolder.web_link.setVisibility(View.GONE);

            }

            else{
            viewHolder.tv_mobile1.setText("" + Service.get(position).getOofer_from().toString());}

            return convertView;
        }
    }
}
