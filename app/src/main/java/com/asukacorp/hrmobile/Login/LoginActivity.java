package com.asukacorp.hrmobile.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.CustomProgressDialog;
import com.asukacorp.hrmobile.MainActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout layoutPhone;
    private LinearLayout layoutUsername;
    private LinearLayout layoutPassword;
    private EditText editTextPhone;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private CheckBox checkBox;
    private Button buttonLogin;
    private TextView textSignIn;
    private TextView textForgot;
    private ImageView imageVisibilityOn;
    private ImageView imageVisibilityOff;

    private SharedPrefManager sharedPrefManager;
    private ViewGroup.LayoutParams params;

    private CustomProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private int access = 0;

    private Date d;
    private SimpleDateFormat df;
    private String todayDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new CustomProgressDialog(this);
        sharedPrefManager = SharedPrefManager.getInstance(this);

        d = new Date();
        df = new SimpleDateFormat("dd-MMM-yyyy");
        todayDate = df.format(d);

        if (sharedPrefManager.getKeyDueDate()==null){
            sharedPrefManager.setDueDate(todayDate);
            sharedPrefManager.setOtpCount(0);
        } else {
            if (!todayDate.matches(sharedPrefManager.getKeyDueDate()))
                sharedPrefManager.setOtpCount(0);
        }

        if(sharedPrefManager.getIsLogin()){
            Intent bukaMainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(bukaMainActivity);
            finish();
        } else getaccess();

        layoutPhone = (LinearLayout) findViewById(R.id.layoutPhone);
        layoutUsername = (LinearLayout) findViewById(R.id.layoutUsername);
        layoutPassword = (LinearLayout) findViewById(R.id.layoutPassword);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textSignIn = (TextView) findViewById(R.id.textSignIn);
        textForgot = (TextView) findViewById(R.id.textForgot);
        imageVisibilityOn = (ImageView) findViewById(R.id.imageVisibilityOn);
        imageVisibilityOff = (ImageView) findViewById(R.id.imageVisibilityOff);

        if (sharedPrefManager.getKeyRememberUsername()!=null){
            editTextUsername.setText(sharedPrefManager.getKeyRememberUsername());
            editTextPassword.setText(sharedPrefManager.getKeyRemenberPassword());
        }

        imageVisibilityOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                params = imageVisibilityOn.getLayoutParams();
                params.height = 0;
                imageVisibilityOn.setLayoutParams(params);
                params = imageVisibilityOff.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                imageVisibilityOff.setLayoutParams(params);
            }
        });
        imageVisibilityOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                params = imageVisibilityOff.getLayoutParams();
                params.height = 0;
                imageVisibilityOff.setLayoutParams(params);
                params = imageVisibilityOn.getLayoutParams();
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                imageVisibilityOn.setLayoutParams(params);
            }
        });

        editTextPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                layoutPhone.setBackgroundResource(R.drawable.border_edittext_on);
                layoutUsername.setBackgroundResource(R.drawable.border_edittext_off);
                layoutPassword.setBackgroundResource(R.drawable.border_edittext_off);
            }
        });
        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                layoutPhone.setBackgroundResource(R.drawable.border_edittext_off);
                layoutUsername.setBackgroundResource(R.drawable.border_edittext_on);
                layoutPassword.setBackgroundResource(R.drawable.border_edittext_off);
            }
        });
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                layoutPhone.setBackgroundResource(R.drawable.border_edittext_off);
                layoutUsername.setBackgroundResource(R.drawable.border_edittext_off);
                layoutPassword.setBackgroundResource(R.drawable.border_edittext_on);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogin();
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

        textForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(bukaActivity);
                finish();
            }
        });
    }

    private void getaccess() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_ACCESS_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    access = jsonObject.getInt("status");
                    if (access==1){
                        params = layoutPhone.getLayoutParams();
                        params.height = 0;
                        layoutPhone.setLayoutParams(params);
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void getLogin() {
        if (String.valueOf(editTextUsername.getText()).equals("") || String.valueOf(editTextPassword.getText()).equals("")){
            Toast.makeText(LoginActivity.this, "Please enter your username and password", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();

            if (access==1)
                loginNoOtp();
            else loginOtp();
        }
    }

    private void loginOtp() {
        if (sharedPrefManager.getKeyOtpCount()>4){
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "You can only send the otp code five times today, try again tomorrow", Toast.LENGTH_LONG).show();
        } else {
            buttonLogin.setVisibility(View.INVISIBLE);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+62"+editTextPhone.getText().toString(),
                    60,
                    TimeUnit.SECONDS,
                    LoginActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            buttonLogin.setVisibility(View.VISIBLE);
                            loginNoOtp();
                        }
                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            progressDialog.dismiss();
                            buttonLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Failed to send OTP code", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            if (checkBox.isChecked()){
                                sharedPrefManager.setRemenberUsername(editTextUsername.getText().toString());
                                sharedPrefManager.setRemenberPassword(editTextPassword.getText().toString());
                            } else {
                                sharedPrefManager.setRemenberUsername(null);
                                sharedPrefManager.setRemenberPassword(null);
                            }

                            sharedPrefManager.setOtpCount(sharedPrefManager.getKeyOtpCount()+1);
                            progressDialog.dismiss();
                            buttonLogin.setVisibility(View.VISIBLE);
                            Intent bukaActivity = new Intent(LoginActivity.this, VerificationActivity.class);
                            bukaActivity.putExtra("userName", editTextUsername.getText().toString());
                            bukaActivity.putExtra("password", editTextPassword.getText().toString());
                            bukaActivity.putExtra("phone", editTextPhone.getText().toString());
                            bukaActivity.putExtra("verificationId", verificationId);
                            bukaActivity.putExtra("code", 0);
                            startActivity(bukaActivity);
                            finish();
                        }
                    }
            );
        }
    }

    private void loginNoOtp() {
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
                        sharedPrefManager.setNik(jsonData.getString("sin_num"));
                        sharedPrefManager.setNpwp(jsonData.getString("npwp"));
                        sharedPrefManager.setBpjs(jsonData.getString("bpjs_health_number"));
                        sharedPrefManager.setBirthday(jsonData.getString("birthday"));
                        sharedPrefManager.setPlaceBirthday(jsonData.getString("place_birthday"));
                        sharedPrefManager.setGender(jsonData.getString("gender"));
                        sharedPrefManager.setBloodGroup(jsonData.getString("blood_group"));
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
                        sharedPrefManager.setIdentityFileName(jsonData.getString("identity_file_name"));
                        sharedPrefManager.setJoinDate(jsonData.getString("join_date"));
                        sharedPrefManager.setComeOutDate(jsonData.getString("come_out_date"));
                        sharedPrefManager.setIslogin();

                        if (checkBox.isChecked()){
                            sharedPrefManager.setRemenberUsername(editTextUsername.getText().toString());
                            sharedPrefManager.setRemenberPassword(editTextPassword.getText().toString());
                        } else {
                            sharedPrefManager.setRemenberUsername(null);
                            sharedPrefManager.setRemenberPassword(null);
                        }

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
                param.put("user_name", editTextUsername.getText().toString());
                param.put("password", editTextPassword.getText().toString());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
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