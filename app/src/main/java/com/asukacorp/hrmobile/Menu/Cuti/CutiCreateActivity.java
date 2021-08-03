package com.asukacorp.hrmobile.Menu.Cuti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.CustomProgressDialog;
import com.asukacorp.hrmobile.Hybrid.CutiActivity;
import com.asukacorp.hrmobile.LoginActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CutiCreateActivity extends AppCompatActivity {

    private ArrayList<String> arrayListKaryawan;
    private int idKaryawan = -1;
    private ArrayAdapter<String> adapter;
    private String[] cutiKaryawanId;
    private String[] cutiKaryawanName;
    private String[] cutiKategoriId;

    private EditText editCutiNo;
    private TextView editCutiKaryawan;
    private Spinner editCutiKategori;
    private TextView editCutiStartDate;
    private TextView editCutiEndDate;
    private EditText editNotes;
    private ImageView buttonBack;
    private Button buttonBuat;

    private Dialog dialog;

    private CustomProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuti_create);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        progressDialog = new CustomProgressDialog(this);
        arrayListKaryawan = new ArrayList<>();

        editCutiNo = (EditText) findViewById(R.id.editCutiNo);
        editCutiKaryawan = (TextView) findViewById(R.id.editCutiKaryawan);
        editCutiKategori = (Spinner) findViewById(R.id.editCutiKategori);
        editCutiStartDate = (TextView) findViewById(R.id.editCutiStartDate);
        editCutiEndDate = (TextView) findViewById(R.id.editCutiEndDate);
        editNotes = (EditText) findViewById(R.id.editNotes);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        buttonBuat = (Button) findViewById(R.id.buttonBuat);

        editCutiKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(CutiCreateActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(900, 1500);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(CutiCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKaryawan);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count<cutiKaryawanName.length){
                            if (newAdapter.getItem(i).equals(cutiKaryawanName[count])){
                                idKaryawan = count;
                                break;
                            } else count++;
                        }
                        editCutiKaryawan.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });

        editCutiStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CutiCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (month+1<10){
                            if (dayOfMonth<10)
                                editCutiStartDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                            else editCutiStartDate.setText(dayOfMonth + "-0" + (month + 1) + "-" + year);
                        } else {
                            if (dayOfMonth<10)
                                editCutiStartDate.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
                            else editCutiStartDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        }
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
                        if (month+1<10){
                            if (dayOfMonth<10)
                                editCutiEndDate.setText("0" + dayOfMonth + "-0" + (month + 1) + "-" + year);
                            else editCutiEndDate.setText(dayOfMonth + "-0" + (month + 1) + "-" + year);
                        } else {
                            if (dayOfMonth<10)
                                editCutiEndDate.setText("0" + dayOfMonth + "-" + (month + 1) + "-" + year);
                            else editCutiEndDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        buttonBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createLeave();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    private void createLeave() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (editCutiKategori.getSelectedItemPosition() == 0 || editCutiStartDate.getText().toString().matches("start date (yyyy-mm-dd)") ||
                editCutiEndDate.getText().toString().matches("end date (yyyy-mm-dd)") || idKaryawan < 0 ||
                sdf.parse(editCutiEndDate.getText().toString()).before(sdf.parse(editCutiStartDate.getText().toString()))) {
            Toast.makeText(CutiCreateActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            final String employeeId = cutiKaryawanId[idKaryawan];
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
                        } else if (status == 2){
                            Toast.makeText(CutiCreateActivity.this, "Your annual leave has expired", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CutiCreateActivity.this, "Filed applied for leave", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(CutiCreateActivity.this, "Error add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();
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

                                //data number
                                editCutiNo.setText("ASK-EL-XX.XXXX");

                                //data employee
                                jsonArray = jsonObject.getJSONArray("data leave employee");
                                cutiKaryawanId = new String[jsonArray.length()];
                                cutiKaryawanName = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    cutiKaryawanId[i] = jsonArray.getJSONObject(i).getString("employee_id");
                                    cutiKaryawanName[i] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                                    arrayListKaryawan.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                }

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
                                Toast.makeText(CutiCreateActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CutiCreateActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(CutiCreateActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                    }
                }){
        };
        Volley.newRequestQueue(CutiCreateActivity.this).add(request);
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, CutiActivity.class);
        intent.putExtra("code", 0);
        startActivityForResult(intent,1);
        finish();
    }
}