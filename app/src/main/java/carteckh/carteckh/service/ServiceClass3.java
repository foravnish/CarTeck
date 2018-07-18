package carteckh.carteckh.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Random;

import carteckh.carteckh.AppController;
import carteckh.carteckh.R;
import carteckh.carteckh.actvities.Testing;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 24-Dec-15.
 */
public class ServiceClass3 extends Service {


    @Nullable

    int flag3=1;
    public  static int count=0;


    private NotificationManager myNotificationManager;

    @Override
    public void onCreate() {


    }


    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {

//        boolean b = isNetworkAvailable();

        Log.d("Network_Connection1","true");

        Constants.sharedPreferences = getApplicationContext().getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();



        final TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Log.d("hghgfhgjghjg",telephonyManager.getDeviceId());

        Constants.sharedPreferences = getApplicationContext().getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

        JsonObjectRequest notification_request1 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=carResponseSellerAlert", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // Constants.exitdialog(dialog);
                Log.d("frgtdfghytrfyu", Constants.sharedPreferences.getString("seller_id", ""));
                Log.d("gdfgfhfh", "true");
                Log.d("gfggffhfg",jsonObject.optString("sellerId"));
                // Toast.makeText(getActivity(), "val: "+Constants.sharedPreferences.getString("seller_id",""), Toast.LENGTH_SHORT).show();

                if (!Constants.sharedPreferences.getString("seller_id","").equals(jsonObject.optString("sellerId"))) {

                    Constants.editor.putString("seller_id", jsonObject.optString("sellerId"));
                    Constants.editor.commit();
                    Log.d("ghfhgfhgf", Constants.sharedPreferences.getString("seller_id", ""));
                    Log.d("gfdghgfghfghfhfg3", Constants.sharedPreferences.getString("user_id", ""));
                }
                else
                {
                    Log.d("gdfgdfghf","flase");
                }

//                else if (jsonObject.optString("Success").equals("0")) {
//
//                    Log.d("dvfdgvdFGDFGfgdfere","0");
//                }
//
//                Crouton.makeText(ServiceClass2.this,jsonObject.optString("message"), Style.CONFIRM).show();

                //adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();
            }
        });
        //Constants.showdialog(dialog);
        //Crouton.makeText(getActivity(),jsonObject.optString("message"), Style.CONFIRM).show();
        AppController.getInstance().addToRequestQueue(notification_request1);
        ////


        if (Constants.sharedPreferences.getString("seller_id","").equals(Constants.sharedPreferences.getString("user_id",""))) {

            JsonObjectRequest notification_request = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=carResponseNotifiationAlert&sellerId=" + Constants.sharedPreferences.getString("seller_id", ""), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    // Constants.exitdialog(dialog);
                    Log.d("frgtdfghytrfyu", Constants.sharedPreferences.getString("seller_id", ""));
                    Log.d("gdfgfhfh", "true");
                    // Toast.makeText(getActivity(), "val: "+Constants.sharedPreferences.getString("seller_id",""), Toast.LENGTH_SHORT).show();


                        Log.d("gfdghfhfg", Constants.sharedPreferences.getString("seller_id", ""));
                        Log.d("gfdghfhfg2", Constants.sharedPreferences.getString("user_id", ""));


                    if (!Constants.sharedPreferences.getString("seller","").equals(jsonObject.optString("sellerId"))){

                        flag3=0;
                        Log.d("fdgdfgdfh","true");
                    }

                    if (flag3==0){

                        Log.d("sdfgdfgdfg", "true");
                        Log.d("fghfgfgfgf", "used true");




                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ServiceClass3.this);

                        mBuilder.setContentTitle("Buyer: "+jsonObject.optString("name"));
//
                        mBuilder.setContentText("For "+jsonObject.optString("brand") + " " + jsonObject.optString("model_id") + " " + jsonObject.optString("version_id"));
//
                        mBuilder.setTicker("Buyer: "+jsonObject.optString("name"));
                        mBuilder.setSmallIcon(R.drawable.notification_icon);
                        mBuilder.setDefaults(Notification.DEFAULT_ALL);
                        mBuilder.setAutoCancel(true);

                        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("For "+jsonObject.optString("brand") + " " + jsonObject.optString("model_id") + " " + jsonObject.optString("version_id")));
                        mBuilder.setContentText("For "+jsonObject.optString("brand") + " " + jsonObject.optString("model_id") + " " + jsonObject.optString("version_id")).build();
                        //mBuilder.setWhen(System.currentTimeMillis());
                        //mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()));
