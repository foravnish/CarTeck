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
import carteckh.carteckh.R;

/**
 * Created by andriod1 on 6/23/2016.
 */
public class TermConditionFragment extends Fragment {

    View view;
    @InjectView(R.id.webview)
    WebView webView;
    private String from;
    private Bundle bundle;
    @InjectView(R.id.progressBarweb)
    ProgressBar progressBar;
    @InjectView(R.id.noti_tv)
    TextView header;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.webview, null);
        ButterKnife.inject(this, view);
        webView.setWebViewClient(new webview());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://www.carteckh.com/term-condition/");

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
            // TODO Auto-generated method stub

            super.onPageFinished(view, url);

            progressBar.setVisibility(view.GONE);
            header.setVisibility(View.GONE);
        }

    }

}