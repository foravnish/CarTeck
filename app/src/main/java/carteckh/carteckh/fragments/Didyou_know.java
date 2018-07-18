package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by developer on 23-Nov-15.
 */
public class Didyou_know extends Fragment {

    @InjectView(R.id.grid_view)
    ListView grid_view;
    @InjectView(R.id.tv_relative)
    RelativeLayout tv_relative;

    @InjectView(R.id.text)
    TextView text;

    @InjectView(R.id.linear)
    RelativeLayout linear;
    @InjectView(R.id.ImageUsedCar)
    ImageView ImageUsedCar;

    View view;
    Adapter adapter = null;

    @InjectView(R.id.search_btn)
    Button search_btn;

    @InjectView(R.id.search_edit)
    EditText search_edit;


    List<Constants> RoadAssistance = new ArrayList<Constants>();
    Dialog dialog;
    JsonObjectRequest jsonObjectRequest2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Initializing the dialog
        dialog = new Dialog(Didyou_know.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.didyou_know, container, false);
        ButterKnife.inject(this, view);

        text.setText("Did You Know ?");

        adapter = new Adapter();

        grid_view.setAdapter(adapter);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!search_edit.getText().toString().equals("")) {
                    jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=DidYouKnowSearch" + "&searchkeyword=" + search_edit.getText().toString(), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Constants.exitdialog(dialog);
                            RoadAssistance.clear();

                            if (jsonObject.optString("success").equals("1")) {
                                linear.setBackgroundColor(Color.parseColor("#2a2a2a"));
                                ImageUsedCar.setVisibility(View.GONE);
                                JSONArray jsonArray = jsonObject.optJSONArray("did_you_know_list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                                        RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("question"), jsonObject11.optString("answer"), jsonObject11.optString("post_by"), jsonObject11.optString("page_url"), jsonObject11.optString("post_date"),jsonObject11.optInt("read_counter")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            } else {
                                linear.setBackgroundColor(Color.WHITE);
                                ImageUsedCar.setVisibility(View.VISIBLE);
                               // Crouton.makeText(getActivity(), "Search Not Found", Style.ALERT).show();

                            }
                            adapter.notifyDataSetChanged();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();

                        }
                    });
                    Constants.showdialog(dialog);
                    AppController.getInstance().addToRequestQueue(jsonObjectRequest2);
                }
                else
                {
                    jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=DidYouKnowSearch", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Constants.exitdialog(dialog);
                            RoadAssistance.clear();

                            if (jsonObject.optString("success").equals("1")) {
                                linear.setBackgroundColor(Color.parseColor("#2a2a2a"));
                                ImageUsedCar.setVisibility(View.GONE);
                                JSONArray jsonArray = jsonObject.optJSONArray("did_you_know_list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                                        RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("question"), jsonObject11.optString("answer"), jsonObject11.optString("post_by"), jsonObject11.optString("page_url"), jsonObject11.optString("post_date"),jsonObject11.optInt("read_counter")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            } else {
                                Crouton.makeText(getActivity(), "Search Not Found", Style.ALERT).show();

                            }
                            adapter.notifyDataSetChanged();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();

                        }
                    });
                    Constants.showdialog(dialog);
                    AppController.getInstance().addToRequestQueue(jsonObjectRequest2);
                    //Crouton.makeText(getActivity(), "Please Enter Article Name", Style.ALERT).show();
                }
            }
        });



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
//
//        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                AlertDialog.Builder alertDialogBuilder;
//                alertDialogBuilder = new AlertDialog.Builder(getContext());
//
//                // set title
//                alertDialogBuilder.setTitle("" + RoadAssistance.get(position).getStr_BrandName());
//
//                // set dialog message
//                alertDialogBuilder
//                        .setMessage("" + RoadAssistance.get(position).getStr_roadsideNumber())
//                        .setCancelable(false)
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // if this button is clicked, just close
//                                // the dialog box and do nothing
//                                dialog.cancel();
//                            }
//                        });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();
//
//
//            }
//        });


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=DidYouKnow", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                RoadAssistance.clear();

                if (jsonObject.optString("success").equals("1")) {

                    JSONArray jsonArray = jsonObject.optJSONArray("did_you_know_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            RoadAssistance.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("question"), jsonObject11.optString("answer"), jsonObject11.optString("post_by"), jsonObject11.optString("page_url"), jsonObject11.optString("post_date"),jsonObject11.optInt("read_counter")));
//                            Log.d("gdfgdfgff", jsonObject11.optString("question").toString());
                            //Toast.makeText(getActivity(),jsonObject11.optString("answer").toString() , Toast.LENGTH_SHORT).show();
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
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();

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

        CustomTextView tv_title, tv_ans, tv_postby, tv_postby1, tv_postdate, tv_postdate1, tv_totalview, tv_totalview1, tv_readmore;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Didyou_know.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                convertView = inflater.inflate(R.layout.custom_didyouknow, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.tv_title = (CustomTextView) convertView.findViewById(R.id.tv_question);
                viewHolder.tv_ans = (CustomTextView) convertView.findViewById(R.id.tv_ans);
                viewHolder.tv_postby = (CustomTextView) convertView.findViewById(R.id.tv_postby);
                viewHolder.tv_postby1 = (CustomTextView) convertView.findViewById(R.id.tv_postby1);
                viewHolder.tv_postdate = (CustomTextView) convertView.findViewById(R.id.tv_postdate);
                viewHolder.tv_postdate1 = (CustomTextView) convertView.findViewById(R.id.tv_postdate1);
                viewHolder.tv_totalview = (CustomTextView) convertView.findViewById(R.id.tv_totalview);
                viewHolder.tv_readmore = (CustomTextView) convertView.findViewById(R.id.tv_readmore);
                viewHolder.tv_totalview1 = (CustomTextView) convertView.findViewById(R.id.tv_totalview1);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv_readmore.setTag(position);
            if (RoadAssistance.get(position).getStr_roadsideNumber().length() > 100) {
                int maxLength = 100;
                InputFilter[] fArray = new InputFilter[1];
                fArray[0] = new InputFilter.LengthFilter(maxLength);
                viewHolder.tv_ans.setFilters(fArray);
            }

            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_title.setTypeface(typeface);
            viewHolder.tv_ans.setTypeface(typeface);
            viewHolder.tv_postby1.setTypeface(typeface);
            viewHolder.tv_postdate1.setTypeface(typeface);
            viewHolder.tv_totalview1.setTypeface(typeface);

            viewHolder.tv_title.setText("" + RoadAssistance.get(position).getStr_BrandName().toString());
            //viewHolder.tv_ans.setText("" + RoadAssistance.get(position).getStr_roadsideNumber().toString());
            viewHolder.tv_ans.setText("" + Html.fromHtml(RoadAssistance.get(position).getStr_roadsideNumber().toString()));
            viewHolder.tv_postby1.setText("" + RoadAssistance.get(position).getStr_icon().toString());
            viewHolder.tv_postdate1.setText("" + RoadAssistance.get(position).getStr_counter().toString());
            viewHolder.tv_totalview1.setText("" + RoadAssistance.get(position).getCountint());




            viewHolder.tv_readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = (Integer) v.getTag();


                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=View_Counter&id=" + RoadAssistance.get(pos).getStr_Brandid().toString() + "&tbl=1", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {

                            if (jsonObject.optString("success").equals("1")) {
                                Fragment fragment = new Full_Overview();
                                Bundle bundle = new Bundle();
                                bundle.putString("tv_title", RoadAssistance.get(pos).getStr_BrandName().toString());
                                bundle.putString("tv_ans", RoadAssistance.get(pos).getStr_roadsideNumber().toString());
                                bundle.putString("tv_postby1", RoadAssistance.get(pos).getStr_icon().toString());
                                bundle.putString("tv_postdate1", RoadAssistance.get(pos).getStr_counter().toString());
                                bundle.putString("tv_url", RoadAssistance.get(pos).getStr_postdate().toString());

                                int i = Integer.parseInt(String.valueOf(RoadAssistance.get(pos).getCountint()));
                                i = i + 1;

                                bundle.putInt("tv_totalview1", i);
                                RoadAssistance.get(pos).setStr_counter(String.valueOf(i));
                                bundle.putString("id", RoadAssistance.get(pos).getStr_Brandid().toString());
                                bundle.putString("status", "1");
                                fragment.setArguments(bundle);
                                FragmentManager fragmentManager = getFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });

                    AppController.getInstance().addToRequestQueue(jsonObjectRequest);


                }
            });


            return convertView;
        }
    }
}
