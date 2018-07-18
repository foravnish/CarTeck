package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by Andriod Avnish on 12/14/2016.
 */
public class ResponseList1 extends Fragment {


    GridView gridview;
    Dialog dialog;
    Adapter adapter;
    List<Constants> Response_data=new ArrayList<Constants>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.responce, container, false);

        dialog = new Dialog(ResponseList1.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gridview=(GridView)view.findViewById(R.id.gridview);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=myCarResponse&userId="+Constants.sharedPreferences.getString("user_id",""), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                //JSONObject jsonObject1 = jsonObject.optJSONObject("RoadSideAssistance");
                //if (jsonObject1.optString("success").equals("1")) {
                Response_data.clear();
                JSONArray jsonArray = jsonObject.optJSONArray("resArray");
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {


                        JSONObject jsonObject11 = jsonArray.getJSONObject(i);

                        if (jsonObject11.optString("success").equals("0")) {

                            Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                        }
                            Response_data.add(new Constants(jsonObject11.optString("userName"), jsonObject11.optString("userMobileNo"), jsonObject11.optString("brand"), jsonObject11.optString("model"), jsonObject11.optString("version"), jsonObject11.optString("date"), 0));

//                            tv_plananme.setText(jsonObject11.optString("title "));
//                            tv_days.setText(jsonObject11.optString("noDays")+" Days");
//                            tv_listing.setText(jsonObject11.optString("listing")+" Listing");
//                            tv_price.setText("₹ "+jsonObject11.optString("price"));

                        //RoadAssistance.add(new Constants(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName"), jsonObject11.optString("roadSideNumber"), jsonObject11.optString("icon")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter=new Adapter();
                    gridview.setAdapter(adapter);

                    //}

                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);


                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
                Toast.makeText(getActivity(), ""+ "Some problem occured pls try again", Toast.LENGTH_SHORT).show();

            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);



        return view;
    }

    static class ViewHolder {

        TextView s_no,username,mobile_no,car_details,date;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) ResponseList1.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return Response_data.size();
        }

        @Override
        public Object getItem(int position) {
            return Response_data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            convertView=inflater.inflate(R.layout.custom_response_data,parent,false);
            viewHolder=new ViewHolder();

            //viewHolder.s_no=(TextView)convertView.findViewById(R.id.s_no);
            viewHolder.username=(TextView)convertView.findViewById(R.id.username);
            viewHolder.mobile_no=(TextView)convertView.findViewById(R.id.mobile_no);
            viewHolder.car_details=(TextView)convertView.findViewById(R.id.car_details);
            viewHolder.date=(TextView)convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            //viewHolder.s_no.setTypeface(typeface);
            viewHolder.username.setTypeface(typeface);
            viewHolder.mobile_no.setTypeface(typeface);
            viewHolder.car_details.setTypeface(typeface);
            viewHolder.date.setTypeface(typeface);

           // viewHolder.s_no.setText(Response_data.get(position).getStr_roadsideNumber().toString());
            viewHolder.username.setText(Response_data.get(position).getStr_Brandid().toString());
            viewHolder.mobile_no.setText(Response_data.get(position).getStr_BrandName().toString());
            viewHolder.car_details.setText("Brand: "+Response_data.get(position).getStr_roadsideNumber().toString()+"\n"+"Model: "+Response_data.get(position).getStr_icon().toString()+"\n"+"Version: "+Response_data.get(position).getStr_postdate().toString());
            viewHolder.date.setText(Response_data.get(position).getStr_counter().toString());


//            viewHolder.buy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.carteckh.com/term-condition/"));
////                    startActivity(browserIntent);
//                    Toast.makeText(getActivity(), "Not Working yet", Toast.LENGTH_SHORT).show();
//                }
//            });

            return convertView;
        }
    }


}
