package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.actvities.Testing;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 02-Jan-16.
 */
public class UsedCar_Deatail extends Fragment {

    View view;


    public static final String url = Constants.Url + "task=usedCarDetail";


    private Testing activity;
    private Dialog dialog;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    FragmentManager fragmentManager;


    public static JSONObject MainPartInfo, PhotoList, RightSideBlock, Summary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (Testing) getActivity();
        dialog = new Dialog(activity);

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.carversionview, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        viewPager = (ViewPager) view.findViewById(R.id.pager);


        fragmentManager = getFragmentManager();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Constants.exitdialog(dialog);
                try {


                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("success") == 1) {

                        MainPartInfo = jsonObject.optJSONObject("MainPartInfo");
                        PhotoList = jsonObject.optJSONObject("PhotoList");
                        RightSideBlock = jsonObject.optJSONObject("RightSideBlock");
                        Summary = jsonObject.optJSONObject("Summary");
//                    Specification = jsonObject.optJSONObject("Specification");
//                    Features = jsonObject.optJSONObject("Features");


                        updateUI();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyErrdor) {
                Constants.exitdialog(dialog);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("id", getArguments().getString("id"));
                Log.d("dghfghfgh",getArguments().getString("id"));
                return map;
            }
        };
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);


        return view;
    }

    private void updateUI() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragmentManager);
        viewPagerAdapter.addFrag(new UsedCar_Overview(), "Overview");
        viewPagerAdapter.addFrag(new UsedCar_Summary(), "Summary");


//        viewPagerAdapter.addFrag(new UsedCar_Specificattion(), "Specification");
//        viewPagerAdapter.addFrag(new UsedCar_Features(), "Features");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }


}
