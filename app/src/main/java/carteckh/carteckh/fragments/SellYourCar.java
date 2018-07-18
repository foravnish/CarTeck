package carteckh.carteckh.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import carteckh.carteckh.util.Constants;
import carteckh.carteckh.R;

/**
 * Created by developer on 06-Jan-16.
 */
public class SellYourCar extends Fragment {
    View view;
    @InjectView(R.id.webview)
    WebView webView;
    @InjectView(R.id.progressBarweb)
    ProgressBar progressBar;
    @InjectView(R.id.noti_tv)
    TextView notifi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.webview, container, false);
        ButterKnife.inject(this, view);
        webView.setWebViewClient(new webview());

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://www.carteckh.com/sell-car/?id=" + Constants.sharedPreferences.getString("user_id", "") + "&password=" + Constants.sharedPreferences.getString("user_pass", "") + "&loginFrom=app");


        return view;
    }

    private class webview extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(view.GONE);
            notifi.setVisibility(view.GONE);
        }
    }


}
