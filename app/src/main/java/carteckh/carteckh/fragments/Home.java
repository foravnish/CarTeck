package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 23-Nov-15.
 */
public class Home extends Fragment implements View.OnTouchListener {

    View view;
    @InjectView(R.id.btn_comparecar)
    Button btn_comparecar;


    @InjectView(R.id.btn_newcars)
    Button btn_newcars;

    @InjectView(R.id.btn_sellcars)
    Button btn_sellcars;

    @InjectView(R.id.btn_emical)
    Button btn_emical;

    @InjectView(R.id.btn_dealcar)
    Button btn_dealers;

    @InjectView(R.id.btn_usedcar)
    Button btn_usedcar;


    @InjectView(R .id.centre)
    Button centre;
    @InjectView(R.id.main_image1)
    ImageView frameLayout;

    Dialog dialog,dialog1;
    public  static Dialog dialog2,dialog3,dialog4;
    Snackbar snackbar=null;

    List<Constants> States = new ArrayList<Constants>();
    List<String> State_Names = new ArrayList<String>();
    private String Constant_Object;
    private String Constant_Object_id;

    @InjectView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private double mCurrAngle = 0;
    private double mPrevAngle = 0;
    ImageView bask;
    public static MediaPlayer mediaPlayer, mediaPlayer2;
    private android.support.v7.app.AlertDialog alertDialog;
    private Stack<String> stringStack;

    StringRequest stringRequest2;

    Button btn_go;
    TextView tvterm,tv_view,title;
    CheckBox ch_terms;
    String status="";
    Button yes_action,no_action;
    private Spinner spin_state;
    String state_vales="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.home, container, false);

        ButterKnife.inject(this, view);

