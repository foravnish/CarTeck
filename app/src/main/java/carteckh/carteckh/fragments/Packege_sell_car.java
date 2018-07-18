package carteckh.carteckh.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Packege_sell_car extends Fragment {

    Button free;
    Button packages;
    Dialog dialog,dialog2,dialog3;

    GridView gridview;

    List<Constants> Packeges=new ArrayList<Constants>();

    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_packege_sell_car, container, false);
        free=(Button)view.findViewById(R.id.free);
        packages=(Button)view.findViewById(R.id.packages);
        dialog = new Dialog(Packege_sell_car.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        tv_plananme=(TextView) view.findViewById(R.id.tv_plananme1);
//        tv_days=(TextView)view.findViewById(R.id.tv_days);
//        tv_listing=(TextView)view.findViewById(R.id.tv_listing);
//        tv_price=(TextView)view.findViewById(R.id.tv_price);
        gridview=(GridView)view.findViewById(R.id.gridview);

        Constants.sharedPreferences = getActivity().getSharedPreferences(Constants.Myprefrence, Context.MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=usedCarPlanList", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                //JSONObject jsonObject1 = jsonObject.optJSONObject("RoadSideAssistance");
                //if (jsonObject1.optString("success").equals("1")) {
                Packeges.clear();
                    JSONArray jsonArray = jsonObject.optJSONArray("planList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);


                              Packeges.add(new Constants(jsonObject11.optString("id"),jsonObject11.optString("planEncryptId"),jsonObject11.optString("title "),jsonObject11.optString("noDays"),jsonObject11.optString("listing"),jsonObject11.optString("price"),0));

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

////   userPackageList


/// End  userPackageList

        final StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.Url + "task=checkUsedCarPlan", new Response.Listener<String>() {
            JSONObject jsonObject = null;
            @Override
            public void onResponse(String s) {
                Constants.exitdialog(dialog);
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Constants.exitdialog(dialog);
                    Log.d("fdfgdfgdh",s+"");


                if (Constants.sharedPreferences.contains("user_id")) {

                    if (jsonObject.optString("Success").equals("1")) {

                        Log.d("RSSB", s + "");
                        Fragment fragment = new Sell_your_Car();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.add(R.id.container, fragment).addToBackStack(null).commit();
                        //Constants.editor.putString("id",jsonObject.optString("message"));
                        //Crouton.makeText(getActivity(), "" + jsonObject.optString("message"), Style.CONFIRM).show();
                        //Toast.makeText(getActivity(), "true" , Toast.LENGTH_SHORT).show();
                        Constants.editor.commit();

                    } else {


                        Button Yes_action,No_action;
                        TextView heading;
                        dialog2 = new Dialog(getActivity());
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.setContentView(R.layout.packages_dialog);


                        Yes_action=(Button)dialog2.findViewById(R.id.Yes_action);
                        No_action=(Button)dialog2.findViewById(R.id.No_action);
                        heading=(TextView)dialog2.findViewById(R.id.heading);


                        heading.setText(jsonObject.optString("message"));
                        Yes_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });

                        No_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog2.dismiss();
                            }
                        });
                        dialog2.show();
//                        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
////                        builder.setCancelable(false);
//
//                        builder.setMessage(jsonObject.optString("message"));
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                //
//                            }
//                        });
//                        builder.setNegativeButton("Buy More Package", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Fragment fragment = new Packege_sell_car();
//                                FragmentManager fm = getFragmentManager();
//                                FragmentTransaction ft = fm.beginTransaction();
//                                ft.replace(R.id.container, fragment).addToBackStack(null).commit();
//                            }
//                        });
//                        //builder.setIcon(R.drawable.discontinue);
//                        builder.create().show();

//                        Crouton.makeText(getActivity(), ""+jsonObject.optString("message"), Style.ALERT).show();
//                        Toast.makeText(getActivity(), ""+jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Crouton.makeText(getActivity(), "Please Login to Free listing", Style.ALERT).show();
                    Toast.makeText(getActivity(), "Please Login to Free listing", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Constants.handleVolleyError(volleyError, getActivity());

                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.INFO).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", Constants.sharedPreferences.getString("user_id", ""));
                params.put("package_id", "5");

                return params;

            }
        };
        stringRequest2.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest2.setShouldCache(false);


        free.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AppController.getInstance().addToRequestQueue(stringRequest2);

                Constants.editor.putString("packageid", "5");
                Constants.editor.commit();
                Constants.exitdialog(dialog);

//                dialog3 = new Dialog(Packege_sell_car.this.getActivity());
//                dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog3.setContentView(R.layout.uploadcar);
//                dialog3.show();

//                Fragment fragment=new Sell_your_Car1();
//                FragmentManager fm=getFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });
        packages.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Constants.sharedPreferences.contains("user_id")) {
                    Fragment fragment = new Sell_your_Car1();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, fragment).addToBackStack(null).commit();
                }
                else {
                    Crouton.makeText(getActivity(), "Please Login to Show Available Package", Style.ALERT).show();
                    Toast.makeText(getActivity(), "Please Login to Show Available Package", Toast.LENGTH_SHORT).show();

                }
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Packeges.get(position).getStr_Brandid().toString().equals("5")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.carteckh.com/payment-method.php?id=" + Packeges.get(position).getStr_BrandName().toString()+"&uid="+Constants.sharedPreferences.getString("user_id", "") + "&password=" + Constants.sharedPreferences.getString("user_pass", "") + "&loginFrom=app"));
                    startActivity(browserIntent);
                }
                //Toast.makeText(getActivity(), ""+Packeges.get(position).getStr_BrandName().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }
    static class ViewHolder {

        TextView tv_plananme,tv_days,tv_listing,tv_price,buy;
    }

    class Adapter extends BaseAdapter{

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Packege_sell_car.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return Packeges.size();
        }

        @Override
        public Object getItem(int position) {
            return Packeges.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            convertView=inflater.inflate(R.layout.custom_packages,parent,false);
            viewHolder=new ViewHolder();

            viewHolder.tv_plananme=(TextView)convertView.findViewById(R.id.tv_plananme1);
            viewHolder.tv_days=(TextView)convertView.findViewById(R.id.tv_days);
            viewHolder.tv_listing=(TextView)convertView.findViewById(R.id.tv_listing);
            viewHolder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
            viewHolder.buy=(TextView)convertView.findViewById(R.id.buy);
            convertView.setTag(viewHolder);

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_plananme.setTypeface(typeface);
            viewHolder.tv_days.setTypeface(typeface);
            viewHolder.tv_listing.setTypeface(typeface);
            viewHolder.tv_price.setTypeface(typeface);
            viewHolder.buy.setTypeface(typeface);

            viewHolder.tv_plananme.setText(Packeges.get(position).getStr_roadsideNumber().toString());
            viewHolder.tv_days.setText(Packeges.get(position).getStr_icon().toString()+" days");
            viewHolder.tv_listing.setText(Packeges.get(position).getStr_postdate().toString()+" List");
            viewHolder.tv_price.setText("₹ "+Packeges.get(position).getStr_counter().toString());

            if (Packeges.get(position).getStr_Brandid().toString().equals("5")){
                viewHolder.buy.setVisibility(View.INVISIBLE);
            }
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
