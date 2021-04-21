package com.example.hrmobile.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrmobile.Adapter.RecyclerViewRiwayatHistory;
import com.example.hrmobile.Adapter.RecyclerViewRiwayatJabatan;
import com.example.hrmobile.Config;
import com.example.hrmobile.CustomProgressDialog;
import com.example.hrmobile.Data.RiwayatHistory;
import com.example.hrmobile.Data.RiwayatJabatan;
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

public class RiwayatJabatanActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewRiwayatJabatan adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<RiwayatJabatan> riwayatJabatans;
    private RecyclerView recyclerViewHis;
    private RecyclerViewRiwayatHistory adapterHis;
    private RecyclerView.LayoutManager recylerViewLayoutManagerHis;
    private List<RiwayatHistory> riwayatHistories;

    private CustomProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;

    private TextView textTanggalAwalKerja;
    private TextView textTanggalDiangkat;
    private TextView textTanggalAkhirKontrak;
    private TextView textTanggalKeluar;
    private TextView textAlasanKeluar;
    private TextView textSalary;
    private TextView textCatatan;
    private ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_jabatan);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new CustomProgressDialog(this);

        context = getApplicationContext();
        riwayatJabatans = new ArrayList<>();
        riwayatHistories = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);
        recyclerViewHis = (RecyclerView) findViewById(R.id.recyclerViewKerja);
        recylerViewLayoutManagerHis = new LinearLayoutManager(context);
        recyclerViewHis.setLayoutManager(recylerViewLayoutManagerHis);

        textTanggalAwalKerja = (TextView) findViewById(R.id.textTanggalAwalKerja);
        textTanggalDiangkat = (TextView) findViewById(R.id.textTanggalDiangkat);
        textTanggalAkhirKontrak = (TextView) findViewById(R.id.textTanggalAkhirKontrak);
        textTanggalKeluar = (TextView) findViewById(R.id.textTanggalKeluar);
        textAlasanKeluar = (TextView) findViewById(R.id.textAlasanKeluar);
        textSalary = (TextView) findViewById(R.id.textSalary);
        textCatatan = (TextView) findViewById(R.id.textCatatan);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadDetail();
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
                        Intent logout = new Intent(RiwayatJabatanActivity.this, LoginActivity.class);
                        startActivity(logout);
                        RiwayatJabatanActivity.this.finish();
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
        Volley.newRequestQueue(RiwayatJabatanActivity.this).add(request);
    }

    public void loadDetail(){
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_RIWAYAT_JABATAN_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray;

                        //set adapter history kontrak
                        jsonArray = jsonObject.getJSONArray("data kontrak");
                        for(int i=0;i<jsonArray.length();i++){
                            riwayatJabatans.add(new RiwayatJabatan(jsonArray.getJSONObject(i)));
                        }
                        adapter = new RecyclerViewRiwayatJabatan(riwayatJabatans, context);
                        recyclerView.setAdapter(adapter);

                        //set adapter history kerja
                        jsonArray = jsonObject.getJSONArray("data kerja");
                        for(int i=0;i<jsonArray.length();i++){
                            riwayatHistories.add(new RiwayatHistory(jsonArray.getJSONObject(i)));
                        }
                        adapterHis = new RecyclerViewRiwayatHistory(riwayatHistories, context);
                        recyclerViewHis.setAdapter(adapterHis);

                        JSONObject jsonObjectDetail = jsonObject.getJSONObject("detail");
                        textTanggalAwalKerja.setText(jsonObjectDetail.getString("join_date"));
                        textTanggalDiangkat.setText(jsonObjectDetail.getString("employment_date"));
                        textTanggalAkhirKontrak.setText(jsonObjectDetail.getString("termination_date"));
                        textTanggalKeluar.setText(jsonObjectDetail.getString("come_out_date"));
                        textAlasanKeluar.setText(jsonObjectDetail.getString("termination_reason"));
                        textSalary.setText(jsonObjectDetail.getString("is_active"));
                        textCatatan.setText(jsonObjectDetail.getString("notes"));
                    } else {
                        Toast.makeText(RiwayatJabatanActivity.this, "No data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(RiwayatJabatanActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RiwayatJabatanActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(RiwayatJabatanActivity.this).add(request);
    }
}