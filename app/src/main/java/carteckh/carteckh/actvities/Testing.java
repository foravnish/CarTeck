package carteckh.carteckh.actvities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.adapters.ExpandableListDrawerAdapter;
import carteckh.carteckh.fragments.Aboutus;
import carteckh.carteckh.fragments.AdvancedCarSearch;
import carteckh.carteckh.fragments.AdvertiesWithUs;
import carteckh.carteckh.fragments.Choices;
import carteckh.carteckh.fragments.Compare_Car;
import carteckh.carteckh.fragments.Didyou_know;
import carteckh.carteckh.fragments.Emi_Calculator;
import carteckh.carteckh.fragments.Home;
import carteckh.carteckh.fragments.Locate_Dealer;
import carteckh.carteckh.fragments.NewCarsList;
import carteckh.carteckh.fragments.NewLaunchCars;
import carteckh.carteckh.fragments.Offers;
import carteckh.carteckh.fragments.Packege_sell_car;
import carteckh.carteckh.fragments.PhotoGallery;
import carteckh.carteckh.fragments.Profile;
import carteckh.carteckh.fragments.Road_Assistance;
import carteckh.carteckh.fragments.Service_Center;
import carteckh.carteckh.fragments.TermConditionFragment;
import carteckh.carteckh.fragments.UpComing_Cars;
import carteckh.carteckh.fragments.Used_CarList;
import carteckh.carteckh.listeners.OnItemClicks;
import carteckh.carteckh.model.State;
import carteckh.carteckh.model.StateList;
import carteckh.carteckh.network.GsonRequest;
import carteckh.carteckh.network.VolleySingleton;
import carteckh.carteckh.service.ServiceClass;
import carteckh.carteckh.service.ServiceClass3;
import carteckh.carteckh.util.Constants;

public class Testing extends AppCompatActivity implements OnItemClicks {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public ExpandableListView mExpandableListView;
    private ExpandableListDrawerAdapter mExpandableListAdapter;
    private Map<String, List<String>> mExpandableListData;
    public static SwitchCompat toggleButton;
    private String selectedItem;
    // private LinearLayout linearLayout;
    private Toolbar toolbar;

    private HashMap<String, List<String>> listDataChild;
    private List<String> listDataHeader;
    List<String> State_Names = new ArrayList<String>();
    private List<StateList> stateList;
    private Dialog dialog, dialog1;
    ;
    private Fragment fragment;
    private AppCompatActivity activity;
    GsonRequest<State> gsonRequest;
    private String Constant_Object;
    private String Constant_Object_id;
    List<Constants> States = new ArrayList<Constants>();

    private Stack<Fragment> fragmentStack;
    //private Stack<String> stringStack;
    private FragmentManager fragmentManager;
    private android.support.v7.app.AlertDialog alertDialog;

    public static TextView tv_name, tv_mobile;
    Button edit1, edit2;
    private SwitchCompat toggle;
    private Spinner spin_state;
    public static Thread t = null;

    PendingIntent pendingIntent;

    Dialog dialog3,dialog4;
    Button yes_action,no_action;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    JsonObjectRequest jsonObjectRequest1;
    String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(Testing.this);
        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Initializing the dialog
        dialog1 = new Dialog(Testing.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_testing);
        activity = this;

        tv_name = (TextView) findViewById(R.id.user_name);
        tv_mobile = (TextView) findViewById(R.id.mobile_no);
        toggle = (SwitchCompat) findViewById(R.id.toggle);
        edit1 = (Button) findViewById(R.id.edit1);
        edit2 = (Button) findViewById(R.id.edit2);

        Constants.sharedPreferences = Testing.this.getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();
        Constants.sharedPreferences_advance=Testing.this.getSharedPreferences(Constants.Myprefrence_Advance, Context.MODE_PRIVATE);
        Constants.editor_advance=Constants.sharedPreferences_advance.edit();

        Constants.sharedPreferences_choice = Testing.this.getSharedPreferences(Constants.Myprefrence_choice, MODE_PRIVATE);
            Constants.editor_choice = Constants.sharedPreferences_choice.edit();

            Constants.sharedPreferences_did = Testing.this.getSharedPreferences(Constants.Myprefrence_did, MODE_PRIVATE);
            Constants.editor_did = Constants.sharedPreferences_did.edit();

            Constants.sharedPreferences_newlaunch = Testing.this.getSharedPreferences(Constants.Myprefrence_newlaunches, MODE_PRIVATE);
            Constants.editor_newlaunch = Constants.sharedPreferences_newlaunch.edit();

