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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrmobile.Adapter.RecyclerViewPotongan;
import com.example.hrmobile.Adapter.RecyclerViewTunjangan;
import com.example.hrmobile.Config;
import com.example.hrmobile.Data.Potongan;
import com.example.hrmobile.Data.Tunjangan;
import com.example.hrmobile.LoginActivity;
import com.example.hrmobile.R;
import com.example.hrmobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TunjanganPotonganActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTunjangan;
    private RecyclerViewTunjangan adapterTunjangan;
    private RecyclerView.LayoutManager recylerViewLayoutManagerTunjangan;
    private List<Tunjangan> tunjangans;
    private RecyclerView recyclerViewPotongan;
    private RecyclerViewPotongan adapterPotongan;
    private RecyclerView.LayoutManager recylerViewLayoutManagerPotongan;
    private List<Potongan> potongans;

    private Context context;
    private ProgressDialog progressDialog;
    private NumberFormat formatter;
    private SharedPrefManager sharedPrefManager;

    private ImageView buttonBack;
    private TextView textTotalTunjangan;
    private TextView textTotalPotongan;

    private double totalTunjangan;
    private double totalPotongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunjangan_potongan);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        tunjangans = new ArrayList<>();
        potongans = new ArrayList<>();

        recyclerViewTunjangan = (RecyclerView) findViewById(R.id.recyclerViewTun);
        recylerViewLayoutManagerTunjangan = new LinearLayoutManager(context);
        recyclerViewTunjangan.setLayoutManager(recylerViewLayoutManagerTunjangan);

        recyclerViewPotongan = (RecyclerView) findViewById(R.id.recyclerViewPot);
        recylerViewLayoutManagerPotongan = new LinearLayoutManager(context);
        recyclerViewPotongan.setLayoutManager(recylerViewLayoutManagerPotongan);

        textTotalTunjangan = (TextView) findViewById(R.id.textTotalTunjangan);
        textTotalPotongan = (TextView) findViewById(R.id.textTotalPotongan);
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
                        Intent logout = new Intent(TunjanganPotonganActivity.this, LoginActivity.class);
                        startActivity(logout);
                        TunjanganPotonganActivity.this.finish();
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
        Volley.newRequestQueue(TunjanganPotonganActivity.this).add(request);
    }

    private void loadData() {
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_POTONGAN_TUNJANGAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray;

                        //set data tunjangan
                        totalTunjangan = 0;
                        jsonArray = jsonObject.getJSONArray("data tunjangan");
                        for(int i=0;i<jsonArray.length();i++){
                            tunjangans.add(new Tunjangan(jsonArray.getJSONObject(i)));
                            totalTunjangan += jsonArray.getJSONObject(i).getDouble("value");
                        }
                        textTotalTunjangan.setText("Total Tunjangan = Rp. " + formatter.format(totalTunjangan));
                        adapterTunjangan = new RecyclerViewTunjangan(tunjangans, context);
                        recyclerViewTunjangan.setAdapter(adapterTunjangan);

                        //set data potongan
                        totalPotongan = 0;
                        jsonArray = jsonObject.getJSONArray("data potongan");
                        for(int i=0;i<jsonArray.length();i++){
                            potongans.add(new Potongan(jsonArray.getJSONObject(i)));
                            totalPotongan += jsonArray.getJSONObject(i).getDouble("value");
                        }
                        textTotalPotongan.setText("Total Potongan = Rp. " + formatter.format(totalPotongan));
                        adapterPotongan = new RecyclerViewPotongan(potongans, context);
                        recyclerViewPotongan.setAdapter(adapterPotongan);
                    } else {
                        Toast.makeText(TunjanganPotonganActivity.this, "No data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(TunjanganPotonganActivity.this, "", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(TunjanganPotonganActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", sharedPrefManager.getKeyEmployeeId());
                return param;
            }
        };
        Volley.newRequestQueue(TunjanganPotonganActivity.this).add(request);
    }
}