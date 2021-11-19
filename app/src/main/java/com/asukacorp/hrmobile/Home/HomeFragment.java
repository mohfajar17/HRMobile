package com.asukacorp.hrmobile.Home;

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
import com.asukacorp.hrmobile.Adapter.RecyclerViewBirthday;
import com.asukacorp.hrmobile.Adapter.RecyclerViewNews;
import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.CustomProgressDialog;
import com.asukacorp.hrmobile.Data.Birthday;
import com.asukacorp.hrmobile.Data.News;
import com.asukacorp.hrmobile.Login.LoginActivity;
import com.asukacorp.hrmobile.R;
import com.asukacorp.hrmobile.SharedPrefManager;
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
    private RecyclerView recyclerViewB;
    private RecyclerViewNews recyclerViewNews;
    private RecyclerViewBirthday recyclerViewBirthday;
    private RecyclerView.LayoutManager recylerViewLayoutManager;
    private List<News> news;
    private List<Birthday> birthdays;

    private CustomProgressDialog progressDialog;
    private ViewGroup.LayoutParams params;

    private NumberFormat formatter;
    private SharedPrefManager sharedPrefManager;

    private TextView textViewCuti;
    private TextView textViewMoneyBox;
    private TextView textViewPeriodeKerja;
    private TextView textViewEmpBirthday;
    private CircleImageView imageAkun;
    private ImageView imageNotif;
    private LinearLayout layoutBirthday;

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

        progressDialog = new CustomProgressDialog(getContext());

        context = getActivity().getApplicationContext();
        news = new ArrayList<>();
        birthdays = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        recyclerViewB = (RecyclerView) view.findViewById(R.id.recyclerViewBirthday);
        recylerViewLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewB.setLayoutManager(recylerViewLayoutManager);

        textViewCuti = (TextView) view.findViewById(R.id.textViewCuti);
        textViewMoneyBox = (TextView) view.findViewById(R.id.textViewMoneyBox);
        textViewPeriodeKerja = (TextView) view.findViewById(R.id.textViewPeriodeKerja);
        textViewEmpBirthday = (TextView) view.findViewById(R.id.textViewEmpBirthday);
        imageAkun = (CircleImageView) view.findViewById(R.id.imageAkun);
        imageNotif = (ImageView) view.findViewById(R.id.imageNotif);
        layoutBirthday = (LinearLayout) view.findViewById(R.id.layoutBirthday);

//        if (Integer.valueOf(sharedPrefManager.getKeyDepartmentId()) == 15){
//            ViewGroup.LayoutParams params = imageNotif.getLayoutParams();
//            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            imageNotif.setLayoutParams(params);
//        }

        imageNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (sharedPrefManager.getKeyEmployeeFileName().equals("null")){
            imageAkun.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.akun));
        } else Picasso.get().load(Config.DATA_URL_EMP_PHOTO +sharedPrefManager.getKeyEmployeeFileName()).into(imageAkun);

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
                        recyclerViewNews = new RecyclerViewNews(news, context);
                        recyclerView.setAdapter(recyclerViewNews);

                        //set data birthday
                        jsonArray = jsonObject.getJSONArray("data birthday");
                        if (jsonArray.length()>0){
                            params = layoutBirthday.getLayoutParams();
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            layoutBirthday.setLayoutParams(params);

                            for(int i=0;i<jsonArray.length();i++){
                                birthdays.add(new Birthday(jsonArray.getJSONObject(i)));
                            }
                            recyclerViewBirthday = new RecyclerViewBirthday(birthdays, context);
                            recyclerViewB.setAdapter(recyclerViewBirthday);
                        }
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