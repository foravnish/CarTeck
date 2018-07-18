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
public class ServiceClass extends Service {
    @Nullable

    public static int flag1=1;
    public static int flag2=1;
    public static int flag3=1;
    public static int flag4=1;
    public static int count=0;
    public static int count2=0;
    @Override
    public void onCreate() {


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);


        Log.d("fvgdgdfhgd",telephonyManager.getDeviceId());

//        boolean b = isNetworkAvailable();

        Log.d("Network_Connection","true");

        Constants.sharedPreferences = getApplicationContext().getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();

        Constants.sharedPreferences1 = getApplicationContext().getSharedPreferences(Constants.Myprefrence1, MODE_PRIVATE);
        Constants.editor1 = Constants.sharedPreferences1.edit();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=newLaunchNotifiationAlert1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // Constants.exitdialog(dialog);

                if (jsonObject.optString("Success").equals("1")) {

                    if (!Constants.sharedPreferences1.getString("car_id", "").equals(jsonObject.optString("id"))) {
                        flag1 = 0;
                        Log.d("fdgdfgdfh", "true");
                    }


                    if (flag1 == 0) {

                        Log.d("gfhgfhgfhj", "true");


//                    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//                    bigText.bigText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
//                    bigText.setBigContentTitle("Big Text Notification");
//                    bigText.setSummaryText("By: Author of Lorem ipsum");
//
////build notification
//                    NotificationCompat.Builder mBuilder =
//                            new NotificationCompat.Builder(ServiceClass.this)
//                                    .setSmallIcon(R.drawable.notification_icon)
//                                    .setContentTitle("Big Text notification")
//                                    .setContentText("This is test of big text style notification.")
//
//                                    .setStyle(bigText);
//
//// Gets an instance of the NotificationManager service
//                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
////to post your notification to the notification bar
//                    mNotificationManager.notify(0, mBuilder.build());


//                    NotificationCompat.Builder builder=new NotificationCompat.Builder(ServiceClass.this);
//                    builder.setSmallIcon(R.drawable.notification_icon);
//                    builder.setContentTitle("New Car Launch");
//                    builder.setContentText(jsonObject.optString("message"));
//                    builder.setTicker("New Car Launch");
//                   // builder.setSubText("Sub Text");
//                   // builder.setContentInfo("Contant info");
//
//                    //builder.setContentText(jsonObject.optString("message"));
//
//                    builder.setDefaults(Notification.DEFAULT_ALL);
//                    //builder.setNumber(++count);
//                    builder.setAutoCancel(true);
//                    Intent intent2=new Intent(ServiceClass.this,Testing.class);
//                    intent2.putExtra("values", "newlaunch");
//                    TaskStackBuilder stackBuilder= TaskStackBuilder.create(ServiceClass.this);
//                    stackBuilder.addParentStack(Testing.class);
//                    stackBuilder.addNextIntent(intent2);
//
//                    PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
//                    builder.setContentIntent(pendingIntent);
//
//                    NotificationManager nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                    Random random = new Random();
//                    int randomNumber = random.nextInt(9999 - 1000) + 1000;
//                    nm.notify(randomNumber, builder.getNotification());


                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ServiceClass.this);

                        mBuilder.setContentTitle("New Car Launch");
                        mBuilder.setContentText(jsonObject.optString("message").toString());
                        mBuilder.setTicker("New Car Launch");
                        mBuilder.setSmallIcon(R.drawable.notification_icon);
                        mBuilder.setDefaults(Notification.DEFAULT_ALL);
                        //mBuilder.setSubText("");
                        // Increase notification number every time a new notification arrives
                        //mBuilder.setNumber(++count);
                        mBuilder.setAutoCancel(true);
                        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()));
                        mBuilder.setContentText(jsonObject.optString("message").toString()).build();
                        // Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(ServiceClass.this, Testing.class);
                        resultIntent.putExtra("values", "newlaunch");


                        //This ensures that navigating backward from the Activity leads out of the app to Home page
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ServiceClass.this);

                        // Adds the back stack for the Intent
                        stackBuilder.addParentStack(Testing.class);

                        // Adds the Intent that starts the Activity to the top of the stack
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_CANCEL_CURRENT  //can only be used once
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

                        NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Random random = new Random();
                        int randomNumber = random.nextInt(9999 - 1000) + 1000;
                        myNotificationManager.notify(randomNumber, mBuilder.build());

                        flag1 = 1;

                        Constants.editor1.putString("car_id", jsonObject.optString("id"));
                        Constants.editor1.commit();

                    }
                    if (flag1 == 1) {
                        Log.d("fdgdfgdfghdfh", "false");
                    }
