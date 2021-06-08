package com.example.hrmobile.Menu.SlipGaji;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrmobile.Config;
import com.example.hrmobile.CustomProgressDialog;
import com.example.hrmobile.LoginActivity;
import com.example.hrmobile.R;
import com.example.hrmobile.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SlipGajiActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private Spinner spinnerTgl;
    private Spinner spinnerBulan;
    private Spinner spinnerTahun;
    private LinearLayout btnSearch;
    private LinearLayout layoutAll;

    private TextView textGajiPokok;
    private TextView textMeal;
    private TextView textTransport;
    private TextView textWelfare;
    private TextView textProfesional;
    private TextView textBPJSKesehatan;
    private TextView textBPJSKetenagakerjaan;
    private TextView textLembur;
    private TextView textMakanLembur;
    private TextView textEmergencyCall;
    private TextView textLessPayment;
    private TextView textAbsent;
    private TextView textKesehatan;
    private TextView textKetenagakerjaan;
    private TextView textJht;
    private TextView textJP;
    private TextView textBPJSKaryawan;
    private TextView textIncomeTax;
    private TextView textInvestasiJaminanKerja;
    private TextView textPinjamanKoperasi;
    private TextView textPotonganK3;
    private TextView textPotonganTerlambat;
    private TextView textPotonganLainnya;

    private TextView textJumlahPendapatan;
    private TextView textJumlahPotongan;
    private TextView textGajiBersih;
    private TextView textTakeHomePay;
    private TextView textTunjanganLokasiPerjalanan;
    private TextView textTotal;
    private ImageView downloadFile;

    private RelativeLayout layoutMeal;
    private RelativeLayout layoutTransport;
    private RelativeLayout layoutWelfare;
    private RelativeLayout layoutProfesional;
    private RelativeLayout layoutBPJSKesehatan;
    private RelativeLayout layoutBPJSKetenagakerjaan;
    private RelativeLayout layoutLembur;
    private RelativeLayout layoutMakanLembur;
    private RelativeLayout layoutEmergencyCall;
    private RelativeLayout layoutLessPayment;
    private RelativeLayout layoutAbsent;
    private RelativeLayout layoutIncomeTax;
    private RelativeLayout layoutInvestasiJaminanKerja;
    private RelativeLayout layoutPinjamanKoperasi;
    private RelativeLayout layoutPotonganK3;
    private RelativeLayout layoutPotonganTerlambat;
    private RelativeLayout layoutPotonganLainnya;
    private RelativeLayout layoutTunjanganLokasiPerjalanan;

    private CustomProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip_gaji);

        progressDialog = new CustomProgressDialog(this);
        sharedPrefManager = SharedPrefManager.getInstance(this);

        layoutMeal = (RelativeLayout) findViewById(R.id.layoutMeal);
        layoutTransport = (RelativeLayout) findViewById(R.id.layoutTransport);
        layoutWelfare = (RelativeLayout) findViewById(R.id.layoutWelfare);
        layoutProfesional = (RelativeLayout) findViewById(R.id.layoutProfesional);
        layoutBPJSKesehatan = (RelativeLayout) findViewById(R.id.layoutBPJSKesehatan);
        layoutBPJSKetenagakerjaan = (RelativeLayout) findViewById(R.id.layoutBPJSKetenagakerjaan);
        layoutLembur = (RelativeLayout) findViewById(R.id.layoutLembur);
        layoutMakanLembur = (RelativeLayout) findViewById(R.id.layoutMakanLembur);
        layoutEmergencyCall = (RelativeLayout) findViewById(R.id.layoutEmergencyCall);
        layoutLessPayment = (RelativeLayout) findViewById(R.id.layoutLessPayment);
        layoutAbsent = (RelativeLayout) findViewById(R.id.layoutAbsent);
        layoutIncomeTax = (RelativeLayout) findViewById(R.id.layoutIncomeTax);
        layoutInvestasiJaminanKerja = (RelativeLayout) findViewById(R.id.layoutInvestasiJaminanKerja);
        layoutPinjamanKoperasi = (RelativeLayout) findViewById(R.id.layoutPinjamanKoperasi);
        layoutPotonganK3 = (RelativeLayout) findViewById(R.id.layoutPotonganK3);
        layoutPotonganTerlambat = (RelativeLayout) findViewById(R.id.layoutPotonganTerlambat);
        layoutPotonganLainnya = (RelativeLayout) findViewById(R.id.layoutPotonganLainnya);
        layoutTunjanganLokasiPerjalanan = (RelativeLayout) findViewById(R.id.layoutTunjanganLokasiPerjalanan);

        textGajiPokok = (TextView) findViewById(R.id.textGajiPokok);
        textMeal = (TextView) findViewById(R.id.textMeal);
        textTransport = (TextView) findViewById(R.id.textTransport);
        textWelfare = (TextView) findViewById(R.id.textWelfare);
        textProfesional = (TextView) findViewById(R.id.textProfesional);
        textBPJSKesehatan = (TextView) findViewById(R.id.textBPJSKesehatan);
        textBPJSKetenagakerjaan = (TextView) findViewById(R.id.textBPJSKetenagakerjaan);
        textLembur = (TextView) findViewById(R.id.textLembur);
        textMakanLembur = (TextView) findViewById(R.id.textMakanLembur);
        textEmergencyCall = (TextView) findViewById(R.id.textEmergencyCall);
        textLessPayment = (TextView) findViewById(R.id.textLessPayment);
        textAbsent = (TextView) findViewById(R.id.textAbsent);
        textKesehatan = (TextView) findViewById(R.id.textKesehatan);
        textKetenagakerjaan = (TextView) findViewById(R.id.textKetenagakerjaan);
        textJht = (TextView) findViewById(R.id.textJht);
        textJP = (TextView) findViewById(R.id.textJP);
        textBPJSKaryawan = (TextView) findViewById(R.id.textBPJSKaryawan);
        textIncomeTax = (TextView) findViewById(R.id.textIncomeTax);
        textInvestasiJaminanKerja = (TextView) findViewById(R.id.textInvestasiJaminanKerja);
        textPinjamanKoperasi = (TextView) findViewById(R.id.textPinjamanKoperasi);
        textPotonganK3 = (TextView) findViewById(R.id.textPotonganK3);
        textPotonganTerlambat = (TextView) findViewById(R.id.textPotonganTerlambat);
        textPotonganLainnya = (TextView) findViewById(R.id.textPotonganLainnya);

        textJumlahPendapatan = (TextView) findViewById(R.id.textJumlahPendapatan);
        textJumlahPotongan = (TextView) findViewById(R.id.textJumlahPotongan);
        textGajiBersih = (TextView) findViewById(R.id.textGajiBersih);
        textTakeHomePay = (TextView) findViewById(R.id.textTakeHomePay);
        textTunjanganLokasiPerjalanan = (TextView) findViewById(R.id.textTunjanganLokasiPerjalanan);
        textTotal = (TextView) findViewById(R.id.textTotal);

        layoutAll = (LinearLayout) findViewById(R.id.layoutAll);

        downloadFile = (ImageView) findViewById(R.id.detailFile);
        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type;
                if (spinnerTgl.getSelectedItemPosition()==1)
                    type = 0;
                else if (spinnerTgl.getSelectedItemPosition()==2)
                    type = 1;
                else type = 2;
                Intent bukaMainActivity = new Intent(SlipGajiActivity.this, DownloadSlipActivity.class);
                bukaMainActivity.putExtra("employee_id", sharedPrefManager.getKeyEmployeeId());
                bukaMainActivity.putExtra("tipe", type);
                bukaMainActivity.putExtra("tanggal", spinnerTgl.getSelectedItem().toString());
                bukaMainActivity.putExtra("bulan", spinnerBulan.getSelectedItem().toString());
                bukaMainActivity.putExtra("tahun", spinnerTahun.getSelectedItem().toString());
                startActivity(bukaMainActivity);
            }
        });

        spinnerTgl = (Spinner) findViewById(R.id.spinnerTgl);
        spinnerBulan = (Spinner) findViewById(R.id.spinnerBulan);
        spinnerTahun = (Spinner) findViewById(R.id.spinnerTahun);

        String[] tgl = {"-- Pilih Tanggal --", "1 - 15", "16 - 31", "1 - 31"};
        String[] bulan = {"-- Pilih Bulan --", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String[] tahun = {"-- Pilih Tahun --", "", ""};
        tahun[1] = "" + Calendar.getInstance().get(Calendar.YEAR);
        int year = Calendar.getInstance().get(Calendar.YEAR)-1;
        tahun[2] = "" + year;
        adapter = new ArrayAdapter<String>(SlipGajiActivity.this, android.R.layout.simple_spinner_dropdown_item, tgl);
        spinnerTgl.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(SlipGajiActivity.this, android.R.layout.simple_spinner_dropdown_item, bulan);
        spinnerBulan.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(SlipGajiActivity.this, android.R.layout.simple_spinner_dropdown_item, tahun);
        spinnerTahun.setAdapter(adapter);

        btnSearch = (LinearLayout) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
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
                        Intent logout = new Intent(SlipGajiActivity.this, LoginActivity.class);
                        startActivity(logout);
                        SlipGajiActivity.this.finish();
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
        Volley.newRequestQueue(SlipGajiActivity.this).add(request);
    }

    private void getData() {
        if (spinnerTgl.getSelectedItemPosition() == 0 || spinnerBulan.getSelectedItemPosition() == 0 || spinnerTahun.getSelectedItemPosition() == 0) {
            ViewGroup.LayoutParams params = layoutAll.getLayoutParams();
            params.height = 0;
            layoutAll.setLayoutParams(params);
            Toast.makeText(SlipGajiActivity.this, "Please check your date", Toast.LENGTH_LONG).show();
        } else if (spinnerTahun.getSelectedItemPosition() == 2 && spinnerBulan.getSelectedItemPosition() != 12){
            ViewGroup.LayoutParams params = layoutAll.getLayoutParams();
            params.height = 0;
            layoutAll.setLayoutParams(params);
            Toast.makeText(SlipGajiActivity.this, "You can't access data at this month", Toast.LENGTH_LONG).show();
        } else {
            String date;
            if (spinnerTgl.getSelectedItemPosition() == 3)
                date = spinnerBulan.getSelectedItem().toString()  + " " + spinnerTahun.getSelectedItem().toString();
            else date = spinnerTgl.getSelectedItem().toString() + " " + spinnerBulan.getSelectedItem().toString()  + " " + spinnerTahun.getSelectedItem().toString();
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SLIP_GAJI, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            ViewGroup.LayoutParams params = layoutAll.getLayoutParams();
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            layoutAll.setLayoutParams(params);

                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("meal_allowance") == 0)
                                changeLayout(layoutMeal);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("transport_allowance") == 0)
                                changeLayout(layoutTransport);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("welfare_allowance") == 0)
                                changeLayout(layoutWelfare);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("profesional_allowance") == 0)
                                changeLayout(layoutProfesional);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jamsostek_allowance") == 0)
                                changeLayout(layoutBPJSKesehatan);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("bpjs_allowance") == 0)
                                changeLayout(layoutBPJSKetenagakerjaan);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("overtime") == 0)
                                changeLayout(layoutLembur);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("overtime_meal") == 0)
                                changeLayout(layoutMakanLembur);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("emergency_call") == 0)
                                changeLayout(layoutEmergencyCall);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("less_payment") == 0)
                                changeLayout(layoutLessPayment);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("absent") == 0)
                                changeLayout(layoutAbsent);

                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("pph1")+jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("pph2") == 0)
                                changeLayout(layoutIncomeTax);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("moneybox") == 0)
                                changeLayout(layoutInvestasiJaminanKerja);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("loan_cooperative") == 0)
                                changeLayout(layoutPinjamanKoperasi);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("deduction_k3_amount") == 0)
                                changeLayout(layoutPotonganK3);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("late_deduction") == 0)
                                changeLayout(layoutPotonganTerlambat);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("other_deduction") == 0)
                                changeLayout(layoutPotonganLainnya);
                            if (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("location_project_allowance")+jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("official_travel_allowance") == 0)
                                changeLayout(layoutTunjanganLokasiPerjalanan);

                            NumberFormat formatter = new DecimalFormat("#,###");

                            textGajiPokok.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("basic_salary")));
                            textMeal.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("meal_allowance")));
                            textTransport.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("transport_allowance")));
                            textWelfare.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("welfare_allowance")));
                            textProfesional.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("profesional_allowance")));
                            textBPJSKesehatan.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jamsostek_allowance")));
                            textBPJSKetenagakerjaan.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("bpjs_allowance")));
                            textLembur.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("overtime")));
                            textMakanLembur.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("overtime_meal")));
                            textEmergencyCall.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("emergency_call")));
                            textLessPayment.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("less_payment")));
                            textAbsent.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("absent")));

                            textKesehatan.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("bpjs_paid")));
                            textKetenagakerjaan.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jamsostek_paid")));
                            textJht.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jht")));
                            textJP.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jaminan_pensiun")));
                            textBPJSKaryawan.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("bpjs")));

                            textIncomeTax.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("pph1")+jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("pph2")));
                            textInvestasiJaminanKerja.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("moneybox")));
                            textPinjamanKoperasi.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("loan_cooperative")));
                            textPotonganK3.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("deduction_k3_amount")));
                            textPotonganTerlambat.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("late_deduction")));
                            textPotonganLainnya.setText("Rp. " + formatter.format(jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("other_deduction")));

                            long jmlPendapatan = jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("basic_salary") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("meal_allowance") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("transport_allowance") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("welfare_allowance") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("profesional_allowance") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jamsostek_allowance") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("bpjs_allowance") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("overtime") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("overtime_meal") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("emergency_call") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("less_payment") -
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("absent");
                            long jmlPotongan = jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("bpjs_paid") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jamsostek_paid") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jht") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("jaminan_pensiun") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("bpjs");
                            long gajiBersih = jmlPendapatan - jmlPotongan;
                            long takeHomePay = gajiBersih -
                                    (jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("pph1") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("pph2") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("loan_cooperative") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("deduction_k3_amount") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("late_deduction") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("other_deduction") +
                                    jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("moneybox"));
                            long tunjanganLokasiPerjalanan = jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("location_project_allowance") + jsonObject.getJSONArray("data pokok").getJSONObject(0).getLong("official_travel_allowance");
                            long total = takeHomePay + tunjanganLokasiPerjalanan;
                            textJumlahPendapatan.setText("Rp. " + formatter.format(jmlPendapatan));
                            textJumlahPotongan.setText("Rp. " + formatter.format(jmlPotongan));
                            textGajiBersih.setText("Rp. " + formatter.format(gajiBersih));
                            textTakeHomePay.setText("Rp. " + formatter.format(takeHomePay));
                            textTunjanganLokasiPerjalanan.setText("Rp. " + formatter.format(tunjanganLokasiPerjalanan));
                            textTotal.setText("Rp. " + formatter.format(total));

                            progressDialog.dismiss();
                            Toast.makeText(SlipGajiActivity.this, "Success", Toast.LENGTH_LONG).show();
                        } else {
                            ViewGroup.LayoutParams params = layoutAll.getLayoutParams();
                            params.height = 0;
                            layoutAll.setLayoutParams(params);
                            progressDialog.dismiss();
                            Toast.makeText(SlipGajiActivity.this, "No data in this month", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ViewGroup.LayoutParams params = layoutAll.getLayoutParams();
                        params.height = 0;
                        layoutAll.setLayoutParams(params);
                        progressDialog.dismiss();
                        Toast.makeText(SlipGajiActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    ViewGroup.LayoutParams params = layoutAll.getLayoutParams();
                    params.height = 0;
                    layoutAll.setLayoutParams(params);
                    progressDialog.dismiss();
                    Toast.makeText(SlipGajiActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("empId", sharedPrefManager.getKeyEmployeeId());
                    param.put("monthYear", date);
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    private void changeLayout(RelativeLayout layout){
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = 0;
        layout.setLayoutParams(params);
    }
}