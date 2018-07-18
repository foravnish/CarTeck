package carteckh.carteckh.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by developer on 26-Jun-15.
 */

public class ShareApp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        // sharingIntent.setType("text/html");
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Carteckh ");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://www.carteckh.com/");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
        finish();
    }

}