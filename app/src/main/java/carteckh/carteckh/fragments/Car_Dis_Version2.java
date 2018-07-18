package carteckh.carteckh.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Car_Dis_Version2 extends Fragment {


    GridView list_data;
    private static CarOverView carOverView;
    private JSONArray versionListDataInfoJsonArray;
    private JSONArray versionListDataInfoJsonArray2;
    List<Constants> Data=new ArrayList<Constants>();
    JSONObject jsonObject = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_car__dis__version, container, false);
        list_data=(GridView)view.findViewById(R.id.list_data);
        JSONObject versionListDataInfoJsonObject;
        try {
            if (carOverView.DiscontiVersionListData != null) {


                versionListDataInfoJsonObject = carOverView.DiscontiVersionListData.optJSONObject("VersionListDataInfo");
                versionListDataInfoJsonArray2 = versionListDataInfoJsonObject.optJSONArray("DiscontiVersionListDataInfo");
                Data.clear();
                for (int i = 0; i < versionListDataInfoJsonArray2.length(); i++) {
                    jsonObject = versionListDataInfoJsonArray2.optJSONObject(i);
                    Data.add(new Constants(jsonObject.optString("Id"), jsonObject.optString("Brand"), jsonObject.optString("BrandName"), jsonObject.optString("Model"), jsonObject.optString("ModelName"), jsonObject.optString("Version"), jsonObject.optString("Heading"), null));
                    list_data.setAdapter(new Adapter());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.d("fvsdgdf1",versionListDataInfoJsonArray2.toString());
        Log.d("fvsdgdf1",jsonObject.optString("Brand"));
        Log.d("fvsdgdf2",jsonObject.optString("Model"));
        Log.d("fvsdgdf3",jsonObject.optString("Id"));

        list_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new Car_Version_Overview();
                Bundle bundle = new Bundle();


                bundle.putString("brand_id",  Data.get(position).getBrand().toString());
                bundle.putString("model_id",  Data.get(position).getVersion().toString());
                bundle.putString("version_id",Data.get(position).getId().toString());

                Log.d("gfdgdf1", Data.get(position).getBrand().toString());
                Log.d("gfdgdf2", Data.get(position).getVersion().toString());
                Log.d("gfdgdf3",Data.get(position).getId().toString());

                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
            }
        });



        return  view;
    }

    static class ViewHolder {

        TextView text,text2;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Car_Dis_Version2.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override
        public int getCount() {
            return Data.size();
        }

        @Override
        public Object getItem(int position) {
            return Data.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.discountinue_data, parent, false);
                viewHolder = new ViewHolder();


                viewHolder.text = (TextView) convertView.findViewById(R.id.text);
                viewHolder.text2 = (TextView) convertView.findViewById(R.id.text2);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.text.setTypeface(typeface);
            viewHolder.text2.setTypeface(typeface);

            viewHolder.text.setText("" + Data.get(position).getModel()+" "+ Data.get(position).getOofer_from());
            viewHolder.text2.setText("" + Data.get(position).getPrice());


            return convertView;
        }
    }

}
