package com.example.hrmobile.Menu.Cuti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrmobile.Config;
import com.example.hrmobile.LoginActivity;
import com.example.hrmobile.R;
import com.example.hrmobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CutiCreateActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private String[] cutiRequestedId;
    private String[] cutiKategoriId;

    private Spinner editCutiRequested;
    private Spinner editCutiKategori;
    private EditText editCutiStartDate;
    private EditText editCutiEndDate;
    private EditText editNotes;
    private Button buttonBuat;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuti_create);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        editCutiRequested = (Spinner) findViewById(R.id.editCutiRequested);
        editCutiKategori = (Spinner) findViewById(R.id.editCutiKategori);
        editCutiStartDate = (EditText) findViewById(R.id.editCutiStartDate);
        editCutiEndDate = (EditText) findViewById(R.id.editCutiEndDate);
        editNotes = (EditText) findViewById(R.id.editNotes);
        buttonBuat = (Button) findViewById(R.id.buttonBuat);

        editCutiStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CutiCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month+1<10)
                            if (month+1<10 && dayOfMonth>9)
                                editCutiStartDate.setText(year + "-0" + (month + 1) + "-" + dayOfMonth);
                            else if (month+1>9 && dayOfMonth<10)
                                editCutiStartDate.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                            else if (month+1<10 && dayOfMonth<10)
                                editCutiStartDate.setText(year + "-0" + (month + 1) + "-0" + dayOfMonth);
                            else editCutiStartDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        editCutiEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CutiCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month+1<10 && dayOfMonth>9)
                            editCutiEndDate.setText(year + "-0" + (month + 1) + "-" + dayOfMonth);
                        else if (month+1>9 && dayOfMonth<10)
                            editCutiEndDate.setText(year + "-" + (month + 1) + "-0" + dayOfMonth);
                        else if (month+1<10 && dayOfMonth<10)
                            editCutiEndDate.setText(year + "-0" + (month + 1) + "-0" + dayOfMonth);
                        else editCutiEndDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        buttonBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLeave();
            }
        });

        getLeaveData();
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
                        Intent logout = new Intent(CutiCreateActivity.this, LoginActivity.class);
                        startActivity(logout);
                        CutiCreateActivity.this.finish();
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
        Volley.newRequestQueue(CutiCreateActivity.this).add(request);
    }

    private void createLeave() {
        if (editCutiRequested.getSelectedItemPosition() == 0 || editCutiKategori.getSelectedItemPosition() == 0 ||
                editCutiStartDate.getText().toString().matches("") || editCutiEndDate.getText().toString().matches("")) {
            Toast.makeText(CutiCreateActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else {
            final String employeeId = cutiRequestedId[editCutiRequested.getSelectedItemPosition()];
            final String categoryId = cutiKategoriId[editCutiKategori.getSelectedItemPosition()];

            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_CUTI_CREATE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            Toast.makeText(CutiCreateActivity.this, "Success applied for leave", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(CutiCreateActivity.this, "Filed applied for leave", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(CutiCreateActivity.this, "Error add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(CutiCreateActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("employeeId", employeeId);
                    param.put("leaveCategory", categoryId);
                    param.put("startLeave", editCutiStartDate.getText().toString());
                    param.put("finishLeave", editCutiEndDate.getText().toString());
                    param.put("notes", editNotes.getText().toString() + " ");
                    param.put("userId", sharedPrefManager.getKeyUserId());
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    private void getLeaveData() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_CUTI_CREATE_DATA_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray;

                                //data employee
                                jsonArray = jsonObject.getJSONArray("data leave employee");
                                String[] cutiRequested = new String[jsonArray.length()+1];
                                cutiRequestedId = new String[jsonArray.length()+1];
                                cutiRequested[0] = "-- Pilih Karyawan --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    cutiRequested[i + 1] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                                    cutiRequestedId[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                }
                                adapter = new ArrayAdapter<String>(CutiCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, cutiRequested);
                                editCutiRequested.setAdapter(adapter);

                                //data category
                                jsonArray = jsonObject.getJSONArray("data leave category");
                                String[] cutiKategori = new String[jsonArray.length()+1];
                                cutiKategoriId = new String[jsonArray.length()+1];
                                cutiKategori[0] = "-- Pilih Kategori Cuti --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    cutiKategori[i + 1] = jsonArray.getJSONObject(i).getString("leave_category_name");
                                    cutiKategoriId[i + 1] = jsonArray.getJSONObject(i).getString("leave_category_id");
                                }
                                adapter = new ArrayAdapter<String>(CutiCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, cutiKategori);
                                editCutiKategori.setAdapter(adapter);
                            } else {
                                Toast.makeText(CutiCreateActivity.this, "No data", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
        };
        Volley.newRequestQueue(CutiCreateActivity.this).add(request);
    }
}