package carteckh.carteckh.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 17-Dec-15.
 */
public class Profile extends Fragment {

    public static TabLayout tablayout;
    ViewPager viewpager;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.servicecenter, container, false);

        tablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewpager = (ViewPager) view.findViewById(R.id.pager);

        viewpager.setAdapter(new Adapter(getFragmentManager()));
        tablayout.setupWithViewPager(viewpager);
        //viewpager.beginFakeDrag();


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
                    fragment = new Home();
                    if (fragment != null) {

                        Bundle bundle1=new Bundle();
                        bundle1.putString("tncval", Constants.sharedPreferences.getString("tncvalnew",""));

                        fragment.setArguments(bundle1);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                        //  beginTransction(fragment2,"");
                    }
                    return true;
                }
                return false;
            }
        } );

        return view;
    }


    private class Adapter extends FragmentStatePagerAdapter {

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Fragment.instantiate(getActivity(), UserProfile.class.getName());
                case 1:
                    return Fragment.instantiate(getActivity(), Edit_Profile.class.getName());
                case 2:
                    return Fragment.instantiate(getActivity(), Change_Password.class.getName());
                case 3:
                    return Fragment.instantiate(getActivity(), ManageCarListing.class.getName());
                case 4:
                    return Fragment.instantiate(getActivity(), ResponseList1.class.getName());
                case 5:
                    return Fragment.instantiate(getActivity(), OfferListing.class.getName());
                case 6:
                    return Fragment.instantiate(getActivity(), AddOfferListing.class.getName());
                case 7:
                    return Fragment.instantiate(getActivity(), MyCurrentPackage1.class.getName());

            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Profile Details";
                case 1:
                    return "Edit Profile";
                case 2:
                    return "Change Password";
                case 3:
                    return "Manage CarListing";
                case 4:
                    return "Response List";
                case 5:
                    return "Offer Listing";
                case 6:
                    return "Add Offer Listing";
                case 7:
                    return "My Current Package";
            }


            return null;
        }
    }



}
