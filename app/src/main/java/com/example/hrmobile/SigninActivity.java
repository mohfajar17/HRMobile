package com.example.hrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    private TextView textViewLogin;
    private Spinner spinnerKaryawan;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignup;
    private CheckBox checkBox;

    private ArrayAdapter<String> adapter;
    private String[] emplooyeeId;
    private String[] emplooyeeName;

    private CustomProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        progressDialog = new CustomProgressDialog(this);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        spinnerKaryawan = (Spinner) findViewById(R.id.spinnerKaryawan);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked())
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(SigninActivity.this, LoginActivity.class);
                startActivity(bukaActivity);
                finish();
            }
        });

        getDataEmp();
    }

    private void loadData() {
        if (spinnerKaryawan.getSelectedItemPosition() == 0 || editTextUsername.getText().toString().matches("") || editTextPassword.getText().toString().matches("")) {
            Toast.makeText(SigninActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else if (editTextUsername.getText().length()>20 || editTextPassword.getText().length()>20) {
            Toast.makeText(SigninActivity.this, "Failed, username or password length exceeds the limit", Toast.LENGTH_LONG).show();
        } else {
            final String empId = emplooyeeId[spinnerKaryawan.getSelectedItemPosition()];
            final String empName = emplooyeeName[spinnerKaryawan.getSelectedItemPosition()];
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SIGNUP, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            spinnerKaryawan.setSelection(0);
                            editTextUsername.setText("");
                            editTextPassword.setText("");
                            Toast.makeText(SigninActivity.this, "Registration success, wait until the admin verify your account", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SigninActivity.this, "Registration failed, account with this employee_id already exists", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SigninActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(SigninActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("empId", empId);
                    param.put("empName", empName);
                    param.put("userName", editTextUsername.getText().toString());
                    param.put("password", editTextPassword.getText().toString());
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }

    }

    private void getDataEmp() {
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_SIGNUP_EMP_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray;

                                //data employee
                                jsonArray = jsonObject.getJSONArray("data employee");
                                String[] empName = new String[jsonArray.length()+1];
                                emplooyeeId = new String[jsonArray.length()+1];
                                emplooyeeName = new String[jsonArray.length()+1];
                                empName[0] = "-- Pilih Karyawan --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    empName[i + 1] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                                    emplooyeeId[i + 1] = jsonArray.getJSONObject(i).getString("employee_id");
                                    emplooyeeName[i + 1] = jsonArray.getJSONObject(i).getString("fullname");
                                }
                                adapter = new ArrayAdapter<String>(SigninActivity.this, android.R.layout.simple_spinner_dropdown_item, empName);
                                spinnerKaryawan.setAdapter(adapter);
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
        Volley.newRequestQueue(SigninActivity.this).add(request);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}