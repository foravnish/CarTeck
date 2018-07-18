package carteckh.carteckh.fragments;

import android.app.Dialog;
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

import org.json.JSONArray;
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
 * Created by developer on 29-Dec-15.
 */
public class Car_Version_Overview2 extends Fragment{


    public static final String url = Constants.Url + "task=newVersionDetail";


    private Testing activity;
    private Dialog dialog;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    FragmentManager fragmentManager;


    public static JSONObject PhotoSection, PhotoListInterior, PhotoListExterior, rightSideInfo, EmiSection;
    public static JSONArray SummaryBlock, specificationBlock, FeatureBlock, Available_Colours, Video;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.carversionview, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.beginFakeDrag();

        fragmentManager = getFragmentManager();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Constants.exitdialog(dialog);
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    PhotoSection = jsonObject.optJSONObject("PhotoSection");
                    PhotoListInterior = jsonObject.optJSONObject("PhotoListInterior");
                    PhotoListExterior = jsonObject.optJSONObject("PhotoListExterior");
                    rightSideInfo = jsonObject.optJSONObject("rightSideInfo");
                    EmiSection = jsonObject.optJSONObject("EmiSection");
                    PhotoSection = jsonObject.optJSONObject("PhotoSection");
                    SummaryBlock = jsonObject.optJSONArray("SummaryBlock");
                    specificationBlock = jsonObject.optJSONArray("specificationBlock");
                    FeatureBlock = jsonObject.optJSONArray("FeatureBlock");
                    Available_Colours = jsonObject.optJSONArray("Available_Colours");
                    Video = jsonObject.optJSONArray("Video");

                    Log.d("fdgdfgdfhdf",SummaryBlock.toString());

                    updateUI();


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

                map.put("brand_id", getArguments().getString("brand_id"));
                map.put("model_id", getArguments().getString("model_id"));
                map.put("version_id", getArguments().getString("version_id"));
                map.put("globalstate", Constants.sharedPreferences.getString("state_id",""));

                Log.d("gfdgdfh1", getArguments().getString("brand_id"));
                Log.d("gfdgdfh2", getArguments().getString("model_id"));
                Log.d("gfdgdfh3", getArguments().getString("version_id"));

                return map;
            }
        };


        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest);
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event)   {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//
//                    Fragment NameofFragment = new CarOverView();
//
//                    FragmentTransaction  transaction=getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.container,NameofFragment);
//
//                    transaction.commit();
//
//                    return true;
//                }
//                return false;
//            }
//        });

//
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener( new View.OnKeyListener()
//        {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if( keyCode == KeyEvent.KEYCODE_BACK )
//                {
//                    FragmentManager fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.container, new CarOverView()).commit();
//
//                    return true;
//                }
//
//                return false;
//            }
//
//
//        } );

//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//                        Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
//
//                        Fragment fragment=new CarOverView();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("review_brand", Constants.sharedPreferences.getString("review_brand",""));
//                        bundle.putString("review_model",  Constants.sharedPreferences.getString("review_model",""));
//                        bundle.putString("review_name", Constants.sharedPreferences.getString("review_name",""));
//                        bundle.putString("stateid", Constants.sharedPreferences.getString("stateid",""));
//
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction ft=fragmentManager.beginTransaction();
//                        ft.replace(R.id.container,fragment).addToBackStack("test").commit();
//                        fragment.setArguments(bundle);
////                        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
////                                getActivity());
////                        alertDialog2.setMessage("Are you sure you want to exit?");
////                        alertDialog2.setCancelable(false);
////                        alertDialog2.setPositiveButton("Yes",
////                                new DialogInterface.OnClickListener() {
////                                    public void onClick(DialogInterface dialog, int id) {
////                                        Intent intent = new Intent(Intent.ACTION_MAIN);
////                                        intent.addCategory(Intent.CATEGORY_HOME);
////                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                        startActivity(intent);
////                                        System.exit(0);
////                                    }
////                                }).setNegativeButton("No", null).show();
////
//
//                        //Toast.makeText(getActivity(), "back", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                }
//                return false;
//            }
//        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Fragment fragment = new CarOverView2();
                    Bundle bundle = new Bundle();
                    bundle.putString("review_brand", Constants.sharedPreferences.getString("review_brand",""));
                    bundle.putString("review_model",  Constants.sharedPreferences.getString("review_model",""));
                    bundle.putString("review_name", Constants.sharedPreferences.getString("review_name",""));
                    bundle.putString("stateid", Constants.sharedPreferences.getString("stateid",""));
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("test").commit();
                    return true;
                }
                return false;
            }
        } );
        return view;



    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        count--;
//        if(count<0){
//            fragment = new CarOverView();
//            fragment.setArguments(args);
//            android.support.v4.app.FragmentManager frgManager = getActivity().getSupportFragmentManager();
//            frgManager.beginTransaction().replace(R.id.container, fragment)
//                    .commit();
//        }
//    }


    public void updateUI() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new Car_Versioni_OverView2(), "OverView");
        adapter.addFrag(new Car_Version_Photo2(), "Photos");
        adapter.addFrag(new Car_Version_Colors2(), "Colour");
        adapter.addFrag(new Car_Version_Summary2(), "Summary");
        adapter.addFrag(new Car_Version_Specification2(), "Specification");
        adapter.addFrag(new Car_Version_Feature2(), "Features");
        if (Video!=null) {
            adapter.addFrag(new Car_Version_Youtube2(), "Videos");
        }
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (Testing) getActivity();
        dialog = new Dialog(activity);

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


}