        frameLayout.setOnTouchListener(this);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.honk);
        mediaPlayer2 = MediaPlayer.create(getActivity(), R.raw.noice);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // centre.setOnTouchListener(this);



        Constants.sharedPreferences1=getActivity().getSharedPreferences(Constants.Myprefrence1,Context.MODE_PRIVATE);
        Constants.editor1=Constants.sharedPreferences1.edit();

        centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Constants.sharedPreferences.getBoolean("ms",true)) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    } else {
                        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.honk);
                        mediaPlayer.start();
                    }
                }
                else if (Constants.sharedPreferences.getBoolean("ms",false)){
                    mediaPlayer.stop();
                }
            }
        });


        Bundle bundle=getArguments();
        String value1=bundle.getString("tncval","");

        //Toast.makeText(getActivity(), value1+"", Toast.LENGTH_SHORT).show();




        if (Constants.sharedPreferences_choice.getString("datavalue","").equals("data_choice")){
            Fragment fragment=new Choices();
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.container,fragment).addToBackStack(null).commit();
            Constants.editor_choice.clear();
            Constants.editor_choice.commit();

        }
        else if (Constants.sharedPreferences_did.getString("datavalue","").equals("data_diduknow")){
            Fragment fragment=new Didyou_know();
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.container,fragment).addToBackStack(null).commit();
           // Toast.makeText(getActivity(), "did u know", Toast.LENGTH_SHORT).show();
            Constants.editor_did.clear();
            Constants.editor_did.commit();

        }

        else if (Constants.sharedPreferences_newlaunch.getString("datavalue","").equals("data_newlaunch")){
            Fragment fragment=new NewLaunchCars();
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.container,fragment).addToBackStack(null).commit();

            //Toast.makeText(getActivity(), "new launched", Toast.LENGTH_SHORT).show();
            Constants.editor_newlaunch.clear();
            Constants.editor_newlaunch.commit();

        }
        else if (Constants.sharedPreferences_upcoming.getString("datavalue","").equals("data_upcoming")){
            Fragment fragment=new UpComing_Cars();
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.container,fragment).addToBackStack(null).commit();

            Constants.editor_upcoming.clear();
            Constants.editor_upcoming.commit();

        }

        else if (Constants.sharedPreferences_usedcar.getString("datavalue","").equals("data_usedcar")){
            Fragment fragment=new ResponseList1();
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.container,fragment).addToBackStack(null).commit();

            Constants.editor_usedcar.clear();
            Constants.editor_usedcar.commit();

        }

             stringRequest2 = new StringRequest(Request.Method.POST, Constants.Url + "task=checkTermAndCondition", new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {
                    JSONObject jsonObject = null;
                    Log.d("fffffgdfgfh", s + "");
                    try {
                        jsonObject = new JSONObject(s);
                        Log.d("aaffffafffffgdfgfh", s + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Constants.exitdialog(dialog);

                        if (jsonObject.optString("status").equals("0")) {


                            if (Constants.sharedPreferences.contains("user_id")) {
                                dialog2.show();
                            }
                            Log.d("aaafffffgdfgfh", s + "");

                        } else {
                            //Crouton.makeText(getActivity(), "" + jsonObject.optString("message"), Style.ALERT).show();
                            Log.d("aaafffffgefedfgfh", s + "");

                        }




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Constants.exitdialog(dialog);
                    Crouton.makeText(getActivity(), "" + "Connect to the Internet Please try again", Style.INFO).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("acceptAndCondition", status.toString());
                    params.put("userId", Constants.sharedPreferences.getString("user_id", ""));
                    return params;

                }
            };


//        if (!Constants.sharedPreferences.getString("values","").equals("true")) {
//            Constants.editor.putString("values","");
//            Constants.editor.commit();
//            Constants.showdialog(dialog);
//            AppController.getInstance().addToRequestQueue(stringRequest2);
//        }

        if (value1.equals("true")) {


        }
        else if(value1.equals("false"))
        {
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(stringRequest2);
        }
//            Constants.showdialog(dialog);
//            AppController.getInstance().addToRequestQueue(stringRequest2);
        

        dialog2.setContentView(R.layout.dialog_terms_n_conditions);
        dialog2.setCancelable(false);

        btn_go=(Button)dialog2.findViewById(R.id.btn_go);

        tvterm=(TextView)dialog2.findViewById(R.id.tvterm);
//        tv_view=(TextView)dialog2.findViewById(R.id.tv_view);
        ch_terms=(CheckBox)dialog2.findViewById(R.id.ch_terms);
        title= (TextView)dialog2.findViewById(R.id.title);

        title.setText("Hi,upgrade this Terms & Conditions Please Check and Confim");

        tvterm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.carteckh.com/term-condition/"));
                startActivity(intent2);
            }
        });
        String text = "<font color=#808080>I Accept to all the</font> <font color=#ED1C24>Terms & Conditions</font> <font color=#808080>of Carteckh.com </font>";
        tvterm.setText(Html.fromHtml(text));


        if (ch_terms.isChecked()==true){
            ch_terms.setBackgroundColor(Color.RED);

        }
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ch_terms.isChecked()==true) {
                    dialog2.dismiss();
                    AppController.getInstance().addToRequestQueue(stringRequest2);

                    status="yes";
                    //Constants.editor1.putString("status","yes");
                    //Constants.editor1.putString("status","yes");
                    //Constants.editor1.commit();
                }

                else
                {
                    Crouton.makeText(getActivity(), "Please Accept Terms & Conditions", Style.ALERT).show();
                }

                //finish();
//                Intent intent = new Intent(User_Login.this, Testing.class);
//                startActivity(intent);
//
//                Constants.snacbar=true;
//                finish();
            }
        });


        snackbar = Snackbar.make(coordinatorLayout, "Welcome to Carteckh", Snackbar.LENGTH_SHORT);
        if (Constants.snacbar==true) {

            StateDialog();
            snackbar.show();
            //ExpandableListDrawerAdapter.toggleButton.setChecked(true);
            Constants.snacbar=false;
        }

        btn_comparecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new Compare_Car();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });

        btn_sellcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new Packege_sell_car();
                Bundle bundle=new Bundle();
                bundle.putString("key","avnish2");
                fragment.setArguments(bundle);
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });

        btn_usedcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                //Fragment fragment2 = null;
                //fragment2 = new Home();
                fragment = new Used_CarList();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                   // beginTransction(fragment2,"");
                }
            }
        });

        btn_dealers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogBox();
