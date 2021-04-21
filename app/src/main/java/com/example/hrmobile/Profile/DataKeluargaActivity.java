package com.example.hrmobile.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrmobile.Adapter.RecyclerViewKeluarga;
import com.example.hrmobile.Config;
import com.example.hrmobile.CustomProgressDialog;
import com.example.hrmobile.Data.Keluarga;
import com.example.hrmobile.LoginActivity;
import com.example.hrmobile.R;
import com.example.hrmobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataKeluargaActivity extends AppCompatActivity {

    private CustomProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewKeluarga adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<Keluarga> keluargas;

    private ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_keluarga);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new CustomProgressDialog(this);

        context = getApplicationContext();
        keluargas = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadData();
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
                        Intent logout = new Intent(DataKeluargaActivity.this, LoginActivity.class);
                        startActivity(logout);
                        DataKeluargaActivity.this.finish();
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
        Volley.newRequestQueue(DataKeluargaActivity.this).add(request);
    }

    private void loadData() {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_DATA_KELUARGA_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            keluargas.add(new Keluarga(jsonArray.getJSONObject(i)));
                        }
                        adapter = new RecyclerViewKeluarga(keluargas, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(DataKeluargaActivity.this, "No data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(DataKeluargaActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(DataKeluargaActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", sharedPrefManager.getKeyEmployeeId());
                return param;
            }
        };
        Volley.newRequestQueue(DataKeluargaActivity.this).add(request);
    }
}