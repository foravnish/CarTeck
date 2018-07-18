package carteckh.carteckh.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import carteckh.carteckh.util.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.AppController;
import carteckh.carteckh.R;

/**
 * Created by andriod1 on 6/23/2016.
 */
public class Aboutus extends Fragment {

    View view;
    Dialog dialog;
//    @InjectView(R.id.webview)
//    WebView webView;
//    private String from;
//    private Bundle bundle;
//    @InjectView(R.id.progressBarweb)
//    ProgressBar progressBar;
//    @InjectView(R.id.noti_tv)
//    TextView header;
    @InjectView(R.id.about)
    TextView about;
//    @InjectView(R.id.about)
//    WebView about;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new Dialog(Aboutus.this.getActivity());

        //remove the title of dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.aboutus, null);
        ButterKnife.inject(this, view);
        final String htmlText = " %s ";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, Constants.Url + "task=contentType&type=AboutUs", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Constants.exitdialog(dialog);

                JSONArray jsonArray = jsonObject.optJSONArray("ContentInfo");

                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.d("", jsonArray.toString());
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    about.setText(jsonObject1.optString("content"));
//                     String myData=jsonObject1.optString("content");
//                    String htmlView = "<html><body style=\"text-align:justify\"> "+myData+" </body></Html>";
//
//                     htmlView = "<html><head>"
//                            + "<style type=\"text/css\">li{color: #00f} span {color: #000}"
//                            + "</style></head>"
//                            + "<body>"
//                            + myData
//                            + "</body></html>";
//                    about.loadData(String.format(htmlText,htmlView),"text/html", "utf-8");
//                    about.setBackgroundColor(Color.GRAY);

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                //Toast.makeText(Testing.this, "Some problem occured pls try again", Toast.LENGTH_SHORT).show();
                //Crouton.makeText(Testing.this, "Some problem occured pls try again", Style.ALERT).show();
            }
        });
        Constants.showdialog(dialog);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);



        return view;

    }



}