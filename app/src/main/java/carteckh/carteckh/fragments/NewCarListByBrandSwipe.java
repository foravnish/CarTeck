package carteckh.carteckh.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.baoyz.swipemenulistview.BaseSwipListAdapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.listeners.OnItemClicks;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;
import carteckh.carteckh.util.ShareApp;

/**
 * Created by developer on 03-Dec-15.
 */
public class NewCarListByBrandSwipe extends Fragment {


    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;
    @InjectView(R.id.text)
    TextView text;
//    @InjectView(R.id.nodata)
//    TextView nodata;


    @InjectView(R.id.ImageUsedCar)
    ImageView ImageUsedCar;

    @InjectView(R.id.relative)
    RelativeLayout relative;

    //@InjectView(R.id.grid_view)
    public static SwipeMenuListView grid_view;
    private OnItemClicks onItemClicks;

    View view;
    public  static  Adapter adapter = null;
    public static JsonObjectRequest jsonObjectRequest;
    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;
    private Stack<String> stringStack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(NewCarListByBrandSwipe.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.swipelayout, container, false);
        grid_view=(SwipeMenuListView)view.findViewById(R.id.grid_view);
        ButterKnife.inject(this, view);
        Constants.flag2=true;



        adapter = new Adapter();

        grid_view.setAdapter(adapter);
        stringStack = new Stack<>();

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(dp2px(90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);


                SwipeMenuItem wishlist = new SwipeMenuItem(getContext());

                wishlist.setBackground(new ColorDrawable(Color.rgb(0x00, 0x00, 0x00)));

                wishlist.setWidth(dp2px(40));

                wishlist.setIcon(R.drawable.ic_action_share);
                menu.addMenuItem(wishlist);
//
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x00,
//                        0x00, 0x00)));
//                // set item width
//                deleteItem.setWidth(dp2px(30));
//                // set a icon
//
//                deleteItem.setIcon(R.drawable.ic_action_favorite);
//                // add to menu
//                menu.addMenuItem(deleteItem);
            }
        };
        grid_view.setMenuCreator(swipeMenuCreator);


        grid_view.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open

                        Intent i = new Intent(getActivity(), ShareApp.class);
                        startActivity(i);

                        break;
//                    case 1:
//                        menu.getMenuItem(1).setBackground(android.R.drawable.ic_delete);
//                        RoadAssistance.get(position).setCountint(1);
//                        adapter.notifyDataSetChanged();
//                        break;
                }
                return false;
            }
        });


        grid_view.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
//                Toast.makeText(getActivity(), "start", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSwipeEnd(int position) {
//                Toast.makeText(getActivity(), "End", Toast.LENGTH_LONG).show();
            }
        });

        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new CarOverView();

                stringStack.push("NewCarListByBrandSwipe");
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
                onItemClicks.itemClick("NewCarListByBrandSwipe");
            }
        });
