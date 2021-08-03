package com.asukacorp.hrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {
    private TextView textViewLogin;
    private TextView textViewKaryawan;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignup;
    private CheckBox checkBox;

    private String[] employeeId, employeeText;
    private String[] employeeName;
    private ArrayList<String> arrayListKaryawan;
    private int idKaryawan = -1;

    private Dialog dialog;

    private CustomProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        progressDialog = new CustomProgressDialog(this);
        arrayListKaryawan = new ArrayList<>();

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);
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

        textViewKaryawan = (TextView) findViewById(R.id.textViewKaryawan);
        textViewKaryawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SigninActivity.this);
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                dialog.getWindow().setLayout(900, 1500);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SigninActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKaryawan);
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
                        while (count<employeeText.length){
                            if (newAdapter.getItem(i).equals(employeeText[count])){
                                idKaryawan = count;
                                break;
                            } else count++;
                        }
                        textViewKaryawan.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
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
        if (idKaryawan < 0 || editTextUsername.getText().toString().matches("") || editTextPassword.getText().toString().matches("")) {
            Toast.makeText(SigninActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            final String empId = employeeId[idKaryawan];
            final String empName = employeeName[idKaryawan];
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SIGNUP, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            editTextUsername.setText("");
                            editTextPassword.setText("");
                            textViewKaryawan.setText("Pilih karyawan");
                            idKaryawan = -1;
                            getDataEmp();
                            Toast.makeText(SigninActivity.this, "Registration success, wait until the admin verify your account", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SigninActivity.this, "Registration failed, account with this employee_id already exists", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(SigninActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();
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
        arrayListKaryawan.clear();
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
                                employeeId = new String[jsonArray.length()];
                                employeeText = new String[jsonArray.length()];
                                employeeName = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    employeeId[i] = jsonArray.getJSONObject(i).getString("employee_id");
                                    employeeText[i] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                                    employeeName[i] = jsonArray.getJSONObject(i).getString("fullname");
                                    arrayListKaryawan.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                }
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