//                adapter.notifyDataSetChanged();

                }
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


        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=upcommingCarNotifiationAlert", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // Constants.exitdialog(dialog);

                if (jsonObject.optString("Success").equals("1")) {


                    if (!Constants.sharedPreferences1.getString("car_id2", "").equals(jsonObject.optString("id"))) {
                        flag2 = 0;
                        Log.d("fdgdfgdfh", "true");
                    }

                    if (flag2 == 0) {
                        Log.d("gfhgfhjgf", "true");

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ServiceClass.this);

                        mBuilder.setContentTitle("Upcoming Car");
                        mBuilder.setContentText(jsonObject.optString("message").toString());
                        mBuilder.setTicker("Upcoming Car");
                        mBuilder.setSmallIcon(R.drawable.notification_icon);
                        mBuilder.setDefaults(Notification.DEFAULT_ALL);
                        //mBuilder.setSubText("");
                        // Increase notification number every time a new notification arrives
                        //mBuilder.setNumber(++count);
                        mBuilder.setAutoCancel(true);
                        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()));
                        mBuilder.setContentText(jsonObject.optString("message").toString()).build();
                        // Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(ServiceClass.this, Testing.class);
                        resultIntent.putExtra("values", "upcoming");


                        //This ensures that navigating backward from the Activity leads out of the app to Home page
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ServiceClass.this);

                        // Adds the back stack for the Intent
                        stackBuilder.addParentStack(Testing.class);

                        // Adds the Intent that starts the Activity to the top of the stack
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_CANCEL_CURRENT  //can only be used once
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

                        NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Random random = new Random();
                        int randomNumber = random.nextInt(9999 - 1000) + 1000;

                        // pass the Notification object to the system
                        myNotificationManager.notify(randomNumber, mBuilder.build());


