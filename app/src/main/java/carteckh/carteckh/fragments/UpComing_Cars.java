package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 04-Dec-15.
 */
public class UpComing_Cars extends Fragment {

    @InjectView(R.id.grid_view)
    ListView grid_view;
    @InjectView(R.id.linear)
    LinearLayout linear;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.filter)
    ImageView filter;
    String str_json = "";

    @InjectView(R.id.spin_brand)
    Spinner spin_brand;
    @InjectView(R.id.search_btn)
            Button search_btn;
    List<Constants> Brand=new ArrayList<Constants>();
    List<String> Brand_Names = new ArrayList<String>();
    String val;
    @InjectView(R.id.ImageUsedCar)
    ImageView ImageUsedCar;
    View view;
    Adapter adapter = null;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(UpComing_Cars.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.didyouknow, container, false);
        ButterKnife.inject(this, view);
        filter.setVisibility(View.GONE);

        adapter = new Adapter();

        grid_view.setAdapter(adapter);

        LinearLayout linerlayout=(LinearLayout)view.findViewById(R.id.linerlayout);

        spin_brand.setVisibility(View.VISIBLE);
        search_btn.setVisibility(View.VISIBLE);
        linerlayout.setVisibility(View.VISIBLE);

        grid_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            int mPosition = 0;
            int mOffset = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int position = grid_view.getFirstVisiblePosition();
                View v = grid_view.getChildAt(0);
                int offset = (v == null) ? 0 : v.getTop();

                if (mPosition < position || (mPosition == position && mOffset < offset)) {
                    // Scrolled up
                    tv_relative.setVisibility(View.GONE);

                } else {
                    // Scrolled down
                    tv_relative.setVisibility(View.VISIBLE);

                }
            }
        });

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, "http://www.carteckh.com/appdata/jsondata.php?task=roadsideAssistance", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                JSONObject jsonObject1 = jsonObject.optJSONObject("RoadSideAssistance");
                if (jsonObject1.optString("success").equals("1")) {
                    Log.d("", jsonObject.toString());
                    JSONArray jsonArray = jsonObject1.optJSONArray("brand_data");

                    Brand.add(new Constants("0000", "Select Brand"));
                    Brand.add(new Constants("1111", "All Brands"));
                    Brand_Names.add("Select Brand");
                    Brand_Names.add("All Brands");
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


//        spin_brand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                val=Brand.get(position).getStr_Brandid().toString();
//            }
//        });

        spin_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                val=Brand.get(position).getStr_Brandid().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                //Fragment fragment2 = null;
                //fragment2 = new Home();
                fragment = new UpComing_Cars2();
                if (fragment != null) {
                    Bundle bundle =new Bundle();
                    bundle.putString("val",val.toString());

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                    //  beginTransction(fragment2,"");
                    fragment.setArguments(bundle);
                }


//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=UpcomingCar&brand_id="+val+"&checkshort_by=", null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        Constants.exitdialog(dialog);
//
//                        Fragment fragment = null;
//                        //Fragment fragment2 = null;
//                        //fragment2 = new Home();
//                        fragment = new UpComing_Cars2();
//                        if (fragment != null) {
//                            Bundle bundle =new Bundle();
//                            bundle.putString("val",val.toString());
//
//                            FragmentManager fragmentManager = getFragmentManager();
//                            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//                            //  beginTransction(fragment2,"");
//                            fragment.setArguments(bundle);
//                        }
//
//                        if (spin_brand.getSelectedItem().equals("Select Brand")){
//                            Crouton.makeText(getActivity(), "Please Select a Brand", Style.ALERT).show();
//                        }
//                        else if (spin_brand.getSelectedItem().equals("All Brands")){
//                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=UpcomingCar&brand_id=", null, new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject jsonObject) {
//                                    Constants.exitdialog(dialog);
//
//
//                                        RoadAssistance.clear();
//                                        if (jsonObject.optString("success").equals("1")) {
//                                            linear.setBackgroundColor(Color.parseColor("#2a2a2a"));
//                                            ImageUsedCar.setVisibility(View.GONE);
//                                            str_json = jsonObject.toString();
//                                            //Toast.makeText(getActivity(), str_json, Toast.LENGTH_SHORT).show();
//                                            Log.d("rssb", str_json);
//                                            text.setText("" + jsonObject.optString("heading").toString());
//
//                                            JSONArray jsonArray = jsonObject.optJSONArray("UpcomingCar");
//                                            for (int i = 0; i < jsonArray.length(); i++) {
//                                                try {
//                                                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                                                    RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("car_photo"), jsonObject11.optString("CarName"), jsonObject11.optString("launch_date"), jsonObject11.optString("min_price") + " - " + jsonObject11.optString("max_price"), jsonObject11.optString("short_desc"), 0));
//
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//
//                                            }
//
//
//                                        } else {
//                                            linear.setBackgroundColor(Color.WHITE);
//                                            ImageUsedCar.setVisibility(View.VISIBLE);
//                                            //Crouton.makeText(getActivity(), "No Car Found in "+spin_brand.getSelectedItem(), Style.ALERT).show();
//                                        }
//
//                                    adapter.notifyDataSetChanged();
//
//                                }
//                            }, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError volleyError) {
//                                    try
//                                    {
//                                        Constants.exitdialog(dialog);
//                                        Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
//                                    }catch (Exception e){e.printStackTrace();}
//
//                                }
//                            });
//
//                            Constants.showdialog(dialog);
//                          //  AppController.getInstance().addToRequestQueue(jsonObjectRequest);
//
//                        }
//                        else {
//                            RoadAssistance.clear();
//                            if (jsonObject.optString("success").equals("1")) {
//                                linear.setBackgroundColor(Color.parseColor("#2a2a2a"));
//                                ImageUsedCar.setVisibility(View.GONE);
//                                str_json = jsonObject.toString();
//                                //Toast.makeText(getActivity(), str_json, Toast.LENGTH_SHORT).show();
//                                Log.d("rssb", str_json);
//                                text.setText("" + jsonObject.optString("heading").toString());
//
//                                JSONArray jsonArray = jsonObject.optJSONArray("UpcomingCar");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    try {
//                                        JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//                                        RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("car_photo"), jsonObject11.optString("CarName"), jsonObject11.optString("launch_date"), jsonObject11.optString("min_price") + " - " + jsonObject11.optString("max_price"), jsonObject11.optString("short_desc"), 0));
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//
//
//                            } else {
//                                linear.setBackgroundColor(Color.WHITE);
//                                ImageUsedCar.setVisibility(View.VISIBLE);
//                                //Crouton.makeText(getActivity(), "No Car Found in "+spin_brand.getSelectedItem(), Style.ALERT).show();
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        try
//                        {
//                            Constants.exitdialog(dialog);
//                            Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
//                        }catch (Exception e){e.printStackTrace();}
//
//                    }
//                });
//
//                Constants.showdialog(dialog);
//                //AppController.getInstance().addToRequestQueue(jsonObjectRequest);
            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=UpcomingCar&brand_id=&checkshort_by=", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                RoadAssistance.clear();


                if (jsonObject.optString("success").equals("1")) {
                    str_json = jsonObject.toString();
                    //Toast.makeText(getActivity(), str_json, Toast.LENGTH_SHORT).show();
                    Log.d("rssb",str_json);
                    text.setText("" + jsonObject.optString("heading").toString());

                    JSONArray jsonArray = jsonObject.optJSONArray("UpcomingCar");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("car_photo"), jsonObject11.optString("CarName"), jsonObject11.optString("launch_date"), jsonObject11.optString("min_price") + " - ₹ " + jsonObject11.optString("max_price"), jsonObject11.optString("short_desc"),0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
               try
               {
                   Constants.exitdialog(dialog);
                   Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
               }catch (Exception e){e.printStackTrace();}

            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new UpComingCars_ViewPager();
                Bundle bundle = new Bundle();
                bundle.putString("str_json", str_json);

                bundle.putInt("pos", position);
                bundle.putString("id",(RoadAssistance.get(position).getStr_Brandid().toString()));

                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    static class ViewHolder {

        TextView tv_title, tv_price, tv_launch,read_more;
        NetworkImageView img;
        Button btn;

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) UpComing_Cars.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_newcars, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.tv_price = (TextView) convertView.findViewById(R.id.price);
                viewHolder.read_more = (TextView) convertView.findViewById(R.id.read_more);
                viewHolder.img = (NetworkImageView) convertView.findViewById(R.id.network);
                viewHolder.tv_launch = (TextView) convertView.findViewById(R.id.launchdate);
                viewHolder.btn=(Button)convertView.findViewById(R.id.seller_Detail);
                viewHolder.btn.setVisibility(View.GONE);
                viewHolder.read_more.setVisibility(View.VISIBLE);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_title.setTypeface(typeface);
            viewHolder.tv_price.setTypeface(typeface);
            viewHolder.tv_launch.setTypeface(typeface);
            viewHolder.read_more.setTypeface(typeface);

            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getStr_roadsideNumber().toString());
            viewHolder.tv_price.setText("Expected Price: ₹ " + RoadAssistance.get(position).getStr_postdate().toString());
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.img.setImageUrl(RoadAssistance.get(position).getStr_BrandName().toString(), imageLoader);
            viewHolder.tv_launch.setText("When to expect: " + RoadAssistance.get(position).getStr_icon().toString());

            return convertView;
        }
    }
}
