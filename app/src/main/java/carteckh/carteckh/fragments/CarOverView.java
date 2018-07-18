package carteckh.carteckh.fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
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

import carteckh.carteckh.actvities.Testing;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;


/**
 * Created by deep on 12/18/2015.
 */
public class CarOverView extends Fragment {
    private static final String url = Constants.Url + "task=modelDetailWithVersion";

    //Declaring Objects
    public static JSONObject PhotoSection, PhotoListInterior, PhotoListExterior, rightSideInfo, EmiSection, VersionListData,
            RoadSideAssisData, SummaryData, IntroDuctionData, VideoData, ReviewList, ColourData, ReviewData;
    public static JSONObject DiscontiVersionListData;


    public static String BrandModelName;

    private Testing activity;
    private Dialog dialog;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    int count=1;
    Fragment fragment;
    Bundle args;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Testing) getActivity();
        dialog = new Dialog(activity);

//        String test = "\"DiscontiVersionListData\": {}";
//        try {
//            DiscontiVersionListData = new JSONObject(test);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.car_over_view, null);
        Constants.flag2=true;

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager = (ViewPager) view.findViewById(R.id.pager);

        viewPager.beginFakeDrag();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Constants.exitdialog(dialog);
               // Log.e("Response", jsonObject.toString());
                try {

                    BrandModelName = jsonObject.optString("BrandModelName");
                    PhotoSection = jsonObject.optJSONObject("PhotoSection");
                    PhotoListInterior = jsonObject.optJSONObject("PhotoListInterior");
                    PhotoListExterior = jsonObject.optJSONObject("PhotoListExterior");
                    rightSideInfo = jsonObject.optJSONObject("rightSideInfo");
                    EmiSection = jsonObject.optJSONObject("EmiSection");
                    VersionListData = jsonObject.optJSONObject("VersionListData1");
                    DiscontiVersionListData = jsonObject.optJSONObject("DiscontiVersionListData");
                    RoadSideAssisData = jsonObject.optJSONObject("RoadSideAssisData");
                    SummaryData = jsonObject.optJSONObject("SummaryData");
                    IntroDuctionData = jsonObject.optJSONObject("IntroDuctionData");
                    VideoData = jsonObject.optJSONObject("VideoData");
                    ReviewList = jsonObject.optJSONObject("ReviewList");
                    ColourData = jsonObject.optJSONObject("ColourData");
                    ReviewData = jsonObject.optJSONObject("ReviewData");

                    Constants.editor.putString("brandname",BrandModelName.toString());
                    Constants.editor.commit();
                    Log.d("fgfgfgfgfg", jsonObject.optJSONObject("VersionListData1").toString());
                    updateUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
                volleyError.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                Constants.brand_id = getArguments().getString("review_brand");
                Constants.model_id = getArguments().getString("review_model");

                params.put("brand_id", getArguments().getString("review_brand"));
                params.put("model_id", getArguments().getString("review_model"));
                params.put("model_name", getArguments().getString("review_name"));
                params.put("globalstate",getArguments().getString("stateid"));


                Log.d("ggbhgfh1",getArguments().getString("review_brand"));
                Log.d("ggbhgfh2",getArguments().getString("review_model"));
                Log.d("ggbhgfh3",getArguments().getString("review_name"));
                Log.d("ggbhgfh4",getArguments().getString("stateid"));

                return params;

            }
        };


        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);

/////////////***********

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
                    fragment = new NewCarListByBrandSwipe();
                    if (fragment != null) {

                        Bundle bundle1=new Bundle();
                        bundle1.putString("id", Constants.sharedPreferences.getString("idnew",""));

                        fragment.setArguments(bundle1);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                        //  beginTransction(fragment2,"");
                    }
                    return true;



//                    Fragment fragment = new NewCarListByBrandSwipe();
//                    if (fragment != null) {
//                        //Constants.stringStack.push("NewCarListByBrandSwipe");
//                        FragmentManager fragmentManager = getFragmentManager();
//                        Bundle bundle = new Bundle();
//
//                        bundle.putString("id", Constants.sharedPreferences.getString("idnew",""));
//                        //Log.d("rssb",roadSideAssistance.getBrandData().get(position).getGetBrandId());
//                        fragment.setArguments(bundle);
//                        fragmentManager.beginTransaction().addToBackStack("test").replace(R.id.container, fragment).commit();
//
//                    }
//                    return true;
                }
                return false;
            }
        } );

        //////////////*******************/////////
        return view;
    }

    public void updateUI() {

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new C_OverView(), "OverView");

//        if (VersionListData!=null && DiscontiVersionListData!=null) {
//            adapter.addFrag(new C_Version(), "Versions");
//        }
        if (EmiSection!=null) {
            adapter.addFrag(new C_Version(), "Versions");
        }
        if (DiscontiVersionListData!=null) {
            adapter.addFrag(new Car_Dis_Version(), "Discontinued Versions");
        }
        if (EmiSection != null) {

            adapter.addFrag(new C_Features(), "Summary");
            // C_Features.summary.setText(BrandModelName);

        }

        if (PhotoListInterior != null || PhotoListExterior != null) {

            adapter.addFrag(new C_Photoes(), "Photos");
        }
        adapter.addFrag(new C_Colors(), "Colours");

        if(VideoData != null) {
            adapter.addFrag(new Videos(), "Videos");
        }
//        if (ReviewData != null) {
//            adapter.addFrag(new C_Reviews(), "Reviews");
//        }

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }




//    @Override
//    public void onResume() {
//        super.onResume();
//        count--;
//        if(count<0){
//            //Toast.makeText(getActivity(), "testing...", Toast.LENGTH_SHORT).show();
//            Fragment fragment = new CarOverView();
//            Bundle bundle = new Bundle();
//            bundle.putString("review_brand", Constants.sharedPreferences.getString("review_brand",""));
//            bundle.putString("review_model",  Constants.sharedPreferences.getString("review_model",""));
//            bundle.putString("review_name", Constants.sharedPreferences.getString("review_name",""));
//            bundle.putString("stateid", Constants.sharedPreferences.getString("stateid",""));
//            fragment.setArguments(bundle);
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
//
//            //getActivity().getFragmentManager().beginTransaction().remove(this).commit();
//            //  count=1;
//
//        }
//    }


}


