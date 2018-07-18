package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 04-Dec-15.
 */
public class PhotogalleryViewPager extends Fragment {

    @InjectView(R.id.pager)
    ViewPager pager;
    View view;

    JSONArray jsonArray;
    List<Constants> Save;
    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;
    StringRequest stringRequest;
    int pos;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.upcomingviewpagte, container, false);

        ButterKnife.inject(this, view);

        stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=photoGalleryLike", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Constants.exitdialog(dialog);


                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Iterator<String> iterator = jsonObject.keys();

                    String keyvalue = iterator.next();
                    Constants sub = new Constants(Save.get(pos).getStr_Brandid(), Save.get(pos).getStr_BrandName(), Save.get(pos).getStr_roadsideNumber(), jsonObject.getString("incrementValue"));

                    Save.set(pos, sub);
                    viewPagerAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("imagesId", Save.get(pos).getStr_Brandid().toString());
                return map;
            }
        };

        try {
            JSONObject jsonObject = new JSONObject(getArguments().getString("json"));

            JSONArray jsonArray = jsonObject.optJSONArray("Listing");

            Save = new ArrayList<Constants>(jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                URL url = null;
                try {
                    url = new URL(jsonObject1.optString("photo"));
                    URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
                    url = uri.toURL();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                Save.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("title"), url.toString(), jsonObject1.optString("likes")));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewPagerAdapter = new ViewPagerAdapter();
        pager.setAdapter(viewPagerAdapter);
        pager.setCurrentItem(getArguments().getInt("pos"));



        return view;
    }

    public class ViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

        LayoutInflater layoutInflater;

        @Override
        public int getCount() {
            return Save.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            NetworkImageView networkImageView;
            TextView likecount,brandlist;
            Button like;

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.custom_photogallery, container, false);
            networkImageView = (NetworkImageView) view.findViewById(R.id.networkimg);
            likecount = (TextView) view.findViewById(R.id.likecount);
            brandlist = (TextView) view.findViewById(R.id.brandlist);
            like = (Button) view.findViewById(R.id.like);

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            networkImageView.setImageUrl(Save.get(position).getStr_roadsideNumber().toString(), imageLoader);
            likecount.setText("" + Save.get(position).getStr_icon().toString());
            brandlist.setText("" + Save.get(position).getStr_BrandName().toString());



            like.setOnClickListener(this);
            like.setTag(position);

            (container).addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((ScrollView) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.POSITION_NONE;
        }

        @Override
        public void onClick(View v) {
            pos = (Integer) v.getTag();
            Constants.showdialog(dialog);
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);


        }
    }
}
