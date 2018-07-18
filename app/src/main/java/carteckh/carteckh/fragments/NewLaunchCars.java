package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import carteckh.carteckh.listeners.OnItemClicks;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 03-Dec-15.
 */
public class NewLaunchCars extends Fragment {

    @InjectView(R.id.grid_view)
    ListView grid_view;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;
    @InjectView(R.id.text)
    TextView text;
    @InjectView(R.id.filter)
    ImageView filter;



    View view;
    Adapter adapter = null;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;
    private OnItemClicks onItemClicks;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(NewLaunchCars.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.didyouknow, container, false);
        ButterKnife.inject(this, view);
        filter.setVisibility(View.GONE);

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


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=NewLaunches", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                RoadAssistance.clear();


                if (jsonObject.optString("success").equals("1")) {
                    text.setText("" + jsonObject.optString("heading").toString());

                    JSONArray jsonArray = jsonObject.optJSONArray("NewLaunches");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("brandId"), jsonObject11.optString("modelId"), jsonObject11.optString("versionId"), jsonObject11.optString("car_photo"),jsonObject11.optString("CarName"), jsonObject11.optString("launch_date"),jsonObject11.optString("showroom_price")+" "+jsonObject11.optString("showroom_value")));
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
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "Not Working yet", Toast.LENGTH_SHORT).show();

                Fragment fragment = new CarOverView2();
                if (fragment != null) {
                    Constants.stringStack.push("NewCarListByBrandSwipe");
                    FragmentManager fragmentManager = getFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("id", RoadAssistance.get(position).getId().toString());
                    bundle.putString("review_brand", RoadAssistance.get(position).getBrand().toString());
                    bundle.putString("review_model", RoadAssistance.get(position).getModel().toString());
                    bundle.putString("review_name", RoadAssistance.get(position).getVersion().toString());
                    bundle.putString("stateid", Constants.sharedPreferences.getString("state_id",""));
                    //bundle.putString("review_name", RoadAssistance.get(position).getVersion().toString());

                    //Log.d("rssb",roadSideAssistance.getBrandData().get(position).getGetBrandId());
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();

                    Constants.editor.putString("review_brand",RoadAssistance.get(position).getBrand().toString());
                    Constants.editor.putString("review_model", RoadAssistance.get(position).getModel().toString());
                    Constants.editor.putString("review_name", RoadAssistance.get(position).getVersion().toString());
                    Constants.editor.putString("stateid", Constants.sharedPreferences.getString("state_id",""));
                    Constants.editor.commit();

                }

            }
        });


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
                    fragment = new Home();
                    if (fragment != null) {

                        Bundle bundle1=new Bundle();
                        bundle1.putString("tncval",Constants.sharedPreferences.getString("tncvalnew",""));

                        fragment.setArguments(bundle1);
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
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    static class ViewHolder {

        TextView tv_title, tv_price, tv_launch;
        Button btn;
        NetworkImageView img;

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) NewLaunchCars.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                viewHolder.tv_launch = (TextView) convertView.findViewById(R.id.launchdate);
                viewHolder.btn=(Button)convertView.findViewById(R.id.seller_Detail);
                viewHolder.btn.setVisibility(View.GONE);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_title.setTypeface(typeface);
            viewHolder.tv_price.setTypeface(typeface);


            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getPrice().toString());
            viewHolder.tv_price.setText("Starting at ₹ " + RoadAssistance.get(position).getDealer_id().toString()+"\n"+"(Ex-Showroom, New Delhi)");
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.img.setImageUrl(RoadAssistance.get(position).getOofer_from().toString(), imageLoader);
            viewHolder.tv_launch.setText("Launch Date: " + RoadAssistance.get(position).getOffertitle().toString());


            return convertView;
        }
    }
}
