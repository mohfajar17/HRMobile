package com.example.hrmobile.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;
    private ProgressDialog progressDialog;

    private EditText editTextEmployeeName;
    private EditText editTextGender;
    private EditText editTextBirthday;
    private EditText editTextReligion;
    private EditText editTextMaritalStatus;
    private EditText editTextAddress;
    private EditText editTextCity;
    private EditText editTextState;
    private EditText editTextMobilePhone;
    private EditText editTextEmail;
    private EditText editTextKonfPassword;
    private EditText editTextPassword;

    private Button buttonSimpanAkun;
    private Button buttonSimpanPersonal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        editTextEmployeeName = (EditText) findViewById(R.id.editTextEmployeeName);
        editTextGender = (EditText) findViewById(R.id.editTextGender);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextReligion = (EditText) findViewById(R.id.editTextReligion);
        editTextMaritalStatus = (EditText) findViewById(R.id.editTextMaritalStatus);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextCity = (EditText) findViewById(R.id.editTextCity);
        editTextState = (EditText) findViewById(R.id.editTextState);
        editTextMobilePhone = (EditText) findViewById(R.id.editTextMobilePhone);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextKonfPassword = (EditText) findViewById(R.id.editTextKonfPassword);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSimpanAkun = (Button) findViewById(R.id.buttonSimpanAkun);
        buttonSimpanPersonal = (Button) findViewById(R.id.buttonSimpanPersonal);

        editTextEmployeeName.setText(sharedPrefManager.getKeyFullname());
        editTextGender.setText(sharedPrefManager.getKeyGender());
        editTextBirthday.setText(sharedPrefManager.getKeyPlaceBirthday() + ", " + sharedPrefManager.getKeyBirthday());
        editTextReligion.setText(sharedPrefManager.getKeyReligionName());
        editTextMaritalStatus.setText(sharedPrefManager.getKeyMaritalStatusName());
        editTextAddress.setText(sharedPrefManager.getKeyAddress());
        editTextCity.setText(sharedPrefManager.getKeyCity());
        editTextState.setText(sharedPrefManager.getKeyState());
        editTextMobilePhone.setText(sharedPrefManager.getKeyMobilePhone());
        editTextEmail.setText(sharedPrefManager.getKeyEmail());

        buttonSimpanAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataPassword();
            }
        });
        buttonSimpanPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataPersonal();
            }
        });

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
                        Intent logout = new Intent(EditProfileActivity.this, LoginActivity.class);
                        startActivity(logout);
                        EditProfileActivity.this.finish();
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
        Volley.newRequestQueue(EditProfileActivity.this).add(request);
    }

    private void setDataPassword() {
        if (String.valueOf(editTextKonfPassword.getText()).equals("") || String.valueOf(editTextPassword.getText()).equals("")){
            Toast.makeText(EditProfileActivity.this, "Please enter data", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_UPDATE_PASSWORD, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            onBackPressed();
                            Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                        } else if (status==2){
                            Toast.makeText(EditProfileActivity.this, "You don't have an access, please contact admin", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Incorrect data input", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditProfileActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("user_id", sharedPrefManager.getKeyUserId());
                    param.put("new_password", String.valueOf(editTextPassword.getText()));
                    param.put("old_password", String.valueOf(editTextKonfPassword.getText()));
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    private void setDataPersonal() {
        if (String.valueOf(editTextAddress.getText()).equals("") || String.valueOf(editTextMobilePhone.getText()).equals("") || String.valueOf(editTextEmail.getText()).equals("")){
            Toast.makeText(EditProfileActivity.this, "Please enter data", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_UPDATE_PERSONAL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            sharedPrefManager.setAddress(editTextAddress.getText().toString());
                            sharedPrefManager.setMobilePhone(editTextMobilePhone.getText().toString());
                            sharedPrefManager.setEmail(editTextEmail.getText().toString());

                            onBackPressed();
                            Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Incorrect data input", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditProfileActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("employee_id", sharedPrefManager.getKeyEmployeeId());
                    param.put("address", String.valueOf(editTextAddress.getText()));
                    param.put("mobile_phone", String.valueOf(editTextMobilePhone.getText()));
                    param.put("email", String.valueOf(editTextEmail.getText()));
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    @Override
    public void onBackPressed() {
        Intent bukaActivity = new Intent(EditProfileActivity.this, DataLengkapActivity.class);
        startActivityForResult(bukaActivity,1);
        finish();
    }
}