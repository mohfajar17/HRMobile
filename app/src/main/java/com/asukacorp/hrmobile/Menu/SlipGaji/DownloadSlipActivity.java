package com.asukacorp.hrmobile.Menu.SlipGaji;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.R;

public class DownloadSlipActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_slip);

        Bundle bundle = getIntent().getExtras();

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
        webView.loadUrl(Uri.parse(Config.DATA_URL_SLIP_GAJI_WEB+bundle.getString("employee_id")+"&tipe="+bundle.getString("tipe")+"&tanggal="+bundle.getString("tanggal")+"&bulan="+bundle.getString("bulan")+"&tahun="+bundle.getString("tahun")).toString());
    }
}