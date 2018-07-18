package carteckh.carteckh.actvities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * Created by developer on 12-Feb-16.
 */
public class Splash extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        ImageView imageView = (ImageView) findViewById(R.id.logo);
        mediaPlayer = MediaPlayer.create(Splash.this, R.raw.splash);

        Constants.sharedPreferences = Splash.this.getSharedPreferences(Constants.Myprefrence, MODE_PRIVATE);
        Constants.editor = Constants.sharedPreferences.edit();


//

//        if(Constants.music_stats.equals(true)) {
//            mediaPlayer.start();
//        }
//        else if(Constants.music_stats.equals(false)){
//            mediaPlayer.stop();
//        }



        TranslateAnimation animation = new TranslateAnimation(-420.0f, 0.0f,0.0f, 0.0f);  //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(2000);  // animation duration
        // animation repeat count
        // repeat animation (left to right, right to left )
        animation.setFillAfter(true);
        imageView.startAnimation(animation);

        Thread thread = new Thread() {

            @Override
            public void run() {
                super.run();

                try {

                    if (Constants.sharedPreferences.getBoolean("ms",true)){
                        mediaPlayer.start();
                    }
                    else if (Constants.sharedPreferences.getBoolean("ms",false)){
                        mediaPlayer.stop();
                    }
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(Splash.this, User_Login.class);
                startActivity(intent);
                finish();
            }
        };
        thread.start();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "sntinfotech.carteck",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);

    }

}
