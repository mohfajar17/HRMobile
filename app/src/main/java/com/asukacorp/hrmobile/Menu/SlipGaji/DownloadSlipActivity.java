package com.asukacorp.hrmobile.Menu.SlipGaji;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.CustomProgressDialog;
import com.asukacorp.hrmobile.R;
import com.webviewtopdf.PdfView;

import java.io.File;

public class DownloadSlipActivity extends AppCompatActivity {

    private WebView webView;
    private Button button_convert;

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

        button_convert = (Button) findViewById(R.id.button_convert);
        button_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                final String fileName = "slipgaji_" + bundle.getString("bulan") + "_" + bundle.getString("tahun") + ".pdf";
                final CustomProgressDialog progressDialog;
                progressDialog = new CustomProgressDialog(DownloadSlipActivity.this);
                progressDialog.show();
                PdfView.createWebPrintJob(DownloadSlipActivity.this, webView, directory, fileName, new PdfView.Callback() {
                    @Override
                    public void success(String path) {
                        progressDialog.dismiss();
                        Toast.makeText(DownloadSlipActivity.this,"Success download data",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure() {
                        progressDialog.dismiss();
                        Toast.makeText(DownloadSlipActivity.this,"Filed download data",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}