//
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


                    Fragment fragment = new CarOverView();
                    stringStack.push("NewCarListByBrandSwipe");
                    Bundle bundle = new Bundle();
                    bundle.putString("review_brand", getArguments().getString("id"));
                    bundle.putString("review_model", RoadAssistance.get(position).getStr_Brandid().toString());
                    bundle.putString("review_name", RoadAssistance.get(position).getStr_roadsideNumber().toString());
                    bundle.putString("stateid", Constants.sharedPreferences.getString("state_id",""));


                        Log.d("review_brand", getArguments().getString("id"));
                        Log.d("review_model", RoadAssistance.get(position).getStr_Brandid().toString());
                        Log.d("review_name", RoadAssistance.get(position).getStr_roadsideNumber().toString());
                        Log.d("stateid", Constants.sharedPreferences.getString("state_id",""));

                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
                    onItemClicks.itemClick("NewCarListByBrandSwipe");

                Constants.editor.putString("review_brand",getArguments().getString("id"));
                Constants.editor.putString("review_model",RoadAssistance.get(position).getStr_Brandid().toString());
                Constants.editor.putString("review_name", RoadAssistance.get(position).getStr_roadsideNumber().toString());

                Constants.editor.commit();
                }
        });



        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=FindNewCars&brand_id=" + getArguments().getString("id").toString() + "&globalstate=" + Constants.sharedPreferences.getString("state_id", ""), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                RoadAssistance.clear();

                Log.d("fdfgdh",jsonObjectRequest.toString());

                Log.d("ghfhfdfggdfghdh",Constants.sharedPreferences.getString("state_id", ""));

                if (jsonObject.optString("success").equals("1")) {
                    text.setText("" + jsonObject.optString("heading").toString());

                    JSONArray jsonArray = null;
                    try {
                        jsonArray = jsonObject.optJSONArray("FindNewCars");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    if (jsonArray!=null) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {

                                JSONObject jsonObject11 = jsonArray.getJSONObject(i);

                                if (jsonObject11.optString("maxprice").equals("null")) {
                                    Log.d("dgdfgdfhgf", "true");
                                    RoadAssistance.add(new Constants(jsonObject11.optString("modelid"), jsonObject11.optString("brandid"), jsonObject11.optString("modelname"), jsonObject11.optString("minprice"), jsonObject11.optString("starrating"), jsonObject11.optString("car_photo"), 0));
                                } else if (jsonObject11.optString("maxprice").equals("0")) {
                                    Log.d("dgdfgdfhgf", "true");
                                    RoadAssistance.add(new Constants(jsonObject11.optString("modelid"), jsonObject11.optString("brandid"), jsonObject11.optString("modelname"), jsonObject11.optString("minprice"), jsonObject11.optString("starrating"), jsonObject11.optString("car_photo"), 0));
                                } else {
                                    Log.d("dgdgfgfgdfhgf", "false");
                                    RoadAssistance.add(new Constants(jsonObject11.optString("modelid"), jsonObject11.optString("brandid"), jsonObject11.optString("modelname"), jsonObject11.optString("minprice") + " - ₹ " + jsonObject11.optString("maxprice"), jsonObject11.optString("starrating"), jsonObject11.optString("car_photo"), 0));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    else{
                        grid_view.setVisibility(View.GONE);
                        relative.setBackgroundColor(Color.WHITE);
                        ImageUsedCar.setVisibility(View.VISIBLE);
                    }

                }
                else if (jsonObject.optString("success").equals("0")) {
                    grid_view.setVisibility(View.GONE);
                    relative.setBackgroundColor(Color.WHITE);
                    ImageUsedCar.setVisibility(View.VISIBLE);
                    Log.d("dfhgfh","true");
                }
                if (RoadAssistance.size() != 0) {

                    adapter.notifyDataSetChanged();

                }

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment fragment = null;
                    //Fragment fragment2 = null;
                    //fragment2 = new Home();
                    fragment = new NewCarsList();
                    if (fragment != null) {

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                        //  beginTransction(fragment2,"");
                    }
                    return true;
                }
                return false;
            }
        } );



        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onItemClicks= (OnItemClicks) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
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
        RatingBar ratingBar;
        Button btn;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) NewCarListByBrandSwipe.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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


            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getStr_roadsideNumber().toString());
            if (RoadAssistance.get(position).getStr_icon().toString().equals("Discontinued - ₹ Discontinued")){
                Log.d("ghfhgfgdfghgfhfg",RoadAssistance.get(position).getStr_icon().toString());

                viewHolder.tv_price.setText("Discontinued");
            }
            else {

                viewHolder.tv_price.setText("Price: ₹ " + RoadAssistance.get(position).getStr_icon().toString());
            }
            Log.d("ghfhgfhgfhfg",RoadAssistance.get(position).getStr_icon().toString());

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.img.setImageUrl(RoadAssistance.get(position).getStr_counter().toString(), imageLoader);

            if (!RoadAssistance.get(position).getStr_postdate().equals(""))
                viewHolder.ratingBar.setRating(Float.parseFloat(RoadAssistance.get(position).getStr_postdate()));
            else
                viewHolder.ratingBar.setRating((float) 0.0);


            return convertView;
        }
    }

    class Adapter1 extends BaseSwipListAdapter {
        LayoutInflater inflater;

        Adapter1() {
            inflater = (LayoutInflater) NewCarListByBrandSwipe.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_title.setTypeface(typeface);
            viewHolder.tv_price.setTypeface(typeface);

            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getStr_roadsideNumber().toString());
            viewHolder.tv_price.setText("Price: " + RoadAssistance.get(position).getStr_icon().toString());
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.img.setImageUrl(RoadAssistance.get(position).getStr_counter().toString(), imageLoader);

            if (!RoadAssistance.get(position).getStr_postdate().equals("null"))
                viewHolder.ratingBar.setRating(Float.parseFloat(RoadAssistance.get(position).getStr_postdate()));
            else
                viewHolder.ratingBar.setRating((float) 0.0);


            return convertView;
        }
    }
}
