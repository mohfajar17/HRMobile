package com.asukacorp.hrmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    private Button buttonVerify;
    private TextView textViewResendOtp;
    private EditText editTextCode1, editTextCode2, editTextCode3, editTextCode4, editTextCode5, editTextCode6;
    private String verificationId, empId, empName, userName, password, phone, code;

    private CustomProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        verificationId = getIntent().getStringExtra("verificationId");
        empId = getIntent().getStringExtra("empId");
        empName = getIntent().getStringExtra("empName");
        userName = getIntent().getStringExtra("userName");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");

        progressDialog = new CustomProgressDialog(this);

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
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+62"+phone,
                        120,
                        TimeUnit.SECONDS,
                        VerificationActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                Toast.makeText(VerificationActivity.this, "Completed to send OTP code", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerificationActivity.this, "Failed to send OTP code", Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newVerificationId;
                                Toast.makeText(VerificationActivity.this, "Success send OTP code", Toast.LENGTH_LONG).show();
                            }
                        }
                );
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
                    code = editTextCode1.getText().toString() +
                            editTextCode2.getText().toString() +
                            editTextCode3.getText().toString() +
                            editTextCode4.getText().toString() +
                            editTextCode5.getText().toString() +
                            editTextCode6.getText().toString();

                    if (verificationId != null){
                        progressDialog.show();
                        buttonVerify.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                buttonVerify.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()){
                                    createData();
                                } else Toast.makeText(VerificationActivity.this, "Failed to verify otp code, please try again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });

        setOtpInput();
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
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void createData() {
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
}