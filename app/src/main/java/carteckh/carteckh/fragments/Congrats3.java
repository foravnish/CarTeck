package carteckh.carteckh.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import carteckh.carteckh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Congrats3 extends Fragment {


    Button car_list;
    TextView et1_name,text1,text3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.congrats2, container, false);
        car_list=(Button)view.findViewById(R.id.car_list);
        et1_name=(TextView)view.findViewById(R.id.et1_name);
        text1=(TextView)view.findViewById(R.id.text1);
        text3=(TextView)view.findViewById(R.id.text3);

        Bundle bundle2 = getArguments();
        text1.setText("Sorry");
        et1_name.setText("Hello "+bundle2.getString("keyname")+" !");
        text3.setText(bundle2.getString("msg"));
        car_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment =new Used_CarList();

                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.container,fragment).addToBackStack("test").commit();
            }
        });

        return  view;
    }

}