//
//                    NotificationCompat.Builder builder=new NotificationCompat.Builder(ServiceClass.this);
//                    builder.setSmallIcon(R.drawable.notification_icon);
//
//                    builder.setContentTitle("Upcoming Car");
//                    //builder.setContentText(jsonObject.optString("message"));
//                    builder.setContentText(jsonObject.optString("message"));
//
//                    builder.setDefaults(Notification.DEFAULT_ALL);
//                    //builder.setNumber(++count2);
//                    builder.setAutoCancel(true);
//                    Intent intent2=new Intent(ServiceClass.this,Testing.class);
//                    intent2.putExtra("values", "upcoming");
//                    TaskStackBuilder stackBuilder= TaskStackBuilder.create(ServiceClass.this);
//                    stackBuilder.addParentStack(Testing.class);
//                    stackBuilder.addNextIntent(intent2);
//                    PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
//                    builder.setContentIntent(pendingIntent);
//
//                    NotificationManager nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    Random random = new Random();
//                    int randomNumber = random.nextInt(9999 - 1000) + 1000;
//                    nm.notify(randomNumber, builder.getNotification());


                        flag2 = 1;

                        Constants.editor1.putString("car_id2", jsonObject.optString("id"));
                        Constants.editor1.commit();
                    }

                    if (flag2 == 1) {
                        Log.d("fdgdfgdfghdfh", "false");
                    }

                    //adapter.notifyDataSetChanged();

                }
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


        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=didYouKnowNotifiationAlert", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // Constants.exitdialog(dialog);
                if (jsonObject.optString("Success").equals("1")) {
                    //  if (jsonObject.optString("Success").equals("1")) {

//                Constants.editor1.putString("message",jsonObject.optString("message").toString());
//                Constants.editor1.commit();
                    Log.d("dvfdgvdfgdfgdfgdfhdf", "1");

                    //if (!Constants.sharedPreferences.getString("artical_id","").equals(jsonObject.optString("id")) && Constants.sharedPreferences.getString("device_id","").equals(telephonyManager.getDeviceId())) {

                    if (!Constants.sharedPreferences1.getString("artical_id", "").equals(jsonObject.optString("id"))) {

                        flag3 = 0;
                        Log.d("fdgdfgdfh", "true");
                    }


                    if (flag3 == 0) {

                        Log.d("sdfgdfgdfg", "true");
                        //

//                    int mNotificationId = 001;
//
//                    Intent intent2 = new Intent(ServiceClass2.this, Testing.class);
//                    PendingIntent resultPendingIntent =
//                            PendingIntent.getActivity(
//                                    getApplicationContext(),
//                                    0,
//                                    intent2,
//                                    PendingIntent.FLAG_CANCEL_CURRENT
//                            );
//
//
//                    intent2.putExtra("values", "diduknow");
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                            getApplicationContext());
//
//                    Notification notification = mBuilder.setSmallIcon(R.drawable.notification_icon).setTicker("f gdfgh").setWhen(0)
//                            .setAutoCancel(true)
//                            .setContentTitle("g fdgdfghdf h")
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText("gdf h ghgj hgjh gjhgj hgj hgj"))
//                            .setContentIntent(resultPendingIntent)
//
//                            .setContentText("f gfh").build();
//
//                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.notify(mNotificationId, notification);


                        //


                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ServiceClass.this);

                        mBuilder.setContentTitle("Did You Know");
                        mBuilder.setContentText(jsonObject.optString("message").toString());
                        mBuilder.setTicker("Did You Know");
                        mBuilder.setSmallIcon(R.drawable.notification_icon);
                        mBuilder.setDefaults(Notification.DEFAULT_ALL);
                        //mBuilder.setSubText("");
                        // Increase notification number every time a new notification arrives
                        //mBuilder.setNumber(++count);
                        mBuilder.setAutoCancel(true);
                        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()));
                        mBuilder.setContentText(jsonObject.optString("message").toString()).build();
                        // Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(ServiceClass.this, Testing.class);
                        resultIntent.putExtra("values", "diduknow");


                        //This ensures that navigating backward from the Activity leads out of the app to Home page
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ServiceClass.this);

                        // Adds the back stack for the Intent
                        stackBuilder.addParentStack(Testing.class);

                        // Adds the Intent that starts the Activity to the top of the stack
                        stackBuilder.addNextIntent(resultIntent);
                        PendingIntent resultPendingIntent =
                                stackBuilder.getPendingIntent(
                                        0,
                                        PendingIntent.FLAG_CANCEL_CURRENT  //can only be used once
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

                        NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Random random = new Random();
                        int randomNumber = random.nextInt(9999 - 1000) + 1000;

                        // pass the Notification object to the system
                        myNotificationManager.notify(randomNumber, mBuilder.build());


//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(ServiceClass2.this);
//                    builder.setSmallIcon(R.drawable.notification_icon);
//                    builder.setContentTitle("Did You Know");
//                    builder.setContentText(jsonObject.optString("message").toString());
//                    builder.setTicker("Did You Know");
//                    builder.setDefaults(Notification.DEFAULT_ALL);
//                    builder.setAutoCancel(true);
//                    builder.setNumber(++count);
//
//                    Intent intent2 = new Intent(ServiceClass2.this, Testing.class);
//                    intent2.putExtra("values", "diduknow");
//
//                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(ServiceClass2.this);
//                    stackBuilder.addParentStack(Testing.class);
//                    stackBuilder.addNextIntent(intent2);
//
//                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
//
////                    Notification notification = builder.setSmallIcon(R.drawable.notification_icon).setTicker("Did You Know").setWhen(0)
////                            .setAutoCancel(true)
////                            .setContentTitle("Did You Know")
////                            .setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()))
////                            .setContentIntent(pendingIntent)
////                            .setContentText(jsonObject.optString("message").toString()).build();
//
//
//                    builder.setContentIntent(pendingIntent);
//                    NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    Random random = new Random();
//                    int randomNumber = random.nextInt(9999 - 1000) + 1000;
//                    nm.notify(111, builder.getNotification());

                        flag3 = 1;

                        Constants.editor1.putString("artical_id", jsonObject.optString("id"));
                        //Constants.editor1.putString("device_id",telephonyManager.getDeviceId());
                        Constants.editor1.commit();

                    }
                    if (flag3 == 1) {
                        Log.d("fdgdfgdfghdfh", "false");
                    }


                    //adapter.notifyDataSetChanged();

                }
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


        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest(Request.Method.GET, Constants.Url + "task=carteckhChoiceNotifiationAlert", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                // Constants.exitdialog(dialog);
                if (jsonObject.optString("Success").equals("1")) {
                    if (!Constants.sharedPreferences1.getString("artical_id2", "").equals(jsonObject.optString("id"))) {
                        flag4 = 0;
                        Log.d("fdgdfgdfh", "true");
                    }

                    if (flag4 == 0) {

                        Log.d("dvfdgvdfgdfgdfgdfhdf", "1");


                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ServiceClass.this);

                        mBuilder.setContentTitle("Carteckh Choice");
                        mBuilder.setContentText(jsonObject.optString("message").toString());
                        mBuilder.setTicker("Carteckh Choice");
                        mBuilder.setSmallIcon(R.drawable.notification_icon);
                        mBuilder.setDefaults(Notification.DEFAULT_ALL);
                        mBuilder.setAutoCancel(true);
                        // Increase notification number every time a new notification arrives
                        //  mBuilder.setNumber(++count2);
                        //          mBuilder.setAutoCancel(true);
                        mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()));
                        mBuilder.setContentText(jsonObject.optString("message").toString()).build();
                        // Creates an explicit intent for an Activity in your app
                        Intent resultIntent = new Intent(ServiceClass.this, Testing.class);
                        resultIntent.putExtra("values", "choice");


                        //This ensures that navigating backward from the Activity leads out of the app to Home page
                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ServiceClass.this);

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

                        NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        Random random = new Random();
                        int randomNumber = random.nextInt(9999 - 1000) + 1000;
                        // pass the Notification object to the system
                        myNotificationManager.notify(randomNumber, mBuilder.build());


