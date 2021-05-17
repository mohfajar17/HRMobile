package com.example.hrmobile.Menu.SlipGaji;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hrmobile.Config;
import com.example.hrmobile.R;
import com.example.hrmobile.SharedPrefManager;

public class DownloadSlipActivity extends AppCompatActivity {

    private WebView webView;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_slip);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        webView = (WebView) findViewById(R.id.webSlipGaji);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // melakukan zoom
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // menambahkan scrollbar
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        // set url webview
        webView.loadUrl(Uri.parse(Config.DATA_URL_SLIP_GAJI_WEB_COBA+sharedPrefManager.getKeyEmployeeId()).toString());
    }
}