            Constants.sharedPreferences_upcoming = Testing.this.getSharedPreferences(Constants.Myprefrence_upcomming, MODE_PRIVATE);
            Constants.editor_upcoming = Constants.sharedPreferences_upcoming.edit();

        Constants.sharedPreferences_usedcar = Testing.this.getSharedPreferences(Constants.Myprefrence_usedcar, MODE_PRIVATE);
        Constants.editor_usedcar = Constants.sharedPreferences_usedcar.edit();


        if (Constants.sharedPreferences.getBoolean("ms", true)) {
            toggle.setChecked(true);
        } else if (Constants.sharedPreferences.getBoolean("ms", false)) {
            toggle.setChecked(false);
        }
        toggle.setChecked(Constants.sharedPreferences.getBoolean("ms", true));

//        toggle.setChecked(true);
        //***********Update version ****************//
                    PackageInfo pInfo = null;
                    try {
                        pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    version = pInfo.versionName;
                    int verCode = pInfo.versionCode;
                    Log.d("gdfghgf",version);
                    Log.d("gdfghgf2", String.valueOf(verCode));



        jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=mobileAppVersionAlert", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);

                if (jsonObject.optString("Success").equals("1")) {

                    Log.d("dfgdfgh",jsonObject.optString("message"));

                    if (!version.equals(jsonObject.optString("message"))) {
                        dialog3 = new Dialog(Testing.this);
                        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog3.setContentView(R.layout.update_dialog);
                        yes_action = (Button) dialog3.findViewById(R.id.yes_action);
                        no_action = (Button) dialog3.findViewById(R.id.no_action);
                        no_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog3.dismiss();
                            }
                        });
                        yes_action.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=carteckh.carteckh"));
                                startActivity(intent);
                                Toast.makeText(Testing.this, "Redirecting to Google Play Store...", Toast.LENGTH_SHORT).show();
                                dialog3.dismiss();
                            }
                        });
                        dialog3.show();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Constants.exitdialog(dialog);

            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest1);


            //Log.d("gdfghgfhfgh",message);


        //***********Update version End ****************//

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    Constants.music_stats = true;
                    Constants.editor.putBoolean("ms", true);

                    Constants.editor.commit();
                    Toast.makeText(Testing.this, "Sound Effect ON", Toast.LENGTH_SHORT).show();

                } else if (isChecked == false) {
                    Constants.music_stats = false;
                    Constants.editor.putBoolean("ms", false);
                    Constants.editor.commit();
                    Home.mediaPlayer.stop();
                    Home.mediaPlayer2.stop();

                    Toast.makeText(Testing.this, "Sound Effect OFF", Toast.LENGTH_SHORT).show();

                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        String val1 = bundle.getString("values");


        if (val1.equals("choice")){

            Constants.editor_choice.putString("datavalue","data_choice");
            Constants.editor_choice.commit();

        }
        else if (val1.equals("diduknow")){

            Constants.editor_did.putString("datavalue","data_diduknow");
            Constants.editor_did.commit();

        }
        else if (val1.equals("newlaunch")){

            Constants.editor_newlaunch.putString("datavalue","data_newlaunch");
            Constants.editor_newlaunch.commit();

        }
        else if (val1.equals("upcoming")){

            Constants.editor_upcoming.putString("datavalue","data_upcoming");
            Constants.editor_upcoming.commit();

        }
        else if (val1.equals("usedcarlist")){

            Constants.editor_usedcar.putString("datavalue","data_usedcar");
            Constants.editor_usedcar.commit();

        }
        //Toast.makeText(getApplicationContext(),Constants.sharedPreferences.contains("state_id")+"",Toast.LENGTH_LONG).show();
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            fragment=new Profile();
//            FragmentManager fm=getSupportFragmentManager();
//            FragmentTransaction ft=fm.beginTransaction();
//            ft.replace(R.id.container,fragment).addToBackStack("test").commit();
//            mDrawerLayout.closeDrawer(GravityCompat.START);

                if (Constants.sharedPreferences.contains("user_id")) {
                    fragment = new Profile();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, fragment).addToBackStack("test").commit();
                    mDrawerLayout.closeDrawer(GravityCompat.START);

                } else {

                    Toast.makeText(Testing.this, "Please Login", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Testing.this, User_Login.class));
                    finish();
                }
            }
        });
        edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constants.sharedPreferences.contains("user_id")) {
                    fragment = new Profile();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, fragment).addToBackStack("test").commit();
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {

                    Toast.makeText(Testing.this, "Please Login", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Testing.this, User_Login.class));
                    finish();
                }


            }
        });

        tv_name.setText(Constants.sharedPreferences.getString("user_name", ""));
        tv_mobile.setText(Constants.sharedPreferences.getString("user_mail", ""));

        fragmentStack = new Stack<Fragment>();
        Constants.stringStack = new Stack<>();
        toolbar = (Toolbar) findViewById(R.id.tool);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mExpandableListView = (ExpandableListView) findViewById(R.id.navList2);
        ImageView img_animation = (ImageView) findViewById(R.id.toolbartext);

        TranslateAnimation animation = new TranslateAnimation(0.0f, 600.0f, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(5000);  // animation duration
        animation.setRepeatCount(Animation.INFINITE);  // animation repeat count
        animation.setRepeatCount(Animation.INFINITE);  // animation repeat count
        animation.setRepeatMode(1);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);

        //Constants.editor.putString("state_id", "");
        //Constants.editor.putString("state_name", "");
        //Constants.editor.commit();

        fragment = new Home();
        Bundle bundle1 = new Bundle();
        bundle1.putString("tncval",val1.toString());

        Constants.editor.putString("tncvalnew", val1.toString());
        Constants.editor.commit();
        fragment.setArguments(bundle1);

        Constants.sharedPreferences = Testing.this.getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();


        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.container, fragment);
            fragmentStack.push(fragment);
            ft.commit();
            Constants.stringStack.push("home");
            Log.e("Test", " add first frag");
            Log.e("Test", " Stack size 0 " + fragmentStack.size());
        }


        setToolbar();
        prepareListData();
        addDrawerItems();
        setupDrawer();
        //beginTransction(new Home());

        doNetworkProcess();


        Log.d("dgdfgdfgh1", Constants.sharedPreferences1.getString("sound", ""));
        //Log.d("dgdfgdfgh2", String.valueOf(Constants.sharedPreferences.getString("sound","")));
