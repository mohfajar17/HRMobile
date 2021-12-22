package com.asukacorp.hrmobile.Menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.Hybrid.CutiActivity;
import com.asukacorp.hrmobile.Login.LoginActivity;
import com.asukacorp.hrmobile.Menu.Absensi.CheckClockActivity;
import com.asukacorp.hrmobile.Menu.Chat.ChatActivity;
import com.asukacorp.hrmobile.Menu.SlipGaji.SlipGajiActivity;
import com.asukacorp.hrmobile.Menu.Spkl.SpklActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MenuFragment extends Fragment {

    private LinearLayout menuCuti;
    private LinearLayout menuSpkl;
    private LinearLayout menuAbsensi;
    private LinearLayout menuSlipGaji;
    private LinearLayout menuChat;

    private SharedPrefManager sharedPrefManager;

    public MenuFragment() {
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());

        menuCuti = (LinearLayout) view.findViewById(R.id.menuCuti);
        menuSpkl = (LinearLayout) view.findViewById(R.id.menuSpkl);
        menuAbsensi = (LinearLayout) view.findViewById(R.id.menuAbsensi);
        menuSlipGaji = (LinearLayout) view.findViewById(R.id.menuSlipGaji);
        menuChat = (LinearLayout) view.findViewById(R.id.menuChat);

        menuCuti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), CutiActivity.class);
                bukaActivity.putExtra("code", 0);
                startActivityForResult(bukaActivity,1);
            }
        });
        menuSpkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), SpklActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });
        menuAbsensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), CheckClockActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });
        menuSlipGaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), SlipGajiActivity.class);
                startActivityForResult(bukaActivity,1);
            }
        });
        menuChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bukaActivity = new Intent(getActivity(), ChatActivity.class);
                startActivityForResult(bukaActivity,1);
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
}