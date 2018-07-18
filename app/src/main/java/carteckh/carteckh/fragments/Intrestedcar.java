package carteckh.carteckh.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intrestedcar extends Fragment {


    TextView yourname,name,brand,model,version,contact;
    Button callhim;
    public Intrestedcar() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.intrestedcar, container, false);
        //yourname=(TextView)view.findViewById(R.id.yourname);
        name=(TextView)view.findViewById(R.id.name);
        brand=(TextView)view.findViewById(R.id.brand);
        model=(TextView)view.findViewById(R.id.model);
        version=(TextView)view.findViewById(R.id.version);
        contact=(TextView)view.findViewById(R.id.contact);
        callhim=(Button) view.findViewById(R.id.callhim);

       // yourname.setText();
        Log.d("gdfgdfghghf",Constants.sharedPreferences.getString("name",""));
        Log.d("gdfgdfghghf",Constants.sharedPreferences.getString("brand",""));
        Log.d("gdfgdfghghf",Constants.sharedPreferences.getString("model_id",""));
        Log.d("gdfgdfghghf",Constants.sharedPreferences.getString("version_id",""));
        Log.d("gdfgdfghghf",Constants.sharedPreferences.getString("mobile",""));

        name.setText(Constants.sharedPreferences.getString("name",""));
        brand.setText(Constants.sharedPreferences.getString("brand",""));
        model.setText(Constants.sharedPreferences.getString("model_id",""));
        version.setText(Constants.sharedPreferences.getString("version_id",""));
        contact.setText(Constants.sharedPreferences.getString("mobile",""));

        callhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact.getText().toString()));
                startActivity(callIntent);
            }
        });

        return  view;
    }

}
