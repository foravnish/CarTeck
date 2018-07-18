package carteckh.carteckh.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import carteckh.carteckh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class congrarts extends Fragment {

Button car_list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.congrarts, container, false);
        car_list=(Button)view.findViewById(R.id.car_list);
        car_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment =new ManageCarListing();
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("key","avnisht1");
//                fragment.setArguments(bundle2);
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            }
        });


        return view;
    }

}
