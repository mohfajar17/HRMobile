package com.asukacorp.hrmobile.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ForgotActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextNewPassword;
    private EditText editTextConfirmPassword;
    private TextView editTextNewUsername;
    private TextView textLogin;
    private Button buttonChangeUserPass;

    private TextView textViewChangePassword;
    private TextView textViewChangeUser;
    private LinearLayout layoutPassword;
    private LinearLayout layoutUsername;
    private ViewGroup.LayoutParams params;

    private Dialog myDialog;
    public SharedPrefManager sharedPrefManager;
    private CustomProgressDialog progressDialog;

    private int codeForget = 0;
    private String userId;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        progressDialog = new CustomProgressDialog(this);
        myDialog = new Dialog(ForgotActivity.this);

        layoutUsername = (LinearLayout) findViewById(R.id.layoutUsername);
        layoutPassword = (LinearLayout) findViewById(R.id.layoutPassword);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextNewUsername = (EditText) findViewById(R.id.editTextNewUsername);

        textLogin = (TextView) findViewById(R.id.textLogin);
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(ForgotActivity.this, LoginActivity.class);
                startActivity(bukaActivity);
                finish();
            }
        });

        buttonChangeUserPass = (Button) findViewById(R.id.buttonChangeUserPass);
        buttonChangeUserPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOTP();
            }
        });

        textViewChangePassword = (TextView) findViewById(R.id.textViewChangePassword);
        textViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeForget = 1;

                textViewChangePassword.setTextColor(getResources().getColor(R.color.primary_dark));
                textViewChangePassword.setBackgroundColor(getResources().getColor(R.color.white));
                textViewChangeUser.setTextColor(getResources().getColor(R.color.white));
                textViewChangeUser.setBackgroundColor(getResources().getColor(R.color.primary_dark));

                params = layoutUsername.getLayoutParams();
                params.height = 0;
                layoutUsername.setLayoutParams(params);
                params = layoutPassword.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutPassword.setLayoutParams(params);
            }
        });

        textViewChangeUser = (TextView) findViewById(R.id.textViewChangeUser);
        textViewChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeForget = 0;

                textViewChangeUser.setTextColor(getResources().getColor(R.color.primary_dark));
                textViewChangeUser.setBackgroundColor(getResources().getColor(R.color.white));
                textViewChangePassword.setTextColor(getResources().getColor(R.color.white));
                textViewChangePassword.setBackgroundColor(getResources().getColor(R.color.primary_dark));

                params = layoutPassword.getLayoutParams();
                params.height = 0;
                layoutPassword.setLayoutParams(params);
                params = layoutUsername.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutUsername.setLayoutParams(params);
            }
        });
    }

    private void filterOTP(){
        if (codeForget == 1){
            if (editTextPhone.getText().toString().trim().isEmpty() || editTextNewPassword.getText().toString().trim().isEmpty() ||
                    editTextConfirmPassword.getText().toString().trim().isEmpty() || editTextPhone.getText().length()<10 ||
                    editTextNewPassword.getText().length()<6 || editTextConfirmPassword.getText().length()<6)
                Toast.makeText(ForgotActivity.this, "Please complete your data", Toast.LENGTH_LONG).show();
            else if (!editTextNewPassword.getText().toString().matches(editTextConfirmPassword.getText().toString()))
                Toast.makeText(ForgotActivity.this, "Your confirmation password does not match with your new password", Toast.LENGTH_LONG).show();
            else if (sharedPrefManager.getKeyOtpCount()>4){
                Toast.makeText(ForgotActivity.this, "You can only send the otp code five times today, try again tomorrow", Toast.LENGTH_LONG).show();
            } else {
                ShowPopup("Remember your new password to Login!");
            }
        } else {
            if (editTextPhone.getText().toString().trim().isEmpty() || editTextPhone.getText().length()<10 ||
                    editTextNewUsername.getText().toString().trim().isEmpty())
                Toast.makeText(ForgotActivity.this, "Please complete your data", Toast.LENGTH_LONG).show();
            else if (sharedPrefManager.getKeyOtpCount()>4){
                Toast.makeText(ForgotActivity.this, "You can only send the otp code five times today, try again tomorrow", Toast.LENGTH_LONG).show();
            } else {
                ShowPopup("Remember your new username to Login!");
            }
        }
    }

    private void sendData(){
        progressDialog.show();
        buttonChangeUserPass.setVisibility(View.INVISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SEND_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    userId = jsonObject.getString("data");
                    if(status==1){
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+62"+editTextPhone.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                ForgotActivity.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        if (codeForget==1)
                                            updatePassword();
                                        else updateUsername();
                                    }
                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        Toast.makeText(ForgotActivity.this, "Failed to send OTP code", Toast.LENGTH_LONG).show();
                                    }
                                    @Override
                                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        sharedPrefManager.setOtpCount(sharedPrefManager.getKeyOtpCount()+1);
                                        Intent bukaActivity = new Intent(ForgotActivity.this, VerificationActivity.class);
                                        if (codeForget==1){
                                            bukaActivity.putExtra("newPassword", editTextNewPassword.getText().toString());
                                            bukaActivity.putExtra("confirmPassword", editTextConfirmPassword.getText().toString());
                                            bukaActivity.putExtra("phone", editTextPhone.getText().toString());
                                            bukaActivity.putExtra("userId", userId);
                                            bukaActivity.putExtra("verificationId", verificationId);
                                            bukaActivity.putExtra("code", 2);
                                        } else {
                                            bukaActivity.putExtra("newUsername", editTextNewUsername.getText().toString());
                                            bukaActivity.putExtra("phone", editTextPhone.getText().toString());
                                            bukaActivity.putExtra("userId", userId);
                                            bukaActivity.putExtra("verificationId", verificationId);
                                            bukaActivity.putExtra("code", 3);
                                        }
                                        startActivity(bukaActivity);
                                        finish();
                                    }
                                }
                        );
                    } else {
                        Toast.makeText(ForgotActivity.this, "Your phone number doesn't match the employee data, confirm your data to HRD", Toast.LENGTH_LONG).show();
                    }
                    buttonChangeUserPass.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    buttonChangeUserPass.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(ForgotActivity.this, "Failed change password", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(ForgotActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("phone", editTextPhone.getText().toString());
                param.put("code", "1");
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
                        Intent bukaActivity = new Intent(ForgotActivity.this, LoginActivity.class);
                        startActivity(bukaActivity);
                        finish();
                        Toast.makeText(ForgotActivity.this, "Success change password, try to login", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ForgotActivity.this, "Failed change password", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ForgotActivity.this, "Error change password", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(ForgotActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                param.put("new_password", editTextNewPassword.getText().toString());
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
                        Intent bukaActivity = new Intent(ForgotActivity.this, LoginActivity.class);
                        startActivity(bukaActivity);
                        finish();
                        Toast.makeText(ForgotActivity.this, "Success change username, try to login", Toast.LENGTH_LONG).show();
                    } else {
                        Intent bukaActivity = new Intent(ForgotActivity.this, LoginActivity.class);
                        startActivity(bukaActivity);
                        finish();
                        Toast.makeText(ForgotActivity.this, "Failed, username has been used", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ForgotActivity.this, "Error change username", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(ForgotActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("user_id", userId);
                param.put("new_username", editTextNewUsername.getText().toString());
                return param;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    public void ShowPopup(String massage) {
        ImageView image;
        TextView textDialog;
        TextView btnYes;
        TextView btnNo;

        myDialog.setContentView(R.layout.custom_confirm_dialog);
        image = (ImageView) myDialog.findViewById(R.id.imageDialog);
        textDialog = (TextView) myDialog.findViewById(R.id.textDialog);
        btnYes = (TextView) myDialog.findViewById(R.id.btnYes);
        btnNo = (TextView) myDialog.findViewById(R.id.btnNo);

        image.setImageResource(R.drawable.ic_info);
        textDialog.setText(massage);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        WindowManager.LayoutParams params = myDialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        myDialog.getWindow().setAttributes(params);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.setCancelable(false);
        myDialog.show();
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