package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
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
 * Created by developer on 03-Dec-15.
 */
public class AdvancedCarSearch extends Fragment {


    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;
    @InjectView(R.id.text)
    TextView text;

    @InjectView(R.id.ImageUsedCar)
    ImageView imageView;
    @InjectView(R.id.linear)
    LinearLayout linear;

//    @InjectView(R.id.grid_view)
//    SwipeMenuListView grid_view;
    @InjectView(R.id.grid_view)
    ListView grid_view;
    @InjectView(R.id.filter)
    ImageView filter;


    View view;
    Adapter adapter = null;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(AdvancedCarSearch.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.advancecarsearch, container, false);
        ButterKnife.inject(this, view);



        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AdvanceFilter();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        adapter = new Adapter();

        grid_view.setAdapter(adapter);


//        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "open" item
////                SwipeMenuItem openItem = new SwipeMenuItem(
////                        getContext());
////                // set item background
////                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
////                        0xCE)));
////                // set item width
////                openItem.setWidth(dp2px(90));
////                // set item title
////                openItem.setTitle("Open");
////                // set item title fontsize
////                openItem.setTitleSize(18);
////                // set item title font color
////                openItem.setTitleColor(Color.WHITE);
////                // add to menu
////                menu.addMenuItem(openItem);
//
//
//                SwipeMenuItem wishlist = new SwipeMenuItem(getContext());
//
//                wishlist.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
//
//                wishlist.setWidth(dp2px(50));
//
//                wishlist.setIcon(R.drawable.ic_action_share);
//                menu.addMenuItem(wishlist);
//
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(dp2px(50));
//                // set a icon
//                deleteItem.setIcon(R.drawable.ic_action_favorite);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };

        //grid_view.setMenuCreator(swipeMenuCreator);

//        grid_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        // open
//
//                        Crouton.makeText(getActivity(), "first", Style.ALERT).show();
//
//                        break;
//                    case 1:
//                        // delete
////					delete(item);
//                        Crouton.makeText(getActivity(), "first", Style.CONFIRM).show();
//                        break;
//                }
//                return false;
//            }
//        });


//        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Fragment fragment = new CarOverView();
//
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
//            }
//        });

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


                Fragment fragment = new Car_Version_Overview3();
                Bundle bundle = new Bundle();
                bundle.putString("review_brand", RoadAssistance.get(position).getBrand().toString());
                bundle.putString("review_model", RoadAssistance.get(position).getModel().toString());
                bundle.putString("review_name", RoadAssistance.get(position).getOofer_from().toString());
                //bundle.putString("stateid", Constants.sharedPreferences.getString("state_id",""));

                Log.d("review_brand", RoadAssistance.get(position).getBrand().toString());
                Log.d("review_model", RoadAssistance.get(position).getModel().toString());
                Log.d("review_name", RoadAssistance.get(position).getOofer_from().toString());
                //Log.d("stateid", Constants.sharedPreferences.getString("state_id",""));

                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();

            }
        });


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=AdvancedCarSearch", new Response.Listener<String>() {
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
                    text.setText("Advanced Car Search");

                    linear.setBackgroundResource(R.drawable.background_bg);
                    imageView.setVisibility(view.GONE);

                    JSONArray jsonArray = jsonObject.optJSONArray("newCarList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("brandid"), jsonObject11.optString("modelid"), jsonObject11.optString("modelname"), jsonObject11.optString("versionid"), jsonObject11.optString("carPhoto"), jsonObject11.optString("carName"), jsonObject11.optString("price")));
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
                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                if (Constants.bol_search) {
                    Constants.bol_search = false;
                    Log.d("gfnhfgjf","true");

                    map.put("amount", getArguments().getString("minamount"));
                    map.put("fuel_type", getArguments().getString("fuel_type"));
                    map.put("seats_type", String.valueOf(getArguments().getString("seats_type")));
                    map.put("transmission_type", getArguments().getString("transmission_type"));
                    map.put("engine_displacement", getArguments().getString("engine_displacement"));
                    map.put("brands", getArguments().getString("brands"));
                    map.put("body_type", getArguments().getString("body_type"));

                    Log.d("amount", getArguments().getString("minamount"));
                    Log.d("fuel_type", getArguments().getString("fuel_type"));
                    Log.d("seats_type", String.valueOf(getArguments().getString("seats_type")));
                    Log.d("transmission_type", getArguments().getString("transmission_type"));
                    Log.d("engine_displacement", getArguments().getString("engine_displacement"));
                    Log.d("brands", getArguments().getString("brands"));
                    Log.d("body_type", getArguments().getString("body_type"));

                }

                return map;
            }
        };


        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment fragment = new Home();

                    Bundle bundle1=new Bundle();
                    bundle1.putString("tncval",Constants.sharedPreferences.getString("tncvalnew",""));

                    fragment.setArguments(bundle1);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("test").commit();
                    return true;
                }
                return false;
            }
        } );


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    static class ViewHolder {

        TextView tv_title, tv_price;
        NetworkImageView img;
        Button btn;
        RatingBar ratingBar;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) AdvancedCarSearch.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                viewHolder.img = (NetworkImageView) convertView.findViewById(R.id.network);
                viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);

                viewHolder.btn=(Button)convertView.findViewById(R.id.seller_Detail);
                viewHolder.btn.setVisibility(View.GONE);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }



            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getOffertitle().toString());
            viewHolder.tv_price.setText("Price: ₹ " + RoadAssistance.get(position).getDealer_id().toString());
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();

            viewHolder.img.setImageUrl(RoadAssistance.get(position).getPrice().toString().replace(" ","%20"), imageLoader);


//            if (!RoadAssistance.get(position).getStr_postdate().equals(""))
//                viewHolder.ratingBar.setRating(Float.parseFloat(RoadAssistance.get(position).getStr_postdate()));
//            else
//                viewHolder.ratingBar.setRating((float) 0.0);


            return convertView;
        }
    }

//    class Adapter1 extends BaseSwipListAdapter {
//        LayoutInflater inflater;
//
//        Adapter1() {
//            inflater = (LayoutInflater) AdvancedCarSearch.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        }
//
//        @Override
//        public int getCount() {
//            return RoadAssistance.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return RoadAssistance.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder viewHolder;
//            if (convertView == null) {
//                convertView = inflater.inflate(R.layout.custom_newcars, parent, false);
//                viewHolder = new ViewHolder();
//
//                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.title);
//                viewHolder.tv_price = (TextView) convertView.findViewById(R.id.price);
//                viewHolder.img = (NetworkImageView) convertView.findViewById(R.id.network);
//                viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingbar);
//
//                convertView.setTag(viewHolder);
//
//            } else {
//                viewHolder = (ViewHolder) convertView.getTag();
//            }
//
//            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
//            viewHolder.tv_title.setTypeface(typeface);
//            viewHolder.tv_price.setTypeface(typeface);
//
//            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getOffertitle().toString());
//            viewHolder.tv_price.setText("Price :" + RoadAssistance.get(position).getDealer_id().toString());
//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//            viewHolder.img.setImageUrl(RoadAssistance.get(position).getPrice().toString(), imageLoader);
//
//            if (!RoadAssistance.get(position).getStr_postdate().equals("null"))
//                viewHolder.ratingBar.setRating(Float.parseFloat(RoadAssistance.get(position).getStr_postdate()));
//            else
//                viewHolder.ratingBar.setRating((float) 0.0);
//
//
//            return convertView;
//        }
//    }
}
