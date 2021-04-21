package com.example.hrmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout layoutUsername;
    private LinearLayout layoutPassword;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private CheckBox checkBox;
    private Button buttonLogin;
    private TextView textSignIn;

    private SharedPrefManager sharedPrefManager;

    private CustomProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        if(sharedPrefManager.getIsLogin()){
            Intent bukaMainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(bukaMainActivity);
            finish();
        }

        progressDialog = new CustomProgressDialog(this);

        layoutUsername = (LinearLayout) findViewById(R.id.layoutUsername);
        layoutPassword = (LinearLayout) findViewById(R.id.layoutPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textSignIn = (TextView) findViewById(R.id.textSignIn);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked())
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                layoutUsername.setBackgroundResource(R.drawable.border_edittext_on);
                layoutPassword.setBackgroundResource(R.drawable.border_edittext_off);
            }
        });
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                layoutPassword.setBackgroundResource(R.drawable.border_edittext_on);
                layoutUsername.setBackgroundResource(R.drawable.border_edittext_off);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataLogin();
            }
        });

        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(bukaActivity);
                finish();
            }
        });
    }

    private void getDataLogin() {
        if (String.valueOf(editTextUsername.getText()).equals("") || String.valueOf(editTextPassword.getText()).equals("")){
            Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            JSONObject jsonData = jsonObject.getJSONObject("data");

                            sharedPrefManager.setUserId(jsonData.getString("user_id"));
                            sharedPrefManager.setEmployeeId(jsonData.getString("employee_id"));
                            sharedPrefManager.setUserName(jsonData.getString("user_name"));
                            sharedPrefManager.setFullname(jsonData.getString("fullname"));
                            sharedPrefManager.setNickname(jsonData.getString("nickname"));
                            sharedPrefManager.setEmployeeNumber(jsonData.getString("employee_number"));
                            sharedPrefManager.setEmployeeGradeId(jsonData.getString("employee_grade_id"));
                            sharedPrefManager.setEmployeeGradeName(jsonData.getString("employee_grade_name"));
                            sharedPrefManager.setJobGradeId(jsonData.getString("job_grade_id"));
                            sharedPrefManager.setJobGradeName(jsonData.getString("job_grade_name"));
                            sharedPrefManager.setEmployeeStatusId(jsonData.getString("employee_status_id"));
                            sharedPrefManager.setEmployeeStatus(jsonData.getString("employee_status"));
                            sharedPrefManager.setWorkingStatus(jsonData.getString("working_status"));
                            sharedPrefManager.setDepartmentId(jsonData.getString("department_id"));
                            sharedPrefManager.setDepartmentName(jsonData.getString("department_name"));
                            sharedPrefManager.setBirthday(jsonData.getString("birthday"));
                            sharedPrefManager.setPlaceBirthday(jsonData.getString("place_birthday"));
                            sharedPrefManager.setGender(jsonData.getString("gender"));
                            sharedPrefManager.setAddress(jsonData.getString("address"));
                            sharedPrefManager.setCity(jsonData.getString("city"));
                            sharedPrefManager.setState(jsonData.getString("state"));
                            sharedPrefManager.setCountry(jsonData.getString("country"));
                            sharedPrefManager.setCompanyWorkbaseId(jsonData.getString("company_workbase_id"));
                            sharedPrefManager.setCompanyWorkbaseName(jsonData.getString("company_workbase_name"));
                            sharedPrefManager.setReligionName(jsonData.getString("religion_name"));
                            sharedPrefManager.setMaritalStatusId(jsonData.getString("marital_status_id"));
                            sharedPrefManager.setMaritalStatusName(jsonData.getString("marital_status_name"));
                            sharedPrefManager.setMobilePhone(jsonData.getString("mobile_phone"));
                            sharedPrefManager.setEmail(jsonData.getString("email1"));
                            sharedPrefManager.setEmployeeFileName(jsonData.getString("employee_file_name"));
                            sharedPrefManager.setJoinDate(jsonData.getString("join_date"));
                            sharedPrefManager.setComeOutDate(jsonData.getString("come_out_date"));
                            sharedPrefManager.setIslogin();

                            Intent bukaMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(bukaMainActivity);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Username and password incorrect", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(LoginActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("user_name", String.valueOf(editTextUsername.getText()));
                    param.put("password", String.valueOf(editTextPassword.getText()));
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
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