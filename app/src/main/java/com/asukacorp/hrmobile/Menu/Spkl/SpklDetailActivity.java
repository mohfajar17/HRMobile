package com.asukacorp.hrmobile.Menu.Spkl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.asukacorp.hrmobile.Adapter.RecyclerViewSpklDetail;
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.Data.Spkl;
import com.asukacorp.hrmobile.Data.SpklDetail;
import com.asukacorp.hrmobile.Login.LoginActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpklDetailActivity extends AppCompatActivity {

    private Spkl spkls;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewSpklDetail adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<SpklDetail> spklDetails;
    private ProgressDialog progressDialog;

    private ImageView buttonBack;
    private ImageView downloadAttachment;
    private TextView textSpklNumber;
    private TextView jodKeterangan;
    private TextView detailSpklDepartment;
    private TextView detailSpklLocation;
    private TextView detailSpklJobCode;
    private TextView detailSpklProposedDate;
    private TextView detailSpklReq;
    private TextView detailSpklReqDate;
    private TextView detailSpklApproval1;
    private TextView detailSpklApproval2;
    private TextView detailSpklVerifiedBy;
    private TextView historySpklCreatedBy;
    private TextView historySpklCreatedDate;
    private TextView historySpklModifiedBy;
    private TextView historySpklModifiedDate;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spkl_detail);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        Bundle bundle = getIntent().getExtras();
        spkls = bundle.getParcelable("detail");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getApplicationContext();
        spklDetails = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        downloadAttachment = (ImageView) findViewById(R.id.downloadAttachment);
        textSpklNumber = (TextView) findViewById(R.id.textSpklNumber);
        jodKeterangan = (TextView) findViewById(R.id.jodKeterangan);
        detailSpklDepartment = (TextView) findViewById(R.id.detailSpklDepartment);
        detailSpklLocation = (TextView) findViewById(R.id.detailSpklLocation);
        detailSpklJobCode = (TextView) findViewById(R.id.detailSpklJobCode);
        detailSpklProposedDate = (TextView) findViewById(R.id.detailSpklProposedDate);
        detailSpklReq = (TextView) findViewById(R.id.detailSpklReq);
        detailSpklReqDate = (TextView) findViewById(R.id.detailSpklReqDate);
        detailSpklApproval1 = (TextView) findViewById(R.id.detailSpklApproval1);
        detailSpklApproval2 = (TextView) findViewById(R.id.detailSpklApproval2);
        detailSpklVerifiedBy = (TextView) findViewById(R.id.detailSpklVerifiedBy);
        historySpklCreatedBy = (TextView) findViewById(R.id.historySpklCreatedBy);
        historySpklCreatedDate = (TextView) findViewById(R.id.historySpklCreatedDate);
        historySpklModifiedBy = (TextView) findViewById(R.id.historySpklModifiedBy);
        historySpklModifiedDate = (TextView) findViewById(R.id.historySpklModifiedDate);

        textSpklNumber.setText(spkls.getOvertime_workorder_number());
        jodKeterangan.setText("Keterangan : " + spkls.getWork_description());
        detailSpklDepartment.setText(spkls.getDepartment_id());
        detailSpklLocation.setText(spkls.getWork_location());
        detailSpklJobCode.setText(spkls.getJob_order_id());
        detailSpklProposedDate.setText(spkls.getProposed_date());
        detailSpklReq.setText(spkls.getRequested_id());
        detailSpklReqDate.setText("");//spkls.getRequest_date());
        detailSpklApproval1.setText(spkls.getApproval1_by() + "\n" + spkls.getApproval1_date());
        detailSpklApproval2.setText(spkls.getApproval2_by() + "\n" + spkls.getApproval2_date());
        detailSpklVerifiedBy.setText(spkls.getVerified_by() + "\n" + spkls.getVerified_date());
        historySpklCreatedBy.setText(spkls.getCreated_by());
        historySpklCreatedDate.setText(spkls.getCreated_date());
        historySpklModifiedBy.setText(spkls.getModified_by());
        historySpklModifiedDate.setText(spkls.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (!spkls.getOvertime_file_name().matches("null")){
            downloadAttachment.setColorFilter(ContextCompat.getColor(this, R.color.colorAsukaRed));
            downloadAttachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/overtimeWorkorder/" + spkls.getOvertime_file_name());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });
        }

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
                        Intent logout = new Intent(SpklDetailActivity.this, LoginActivity.class);
                        startActivity(logout);
                        SpklDetailActivity.this.finish();
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
        Volley.newRequestQueue(SpklDetailActivity.this).add(request);
    }

    public void loadData(){
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SPKL_DETAIL_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            spklDetails.add(new SpklDetail(jsonArray.getJSONObject(i)));
                        }
                        adapter = new RecyclerViewSpklDetail(spklDetails, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(SpklDetailActivity.this, "No data", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(SpklDetailActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SpklDetailActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("spklId", "" + spkls.getOvertime_workorder_id());
                return param;
            }
        };
        Volley.newRequestQueue(SpklDetailActivity.this).add(request);
    }
}