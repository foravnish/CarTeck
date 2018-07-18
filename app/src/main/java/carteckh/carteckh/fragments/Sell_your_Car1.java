package carteckh.carteckh.fragments;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
 *
 */
public class Sell_your_Car1 extends Fragment {


    Dialog dialog,dialog2,dialog3;

    GridView gridview2;
    List<Constants> PackegesList=new ArrayList<Constants>();

    Adapter2 adapter2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.sell_your_car1, container, false);

        dialog = new Dialog(Sell_your_Car1.this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        gridview2=(GridView)view.findViewById(R.id.gridview2);

        Constants.sharedPreferences = getActivity().getSharedPreferences(Constants.Myprefrence, Context.MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();



        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=userPackageList&userId="+Constants.sharedPreferences.getString("user_id",""), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);

                if (jsonObject.optString("Success").equals("1")) {
                    PackegesList.clear();
                    JSONArray jsonArray = jsonObject.optJSONArray("packageList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);


                            PackegesList.add(new Constants(jsonObject11.optString("Plan Id"),jsonObject11.optString("Plan Name"),jsonObject11.optString("No of Days"),jsonObject11.optString("Total No of Listing"),jsonObject11.optString("No of Listing Available"),jsonObject11.optString("Price"),jsonObject11.optString("Package Start Date"),jsonObject11.optString("Package Expiry Date")));

//                            tv_plananme.setText(jsonObject11.optString("title "));
//                            tv_days.setText(jsonObject11.optString("noDays")+" Days");
//                            tv_listing.setText(jsonObject11.optString("listing")+" Listing");
//                            tv_price.setText("₹ "+jsonObject11.optString("price"));

                            //RoadAssistance.add(new Constants(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName"), jsonObject11.optString("roadSideNumber"), jsonObject11.optString("icon")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter2=new Adapter2();
                        gridview2.setAdapter(adapter2);
                        adapter2.notifyDataSetChanged();
                    }

                }
                else if (jsonObject.optString("Success").equals("0"))
                {
                    Button Yes_action,No_action;
                    TextView heading;
                    dialog3 = new Dialog(getActivity());
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setContentView(R.layout.packages_dialog);
                    dialog3.setCancelable(false);

                    Yes_action=(Button)dialog3.findViewById(R.id.Yes_action);
                    No_action=(Button)dialog3.findViewById(R.id.No_action);
                    heading=(TextView)dialog3.findViewById(R.id.heading);

                    heading.setText("You have not available package");
                    Yes_action.setVisibility(View.GONE);
//                    Yes_action.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Fragment fragment=new Packege_sell_car();
//                            FragmentManager fm=getFragmentManager();
//                            FragmentTransaction ft=fm.beginTransaction();
//                            ft.replace(R.id.container,fragment).addToBackStack(null).commit();
//                            dialog3.dismiss();
//                        }
//                    });

                    No_action.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Fragment fragment=new Packege_sell_car();
                            FragmentManager fm=getFragmentManager();
                            FragmentTransaction ft=fm.beginTransaction();
                            ft.replace(R.id.container,fragment).addToBackStack(null).commit();
                            dialog3.dismiss();
                        }
                    });
                    dialog3.show();
                }

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
        AppController.getInstance().addToRequestQueue(jsonObjectRequest2);

        return view;
    }

    static class ViewHolder2 {

        TextView plan_name,no_of_days,no_of_List,available_list,date_pur,date_exp;
        Button free;
    }

    class Adapter2 extends BaseAdapter {

        LayoutInflater inflater;

        Adapter2() {
            inflater = (LayoutInflater) Sell_your_Car1.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return PackegesList.size();
        }

        @Override
        public Object getItem(int position) {
            return PackegesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder2 viewHolder2;
            convertView=inflater.inflate(R.layout.custom_packages_paid,parent,false);
            viewHolder2=new ViewHolder2();

            viewHolder2.plan_name=(TextView)convertView.findViewById(R.id.plan_name);
            viewHolder2.no_of_days=(TextView)convertView.findViewById(R.id.no_of_days);
            viewHolder2.no_of_List=(TextView)convertView.findViewById(R.id.no_of_List);
            viewHolder2.available_list=(TextView)convertView.findViewById(R.id.available_list);
            viewHolder2.date_pur=(TextView)convertView.findViewById(R.id.date_pur);
            viewHolder2.date_exp=(TextView)convertView.findViewById(R.id.date_exp);

            viewHolder2.free=(Button)convertView.findViewById(R.id.free);

            convertView.setTag(viewHolder2);

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder2.plan_name.setTypeface(typeface);
            viewHolder2.no_of_days.setTypeface(typeface);
            viewHolder2.no_of_List.setTypeface(typeface);
            viewHolder2.available_list.setTypeface(typeface);
            viewHolder2.date_pur.setTypeface(typeface);
            viewHolder2.date_exp.setTypeface(typeface);


            viewHolder2.plan_name.setText(PackegesList.get(position).getBrand().toString());
            viewHolder2.no_of_days.setText(PackegesList.get(position).getModel().toString()+" Days");
            viewHolder2.no_of_List.setText(PackegesList.get(position).getVersion().toString());
            viewHolder2.available_list.setText(PackegesList.get(position).getOofer_from().toString());
            viewHolder2.date_pur.setText(PackegesList.get(position).getOffertitle().toString());
            viewHolder2.date_exp.setText(PackegesList.get(position).getDealer_id().toString());


            viewHolder2.free.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(getActivity(),PackegesList.get(position).getId() , Toast.LENGTH_SHORT).show();
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

                                        Fragment fragment = new Packege_sell_car();
                                        FragmentManager fm = getFragmentManager();
                                        FragmentTransaction ft = fm.beginTransaction();
                                        ft.replace(R.id.container, fragment).addToBackStack(null).commit();
                                        }
                                    });

                                    No_action.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog2.dismiss();
                                        }
                                    });
                                    dialog2.show();

//                                    android.support.v7.app.AlertDialog.Builder  builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
////                        builder.setCancelable(false);
//                                    //builder.setTitle("Choose You Plan");
//                                    //  builder.setTitle("State");
//                                    builder.setMessage(jsonObject.optString("message"));
//                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            //
//                                        }
//                                    });
//                                    builder.setNegativeButton("Buy More Package", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                        Fragment fragment = new Packege_sell_car();
//                                        FragmentManager fm = getFragmentManager();
//                                        FragmentTransaction ft = fm.beginTransaction();
//                                        ft.replace(R.id.container, fragment).addToBackStack(null).commit();
//                                        }
//                                    });
//                                    //builder.setIcon(R.drawable.discontinue);
//                                    builder.create().show();

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
                            params.put("package_id", PackegesList.get(position).getId());


                            Constants.editor.putString("packageid", PackegesList.get(position).getId());
                            Constants.editor.commit();

                            return params;

                        }
                    };
                    stringRequest2.setRetryPolicy(new DefaultRetryPolicy(27000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    stringRequest2.setShouldCache(false);
                    Constants.showdialog(dialog);
                    AppController.getInstance().addToRequestQueue(stringRequest2);

                }
            });


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