//        Constants.editor.putString("sound","true");
//        Constants.editor.commit();



        if (Constants.sharedPreferences1.getString("sound", "").equals("true")) {

                    Log.d("fdgdfgd1", "true");
                    Intent myIntent = new Intent(Testing.this, ServiceClass.class);
//          myIntent = new Intent("com.example.pc.cartus.Service");
                    pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    //long interval = 5000;
            long interval = 1000*60*30;
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);


//            Log.d("fdgdfgd1", "true");
//            Intent myIntent2 = new Intent(Testing.this, ServiceClass2.class);
////          myIntent = new Intent("com.example.pc.cartus.Service");
//            pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent2, 0);
//            AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
//            long interval2 = 6000;
//            //long interval2 = 1000*60*35;
//            alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval2, pendingIntent);

            Log.d("fdgdfgd1", "true");
            Intent myIntent3 = new Intent(Testing.this, ServiceClass3.class);
//          myIntent = new Intent("com.example.pc.cartus.Service");
            pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent3, 0);
            AlarmManager alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);
         //   long interval3 = 500;
            long interval3 = 1000*60*30;
            alarmManager3.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval3, pendingIntent);
//            Thread t2=new Thread();
//            t2 = new Thread() {
//            @Override
//            public void run() {
//                try {
//
//                    while (!isInterrupted()) {
//                        Thread.sleep(5000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=newLaunchNotifiationAlert", null, new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject jsonObject) {
//                                        // Constants.exitdialog(dialog);
//
//                                        if (jsonObject.optString("Success").equals("1")) {
//
//                                            Log.d("dvfdgvdfgdf","1");
//
//                                        }
//
//                                        else if (jsonObject.optString("Success").equals("0")) {
//
//                                            Log.d("dvfdgvdFGDFGfgdf","0");
//                                        }
//
//                                        //adapter.notifyDataSetChanged();
//
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError volleyError) {
////                Constants.exitdialog(dialog);
////                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
//
//                                    }
//                                });
//                                //Constants.showdialog(dialog);
//                                AppController.getInstance().addToRequestQueue(jsonObjectRequest);
//                            }
//
//
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };t2.start();
                }
                else if (Constants.sharedPreferences1.getString("sound", "").equals("false")) {

                    Log.d("fdgdfgd2", "false");

                    Intent myIntent = new Intent(Testing.this, ServiceClass.class);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent, 0);
                    alarmManager.cancel(pendingIntent);
