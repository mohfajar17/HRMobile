package com.example.hrmobile.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrmobile.Adapter.RecyclerViewNews;
import com.example.hrmobile.Config;
import com.example.hrmobile.Data.News;
import com.example.hrmobile.LoginActivity;
import com.example.hrmobile.R;
import com.example.hrmobile.SharedPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerViewNews adapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<News> news;
    private ProgressDialog progressDialog;

    private NumberFormat formatter;
    private SharedPrefManager sharedPrefManager;

    private TextView textViewCuti;
    private TextView textViewMoneyBox;
    private TextView textViewPeriodeKerja;
    private CircleImageView imageAkun;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        formatter = new DecimalFormat("#,###");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        context = getActivity().getApplicationContext();
        news = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        textViewCuti = (TextView) view.findViewById(R.id.textViewCuti);
        textViewMoneyBox = (TextView) view.findViewById(R.id.textViewMoneyBox);
        textViewPeriodeKerja = (TextView) view.findViewById(R.id.textViewPeriodeKerja);
        imageAkun = (CircleImageView) view.findViewById(R.id.imageAkun);

        if (sharedPrefManager.getKeyEmployeeFileName().equals("")){
            imageAkun.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.akun));
        } else Picasso.get().load(Config.DATA_URL_PHOTO_PROFILE+sharedPrefManager.getKeyEmployeeFileName()).into(imageAkun);

        loadData();
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

    private void loadData() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_LEAVE_MONEY_BOX, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status=jsonObject.getInt("status");
                    if(status==1){
                        textViewCuti.setText(jsonObject.getInt("data leave") + " Hari");
                        long money = jsonObject.getLong("data money box");
                        textViewMoneyBox.setText("Rp. " + formatter.format(money));
                        textViewPeriodeKerja.setText(jsonObject.getString("data periode"));

                        //set data news
                        JSONArray jsonArray = jsonObject.getJSONArray("data news");
                        for(int i=0;i<jsonArray.length();i++){
                            news.add(new News(jsonArray.getJSONObject(i)));
                        }
                        adapter = new RecyclerViewNews(news, context);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), "Filed load data", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Error load data", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Network is broken", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("empId", sharedPrefManager.getKeyEmployeeId());
                param.put("begin", sharedPrefManager.getKeyJoinDate());
                param.put("end", sharedPrefManager.getKeyComeOutDate());
                return param;
            }
        };
        Volley.newRequestQueue(getActivity()).add(request);
    }
}