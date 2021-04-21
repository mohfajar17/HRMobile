package com.example.hrmobile.Profile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrmobile.Config;
import com.example.hrmobile.Hybrid.CutiActivity;
import com.example.hrmobile.LoginActivity;
import com.example.hrmobile.R;
import com.example.hrmobile.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private LinearLayout menuDataLengkap;
    private LinearLayout menuDataKeluarga;
    private LinearLayout menuRiwayatJabatan;
    private LinearLayout menuRiwayatPendidikan;
    private LinearLayout menuPengalamanKerja;
    private LinearLayout menuPelatihan;
    private LinearLayout menuFile;
    private LinearLayout menuTunjanganPotongan;
    private LinearLayout menuCuti;
    private LinearLayout menuAbout;
    private LinearLayout menuLogout;
    private TextView textEmployeeName;
    private TextView textJobGrade;
    private TextView textEmployeeNumber;
    private CircleImageView imageAkun;
    private Dialog myDialog;

    private SharedPrefManager sharedPrefManager;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        myDialog = new Dialog(getContext());

        menuDataLengkap = (LinearLayout) view.findViewById(R.id.menuDataLengkap);
        menuDataKeluarga = (LinearLayout) view.findViewById(R.id.menuDataKeluarga);
        menuRiwayatJabatan = (LinearLayout) view.findViewById(R.id.menuRiwayatJabatan);
        menuRiwayatPendidikan = (LinearLayout) view.findViewById(R.id.menuRiwayatPendidikan);
        menuPengalamanKerja = (LinearLayout) view.findViewById(R.id.menuPengalamanKerja);
        menuPelatihan = (LinearLayout) view.findViewById(R.id.menuPelatihan);
        menuFile = (LinearLayout) view.findViewById(R.id.menuFile);
        menuTunjanganPotongan = (LinearLayout) view.findViewById(R.id.menuTunjanganPotongan);
        menuCuti = (LinearLayout) view.findViewById(R.id.menuCuti);
        menuAbout = (LinearLayout) view.findViewById(R.id.menuAbout);
        menuLogout = (LinearLayout) view.findViewById(R.id.menuLogout);

        textEmployeeName = (TextView) view.findViewById(R.id.textEmployeeName);
        textJobGrade = (TextView) view.findViewById(R.id.textJobGrade);
        textEmployeeNumber = (TextView) view.findViewById(R.id.textEmployeeNumber);
        imageAkun = (CircleImageView) view.findViewById(R.id.imageAkun);

        if (sharedPrefManager.getKeyEmployeeFileName().equals("")){
            imageAkun.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.akun));
        } else Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getKeyEmployeeFileName()).into(imageAkun);

        textEmployeeName.setText(sharedPrefManager.getKeyFullname());
        textJobGrade.setText(sharedPrefManager.getKeyJobGradeName());
        textEmployeeNumber.setText(sharedPrefManager.getKeyEmployeeNumber());

        menuDataLengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), DataLengkapActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuDataKeluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), DataKeluargaActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuRiwayatJabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), RiwayatJabatanActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuRiwayatPendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), RiwayatPendidikanActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuPengalamanKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), PengalamanKerjaActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuPelatihan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), PelatihanActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), FileActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuTunjanganPotongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), TunjanganPotonganActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), CutiActivity.class);
                bukaActivity.putExtra("code", 1);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), AboutAppsActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });

        menuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup();
            }
        });
        loadUserEnable();

        return view;
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
                        Intent logout = new Intent(getActivity(), LoginActivity.class);
                        startActivity(logout);
                        getActivity().finish();
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
        Volley.newRequestQueue(getActivity()).add(request);
    }



    public void ShowPopup() {
        ImageView image;
        TextView textDialog;
        TextView btnYes;
        TextView btnNo;

        myDialog.setContentView(R.layout.custom_confirm_dialog);
        image = (ImageView) myDialog.findViewById(R.id.imageDialog);
        textDialog = (TextView) myDialog.findViewById(R.id.textDialog);
        btnYes = (TextView) myDialog.findViewById(R.id.btnYes);
        btnNo = (TextView) myDialog.findViewById(R.id.btnNo);

        image.setImageResource(R.drawable.ic_exit);
        textDialog.setText("Do you want to logout?");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(getActivity(), LoginActivity.class);
                startActivity(logout);
                sharedPrefManager.setIsLogout();
                getActivity().finish();
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
}