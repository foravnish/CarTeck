package carteckh.carteckh.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.actvities.Testing;
import carteckh.carteckh.service.ServiceClass;
import carteckh.carteckh.util.Constants;

public class ExpandableListDrawerAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mExpandableListTitle;
    private Map<String, List<String>> mExpandableListDetail;
    private LayoutInflater mLayoutInflater;
    CoordinatorLayout coordinatorLayout;
    //private ToggleButton toggleButton;
    SwitchCompat toggleButton;
    int flag=1;




    private int[] icons={R.drawable.home,R.drawable.utilites,R.drawable.usedcar,R.drawable.sellcar,R.drawable.compare,R.drawable.srch,R.drawable.login,R.drawable.adver,R.drawable.noti,R.drawable.newcar,R.drawable.newcar};
    //private int[] icons={R.drawable.home,R.drawable.newcar,R.drawable.usedcar,R.drawable.sellcar,R.drawable.compare,R.drawable.utilites,R.drawable.adver,R.drawable.noti,R.drawable.login,R.drawable.login};
    public ExpandableListDrawerAdapter(Context context, List<String> expandableListTitle,
                                       Map<String, List<String>> expandableListDetail) {
        mContext = context;
        mExpandableListTitle = expandableListTitle;
        mExpandableListDetail = expandableListDetail;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return mExpandableListDetail.get(mExpandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.nav_drawer_row, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.tv_childitem);
        Typeface typeface = Typeface.createFromAsset(expandedListTextView.getContext().getAssets(), "OpenSans-Regular.ttf");
        expandedListTextView.setTypeface(typeface);

        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return mExpandableListDetail.get(mExpandableListTitle.get(listPosition)).size();
    }


    @Override
    public Object getGroup(int listPosition) {
        return mExpandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return mExpandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.nav_drawer_item_group, null);
        }


        toggleButton = (SwitchCompat) convertView.findViewById(R.id.toggle);
//        toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle);
        ImageView iconExpand = (ImageView) convertView.findViewById(R.id.icon_expand);
        ImageView iconCollapse = (ImageView) convertView .findViewById(R.id.icon_collapse);
        ImageView icon= (ImageView) convertView.findViewById(R.id.iv_icon);
        final  Context v1=convertView.getContext();
       // toggleButton.setChecked(Constants.sharedPreferences.getString("sound","").equals("true"));
        //Constants.sound=true;
//        Constants.editor.putString("sound", "true");
//        Constants.editor.commit();


//        if (toggleButton.isChecked()==true){
//            flag=0;
//            Constants.editor.putString("sound1","false");
//            Constants.editor.commit();
//            toggleButton.setChecked(false);
//            Toast.makeText(v1, "ONnnnnnnnnnnn", Toast.LENGTH_SHORT).show();
//        }
//        if (flag==0)
//        {
//            Constants.editor.putString("sound", "true");
//            Constants.editor.commit();
//            flag=1;
//        }



        if (listPosition==8)
        {
            toggleButton.setVisibility(View.VISIBLE);





            if (Constants.sharedPreferences1.getString("sound","").equals("true")){

                Constants.editor1.putString("sound1","true");
                Constants.editor1.commit();
                toggleButton.setChecked(true);

            }

            else if (Constants.sharedPreferences1.getString("sound","").equals("false")){
                Constants.editor1.putString("sound1","false");
                Constants.editor1.commit();
                toggleButton.setChecked(false);
            }


            toggleButton.setChecked(Constants.sharedPreferences1.getString("sound","").equals("true"));


            Log.d("fffffgfdfdg", String.valueOf(Boolean.parseBoolean(Constants.sharedPreferences1.getString("sound",""))));



            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(b==true){
//

                        //

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=newLaunchNotifiationAlert1", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                // Constants.exitdialog(dialog);

                                    ServiceClass.flag1=1;
                                    Constants.editor1.putString("car_id", jsonObject.optString("id"));
                                    Constants.editor1.commit();
                                }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
//                Constants.exitdialog(dialog);
                                Log.d("vgfdghfg","false");
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
                            }
                        });
                        //Constants.showdialog(dialog);
                        AppController.getInstance().addToRequestQueue(jsonObjectRequest);




                        ////*** 22

                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=upcommingCarNotifiationAlert", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                // Constants.exitdialog(dialog);


                                    ServiceClass.flag2=1;

                                    Constants.editor1.putString("car_id2", jsonObject.optString("id"));
                                    Constants.editor1.commit();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