//                    NotificationCompat.Builder builder=new NotificationCompat.Builder(ServiceClass2.this);
//                    builder.setSmallIcon(R.drawable.notification_icon);
//
//                    builder.setContentTitle("Carteckh Choice");
////                    builder.setContentText(jsonObject.optString("message"));
//                    builder.setContentText(jsonObject.optString("message").toString());
//                    builder.setDefaults(Notification.DEFAULT_ALL);
//                    builder.setAutoCancel(true);
//                    Intent intent2=new Intent(ServiceClass2.this,Testing.class);
//                    intent2.putExtra("values", "choice");
//                    TaskStackBuilder stackBuilder= TaskStackBuilder.create(ServiceClass2.this);
//                    stackBuilder.addParentStack(Testing.class);
//                    stackBuilder.addNextIntent(intent2);
//
//                    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
//                    Notification notification = builder.setSmallIcon(R.drawable.notification_icon).setTicker("Carteckh Choice").setWhen(0)
//                            .setAutoCancel(true)
//                            .setContentTitle("Carteckh Choice")
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText(jsonObject.optString("message").toString()))
//                            .setContentIntent(pendingIntent)
//                            .setContentText(jsonObject.optString("message").toString()).build();
//
//                    NotificationManager nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    Random random = new Random();
//                    int randomNumber = random.nextInt(9999 - 1000) + 1000;
//                    nm.notify(randomNumber, builder.getNotification());

                        flag4 = 1;

                        Constants.editor1.putString("artical_id2", jsonObject.optString("id"));
                        Constants.editor1.commit();
                    }

                    if (flag4 == 1) {
                        Log.d("fdgdfgd fghdfh", "false");
                    }


                    //adapter.notifyDataSetChanged();

                }
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
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(ServiceClass.this);
//        builder.setSmallIcon(R.drawable.ic_launcher);
//        builder.setContentTitle("My Notification");
//        builder.setContentText("First Notification");
//        builder.setDefaults(Notification.DEFAULT_ALL);
//        Intent intent2=new Intent(ServiceClass.this,Testing.class);
//        TaskStackBuilder stackBuilder= TaskStackBuilder.create(ServiceClass.this);
//        stackBuilder.addParentStack(Testing.class);
//        stackBuilder.addNextIntent(intent2);
//        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        NotificationManager nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        nm.notify(0,builder.build());

        // Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();

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





