package carteckh.carteckh.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carteckh.carteckh.R;
import carteckh.carteckh.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvertiesWithUs extends Fragment {
    @InjectView(R.id.webview)
    WebView webView;
    @InjectView(R.id.progressBarweb)
    ProgressBar progressBar;
    @InjectView(R.id.noti_tv)
    TextView notifi;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.webview, container, false);
        ButterKnife.inject(this,view);
        webView.setWebViewClient(new webview());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(view.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://www.carteckh.com/advertise-with-us/?uid=" + Constants.sharedPreferences.getString("user_id", "") + "&password=" + Constants.sharedPreferences.getString("user_pass", "") + "&loginFrom=app");

        return  view;
    }


    private class webview extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(view.GONE);
            notifi.setVisibility(view.GONE);
        }
    }
}
