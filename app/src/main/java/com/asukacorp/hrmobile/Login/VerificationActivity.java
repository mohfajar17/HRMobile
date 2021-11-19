package com.asukacorp.hrmobile.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.CustomProgressDialog;
import com.asukacorp.hrmobile.MainActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    private Button buttonVerify;
    private TextView textViewTimer;
    private TextView textViewResendOtp;
    private EditText editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5, editTextCode6;
    private String verificationId, empId, empName, userName, password, phone, otpCode, newPassword, newUsername, userId;
    private int code;

    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 600000;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private int second;

    private LinearLayout layoutTextLogin;
    private ViewGroup.LayoutParams params;
    private CustomProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        progressDialog = new CustomProgressDialog(this);
        sharedPrefManager = SharedPrefManager.getInstance(this);

        code = getIntent().getIntExtra("code", 1);
        phone = getIntent().getStringExtra("phone");
        verificationId = getIntent().getStringExtra("verificationId");

        if (code==1){ //signup
            empId = getIntent().getStringExtra("empId");
            empName = getIntent().getStringExtra("empName");
            userName = getIntent().getStringExtra("userName");
            password = getIntent().getStringExtra("password");
        } if (code==2){ //change password
            newPassword = getIntent().getStringExtra("newPassword");
            userId = getIntent().getStringExtra("userId");
        } else if (code==3){ //change username
            newUsername = getIntent().getStringExtra("newUsername");
            userId = getIntent().getStringExtra("userId");
        } else { //login
            userName = getIntent().getStringExtra("userName");
            password = getIntent().getStringExtra("password");

            layoutTextLogin = (LinearLayout) findViewById(R.id.layoutTextLogin);
            params = layoutTextLogin.getLayoutParams();
            params.height = 0;
            layoutTextLogin.setLayoutParams(params);
        }

        textViewTimer = (TextView) findViewById(R.id.textViewTimer);
        editTextCode1 = (EditText) findViewById(R.id.editTextCode1);
        editTextCode2 = (EditText) findViewById(R.id.editTextCode2);
        editTextCode3 = (EditText) findViewById(R.id.editTextCode3);
        editTextCode4 = (EditText) findViewById(R.id.editTextCode4);
        editTextCode5 = (EditText) findViewById(R.id.editTextCode5);
        editTextCode6 = (EditText) findViewById(R.id.editTextCode6);

        textViewResendOtp = (TextView) findViewById(R.id.textViewResendOtp);
        textViewResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if (sharedPrefManager.getKeyOtpCount()>4){
                    progressDialog.dismiss();
                    Toast.makeText(VerificationActivity.this, "You can only send the otp code five times today, try again tomorrow", Toast.LENGTH_LONG).show();
                } else {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+62"+phone,
                            55,
                            TimeUnit.SECONDS,
                            VerificationActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressDialog.dismiss();
                                    verificationId = phoneAuthCredential.getSmsCode();
                                    Toast.makeText(VerificationActivity.this, "Success send OTP code", Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(VerificationActivity.this, "Failed to send OTP, you can only send five times today", Toast.LENGTH_LONG).show();
                                }
                                @Override
                                public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressDialog.dismiss();
                                    sharedPrefManager.setOtpCount(sharedPrefManager.getKeyOtpCount()+1);
                                    verificationId = newVerificationId;
                                    Toast.makeText(VerificationActivity.this, "Success send OTP code", Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                }
                startTimer();
            }
        });

        buttonVerify = (Button) findViewById(R.id.buttonVerify);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextCode1.getText().toString().isEmpty() || editTextCode2.getText().toString().isEmpty() ||
                        editTextCode3.getText().toString().isEmpty() || editTextCode4.getText().toString().isEmpty() ||
                        editTextCode5.getText().toString().isEmpty() || editTextCode6.getText().toString().isEmpty()){
                    Toast.makeText(VerificationActivity.this, "Please enter valid code", Toast.LENGTH_LONG).show();
                } else {
                    otpCode = editTextCode1.getText().toString() +
                            editTextCode2.getText().toString() +
                            editTextCode3.getText().toString() +
                            editTextCode4.getText().toString() +
                            editTextCode5.getText().toString() +
                            editTextCode6.getText().toString();

                    if (verificationId != null){
                        progressDialog.show();
                        buttonVerify.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, otpCode);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                buttonVerify.setVisibility(View.VISIBLE);
                                if (task.isSuccessful() || otpCode.matches(verificationId)){
                                    if (code==1)
                                        createDataSignup();
                                    else if (code==2)
                                        updatePassword();
                                    else if (code==3)
                                        updateUsername();
                                    else loginAccount();
                                } else Toast.makeText(VerificationActivity.this, "Failed to verify otp code, please try again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });

        setOtpInput();
        startTimer();
    }

    private void startTimer() {
        params = textViewResendOtp.getLayoutParams();
        params.width = 0;
        params.height = 0;
        textViewResendOtp.setLayoutParams(params);

        timeLeftInMillis = START_TIME_IN_MILLIS;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilEnd) {
                timeLeftInMillis = millisUntilEnd;

                second = (int) (timeLeftInMillis/1000)%60;
                String timeLeftFormatted = String.format(Locale.getDefault(),"(00:%02d)", second);
                textViewTimer.setText(timeLeftFormatted);
                if (second<1){
                    cancel();

                    params = textViewResendOtp.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    textViewResendOtp.setLayoutParams(params);
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    private void setOtpInput(){
        editTextCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    editTextCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    editTextCode3.requestFocus();
                } else editTextCode1.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    editTextCode4.requestFocus();
                } else editTextCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    editTextCode5.requestFocus();
                } else editTextCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    editTextCode6.requestFocus();
                } else editTextCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCode6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty())
                    editTextCode5.requestFocus();
                else if (!editTextCode1.getText().toString().trim().isEmpty() || !editTextCode2.getText().toString().trim().isEmpty() ||
                        !editTextCode3.getText().toString().trim().isEmpty() || !editTextCode4.getText().toString().trim().isEmpty() ||
                        !editTextCode5.getText().toString().trim().isEmpty() || !editTextCode6.getText().toString().trim().isEmpty())
                    buttonVerify.performClick();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void loginAccount() {
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

                        Intent bukaMainActivity = new Intent(VerificationActivity.this, MainActivity.class);
                        startActivity(bukaMainActivity);
                        finish();
                    } else {
                        Toast.makeText(VerificationActivity.this, "Username and password incorrect", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(VerificationActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(VerificationActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_name", userName);
                param.put("password", password);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void createDataSignup() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        Intent bukaActivity = new Intent(VerificationActivity.this, LoginActivity.class);
                        startActivity(bukaActivity);
                        finish();
                        Toast.makeText(VerificationActivity.this, "Registration success, wait until the admin verify your account", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(VerificationActivity.this, "Registration failed, account with this employee_id already exists", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(VerificationActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(VerificationActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", empId);
                param.put("empName", empName);
                param.put("userName", userName);
                param.put("password", password);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void updatePassword() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_UPDATE_FORGOT_PASSWORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        Intent bukaActivity = new Intent(VerificationActivity.this, LoginActivity.class);
                        startActivity(bukaActivity);
                        finish();
                        Toast.makeText(VerificationActivity.this, "Success change password, try to login", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(VerificationActivity.this, "Failed change password", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(VerificationActivity.this, "Error change password", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(VerificationActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                param.put("new_password", newPassword);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void updateUsername() {
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_UPDATE_FORGOT_USERNAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    if(status==1){
                        Intent bukaActivity = new Intent(VerificationActivity.this, LoginActivity.class);
                        startActivity(bukaActivity);
                        finish();
                        Toast.makeText(VerificationActivity.this, "Success change username, try to login", Toast.LENGTH_LONG).show();
                    } else {
                        Intent bukaActivity = new Intent(VerificationActivity.this, LoginActivity.class);
                        startActivity(bukaActivity);
                        finish();
                        Toast.makeText(VerificationActivity.this, "Failed, username has been used", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(VerificationActivity.this, "Error change username", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(VerificationActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                param.put("new_username", newUsername);
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            if (code == 1) {
                Intent bukaActivity = new Intent(VerificationActivity.this, SigninActivity.class);
                startActivity(bukaActivity);
                finish();
            } else {
                Intent bukaActivity = new Intent(VerificationActivity.this, LoginActivity.class);
                startActivity(bukaActivity);
                finish();
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to back", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}