package com.asukacorp.hrmobile.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.LoginActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DataLengkapActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;
    private Dialog myDialog;

    private TextView textEmployeeName;
    private TextView textGender;
    private TextView textGolDarah;
    private TextView textPlaceDateOfBirthday;
    private TextView textReligion;
    private TextView textMaritalStatus;
    private TextView textAddress;
    private TextView textMobilePhone;
    private TextView textEmail;
    private TextView textNik;
    private ImageView imageKtp;
    private TextView textNpwp;
    private TextView textBpjs;
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
        myDialog = new Dialog(this);

        textEmployeeName = (TextView) findViewById(R.id.textEmployeeName);
        textGender = (TextView) findViewById(R.id.textGender);
        textGolDarah = (TextView) findViewById(R.id.textGolDarah);
        textPlaceDateOfBirthday = (TextView) findViewById(R.id.textPlaceDateOfBirthday);
        textReligion = (TextView) findViewById(R.id.textReligion);
        textMaritalStatus = (TextView) findViewById(R.id.textMaritalStatus);
        textAddress = (TextView) findViewById(R.id.textAddress);
        textMobilePhone = (TextView) findViewById(R.id.textMobilePhone);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textNik = (TextView) findViewById(R.id.textNik);
        imageKtp = (ImageView) findViewById(R.id.imageKtp);
        textNpwp = (TextView) findViewById(R.id.textNpwp);
        textBpjs = (TextView) findViewById(R.id.textBpjs);
        textEmployeeNumber = (TextView) findViewById(R.id.textEmployeeNumber);
        textEmployeeGrade = (TextView) findViewById(R.id.textEmployeeGrade);
        textJobGrade = (TextView) findViewById(R.id.textJobGrade);
        textDepartment = (TextView) findViewById(R.id.textDepartment);
        textCompanyWorkbase = (TextView) findViewById(R.id.textCompanyWorkbase);
        textEmployeeStatus = (TextView) findViewById(R.id.textEmployeeStatus);
        textWorkingStatus = (TextView) findViewById(R.id.textWorkingStatus);
        btnEditProfile = (LinearLayout) findViewById(R.id.btnEditProfile);
        imageAkun = (CircleImageView) findViewById(R.id.imageAkun);

        imageKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup();
            }
        });

        if (sharedPrefManager.getKeyEmployeeFileName().equals("")){
            imageAkun.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.akun));
        } else Picasso.get().load(Config.DATA_URL_EMP_PHOTO +sharedPrefManager.getKeyEmployeeFileName()).into(imageAkun);

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
        textGolDarah.setText(sharedPrefManager.getKeyBloodGroup());
        textReligion.setText(sharedPrefManager.getKeyReligionName());
        textMaritalStatus.setText(sharedPrefManager.getKeyMaritalStatusName());
        textAddress.setText(sharedPrefManager.getKeyAddress()+", "+sharedPrefManager.getKeyCity()+", "+sharedPrefManager.getKeyState());
        textMobilePhone.setText(sharedPrefManager.getKeyMobilePhone());
        textEmail.setText(sharedPrefManager.getKeyEmail());
        textNik.setText(sharedPrefManager.getKeyNik());
        textNpwp.setText(sharedPrefManager.getKeyNpwp());
        textBpjs.setText(sharedPrefManager.getKeyBpjs());
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

    public void ShowPopup() {
        ImageView image;
        myDialog.setContentView(R.layout.custom_popup_image);
        image = (ImageView) myDialog.findViewById(R.id.imageNo);
        Picasso.get().load(Config.DATA_URL_EMP_PHOTO+sharedPrefManager.getKeyIdentityFileName()).into(image);
        if (image.getDrawable() == null)
            image.setImageResource(R.drawable.no_image);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}