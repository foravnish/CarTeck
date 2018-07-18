package carteckh.carteckh.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
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
public class OfferListing extends Fragment {
    View view;
    @InjectView(R.id.webview)
    WebView webView;

    @InjectView(R.id.progressBarweb)
    ProgressBar pb;
    @InjectView( R.id.noti_tv)
    TextView notofi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.webview, container, false);
        ButterKnife.inject(this, view);
        webView.setWebViewClient(new webview());

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://www.carteckh.com/my-offer-listing.php?uid=" + Constants.sharedPreferences.getString("user_id", "") + "&password=" + Constants.sharedPreferences.getString("user_pass", "") + "&loginFrom=app");
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                            } else {
                                getFragmentManager().popBackStackImmediate();
                            }
                            return true;
                    }

                }
                return false;
            }
        });


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
            pb.setVisibility(view.GONE);
            notofi.setVisibility(View.GONE);
        }
    }


}