//
//                    Intent myIntent2 = new Intent(Testing.this, ServiceClass2.class);
//                    AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent2, 0);
//                    alarmManager2.cancel(pendingIntent);

                    Intent myIntent3 = new Intent(Testing.this, ServiceClass3.class);
                    AlarmManager alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);
                    pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent3, 0);
                    alarmManager3.cancel(pendingIntent);
                }

//        Thread thread = new Thread() {
//
//            @Override
//            public void run() {
//                super.run();
//
//                try {
//
//
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                if (Constants.sharedPreferences.getString("sound", "").equals("true")) {
//
//                    Log.d("fdgdfgd1", "true");
//                    Intent myIntent = new Intent(Testing.this, ServiceClass.class);
////          myIntent = new Intent("com.example.pc.cartus.Service");
//                    pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent, 0);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    long interval = 5000;
//                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
//                }
//                else if (Constants.sharedPreferences.getString("sound", "").equals("false")) {
//
//                    Log.d("fdgdfgd2", "false");
//
//                    Intent myIntent = new Intent(Testing.this, ServiceClass.class);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    pendingIntent = PendingIntent.getService(Testing.this, 0, myIntent, 0);
//                    alarmManager.cancel(pendingIntent);
//                }
//
////                Intent intent = new Intent(Testing.this, User_Login.class);
////                startActivity(intent);
////                finish();
//
//            }
//        };
//        thread.start();





            //stopService(new Intent(Testing.this, ServiceClass.class));
//            ServiceClass serviceClass=new ServiceClass();
//            serviceClass.onDestroy();
//            serviceClass.stopSelf();
//
//            stopService(new Intent(Testing.this,ServiceClass.class));
//            Intent myIntent = new Intent(this, ServiceClass.class);
////          myIntent = new Intent("com.example.pc.cartus.Service");
//            pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
//            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//            long interval = 5000;
//            //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);





        /**==================Service Started================*/

      //  Toast.makeText(this, "Start Service...", Toast.LENGTH_LONG).show();

/**==================Service Started End================*/




//        Thread t2=new Thread();
//        t2 = new Thread() {
//            @Override
//            public void run() {
//                try {
//
//                    while (!isInterrupted()) {
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//
//
//                        });
//                    }
//                } catch (InterruptedException e) {
//                }
//            }
//        };t2.start();
//
//
//
//
//        if (Constants.sharedPreferences.getBoolean("sound", true)) {
//
//            t = new Thread() {
//                @Override
//                public void run() {
//                    try {
//
//                        while (!isInterrupted()) {
//                            Thread.sleep(5000);
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ServiceClass obj = new ServiceClass();
//                                    obj.onStart();
//                                    //Toast.makeText(Testing.this, "yes", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    } catch (InterruptedException e) {
//                    }
//                }
//            };
//            t.start();
//
//        } else if (Constants.sharedPreferences.getBoolean("sound", false)) {
//            t.stop();
//        }


//        else {
//            Toast.makeText(Testing.this, "false", Toast.LENGTH_SHORT).show();
//        }


//


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void doNetworkProcess() {
        gsonRequest = new GsonRequest<>(Request.Method.GET, Constants.STATE_LIST, State.class, successListener(), errorListener());
        VolleySingleton.getInstance(Testing.this).addToRequestQueue(gsonRequest, "StateList");
        Log.e("Test", " Request gson URL  " + gsonRequest);

    }

    public Response.Listener successListener() {
        return new Response.Listener<State>() {
            @Override
            public void onResponse(State response) {
                if (response != null) {

                    Log.e("Test", " Responce  " + response.getStateList().size());
                    stateList = response.getStateList();
                    // Constants.dismissDialog();
                }
            }


        };
    }

    public Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Testing", " Responce Error data " + error.toString());

                Constants.handleVolleyError(error, Testing.this);
                //Constants.dismissDialog();
            }
        };
    }


    // setting toolbar
    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
    }

    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        listDataHeader = Arrays.asList(getResources().getStringArray(R.array.nav_drawer_items));

        List<String> home = Arrays.asList(getResources().getStringArray(R.array.home));

        List<String> newcar = Arrays.asList(getResources().getStringArray(R.array.NewCar));

        List<String> usedcar = Arrays.asList(getResources().getStringArray(R.array.Usedcar));

        List<String> sellcars = Arrays.asList(getResources().getStringArray(R.array.Sellyourcar));

        List<String> comparecars = Arrays.asList(getResources().getStringArray(R.array.Comparecars));

        List<String> advance = Arrays.asList(getResources().getStringArray(R.array.Advancecar));

        List<String> utilities = Arrays.asList(getResources().getStringArray(R.array.Utilities));

        List<String> advertize = Arrays.asList(getResources().getStringArray(R.array.Advertisewithus));

        List<String> notification = Arrays.asList(getResources().getStringArray(R.array.Notification));


        List<String> login = Arrays.asList(getResources().getStringArray(R.array.Login));


        listDataChild.put(listDataHeader.get(0), home);
        listDataChild.put(listDataHeader.get(1), newcar);
        listDataChild.put(listDataHeader.get(2), usedcar);
        listDataChild.put(listDataHeader.get(3), sellcars);
        listDataChild.put(listDataHeader.get(4), comparecars);
        listDataChild.put(listDataHeader.get(5), comparecars);
        listDataChild.put(listDataHeader.get(6), utilities);
        listDataChild.put(listDataHeader.get(7), advertize);
        listDataChild.put(listDataHeader.get(8), notification);

        listDataChild.put(listDataHeader.get(9), login);

        // mExpandableListView.addHeaderView(listHeaderView);
        mExpandableListData = listDataChild;
        // mExpandableListTitle = new ArrayList(listDataChild.keySet());
        Log.v("Main", " Key set " + listDataChild.keySet());


    }


    private void addDrawerItems() {
        mExpandableListAdapter = new ExpandableListDrawerAdapter(this, listDataHeader, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                switch (groupPosition) {

                    case 0: {
                        Fragment fragmentused = null;
                        fragmentused = new Home();
                        if (fragmentused != null) {
                            beginTransction(fragmentused, "");

                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        Fragment fragmentused = null;
                        Fragment fragment2 = null;
                        fragment2 = new Home();
                        fragmentused = new Used_CarList();
                        if (fragmentused != null) {
                            beginTransction(fragment2, "");
                            beginTransction(fragmentused, "");
                            Constants.stringStack.push("Used_CarList");
                            Log.e("Test", "Usedcar");

                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 3: {
//
//

                            Fragment fragment = null;
                            Fragment fragment2 = null;
                            fragment2 = new Home();
                            fragment = new Packege_sell_car();
                            Bundle bundle = new Bundle();

                            fragment.setArguments(bundle);
                            if (fragment != null) {
                                Constants.stringStack.push("sellyourcar");
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                beginTransction(fragment2, "");
                                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
                            }



//                        if (Constants.sharedPreferences.contains("user_id")) {
//                            Fragment fragment = null;
//                            Fragment fragment2 = null;
//                            fragment2 = new Home();
//                            fragment = new Packege_sell_car();
//                            Bundle bundle = new Bundle();
//
//                            fragment.setArguments(bundle);
//                            if (fragment != null) {
//                                Constants.stringStack.push("sellyourcar");
//                                FragmentManager fragmentManager = getSupportFragmentManager();
//                                beginTransction(fragment2, "");
//                                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//                            }
//                            mDrawerLayout.closeDrawer(GravityCompat.START);
//                        } else {
//
//                            Toast.makeText(Testing.this, "Please Login", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(Testing.this, User_Login.class));
//                            finish();
                        

//                        Fragment fragmentsell = null;
//                        fragmentsell = new Sell_your_Car();
//
//                        Bundle bundle1=new Bundle();
//                        bundle1.putString("key","avnish3");
//                        fragmentsell.setArguments(bundle1);
//                        if (fragmentsell != null) {
//                            beginTransction(fragmentsell,"");
//                            stringStack.push("SellYourCar");
//                        }
                        Log.e("Test", "Sell");
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 4: {
                        Fragment fragmentcompare = null;
                        Fragment fragment2 = null;
                        fragment2 = new Home();
                        fragmentcompare = new Compare_Car();
                        if (fragmentcompare != null) {
                            beginTransction(fragment2, "");
                            beginTransction(fragmentcompare, "");
                            Constants.stringStack.push("Compare");
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 5: {

                        Constants.editor_advance.clear();
                        Constants.editor_advance.commit();
                        Fragment advacncecarsearch = null;
                        Fragment fragment2 = null;
                        fragment2 = new Home();
                        advacncecarsearch = new AdvancedCarSearch();
                        if (advacncecarsearch != null) {
                            beginTransction(fragment2, "");
                            beginTransction(advacncecarsearch, "");
                            Constants.stringStack.push("advancecarsearch");
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case 6: {
                        break;
                    }
                    case 7:

                        Fragment fragmentadver = null;
                        fragmentadver = new AdvertiesWithUs();
                        if (fragmentadver != null) {
                            beginTransction(fragmentadver, "");
                            Constants.stringStack.push("Advertieswithus");
                        }
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 8:


                        break;
                    case 9:
                        if (Constants.sharedPreferences.contains("user_id")) {
//                            Constants.editor_noti.putString("artical_id",Constants.sharedPreferences.getString("artical_id",""));
//                            Constants.editor_noti.putString("car_id",Constants.sharedPreferences.getString("car_id",""));
//                            Constants.editor_noti.putString("artical_id2",Constants.sharedPreferences.getString("artical_id2",""));
//                            Constants.editor_noti.putString("car_id2",Constants.sharedPreferences.getString("car_id2",""));
//                            Constants.editor_noti.commit();
                            SharedPreferences.Editor editor = Constants.sharedPreferences.edit();
                            startActivity(new Intent(Testing.this, User_Login.class));
                            finish();
                            editor.clear();

                            editor.commit();

                        } else {
                            startActivity(new Intent(Testing.this, User_Login.class));
                        }
                        break;
                }

                return false;
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.e("Main", " you click child ");
                selectedItem = ((List) (mExpandableListData.get(listDataHeader.get(groupPosition)))).get(childPosition).toString();
                // toolbar.setTitle(selectedItem);

                switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                        }
                        break;
                    case 1:
                        switch (childPosition) {
                            case 0:
                                Fragment fragment = null;
                                Fragment fragment2 = null;
                                fragment2 = new Home();
                                fragment = new NewCarsList();
                                if (fragment != null) {
                                    Constants.stringStack.push("newc");

                                    beginTransction(fragment2, "");
                                    beginTransction(fragment, "");
                                }
                                break;
                            case 1:
                                Fragment fragmentnewLaunch = null;
                                fragment2 = new Home();
                                fragmentnewLaunch = new NewLaunchCars();  // ???
                                if (fragmentnewLaunch != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentnewLaunch, "");
                                    Constants.stringStack.push("newlaunches");
                                }
                                break;
                            case 2:
                                Fragment fragmentUpcoming = null;
                                fragment2 = new Home();
                                fragmentUpcoming = new UpComing_Cars();   // ???
                                if (fragmentUpcoming != null) {

                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentUpcoming, "");
                                    Constants.stringStack.push("upcoming");
                                }
                                break;
                            case 3:
                                startDialogBox();
//                                Fragment Dealers = null;
//                                Dealers = new ServiceCenterMain();   // ???
//                                if (Dealers != null) {
//                                      beginTransction(Dealers,"");
//                                    stringStack.push("Dealers");
//                                }
                                break;
                            case 4:
                                Fragment fragmentemicalculator = null;
                                fragment2 = new Home();
                                fragmentemicalculator = new Emi_Calculator();   // ???
                                if (fragmentemicalculator != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentemicalculator, "");
                                    Constants.stringStack.push("Emi");
                                }
                                break;

                            case 5:
                                Fragment fragmentcheckoffer = null;
                                fragment2 = new Home();
                                fragmentcheckoffer = new Offers();   // ???
                                if (fragmentcheckoffer != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentcheckoffer, "");
                                    Constants.stringStack.push("offers");
                                }
                                break;

                        }
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 6:
                        switch (childPosition) {
                            case 0:
                                Fragment fragment2 = null;
                                Fragment fragmentroad = null;
                                fragment2 = new Home();
                                fragmentroad = new Road_Assistance();
                                if (fragmentroad != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentroad, "");
                                    Constants.stringStack.push("road");
                                }
                                break;
                            case 1:
                                Fragment fragmentdiduknow = null;
                                fragment2 = new Home();
                                fragmentdiduknow = new Didyou_know();
                                if (fragmentdiduknow != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentdiduknow, "");
                                    Constants.stringStack.push("diduknow");
                                }
                                break;
                            case 2:
                                Fragment fragmentcarchoice = null;
                                fragment2 = new Home();
                                fragmentcarchoice = new Choices();  // ???
                                if (fragmentcarchoice != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentcarchoice, "");
                                    Constants.stringStack.push("choice");
                                }
                                break;
                            case 3:
                                Fragment fragmentgallery = null;
                                fragment2 = new Home();
                                fragmentgallery = new PhotoGallery();
                                if (fragmentgallery != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentgallery, "");
                                    Constants.stringStack.push("gellery");
                                }
                                break;
                            case 4:
                                Fragment fragmentabout = null;
                                fragment2 = new Home();
                                fragmentabout = new Aboutus();  // ??
                                if (fragmentabout != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentabout, "about");
                                    Constants.stringStack.push("about");
                                }
                                break;
                            case 5:
                                Fragment fragmentterm = null;
                                fragment2 = new Home();
                                fragmentterm = new TermConditionFragment(); // ??
                                if (fragmentterm != null) {
                                    beginTransction(fragment2, "");
                                    beginTransction(fragmentterm, "term");
                                    Constants.stringStack.push("term");
                                }
                                break;
                        }
                        break;

                }


                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                //toolbar.setTitle(listDataHeader.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });


    }

    private void startDialogBox() {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Testing.this);
        builder.setTitle("Choose one");
        LayoutInflater layoutInflater = LayoutInflater.from(Testing.this);
        View view = layoutInflater.inflate(R.layout.dialogbox, null);
        Button btndealer = (Button) view.findViewById(R.id.locatedealer);
        Button btnservicecenter = (Button) view.findViewById(R.id.locateservice);
        btndealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentdealers = null;
                Fragment fragment2 = null;
                fragment2 = new Home();
                fragmentdealers = new Locate_Dealer();   // ???
                if (fragmentdealers != null) {
                    beginTransction(fragment2, "");
                    beginTransction(fragmentdealers, "");
                    Constants.stringStack.push("dealers");
                    alertDialog.dismiss();
                }
            }
        });
        btnservicecenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentdealers = null;
                Fragment fragment2 = null;
                fragment2 = new Home();
                fragmentdealers = new Service_Center();   // ???
                if (fragmentdealers != null) {
                    beginTransction(fragment2, "");
                    beginTransction(fragmentdealers, "");
                    Constants.stringStack.push("service");
                    alertDialog.dismiss();
                }
            }
        });

        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.show();

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // toolbar.setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (selectedItem != null) {
                    //  toolbar.setTitle(selectedItem);
                } else {
                    // toolbar.setTitle("MyShopingCart");
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER

                return true;

        }

        int id = item.getItemId();
        Fragment fragment = null;
        if (R.id.action_call == id) {
            fragment = new Road_Assistance();


        } else if (R.id.action_state == id) {
            //dialog1.setTitle("TELL US YOUR STATE");

            dialog1.setContentView(R.layout.state_dialog);
            //popupdialog.setCanceledOnTouchOutside(false);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=StateList", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Constants.exitdialog(dialog);
                    spin_state = (Spinner) dialog1.findViewById(R.id.spin_state);
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


                            final ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(Testing.this, R.layout.spinner_item, State_Names);
                            adapter_state1.setDropDownViewResource(R.layout.spinnertext);
                            spin_state.setAdapter(adapter_state1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Testing.this, R.layout.spinner_item, State_Names);
                        adapter.setDropDownViewResource(R.layout.spinnertext);
                    }
                    final ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(Testing.this, R.layout.spinner_item, State_Names);
                    adapter_state1.setDropDownViewResource(R.layout.spinnertext);

                    spin_state.setAdapter(adapter_state1);
                    spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Constant_Object = adapter_state1.getItem(i);
                            Constant_Object_id = States.get(i).getStr_Brandid().toString();
                            Log.d("gdfhdfh", States.get(i).getStr_Brandid().toString());
                            Constants.editor.putString("stv", "yes");
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

                    Constants.exitdialog(dialog);
                    //Toast.makeText(Testing.this, "Connect to the Internet Please try again", Toast.LENGTH_SHORT).show();
                    Crouton.makeText(Testing.this, "Connect to the Internet Please try again", Style.ALERT).show();
                }
            });
            dialog1.show();
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
                        Crouton.makeText(Testing.this, "Your Selected State is " + Constants.sharedPreferences.getString("state_name", ""), Style.ALERT).show();
                        Toast.makeText(Testing.this, "Your Selected State is " + Constants.sharedPreferences.getString("state_name", ""), Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();

                    } else if (spin_state.getSelectedItem().equals("Select State")) {

                        //Toast.makeText(Testing.this, Constant_Object, Toast.LENGTH_SHORT).show();
//                        Crouton.makeText(Testing.this, Constant_Object.getId() + "," + Constant_Object.getState(), Style.ALERT).show();
//                        Constants.editor.putString("state_id", Constant_Object.getId());
//                        Constants.editor.putString("state_name", Constant_Object.getState());
//                        Constants.editor.commit();
                        //Crouton.makeText(getActivity(), Constant_Object, Style.ALERT).show();
                        Log.d("asfdgdfgfd", Constant_Object);
                        Constants.editor.putString("state_name", Constant_Object);
                        Constants.editor.putString("state_id", Constant_Object_id);
                        Log.d("gfdgdf", Constant_Object_id);
                        Constants.editor.commit();
                        dialog1.dismiss();
                        Crouton.makeText(Testing.this, "Your Default State is Delhi", Style.ALERT).show();
                        Toast.makeText(Testing.this, "Your Default State is Delhi", Toast.LENGTH_SHORT).show();

//                        AlertDialog.Builder builder = new AlertDialog.Builder(Testing.this);
//                        builder.setCancelable(false);
//                        builder.setTitle("AlertDialog Title");
//                        builder.setMessage("Simple Dialog Message");
//                        builder.setPositiveButton("OK!!!", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int id) {
//                                //
//                            }
//                        })
//                                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                });
//
//                        // Create the AlertDialog object and return it
//                        builder.create().show();


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
                        Log.d("asd", Constant_Object);
                        Constants.editor.putString("state_name", Constant_Object);
                        Constants.editor.putString("state_id", Constant_Object_id);
                        Constants.editor.commit();
                        Log.d("gfdgdf", Constant_Object_id);
                        Crouton.makeText(Testing.this, "Your Selected State is " + Constants.sharedPreferences.getString("state_name", ""), Style.ALERT).show();
                        Toast.makeText(Testing.this, "Your Selected State is " + Constants.sharedPreferences.getString("state_name", ""), Toast.LENGTH_SHORT).show();
//
//                        NewCarListByBrandSwipe.grid_view.setAdapter(NewCarListByBrandSwipe.adapter);
//                        AppController.getInstance().addToRequestQueue(NewCarListByBrandSwipe.jsonObjectRequest);

//                        Fragment fragment = new CarOverView();
//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container, fragment).commit();


                        dialog1.dismiss();

                    } else if (spin_state.getSelectedItem().equals("Select State")) {

                        //Toast.makeText(Testing.this, Constant_Object, Toast.LENGTH_SHORT).show();
//                        Crouton.makeText(Testing.this, Constant_Object.getId() + "," + Constant_Object.getState(), Style.ALERT).show();
//                        Constants.editor.putString("state_id", Constant_Object.getId());
//                        Constants.editor.putString("state_name", Constant_Object.getState());
//                        Constants.editor.commit();
                        //Crouton.makeText(getActivity(), Constant_Object, Style.ALERT).show();
                        Log.d("afgfgsd", Constant_Object);
                        Constants.editor.putString("state_name", "Delhi");
                        Constants.editor.putString("state_id", Constant_Object_id);
                        Log.d("gfdgdf", Constant_Object_id);
                        Constants.editor.commit();
                        Crouton.makeText(Testing.this, "Your Default State is Delhi", Style.ALERT).show();
                        Toast.makeText(Testing.this, "Your Default State is Delhi", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();

                    }
                }
            });

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();

        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

//    @Override
//    public void onBackPressed() {
//        // TODO Auto-generated method stub
//        // super.onBackPressed();
//
//        Log.e("Test", " Stack size " + Constants.stringStack.size());
//        if (Constants.stringStack.size() == 1) {
//
//            new AlertDialog.Builder(this)
//                    .setMessage("Are you sure you want to exit?")
//                    .setCancelable(false)
//                    .setPositiveButton("Yes",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                                    intent.addCategory(Intent.CATEGORY_HOME);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                    System.exit(0);
//                                }
//                            }).setNegativeButton("No", null).show();
//        } else {
//            Log.e("Test", " backpressed");
//            Constants.stringStack.pop();
//            super.onBackPressed();
//        }
//
//    }


    private void beginTransction(Fragment fragment, String tag) {
        Bundle bundle = new Bundle();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            bundle.putString("from", tag);
            fragment.setArguments(bundle);
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }


    }

    @Override
    public void itemClick(String tag) {
        Constants.stringStack.push(tag);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Testing Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://sntinfotech.carteck.actvities/http/host/path")
        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Testing Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://sntinfotech.carteck.actvities/http/host/path")
        );
       // AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }




}