//                Fragment fragment = null;
//                fragment = new ServiceCenterMain();
//                if (fragment != null) {
//                    FragmentManager fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//                }


            }
        });

        btn_newcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                //Fragment fragment2 = null;
                //fragment2 = new Home();
                fragment = new NewCarsList();
                if (fragment != null) {

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                  //  beginTransction(fragment2,"");
                }
            }
        });


        btn_emical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//*******
                Fragment fragment = null;
                fragment = new Emi_Calculator();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                }
            }
        });



        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

//                        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
//                            getActivity());
//
//                    alertDialog2.setMessage("Are you sure you want to exit?");
//                    alertDialog2.setCancelable(false);
//
//
//                    alertDialog2.setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                                    intent.addCategory(Intent.CATEGORY_HOME);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                    System.exit(0);
//                                }
//                            }).setNegativeButton("No", null).show();


                        Button Yes_action,No_action;
                        TextView heading;
                        dialog4 = new Dialog(getActivity());
                        dialog4.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog4.setContentView(R.layout.update_state1);

                        Yes_action=(Button)dialog4.findViewById(R.id.Yes_action);
                        No_action=(Button)dialog4.findViewById(R.id.No_action);
                        heading=(TextView)dialog4.findViewById(R.id.heading);


                        heading.setText("Are you sure you want to exit?");
                        Yes_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.exit(0);
                            }
                        });

                        No_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog4.dismiss();
                            }
                        });
                        dialog4.show();
//

                        //Toast.makeText(getActivity(), "back", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });




