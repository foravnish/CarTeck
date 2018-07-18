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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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
public class Model_List extends Fragment {

    @InjectView(R.id.grid_view)
    ListView grid_view;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;

    @InjectView(R.id.text)
    TextView text;


    @InjectView(R.id.ImageUsedCar)
    ImageView ImageUsedCar;
//
//    @InjectView(R.id.linerlayout)
//    LinearLayout linerlayout;

    View view;
    Adapter adapter = null;

    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(Model_List.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.newcarlist, container, false);
        ButterKnife.inject(this, view);

        text.setText("Select Model");


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

                Constants.str_modelid = RoadAssistance.get(position).getStr_Brandid().toString();

                Fragment fragment = new Version_List();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("test").replace(R.id.container, fragment).commit();

            }
        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=modelByBrand&&brand_id=" + Constants.str_brandid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);

                RoadAssistance.clear();

//                try {
//                    JSONObject jsonObject1=jsonObject.getJSONObject("modelByBrand");
//                    if (jsonObject1.optString("success").equals("0")){
//                        Toast.makeText(getActivity(), "Not record found...", Toast.LENGTH_SHORT).show();
//                    }

                try {
                    JSONObject jsonObject1=jsonObject.getJSONObject("modelByBrand");
                    if (jsonObject1.optString("success").equals("1")){
                        JSONArray jsonArray = jsonObject1.optJSONArray("modelList");
                        //ImageUsedCar.setVisibility(View.GONE);
                        grid_view.setVisibility(View.VISIBLE);
                        tv_relative.setVisibility(View.VISIBLE);
                        ImageUsedCar.setVisibility(View.GONE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            RoadAssistance.add(new Constants(jsonObject11.optString("id "), jsonObject11.optString("model")));

                        }
                    }

                    else if (jsonObject1.optString("success").equals("0")){
                        text.setText("No Record");
                        tv_relative.setVisibility(View.GONE);
                        grid_view.setVisibility(View.GONE);
                        ImageUsedCar.setVisibility(View.VISIBLE);
                        //linerlayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        //Toast.makeText(getActivity(), "Not record found...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                JSONArray jsonArray = jsonObject.optJSONArray("modelByBrand");
//
////                        Log.d("fdgdfg", jsonArray.toString());
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            try {
//                                JSONObject jsonObject11 = jsonArray.getJSONObject(i);
//
//                                RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("model")));
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        }



//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }




                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(25000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache(false);
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        if (Constants.flag) {
            getFragmentManager().popBackStack();
        }
    }

    static class ViewHolder {

        TextView tv_servicename;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Model_List.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                convertView = inflater.inflate(R.layout.custom_service, parent, false);
                viewHolder = new ViewHolder();


                viewHolder.tv_servicename = (TextView) convertView.findViewById(R.id.tv_servicename);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_servicename.setTypeface(typeface);
            //viewHolder.tv_servicename.setText("");
            viewHolder.tv_servicename.setText("" + RoadAssistance.get(position).getStr_BrandName());


            return convertView;
        }

    }
}