//                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

                            }
                        });
                        //Constants.showdialog(dialog);
                        AppController.getInstance().addToRequestQueue(jsonObjectRequest2);
                        /////



                        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=didYouKnowNotifiationAlert", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                    ServiceClass.flag3=1;

                                    Constants.editor1.putString("artical_id", jsonObject.optString("id"));
                                    //Constants.editor.putString("device_id",telephonyManager.getDeviceId());
                                    Constants.editor1.commit();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
//                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

                            }
                        });
                        //Constants.showdialog(dialog);
                        AppController.getInstance().addToRequestQueue(jsonObjectRequest3);
                        //

////////////////////////  4
                        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=carteckhChoiceNotifiationAlert", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                // Constants.exitdialog(dialog);

                                    ServiceClass.flag4=1;

                                    Constants.editor1.putString("artical_id2", jsonObject.optString("id"));
                                    Constants.editor1.commit();

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
//                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

                            }
                        });
                        //Constants.showdialog(dialog);
                        AppController.getInstance().addToRequestQueue(jsonObjectRequest4);



                        Constants.sound=true;
                        Constants.editor1.putString("sound", "true");
                        Constants.editor1.commit();
//                        Constants.editor.putString("stv", "no");
//                        Constants.editor.commit();
                        Log.d("Avnish","on");
                        Intent intent = new Intent(v1, Testing.class);
                        intent.putExtra("values","true");
                        v1.startActivity(intent);

                        //Toast.makeText(v1, "Notification ON", Toast.LENGTH_SHORT).show();


                        //Log.d("fdgdfhdfh", String.valueOf(Constants.sharedPreferences.getBoolean("sound",true)));
                    }
                    else if(b==false)
                    {
                        //Testing.t.destroy();

                        Constants.sound = false;
                        Constants.editor1.putString("sound", "false");
                        Constants.editor1.commit();
                        Log.d("Avnish","off");
                        Intent intent = new Intent(v1, Testing.class);
                        intent.putExtra("values","false");
                        v1.startActivity(intent);

                        //Toast.makeText(v1, "Notification OFF", Toast.LENGTH_SHORT).show();
                    }

                }
            });



            Log.d("fdfdfdfdferefrssgs",Constants.sharedPreferences1.getString("notioff",""));

            if (Constants.sharedPreferences1.getString("notioff","").equals("yes")) {
                Constants.editor1.putString("notioff", "no");
                Constants.editor1.commit();
                toggleButton.setChecked(true);
                Constants.snacbar = true;
                toggleButton.setVisibility(View.VISIBLE);
                Log.d("fgfgrttfgfgfg", "true");
            }

            Log.d("fgdgdfgdfghtrr",Constants.sharedPreferences1.getString("notioff",""));





        }
        else {
            toggleButton.setVisibility(View.GONE);
        }




        icon.setImageResource(icons[listPosition]);
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_groupitem);
        Typeface typeface = Typeface.createFromAsset(listTitleTextView.getContext().getAssets(), "OpenSans-Regular.ttf");
        listTitleTextView.setTypeface(typeface);
        listTitleTextView.setTypeface(null, Typeface.NORMAL);
        listTitleTextView.setText(listTitle);
        if(listPosition==9)
        {

            if(Constants.sharedPreferences.contains("user_id"))
            {
                listTitleTextView.setText("Logout");
            }
            else
            {
                Constants.sharedPreferences.contains(null);
                listTitleTextView.setText("Login / Register");

            }
        }
        //String listTitle2 = (String) getGroup(8);
        //listTitleTextView.setText(listTitle2);

        if (isExpanded) {
            iconExpand.setVisibility(View.GONE);
            iconCollapse.setVisibility(View.VISIBLE);
        } else {
            iconExpand.setVisibility(View.VISIBLE);
            iconCollapse.setVisibility(View.GONE);
        }

        if (getChildrenCount(listPosition) == 0) {
            iconExpand.setVisibility(View.GONE);
            iconCollapse.setVisibility(View.GONE);
        }

        //ToggleButton toggleButton=(ToggleButton)convertView.findViewById(R.id.toggle);

        Log.v("Main"," Key title  "+listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
