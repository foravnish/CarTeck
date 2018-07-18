package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
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
 * Created by developer on 23-Nov-15.
 */
public class Road_Assistance extends Fragment {

    @InjectView(R.id.grid_view)
    GridView grid_view;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;


    View view;
    Adapter adapter = null;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;
    public  boolean flag=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(Road_Assistance.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.road_assistance, container, false);
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




                    final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom);

                    //dialog.setTitle("" + RoadAssistance.get(position).getStr_BrandName());
                    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    //dialog.setGravity(Gravity.CENTER_HORIZONTAL);

                    // set the custom dialog components - text, image and button
                     final TextView text = (TextView) dialog.findViewById(R.id.text);
                     final TextView heading = (TextView) dialog.findViewById(R.id.heading);

                     heading.setText(RoadAssistance.get(position).getStr_BrandName());
                     text.setText("" + RoadAssistance.get(position).getStr_roadsideNumber());



                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

                    Button call = (Button) dialog.findViewById(R.id.call);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + text.getText().toString()));
                            startActivity(callIntent);
                        }
                    });
                if (text.getText().length()<=0){
                    //Crouton.makeText(getActivity(), RoadAssistance.get(position).getStr_BrandName().toString()+" Roadside Assistance No." + "is Not Available", Style.ALERT).show();
                    //Toast.makeText(getActivity(), "not", Toast.LENGTH_SHORT).show();
                    dialog.setTitle("" + RoadAssistance.get(position).getStr_BrandName());

                    // set the custom dialog components - text, image and button

                    text.setText(RoadAssistance.get(position).getStr_roadsideNumber()+" Roadside Assistance No. is Not Available");
                    call.setVisibility(View.GONE);
                    dialog.show();
                    //dialog.dismiss();
                }
                else {
                    call.setVisibility(View.VISIBLE);
                    dialog.show();
                }
            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=roadsideAssistance", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                JSONObject jsonObject1 = jsonObject.optJSONObject("RoadSideAssistance");
                if (jsonObject1.optString("success").equals("1")) {


                    JSONArray jsonArray = jsonObject1.optJSONArray("brand_data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            Log.d("gfdgd",jsonObject11.toString());
                            //Log.d("dgfdgf",jsonObject11.optString("icon"));
                            RoadAssistance.add(new Constants(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName"), jsonObject11.optString("roadSideNumber"), jsonObject11.optString("icon")));
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

    static class ViewHolder {
        NetworkImageView imageview;
        TextView title;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Road_Assistance.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                convertView = inflater.inflate(R.layout.custom_roadsideassitance, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.imageview = (NetworkImageView) convertView.findViewById(R.id.iv_img);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_brandname);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.title.setTypeface(typeface);

            viewHolder.title.setText("" + RoadAssistance.get(position).getStr_BrandName());
            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            viewHolder.imageview.setImageUrl(RoadAssistance.get(position).getStr_icon(), imageLoader);

            return convertView;
        }
    }
}
