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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

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
 * Created by developer on 30-Nov-15.
 */
public class Service_Center extends Fragment {

    String state_idvalue = "";
    @InjectView(R.id.spin_brand)
    Spinner spin_brand;
    @InjectView(R.id.spin_state)
    Spinner spin_state;

    @InjectView(R.id.listView)
    ListView listview;

    @InjectView(R.id.text)
    TextView text;


    @InjectView(R.id.linear)
    LinearLayout linear;

    @InjectView(R.id.search)
    Button Search;

    View view;
    Fragment fragment = null;

    List<Constants> Offers_list = new ArrayList<Constants>();

    List<Constants> States = new ArrayList<Constants>();
    List<String> State_Names = new ArrayList<String>();


    List<Constants> Brand = new ArrayList<Constants>();
    List<String> Brand_Names = new ArrayList<String>();

    Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        dialog = new Dialog(Service_Center.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.offers, container, false);

        ButterKnife.inject(this, view);
        Brand.add(new Constants("", "Select Brand"));
        Brand_Names.add("Select Brand");

        text.setText("Locate Service Centre");

//        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Brand_Names);
//        adapter_state.setDropDownViewResource(R.layout.spinnertext);
//        spin_brand.setAdapter(adapter_state);

        GettingData();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=StateList", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                JSONArray jsonArray = jsonObject.optJSONArray("state_list");
                States.add(new Constants("0000", "Select State"));
                State_Names.add("Select State");
                States.clear();
                State_Names.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("", jsonArray.toString());
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    States.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("state")));

                    State_Names.add(jsonObject1.optString("state"));

                    ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Brand_Names);
                    adapter_state.setDropDownViewResource(R.layout.spinnertext);
                    spin_brand.setAdapter(adapter_state);


                }
                ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, State_Names);
                adapter_state1.setDropDownViewResource(R.layout.spinnertext);
                spin_state.setAdapter(adapter_state1);
                if (Constants.sharedPreferences.contains("state_id")) {
                    for (int i = 0; i < States.size(); i++) {
                        if (Constants.sharedPreferences.getString("state_id", "").equals(States.get(i).getStr_Brandid().toString())) {
                            spin_state.setSelection(i);
                        }

                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);


        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    state_idvalue = States.get(position).str_Brandid.toString();
                    BrandList();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spin_state.getSelectedItemPosition() == 0) {
                    Crouton.makeText(getActivity(), "" + "Please Select State", Style.ALERT).show();
//                    Toast.makeText(getActivity(), "Please Select State", Toast.LENGTH_LONG).show();
                } else {
                    fragment = new ServiceCenter();
                    Bundle bundle = new Bundle();
                    bundle.putString("brand_id", Brand.get(spin_brand.getSelectedItemPosition()).getStr_Brandid().toString());
                    bundle.putString("state_id", States.get(spin_state.getSelectedItemPosition()).getStr_Brandid().toString());
                    bundle.putBoolean("status", true);
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (spin_state.getSelectedItemPosition() == 0) {
                    Toast.makeText(getActivity(), "Please Select State", Toast.LENGTH_LONG).show();
                } else {

                    fragment = new ServiceCenter();
                    Bundle bundle = new Bundle();
                    bundle.putString("brand_id", Offers_list.get(position).getStr_Brandid().toString());
                    bundle.putString("state_id", States.get(spin_state.getSelectedItemPosition()).getStr_Brandid());
                    bundle.putBoolean("status", true);
//                    bundle.putString("id", Offers_list.get(position).getStr_Brandid().toString());
//                    bundle.putBoolean("status", false);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();
                }

            }
        });

//        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            int mPosition = 0;
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int position = listview.getFirstVisiblePosition();
//
//                if (mPosition == position) {
//                    // Scrolled up
//                    linear.setVisibility(View.VISIBLE);
//
//                } else {
//                    // Scrolled down
//                    linear.setVisibility(View.GONE);
//
//                }
//            }
//        });


        return view;
    }

    private void BrandList() {

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=sortBrandForDealerServiceCenter&stateId=" + state_idvalue + "&brandFor=ServiceCentre", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                JSONObject jsonObject1 = jsonObject.optJSONObject("sortBrandList");
                if (jsonObject1.optString("success").equals("1")) {
                    Log.d("", jsonObject.toString());
                    JSONArray jsonArray = jsonObject1.optJSONArray("brandList");

                    Brand.clear();
                    Brand_Names.clear();


                    Brand.add(new Constants("", "Select Brand"));
                    Brand_Names.add("Select Brand");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            if (jsonObject11.optString("id").equals("")){
                                Crouton.makeText(getActivity(), "No Brand list in this State", Style.ALERT).show();
                            }
                            else {
                                Brand.add(new Constants(jsonObject11.optString("id"), jsonObject11.optString("name")));

                                Brand_Names.add(jsonObject11.optString("name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, Brand_Names);
                    adapter_state.setDropDownViewResource(R.layout.spinnertext);
                    spin_brand.setAdapter(adapter_state);
                    GettingData();

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

    }

    static class ViewHolder {


        TextView tv_servicename;

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Service_Center.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return Offers_list.size();
        }

        @Override
        public Object getItem(int position) {
            return Offers_list.get(position);
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


            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_servicename.setTypeface(typeface);
            viewHolder.tv_servicename.setText("" + Offers_list.get(position).getStr_BrandName().toString() + "(" + Offers_list.get(position).getStr_icon().toString() + ")");


            return convertView;
        }
    }
//
//    @Override
//    public void onResume() {
//        if(fragment != null)
//            getFragmentManager().beginTransaction().remove(fragment).commit();
//        super.onResume();
//    }


    void GettingData() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.Url + "task=Count_List_Brand&listfor=service_center"+"&state="+state_idvalue,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Offers_list.clear();
                Constants.exitdialog(dialog);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject jsonObject1 = jsonObject.optJSONObject("Count_List_Brand");


                    if (jsonObject1.optString("success").equals("1")) {
                        JSONArray jsonArray = jsonObject1.optJSONArray("brand_data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject11 = jsonArray.optJSONObject(i);


                            Log.d("", jsonArray.toString());

                            Offers_list.add(new Constants(jsonObject11.optString("getBrandId "), jsonObject11.optString("getBrandName"), jsonObject11.optString("icon"), String.valueOf(jsonObject11.optInt("cunt"))));


                        }
                        listview.setAdapter(new Adapter());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                Crouton.makeText(getActivity(), "Some problem occured pls try again", Style.ALERT).show();
            }
        });
        Constants.showdialog(dialog);

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
