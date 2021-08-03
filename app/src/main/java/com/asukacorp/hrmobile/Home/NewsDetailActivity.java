package com.asukacorp.hrmobile.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.Data.News;
import com.asukacorp.hrmobile.LoginActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private ImageView imageDetailInfo;
    private WebView webNewsContent;

    private News news;
    private Dialog myDialog;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        myDialog = new Dialog(this);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        imageDetailInfo = (ImageView) findViewById(R.id.imageDetailInfo);
        webNewsContent = (WebView) findViewById(R.id.webNews);

        Bundle bundle = getIntent().getExtras();
        news = bundle.getParcelable("detail");

        textViewTitle.setText(news.getNews_title());
        webNewsContent.loadData(news.getNews_contents(), "text/html", null);
        imageDetailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup();
            }
        });
        Picasso.get().load(Config.DATA_URL_IMAGE+news.getImage_name()).into(imageDetailInfo);

        if (imageDetailInfo.getDrawable() == null)
            imageDetailInfo.setImageResource(R.drawable.no_image);

        loadUserEnable();
    }

    private void loadUserEnable() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_USER_ENABLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status!=1){
                        sharedPrefManager.setIsLogout();
                        Intent logout = new Intent(NewsDetailActivity.this, LoginActivity.class);
                        startActivity(logout);
                        NewsDetailActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", sharedPrefManager.getKeyEmployeeId());
                param.put("userName", sharedPrefManager.getKeyUserName());
                return param;
            }
        };
        Volley.newRequestQueue(NewsDetailActivity.this).add(request);
    }

    public void ShowPopup() {
        ImageView imageNo;
        myDialog.setContentView(R.layout.custom_popup_image);
        imageNo = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(Config.DATA_URL_IMAGE+news.getImage_name()).into(imageNo);
        if (imageNo.getDrawable() == null)
            imageNo.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}