//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event)   {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//
////                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
////                            getActivity());
////
////                    alertDialog2.setMessage("Are you sure you want to exit?");
////                    alertDialog2.setCancelable(false);
////                    alertDialog2.setPositiveButton("Yes",
////                            new DialogInterface.OnClickListener() {
////                                public void onClick(DialogInterface dialog, int id) {
////                                    Intent intent = new Intent(Intent.ACTION_MAIN);
////                                    intent.addCategory(Intent.CATEGORY_HOME);
////                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                    startActivity(intent);
////                                    System.exit(0);
////                                }
////                            }).setNegativeButton("No", null).show();
//
//                    Toast.makeText(getActivity(), "back", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//            }
//        });

        return view;
    }

    private void StateDialog() {

        dialog1.setContentView(R.layout.state_dialog);
        //popupdialog.setCanceledOnTouchOutside(false);




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=StateList", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);
                spin_state=(Spinner)dialog1.findViewById(R.id.spin_state);
                States.clear();
                State_Names.clear();
                JSONArray jsonArray = jsonObject.optJSONArray("state_list");
                States.add(new Constants("10", "Select State"));
                State_Names.add("Select State");

                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("", jsonArray.toString());
                    try {
                        JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                        States.add(new Constants(jsonObject1.optString("id"), jsonObject1.optString("state")));
                        State_Names.add(jsonObject1.optString("state"));

                        final ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, State_Names);
                        adapter_state1.setDropDownViewResource(R.layout.spinnertext);
                        spin_state.setAdapter(adapter_state1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,State_Names);
                    adapter.setDropDownViewResource(R.layout.spinnertext);
                }
                final ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, State_Names);
                adapter_state1.setDropDownViewResource(R.layout.spinnertext);

                spin_state.setAdapter(adapter_state1);
                spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Constant_Object = adapter_state1.getItem(i);
                        Constant_Object_id = States.get(i).getStr_Brandid().toString();

                        Constants.editor.putString("stv","yes");
                        Constants.editor.commit();

                        //Toast.makeText(getApplicationContext(),Constant_Object_id,Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                if (Constants.sharedPreferences.contains("state_id")) {
                    for (int i = 0; i < States.size(); i++) {
                        if (Constants.sharedPreferences.getString("state_id", "").equals(States.get(i).getStr_Brandid().toString())) {
                            spin_state.setSelection(i);
                        }

                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                //Toast.makeText(Testing.this, "Connect to the Internet Please try again", Toast.LENGTH_SHORT).show();
                Crouton.makeText(getActivity(), "Connect to the Internet Please try again", Style.ALERT).show();
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


//       if(Constants.sharedPreferences1.getString("value","").equals("off")){
//           dialog1.show();
//       }

        if (!Constants.sharedPreferences.getString("stv","").equals("yes")) {
//        if (spin_state.getSelectedItemId()>=0) {


            if (networkInfo != null && networkInfo.isConnected()) {
                // fetch data
                dialog1.show();
            } else {
                Constants.exitdialog(dialog);
                // display error
                Crouton.makeText(getActivity(), "Connect to the Internet Please try again", Style.ALERT).show();
                Toast.makeText(getActivity(), "Connect to the Internet", Toast.LENGTH_SHORT).show();
            }

        }

//        }

        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

//            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) dialog1.findViewById(R.id.auto_complete);
//            Button button_submit = (Button) dialog1.findViewById(R.id.btn_add);
//            Log.e("Testing", " StateList before adap " + stateList.size());
//            final ArrayAdapter<StateList> adapter = new ArrayAdapter<StateList>(this, R.layout.spinner_item, stateList);
//            adapter.setDropDownViewResource(R.layout.spinnertext);
//            autoCompleteTextView.setAdapter(adapter);
//            autoCompleteTextView.setThreshold(1);
//            Log.e("Testing", " StateList after adap " + stateList.size());
//            dialog1.show();
//            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    Constant_Object = adapter.getItem(position);
//
//
//                }
//            });

        Button button_submit = (Button) dialog1.findViewById(R.id.btn_add);
        Button cancel_action = (Button) dialog1.findViewById(R.id.cancel_action);

        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!spin_state.getSelectedItem().equals("Select State")) {

//                        //Toast.makeText(Testing.this, Constant_Object, Toast.LENGTH_SHORT).show();
////                        Crouton.makeText(Testing.this, Constant_Object.getId() + "," + Constant_Object.getState(), Style.ALERT).show();
////                        Constants.editor.putString("state_id", Constant_Object.getId());
////                        Constants.editor.putString("state_name", Constant_Object.getState());
////                        Constants.editor.commit();
//                        //Crouton.makeText(getActivity(), Constant_Object, Style.ALERT).show();
//                        Log.d("asd",Constant_Object);
//                        Constants.editor.putString("state_name", Constant_Object);
//                        Constants.editor.putString("state_id",Constant_Object_id);
//                        Constants.editor.commit();
//                        Log.d("gfdgdf",Constant_Object_id);
//                    Crouton.makeText(getActivity(), "Your Selected State is "+Constants.sharedPreferences.getString("state_name",""), Style.INFO).show();
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(false);
//
//                    builder.setTitle("State");
//                    builder.setMessage("Your Selected State is "+Constants.sharedPreferences.getString("state_name",""));
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            //
//                        }
//                    });
//
//                    // Create the AlertDialog object and return it
//                    builder.create().show();
//
                    Button call;
                    TextView heading;

                    dialog3 = new Dialog(getActivity());
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setContentView(R.layout.update_state);
                    call=(Button)dialog3.findViewById(R.id.call);
                    heading=(TextView)dialog3.findViewById(R.id.heading);
                    heading.setText("Your Selected State is "+Constants.sharedPreferences.getString("state_name",""));
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog3.dismiss();
                        }
                    });
                    dialog3.show();


                    dialog1.dismiss();

                }

                else  if (spin_state.getSelectedItem().equals("Select State")) {

                    //Toast.makeText(Testing.this, Constant_Object, Toast.LENGTH_SHORT).show();
//                        Crouton.makeText(Testing.this, Constant_Object.getId() + "," + Constant_Object.getState(), Style.ALERT).show();
//                        Constants.editor.putString("state_id", Constant_Object.getId());
//                        Constants.editor.putString("state_name", Constant_Object.getState());
//                        Constants.editor.commit();
                    //Crouton.makeText(getActivity(), Constant_Object, Style.ALERT).show();
                    Log.d("asd",Constant_Object);
                    Constants.editor.putString("state_name", Constant_Object);
                    Constants.editor.putString("state_id",Constant_Object_id);
                    Log.d("gfdgdf",Constant_Object_id);
                    Constants.editor.commit();
                    //Crouton.makeText(getActivity(), "Your Default State is Delhi", Style.INFO).show();
                    //Toast.makeText(getActivity(), "Your Default State is Delhi", Toast.LENGTH_SHORT).show();

//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setCancelable(false);
//                        builder.setTitle("State");
//                        builder.setMessage("Your Default State is Delhi");
//                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                //
//                            }
//                        });
//
//                        // Create the AlertDialog object and return it
//                        builder.create().show();

                    Button call;
                    TextView heading;

                    dialog3 = new Dialog(getActivity());
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setContentView(R.layout.update_state);
                    call=(Button)dialog3.findViewById(R.id.call);
                    heading=(TextView)dialog3.findViewById(R.id.heading);
                    heading.setText("Your Default State is Delhi");
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog3.dismiss();
                        }
                    });
                    dialog3.show();

                    dialog1.dismiss();

                }

            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!spin_state.getSelectedItem().equals("Select State")) {

                    //Toast.makeText(Testing.this, Constant_Object, Toast.LENGTH_SHORT).show();
//                        Crouton.makeText(Testing.this, Constant_Object.getId() + "," + Constant_Object.getState(), Style.ALERT).show();
//                        Constants.editor.putString("state_id", Constant_Object.getId());
//                        Constants.editor.putString("state_name", Constant_Object.getState());
//                        Constants.editor.commit();
                    //Crouton.makeText(getActivity(), Constant_Object, Style.ALERT).show();
                    Log.d("asd",Constant_Object);
                    Constants.editor.putString("state_name", Constant_Object);
                    Constants.editor.putString("state_id",Constant_Object_id);
                    Constants.editor.commit();
                    Log.d("gfdgdf",Constant_Object_id);
                    //Crouton.makeText(getActivity(), "Your Selected State is "+Constants.sharedPreferences.getString("state_name",""), Style.INFO).show();

//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(false);
//
//                    builder.setTitle("State");
//                    builder.setMessage("Your Selected State is "+Constants.sharedPreferences.getString("state_name",""));
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            //
//                        }
//                    });
//
//                    // Create the AlertDialog object and return it
//                    builder.create().show();

                    Button call;
                    TextView heading;

                    dialog3 = new Dialog(getActivity());
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setContentView(R.layout.update_state);
                    call=(Button)dialog3.findViewById(R.id.call);
                    heading=(TextView)dialog3.findViewById(R.id.heading);
                    heading.setText("Your Selected State is "+Constants.sharedPreferences.getString("state_name",""));
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog3.dismiss();
                        }
                    });
                    dialog3.show();


                    dialog1.dismiss();

                }
                else  if (spin_state.getSelectedItem().equals("Select State")) {

                    //Toast.makeText(Testing.this, Constant_Object, Toast.LENGTH_SHORT).show();
//                        Crouton.makeText(Testing.this, Constant_Object.getId() + "," + Constant_Object.getState(), Style.ALERT).show();
//                        Constants.editor.putString("state_id", Constant_Object.getId());
//                        Constants.editor.putString("state_name", Constant_Object.getState());
//                        Constants.editor.commit();
                    //Crouton.makeText(getActivity(), Constant_Object, Style.ALERT).show();
                    Log.d("asd",Constant_Object);
                    Constants.editor.putString("state_name", Constant_Object);
                    Constants.editor.putString("state_id",Constant_Object_id);
                    Log.d("gfdgdf",Constant_Object_id);
                    Constants.editor.commit();
                    //Crouton.makeText(getActivity(), "Your Default State is Delhi", Style.INFO).show();


//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(false);
//                    builder.setTitle("State");
//                    builder.setMessage("Your Default State is Delhi");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            //
//                        }
//                    });
//
//                    // Create the AlertDialog object and return it
//                    builder.create().show();


                    Button call;
                    TextView heading;

                    dialog3 = new Dialog(getActivity());
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setContentView(R.layout.update_state);
                    call=(Button)dialog3.findViewById(R.id.call);
                    heading=(TextView)dialog3.findViewById(R.id.heading);
                    heading.setText("Your Default State is Delhi");
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog3.dismiss();
                        }
                    });
                    dialog3.show();

                    dialog1.dismiss();

                }
            }
        });


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final float xc = frameLayout.getWidth() / 2;
        final float yc = frameLayout.getHeight() / 2;

        final float x = event.getX();
        final float y = event.getY();


            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    if(Constants.sharedPreferences.getBoolean("ms",true)) {
                        mediaPlayer2 = MediaPlayer.create(getActivity(), R.raw.noice);
                        mediaPlayer2.start();
                    }

                    frameLayout.clearAnimation();
                    mCurrAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    mPrevAngle = mCurrAngle;
                    mCurrAngle = Math.toDegrees(Math.atan2(x - xc, yc - y));
                    animate(mPrevAngle, mCurrAngle, 0);
                    System.out.println(mCurrAngle);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    mPrevAngle = mCurrAngle = 0;
                    if (mediaPlayer2.isPlaying()) {
                        mediaPlayer2.stop();
                    }
                    break;
                }
            }


        return true;
    }

    private void animate(double fromDegrees, double toDegrees, long durationMillis) {
        final RotateAnimation rotate = new RotateAnimation((float) fromDegrees, (float) toDegrees,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(durationMillis);
        rotate.setFillEnabled(true);
        rotate.setFillAfter(true);
        frameLayout.startAnimation(rotate);
        System.out.println(mCurrAngle);
    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        try {
//            if (mediaPlayer.isPlaying()) {
////                mediaPlayer.stop();
//            } else if (mediaPlayer2.isPlaying()) {
////                mediaPlayer2.stop();
//            }
//        } catch (IllegalStateException e) {
//            Toast.makeText(getActivity(),e+"",Toast.LENGTH_SHORT).show();
//        }
//    }
    private void startDialogBox() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Choose one");
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialogbox, null);
        Button btndealer = (Button) view.findViewById(R.id.locatedealer);
        Button btnservicecenter = (Button) view.findViewById(R.id.locateservice);
        btndealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentdealers = null;
                fragmentdealers = new Locate_Dealer();   // ???
                if (fragmentdealers != null) {
                    beginTransction(fragmentdealers,"");
                    //stringStack.push("dealers");
                    alertDialog.dismiss();
                }
            }
        });
        btnservicecenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentdealers = null;
                fragmentdealers = new Service_Center();   // ???
                if (fragmentdealers != null) {
                    beginTransction(fragmentdealers,"");
                    //stringStack.push("service");
                    alertDialog.dismiss();
                }
            }
        });

        builder.setView(view);

        alertDialog=builder.create();
        alertDialog.show();


    }
    private void beginTransction(Fragment fragment,String tag) {
        Bundle bundle=new Bundle();
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            bundle.putString("from",tag);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }


}