//                        mBuilder.setContentText(jsonObject.optString("message").toString()).build();
                        // Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(ServiceClass3.this, Testing.class);
                        resultIntent.putExtra("values", "usedcarlist");
                        Constants.editor.putString("name", jsonObject.optString("name"));
                        Constants.editor.putString("brand", jsonObject.optString("brand"));
                        Constants.editor.putString("model_id", jsonObject.optString("model_id"));
                        Constants.editor.putString("version_id", jsonObject.optString("version_id"));
                        Constants.editor.putString("mobile", jsonObject.optString("mobile"));
                        Constants.editor.commit();

                        //This ensures that navigating backward from the Activity leads out of the app to Home page
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ServiceClass3.this);

                        // Adds the back stack for the Intent
                        stackBuilder.addParentStack(Testing.class);

                        // Adds the Intent that starts the Activity to the top of the stack
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_CANCEL_CURRENT //can only be used once
                                );
//
//                    Notification notification = mBuilder.setSmallIcon(R.drawable.notification_icon).setTicker("Did You Know").setWhen(0)
//                            .setAutoCancel(true)
//                            .setContentTitle("Did You Know")
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()))
//                            .setContentIntent(resultPendingIntent)
//                            .setContentText(jsonObject.optString("message").toString()).build();
                        // start the activity when the user clicks the notification text
                        mBuilder.setContentIntent(resultPendingIntent);

                        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Random random = new Random();
                        int randomNumber = random.nextInt(9999 - 1000) + 1000;
                        // pass the Notification object to the system
                        myNotificationManager.notify(randomNumber, mBuilder.build());








//                        NotificationCompat.Builder builder = new NotificationCompat.Builder(ServiceClass3.this);
//                        builder.setSmallIcon(R.drawable.notification_icon);
//                        builder.setContentTitle(jsonObject.optString("name") + " Intrested your Car");
//                        //builder.setContentText(jsonObject.optString("message"));
//                        builder.setContentText(jsonObject.optString("brand") + " " + jsonObject.optString("model_id") + " " + jsonObject.optString("version_id") + " " + jsonObject.optString("mobile"));
//                        builder.setDefaults(Notification.DEFAULT_ALL);
//                        builder.setAutoCancel(true);
//                        Intent intent2 = new Intent(ServiceClass3.this, Testing.class);
//                        intent2.putExtra("values", "usedcarlist");
//                        Constants.editor.putString("name", jsonObject.optString("name"));
//                        Constants.editor.putString("brand", jsonObject.optString("brand"));
//                        Constants.editor.putString("model_id", jsonObject.optString("model_id"));
//                        Constants.editor.putString("version_id", jsonObject.optString("version_id"));
//                        Constants.editor.putString("mobile", jsonObject.optString("mobile"));
//                        Constants.editor.commit();
//                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ServiceClass3.this);
//                        stackBuilder.addParentStack(Testing.class);
//                        stackBuilder.addNextIntent(intent2);
//                        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
//                        builder.setContentIntent(pendingIntent);
//                        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                        Random random = new Random();
//                        int randomNumber = random.nextInt(9999 - 1000) + 1000;
//                        nm.notify(randomNumber, builder.getNotification());




                    flag3=1;

                    Constants.editor.putString("seller", jsonObject.optString("sellerId"));
                    //Constants.editor.putString("device_id",telephonyManager.getDeviceId());
                    Constants.editor.commit();

                }
                if (flag3==1){
                    Log.d("fdgdfgdfghdfh","false");
                }

//                Crouton.makeText(ServiceClass2.this,jsonObject.optString("message"), Style.CONFIRM).show();

                    //adapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
//                Constants.exitdialog(dialog);
//                Crouton.makeText(getActivity(), "" + "Some problem occured pls try again", Style.ALERT).show();

                }
            });
            //Constants.showdialog(dialog);
            //Crouton.makeText(getActivity(),jsonObject.optString("message"), Style.CONFIRM).show();
            AppController.getInstance().addToRequestQueue(notification_request);

        }


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}





