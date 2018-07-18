package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;
import carteckh.carteckh.util.CustomEditText;
import carteckh.carteckh.util.CustomTextView;

/**
 * Created by developer on 10-Dec-15.
 */
public class Emi_Calculator extends Fragment {
    View view;

    @InjectView(R.id.et_loanamt)
    CustomEditText et_loanamt;
    @InjectView(R.id.et_rateofinterest)
    CustomEditText et_rateofinterest;
    @InjectView(R.id.radiogroup)
    RadioGroup radioGroup;
    @InjectView(R.id.btn_emi)
    CustomTextView btn_emi;
    RadioButton rb;

    List<Constants> EmiLIst = new ArrayList<Constants>();

    JsonObjectRequest jsonObjectRequest = null;
    String str_emi = "";
    Dialog dialog;

    @InjectView(R.id.listView)
    ListView listView;
    Adapter adapter = null;

    @InjectView(R.id.linear)
    LinearLayout linear_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initializing the dialog
        dialog = new Dialog(Emi_Calculator.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.emi_calculator, container, false);
        ButterKnife.inject(this, view);

        adapter = new Adapter();
        listView.setAdapter(adapter);


        btn_emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_loanamt.getText().length() != 0) {
                    if (et_rateofinterest.getText().length() != 0) {
                        int int_radio = radioGroup.getCheckedRadioButtonId();
                        if (int_radio != -1) {
                            rb = (RadioButton) view.findViewById(int_radio);


                            if (rb.getText().equals("EMI In Arrears")) {
                                str_emi = "emiarrears";

                            } else {
                                str_emi = "emiadvanced";

                            }

                            Constants.showdialog(dialog);
                            sendRequest();
                            AppController.getInstance().addToRequestQueue(jsonObjectRequest);
                        } else {
                            Crouton.makeText(getActivity(), "Select on Option", Style.ALERT).show();
                        }
                    } else {
                        Crouton.makeText(getActivity(), "Enter Rate of Interest Amount", Style.ALERT).show();
                    }
                } else {
                    Crouton.makeText(getActivity(), "Enter Loan Amount", Style.ALERT).show();
                }


            }
        });


//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            int mPosition = 0;
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int pos = listView.getFirstVisiblePosition();
//                if (mPosition == pos) {
//                    linear_layout.setVisibility(View.VISIBLE);
//                } else {
//                    linear_layout.setVisibility(View.GONE);
//                }
//            }
//        });


        return view;
    }

    private void sendRequest() {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=EmiCalculator&emifor=" + str_emi + "&amount=" + et_loanamt.getText().toString() + "&interest=" + et_rateofinterest.getText().toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                EmiLIst.clear();

                Constants.exitdialog(dialog);
                if (jsonObject.optString("success").equals("1")) {
                    JSONArray jsonArray = null;
                    if (str_emi.equals("emiadvanced")) {
                        jsonArray = jsonObject.optJSONArray("EMIAdvanced");
                    } else {
                        jsonArray = jsonObject.optJSONArray("EMIArrears");
                    }


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.optJSONObject(i);

                        EmiLIst.add(new Constants(jsonObject1.optString("principalAmount"), jsonObject1.optString("interestRate"), jsonObject1.optString("totalMonth"), jsonObject1.optString("emi"), jsonObject1.optString("interest")));
                    }
                    adapter.notifyDataSetChanged();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again"(), Style.ALERT).show();
            }
        });
    }

    static class ViewHolder {


        TextView tv_principalamount, tv_principalamount1, tv_interestrate, tv_interestrate1, tv_totalmonth, tv_totalmonth1, tv_emiamount, tv_emiamount1, tv_interestamount, tv_interestamount1;

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;

        Adapter() {
            inflater = (LayoutInflater) Emi_Calculator.this.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return EmiLIst.size();
        }

        @Override
        public Object getItem(int position) {
            return EmiLIst.get(position);
        }

        @Override
        public long getItemId(int position)      {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_emi, parent, false);
                viewHolder = new ViewHolder();


                viewHolder.tv_principalamount = (TextView) convertView.findViewById(R.id.tv_principalamount);

                viewHolder.tv_principalamount1 = (TextView) convertView.findViewById(R.id.tv_principalamount1);

                viewHolder.tv_interestrate = (TextView) convertView.findViewById(R.id.interestrate);
                viewHolder.tv_interestrate1 = (TextView) convertView.findViewById(R.id.interestrate1);

                viewHolder.tv_totalmonth = (TextView) convertView.findViewById(R.id.totalmonth);
                viewHolder.tv_totalmonth1 = (TextView) convertView.findViewById(R.id.totalmonth1);

                viewHolder.tv_emiamount = (TextView) convertView.findViewById(R.id.emiamount);
                viewHolder.tv_emiamount1 = (TextView) convertView.findViewById(R.id.emiamount1);

                viewHolder.tv_interestamount = (TextView) convertView.findViewById(R.id.intrestamount);
                viewHolder.tv_interestamount1 = (TextView) convertView.findViewById(R.id.intrestamount1);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();


            }


            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
            viewHolder.tv_principalamount1.setTypeface(typeface);
            viewHolder.tv_interestrate1.setTypeface(typeface);
            viewHolder.tv_totalmonth1.setTypeface(typeface);
            viewHolder.tv_emiamount1.setTypeface(typeface);
            viewHolder.tv_interestamount1.setTypeface(typeface);

//            NumberFormat.getNumberInstance(Locale.US).format(EmiLIst.get(position).getId().toString())
            viewHolder.tv_principalamount1.setText("₹ "+ NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(EmiLIst.get(position).getId().toString()))+"/-");
            viewHolder.tv_interestrate1.setText("" + EmiLIst.get(position).getBrand().toString()+" %");
            viewHolder.tv_totalmonth1.setText("" + EmiLIst.get(position).getModel().toString());
            viewHolder.tv_emiamount1.setText("₹ " + EmiLIst.get(position).getVersion().toString()+"/-");
            viewHolder.tv_interestamount1.setText("₹ " + NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(EmiLIst.get(position).getOofer_from().toString()))+"/-");

            return convertView;
        }
    }
}
