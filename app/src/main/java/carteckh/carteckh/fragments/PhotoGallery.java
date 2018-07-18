package carteckh.carteckh.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.listeners.OnItemClicks;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 25-Jan-16.
 */
public class PhotoGallery extends Fragment {

    @InjectView(R.id.recycle)
    GridView recyclerView;
    View view;
    Dialog dialog;
    private Bundle bundle = new Bundle();

    List<Constants> Save;
    private OnItemClicks onItemClicks;
    Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.photogallery, container, false);
        ButterKnife.inject(this, view);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=photoGallery", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);


                bundle.putString("json", jsonObject.toString());

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

                adapter = new Adapter();
//                recyclerView.setHasFixedSize(true);

//                mLayoutManager = new LinearLayoutManager(getActivity());
//                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
            }
        });

        Constants.showdialog(dialog);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);


        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new PhotogalleryViewPager();
                bundle.putInt("pos", position);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                //onItemClicks.itemClick("click");
            }
        });


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


    static class ViewHolder {
        NetworkImageView imageView;
        TextView heading;
    }


    public class Adapter extends BaseAdapter {

        List<Constants> data;
        Context context;
        LayoutInflater inflater;
        public Adapter() {
            inflater = (LayoutInflater) PhotoGallery.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

//        @Override
//        public Adapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customphotogallery, parent, false);
//            holder holder = new holder(view);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(Adapter.holder holder, int position) {
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }

        @Override
        public int getCount() {
            return Save.size();
        }

        @Override
        public Object getItem(int position) {
            return  Save.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            convertView = inflater.inflate(R.layout.customphotogallery, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (NetworkImageView) convertView.findViewById(R.id.image);
            viewHolder.heading = (TextView) convertView.findViewById(R.id.heading);

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.imageView.setImageUrl(Save.get(position).getStr_roadsideNumber(), imageLoader);

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.heading.setTypeface(typeface);
            viewHolder.heading.setText("" + Save.get(position).getStr_BrandName().toString());

            return convertView;
        }


//        public class holder extends RecyclerView.ViewHolder implements View.OnClickListener {
//            NetworkImageView imageView;
//            TextView heading;
//
//            public holder(View itemView) {
//                super(itemView);
//                imageView = (NetworkImageView) itemView.findViewById(R.id.image);
//                heading = (TextView) itemView.findViewById(R.id.heading);
//                imageView.setOnClickListener(this);
//            }
//
//            @Override
//            public void onClick(View v) {
//
//                Fragment fragment = new PhotogalleryViewPager();
//                bundle.putInt("pos", getPosition());
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//                onItemClicks.itemClick("click");
//
//            }
//        }
    }



}
