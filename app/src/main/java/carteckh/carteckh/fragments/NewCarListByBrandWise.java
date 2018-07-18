package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
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
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 03-Dec-15.
 */
public class NewCarListByBrandWise extends Fragment {

    @InjectView(R.id.grid_view)
    ListView grid_view;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;
    @InjectView(R.id.text)
    TextView text;


    View view;
    Adapter adapter = null;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(NewCarListByBrandWise.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.didyouknow, container, false);
        ButterKnife.inject(this, view);

        adapter = new Adapter();

        grid_view.setAdapter(adapter);


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


        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Fragment fragment = new NewCarListReviews();
                Bundle bundle = new Bundle();
                bundle.putString("review_brand", getArguments().getString("id"));
                bundle.putString("review_model", RoadAssistance.get(position).getStr_Brandid().toString());
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();

            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=FindNewCars&brand_id=" + getArguments().getString("id").toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                RoadAssistance.clear();

                if (jsonObject.optString("success").equals("1")) {
                    text.setText("" + jsonObject.optString("heading").toString());

                    JSONArray jsonArray = jsonObject.optJSONArray("FindNewCars");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            RoadAssistance.add(new Constants(jsonObject11.optString("modelid"), jsonObject11.optString("brandid"), jsonObject11.optString("modelname"), jsonObject11.optString("price"), jsonObject11.optString("starrating"), jsonObject11.optString("car_photo"), 0));
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


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    static class ViewHolder {

        TextView tv_title, tv_price;
        Button btn;
        NetworkImageView img;
        RatingBar ratingBar;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) NewCarListByBrandWise.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
