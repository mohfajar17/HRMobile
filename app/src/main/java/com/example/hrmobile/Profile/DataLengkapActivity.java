package com.example.hrmobile.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataLengkapActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;

    private TextView textEmployeeName;
    private TextView textGender;
    private TextView textPlaceDateOfBirthday;
    private TextView textReligion;
    private TextView textMaritalStatus;
    private TextView textAddress;
    private TextView textMobilePhone;
    private TextView textEmail;
    private TextView textEmployeeNumber;
    private TextView textEmployeeGrade;
    private TextView textJobGrade;
    private TextView textDepartment;
    private TextView textCompanyWorkbase;
    private TextView textEmployeeStatus;
    private TextView textWorkingStatus;
    private CircleImageView imageAkun;

    private LinearLayout btnEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_lengkap);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        textEmployeeName = (TextView) findViewById(R.id.textEmployeeName);
        textGender = (TextView) findViewById(R.id.textGender);
        textPlaceDateOfBirthday = (TextView) findViewById(R.id.textPlaceDateOfBirthday);
        textReligion = (TextView) findViewById(R.id.textReligion);
        textMaritalStatus = (TextView) findViewById(R.id.textMaritalStatus);
        textAddress = (TextView) findViewById(R.id.textAddress);
        textMobilePhone = (TextView) findViewById(R.id.textMobilePhone);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textEmployeeNumber = (TextView) findViewById(R.id.textEmployeeNumber);
        textEmployeeGrade = (TextView) findViewById(R.id.textEmployeeGrade);
        textJobGrade = (TextView) findViewById(R.id.textJobGrade);
        textDepartment = (TextView) findViewById(R.id.textDepartment);
        textCompanyWorkbase = (TextView) findViewById(R.id.textCompanyWorkbase);
        textEmployeeStatus = (TextView) findViewById(R.id.textEmployeeStatus);
        textWorkingStatus = (TextView) findViewById(R.id.textWorkingStatus);
        btnEditProfile = (LinearLayout) findViewById(R.id.btnEditProfile);
        imageAkun = (CircleImageView) findViewById(R.id.imageAkun);

        if (sharedPrefManager.getKeyEmployeeFileName().equals("")){
            imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun));
        } else Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getKeyEmployeeFileName()).into(imageAkun);

        textEmployeeName.setText(sharedPrefManager.getKeyFullname() + " (" + sharedPrefManager.getKeyNickname() + ")");
        textGender.setText(sharedPrefManager.getKeyGender());
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            String newDate = outputFormat.format(inputFormat.parse(sharedPrefManager.getKeyBirthday()));
            textPlaceDateOfBirthday.setText(sharedPrefManager.getKeyPlaceBirthday() + ", " + newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textReligion.setText(sharedPrefManager.getKeyReligionName());
        textMaritalStatus.setText(sharedPrefManager.getKeyMaritalStatusName());
        textAddress.setText(sharedPrefManager.getKeyAddress()+", "+sharedPrefManager.getKeyCity()+", "+sharedPrefManager.getKeyState());
        textMobilePhone.setText(sharedPrefManager.getKeyMobilePhone());
        textEmail.setText(sharedPrefManager.getKeyEmail());
        textEmployeeNumber.setText(sharedPrefManager.getKeyEmployeeNumber());
        textEmployeeGrade.setText(sharedPrefManager.getKeyEmployeeGradeName());
        textJobGrade.setText(sharedPrefManager.getKeyJobGradeName());
        textDepartment.setText(sharedPrefManager.getKeyDepartmentName());
        textCompanyWorkbase.setText(sharedPrefManager.getKeyCompanyWorkbaseName());
        textEmployeeStatus.setText(sharedPrefManager.getKeyEmployeeStatus());
        textWorkingStatus.setText(sharedPrefManager.getKeyWorkingStatus());

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(DataLengkapActivity.this, EditProfileActivity.class);
                startActivityForResult(bukaActivity,1);
                finish();
            }
        });
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
                        Intent logout = new Intent(DataLengkapActivity.this, LoginActivity.class);
                        startActivity(logout);
                        DataLengkapActivity.this.finish();
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
        Volley.newRequestQueue(DataLengkapActivity.this).add(request);
    }
}