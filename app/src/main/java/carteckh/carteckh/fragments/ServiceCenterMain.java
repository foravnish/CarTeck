package carteckh.carteckh.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import carteckh.carteckh.R;

/**
 * Created by developer on 27-Oct-15.
 */
public class ServiceCenterMain extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.servicecenter, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.pager);

        viewPager.setAdapter(new SectionPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onResume() {
        viewPager.setAdapter(new SectionPagerAdapter(getFragmentManager()));
        super.onResume();
    }

    public class SectionPagerAdapter extends FragmentStatePagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:


                    return Fragment.instantiate(getActivity(), Locate_Dealer.class.getName());
                case 1:
                default:
                    return Fragment.instantiate(getActivity(), Service_Center.class.getName());
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Locate Dealer";
                case 1:
                default:
                    return "Service Center";
            }
        }
    }
}
