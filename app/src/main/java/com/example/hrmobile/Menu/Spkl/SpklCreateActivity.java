package com.example.hrmobile.Menu.Spkl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SpklCreateActivity extends AppCompatActivity {

    private String spklNumber;
    private Dialog dialog;
    private ArrayList<String> arrayListJo, arrayListRequested, arrayListKaryawan1, arrayListKaryawan2, arrayListKaryawan3, arrayListKaryawan4, arrayListKaryawan5;
    private int idJobOrder = -1, idRequested = -1, idKaryawan1 = -1, idKaryawan2 = -1, idKaryawan3 = -1, idKaryawan4 = -1, idKaryawan5 = -1;
    private ArrayAdapter<String> adapter;
    private String[] spklJobCodeId, spklJobCodeText;
    private String[] spklLokasiId;
    private String[] spklDepartmenId;
    private String[] spklDepartmenCode;
    private String[] spklEmpName;
    private String[] spklRequestedId;

    private EditText editSpklNumber;
    private EditText editSpklDesc;
    private EditText editSpklDate;
    private TextView textViewJo;
    private Spinner editSpklLokasi;
    private Spinner editSpklDepartment;
    private TextView textViewRequested;
    private Button buttonBuat;
    private ImageView buttonBack;

    private TextView buttonSpkl1;
    private TextView buttonSpkl2;
    private TextView buttonSpkl3;
    private TextView buttonSpkl4;
    private TextView buttonSpkl5;
    private LinearLayout layoutspkl1;
    private LinearLayout layoutspkl2;
    private LinearLayout layoutspkl3;
    private LinearLayout layoutspkl4;
    private LinearLayout layoutspkl5;
    private TextView spklNamaKaryawan1, spklNamaKaryawan2, spklNamaKaryawan3, spklNamaKaryawan4, spklNamaKaryawan5;
    private EditText spklOvertimeDate1, spklOvertimeDate2, spklOvertimeDate3, spklOvertimeDate4, spklOvertimeDate5;
    private EditText spklStartTime1, spklStartTime2, spklStartTime3, spklStartTime4, spklStartTime5;
    private EditText spklFinishTime1, spklFinishTime2, spklFinishTime3, spklFinishTime4, spklFinishTime5;
    private EditText spklKeterangan1, spklKeterangan2, spklKeterangan3, spklKeterangan4, spklKeterangan5;

    private CustomProgressDialog progressDialog;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spkl_create);

        sharedPrefManager = SharedPrefManager.getInstance(this);
        progressDialog = new CustomProgressDialog(this);
        arrayListJo = new ArrayList<>();
        arrayListRequested = new ArrayList<>();
        arrayListKaryawan1 = new ArrayList<>();
        arrayListKaryawan2 = new ArrayList<>();
        arrayListKaryawan3 = new ArrayList<>();
        arrayListKaryawan4 = new ArrayList<>();
        arrayListKaryawan5 = new ArrayList<>();

        editSpklNumber = (EditText) findViewById(R.id.editSpklNumber);
        editSpklDesc = (EditText) findViewById(R.id.editSpklDesc);
        editSpklDate = (EditText) findViewById(R.id.editSpklDate);
        textViewJo = (TextView) findViewById(R.id.textViewJo);
        editSpklLokasi = (Spinner) findViewById(R.id.editSpklLokasi);
        editSpklDepartment = (Spinner) findViewById(R.id.editSpklDepartment);
        textViewRequested = (TextView) findViewById(R.id.textViewRequested);
        buttonBuat = (Button) findViewById(R.id.buttonBuat);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        buttonSpkl1 = (TextView) findViewById(R.id.buttonSpkl1);
        buttonSpkl2 = (TextView) findViewById(R.id.buttonSpkl2);
        buttonSpkl3 = (TextView) findViewById(R.id.buttonSpkl3);
        buttonSpkl4 = (TextView) findViewById(R.id.buttonSpkl4);
        buttonSpkl5 = (TextView) findViewById(R.id.buttonSpkl5);
        layoutspkl1 = (LinearLayout) findViewById(R.id.layoutspkl1);
        layoutspkl2 = (LinearLayout) findViewById(R.id.layoutspkl2);
        layoutspkl3 = (LinearLayout) findViewById(R.id.layoutspkl3);
        layoutspkl4 = (LinearLayout) findViewById(R.id.layoutspkl4);
        layoutspkl5 = (LinearLayout) findViewById(R.id.layoutspkl5);

        spklNamaKaryawan1 = (TextView) findViewById(R.id.spklKaryawan1);
        spklNamaKaryawan2 = (TextView) findViewById(R.id.spklKaryawan2);
        spklNamaKaryawan3 = (TextView) findViewById(R.id.spklKaryawan3);
        spklNamaKaryawan4 = (TextView) findViewById(R.id.spklKaryawan4);
        spklNamaKaryawan5 = (TextView) findViewById(R.id.spklKaryawan5);
        spklOvertimeDate1 = (EditText) findViewById(R.id.spklOvertimeDate1);
        spklOvertimeDate2 = (EditText) findViewById(R.id.spklOvertimeDate2);
        spklOvertimeDate3 = (EditText) findViewById(R.id.spklOvertimeDate3);
        spklOvertimeDate4 = (EditText) findViewById(R.id.spklOvertimeDate4);
        spklOvertimeDate5 = (EditText) findViewById(R.id.spklOvertimeDate5);
        spklStartTime1 = (EditText) findViewById(R.id.spklStartTime1);
        spklStartTime2 = (EditText) findViewById(R.id.spklStartTime2);
        spklStartTime3 = (EditText) findViewById(R.id.spklStartTime3);
        spklStartTime4 = (EditText) findViewById(R.id.spklStartTime4);
        spklStartTime5 = (EditText) findViewById(R.id.spklStartTime5);
        spklFinishTime1 = (EditText) findViewById(R.id.spklFinishTime1);
        spklFinishTime2 = (EditText) findViewById(R.id.spklFinishTime2);
        spklFinishTime3 = (EditText) findViewById(R.id.spklFinishTime3);
        spklFinishTime4 = (EditText) findViewById(R.id.spklFinishTime4);
        spklFinishTime5 = (EditText) findViewById(R.id.spklFinishTime5);
        spklKeterangan1 = (EditText) findViewById(R.id.spklKeterangan1);
        spklKeterangan2 = (EditText) findViewById(R.id.spklKeterangan2);
        spklKeterangan3 = (EditText) findViewById(R.id.spklKeterangan3);
        spklKeterangan4 = (EditText) findViewById(R.id.spklKeterangan4);
        spklKeterangan5 = (EditText) findViewById(R.id.spklKeterangan5);

        dialog = new Dialog(SpklCreateActivity.this);
        dialog.setContentView(R.layout.dialog_searchable_spinner);
//        dialog.setCancelable(false);
        dialog.getWindow().setLayout(900, 1500);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        buttonBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSpkl();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonSpkl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl1.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl1.setLayoutParams(params);
            }
        });
        buttonSpkl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl2.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl2.setLayoutParams(params);
            }
        });
        buttonSpkl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl3.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl3.setLayoutParams(params);
            }
        });
        buttonSpkl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl4.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl4.setLayoutParams(params);
            }
        });
        buttonSpkl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = layoutspkl5.getLayoutParams();
                if (params.height == 0)
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                else params.height = 0;
                layoutspkl5.setLayoutParams(params);
            }
        });

        editSpklDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SpklCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editSpklDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        textViewJo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListJo);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count<spklJobCodeText.length){
                            if (newAdapter.getItem(i).equals(spklJobCodeText[count])){
                                idJobOrder = count;
                                break;
                            } else count++;
                        }
                        textViewJo.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });

        textViewRequested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListRequested);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count< spklEmpName.length){
                            if (newAdapter.getItem(i).equals(spklEmpName[count])){
                                idRequested = count;
                                break;
                            } else count++;
                        }
                        textViewRequested.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });

        spklNamaKaryawan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKaryawan1);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count< spklEmpName.length){
                            if (newAdapter.getItem(i).equals(spklEmpName[count])){
                                idKaryawan1 = count;
                                break;
                            } else count++;
                        }
                        spklNamaKaryawan1.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });
        spklNamaKaryawan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKaryawan2);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count< spklEmpName.length){
                            if (newAdapter.getItem(i).equals(spklEmpName[count])){
                                idKaryawan2 = count;
                                break;
                            } else count++;
                        }
                        spklNamaKaryawan2.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });
        spklNamaKaryawan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKaryawan3);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count< spklEmpName.length){
                            if (newAdapter.getItem(i).equals(spklEmpName[count])){
                                idKaryawan3 = count;
                                break;
                            } else count++;
                        }
                        spklNamaKaryawan3.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });
        spklNamaKaryawan4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKaryawan4);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count< spklEmpName.length){
                            if (newAdapter.getItem(i).equals(spklEmpName[count])){
                                idKaryawan4 = count;
                                break;
                            } else count++;
                        }
                        spklNamaKaryawan4.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });
        spklNamaKaryawan5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                //initialize dialog variable
                EditText editTextSearch = dialog.findViewById(R.id.editTextSearch);
                ListView listViewSearch = dialog.findViewById(R.id.listViewSearch);
                ArrayAdapter<String> newAdapter = new ArrayAdapter<>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayListKaryawan5);
                listViewSearch.setAdapter(newAdapter);
                editTextSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        newAdapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int count = 0;
                        while (count< spklEmpName.length){
                            if (newAdapter.getItem(i).equals(spklEmpName[count])){
                                idKaryawan5 = count;
                                break;
                            } else count++;
                        }
                        spklNamaKaryawan5.setText(newAdapter.getItem(i));
                        dialog.dismiss();
                    }
                });
            }
        });

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        editSpklDate.setText("" + dateFormat.format(date));
        spklOvertimeDate1.setText("" + dateFormat.format(date));
        spklOvertimeDate2.setText("" + dateFormat.format(date));
        spklOvertimeDate3.setText("" + dateFormat.format(date));
        spklOvertimeDate4.setText("" + dateFormat.format(date));
        spklOvertimeDate5.setText("" + dateFormat.format(date));

        spklOvertimeDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SpklCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate1.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SpklCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate2.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SpklCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate3.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SpklCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate4.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });
        spklOvertimeDate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SpklCreateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        spklOvertimeDate5.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.setTitle("Select Overtime Date");
                datePickerDialog.show();
            }
        });
        spklStartTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklStartTime1.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklStartTime1.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklStartTime1.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklStartTime1.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklStartTime2.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklStartTime2.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklStartTime2.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklStartTime2.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklStartTime3.setText( selectedHour + ":" + selectedMinute);
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklStartTime3.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklStartTime3.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklStartTime3.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklStartTime3.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        spklStartTime4.setText( selectedHour + ":" + selectedMinute);
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklStartTime4.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklStartTime4.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklStartTime4.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklStartTime4.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklStartTime5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklStartTime5.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklStartTime5.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklStartTime5.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklStartTime5.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklFinishTime1.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklFinishTime1.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklFinishTime1.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklFinishTime1.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklFinishTime2.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklFinishTime2.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklFinishTime2.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklFinishTime2.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklFinishTime3.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklFinishTime3.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklFinishTime3.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklFinishTime3.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklFinishTime4.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklFinishTime4.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklFinishTime4.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklFinishTime4.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        spklFinishTime5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(SpklCreateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute<10) {
                            if (selectedHour<10)
                                spklFinishTime5.setText("0" + selectedHour + ":" + "0" + selectedMinute);
                            else spklFinishTime5.setText(selectedHour + ":" + "0" + selectedMinute);
                        } else {
                            if (selectedHour<10)
                                spklFinishTime5.setText("0" + selectedHour + ":" + selectedMinute);
                            else spklFinishTime5.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);//Yes 24 hour time
                timePicker.setTitle("Select Start Time");
                timePicker.show();
            }
        });
        
        getSpklData();
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
                        Intent logout = new Intent(SpklCreateActivity.this, LoginActivity.class);
                        startActivity(logout);
                        SpklCreateActivity.this.finish();
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
        Volley.newRequestQueue(SpklCreateActivity.this).add(request);
    }

    private void getSpklData() {
        arrayListJo.clear();
        StringRequest request = new StringRequest(Request.Method.GET, Config.DATA_URL_SPKL_CREATE_DATA_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 1) {
                                JSONArray jsonArray;

                                //data spkl number
                                spklNumber = jsonObject.getString("data spkl number");
                                editSpklNumber.setText("SPKL-XXX-" + spklNumber);

                                //data spkl jo
                                jsonArray = jsonObject.getJSONArray("data spkl jo");
                                spklJobCodeId = new String[jsonArray.length()];
                                spklJobCodeText = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    spklJobCodeId[i] = jsonArray.getJSONObject(i).getString("job_order_id");
                                    spklJobCodeText[i] = jsonArray.getJSONObject(i).getString("job_order_number") + " | " + jsonArray.getJSONObject(i).getString("job_order_description");
                                    arrayListJo.add(jsonArray.getJSONObject(i).getString("job_order_number") + " | " + jsonArray.getJSONObject(i).getString("job_order_description"));
                                }

                                //data workbase
                                jsonArray = jsonObject.getJSONArray("data spkl workbase");
                                String[] spklLokasi = new String[jsonArray.length()+1];
                                spklLokasiId = new String[jsonArray.length()+1];
                                spklLokasi[0] = "-- Pilih Lokasi Kerja --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    spklLokasi[i + 1] = jsonArray.getJSONObject(i).getString("company_workbase_name");
                                    spklLokasiId[i + 1] = jsonArray.getJSONObject(i).getString("company_workbase_id");
                                }
                                adapter = new ArrayAdapter<String>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, spklLokasi);
                                editSpklLokasi.setAdapter(adapter);

                                //data departemen
                                jsonArray = jsonObject.getJSONArray("data spkl departemen");
                                String[] spklDepartmen = new String[jsonArray.length()+1];
                                spklDepartmenId = new String[jsonArray.length()+1];
                                spklDepartmenCode = new String[jsonArray.length()+1];
                                spklDepartmen[0] = "-- Pilih Departemen --";
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    spklDepartmen[i + 1] = jsonArray.getJSONObject(i).getString("department_name");
                                    spklDepartmenId[i + 1] = jsonArray.getJSONObject(i).getString("department_id");
                                    spklDepartmenCode[i + 1] = jsonArray.getJSONObject(i).getString("department_code");
                                }
                                adapter = new ArrayAdapter<String>(SpklCreateActivity.this, android.R.layout.simple_spinner_dropdown_item, spklDepartmen);
                                editSpklDepartment.setAdapter(adapter);

                                //data employee
                                jsonArray = jsonObject.getJSONArray("data spkl employee");
                                spklRequestedId = new String[jsonArray.length()];
                                spklEmpName = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    spklRequestedId[i] = jsonArray.getJSONObject(i).getString("employee_id");
                                    spklEmpName[i] = jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name");
                                    arrayListRequested.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                    arrayListKaryawan1.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                    arrayListKaryawan2.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                    arrayListKaryawan3.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                    arrayListKaryawan4.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                    arrayListKaryawan5.add(jsonArray.getJSONObject(i).getString("fullname") + " - " + jsonArray.getJSONObject(i).getString("job_grade_name"));
                                }
                            } else Toast.makeText(SpklCreateActivity.this, "Failed load data", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SpklCreateActivity.this, "Error load data", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(SpklCreateActivity.this, "Network is broken", Toast.LENGTH_LONG).show();
                    }
                }){
        };
        Volley.newRequestQueue(SpklCreateActivity.this).add(request);
    }

    private void createSpkl() {
        if (idJobOrder < 0 || editSpklLokasi.getSelectedItemPosition() == 0 ||
                editSpklDepartment.getSelectedItemPosition() == 0 || editSpklDesc.getText().toString().matches("") ||
                editSpklDate.getText().toString().matches("") || /*spklNamaKaryawan1.getSelectedItemPosition() == 0 ||*/
                spklOvertimeDate1.getText().toString().matches("") || spklStartTime1.getText().toString().matches("") ||
                spklFinishTime1.getText().toString().matches("") || idKaryawan1 < 0) {
            Toast.makeText(SpklCreateActivity.this, "Failed, please check your data", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            final String spklJobCode = spklJobCodeId[idJobOrder];
            final String spklWorkLocation = spklLokasiId[editSpklLokasi.getSelectedItemPosition()];
            final String spklDepartment = spklDepartmenId[editSpklDepartment.getSelectedItemPosition()];
            final String spklWorkDescription = String.valueOf(editSpklDesc.getText());
            final String spklProposedDate = String.valueOf(editSpklDate.getText());
            final String spklRequested = spklRequestedId[idRequested];

            final String employeeId1 = spklRequestedId[idKaryawan1];
            final String employeeId2 = spklRequestedId[idKaryawan2];
            final String employeeId3 = spklRequestedId[idKaryawan3];
            final String employeeId4 = spklRequestedId[idKaryawan4];
            final String employeeId5 = spklRequestedId[idKaryawan5];

            final String spklNumberFinal = "SPKL-" + spklDepartmenCode[editSpklDepartment.getSelectedItemPosition()] + "-" + spklNumber;

            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL_SPKL_CREATE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if(status==1){
                            Toast.makeText(SpklCreateActivity.this, "Success", Toast.LENGTH_LONG).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(SpklCreateActivity.this, "Failed add data", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(SpklCreateActivity.this, "Error add data", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(SpklCreateActivity.this, "network is broken, please check your network", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param=new HashMap<>();
                    param.put("number", "" + spklNumberFinal);
                    param.put("proposedDate", "" + spklProposedDate);
                    param.put("workDescription", "" + spklWorkDescription);
                    param.put("workLocation", "" + spklWorkLocation);
                    param.put("jobOrder", "" + spklJobCode);
                    param.put("departmentId", "" + spklDepartment);
                    param.put("requestedId", "" + spklRequested);
                    param.put("createdBy", "" + sharedPrefManager.getKeyUserId());
                    param.put("employeeId1", "" + employeeId1);
                    param.put("overtimeDate1", "" + spklOvertimeDate1.getText().toString());
                    param.put("description1", "" + spklKeterangan1.getText().toString());
                    param.put("startTime1", "" + spklStartTime1.getText().toString());
                    param.put("finishTime1", "" + spklFinishTime1.getText().toString());
                    param.put("employeeId2", "" + employeeId2);
                    param.put("overtimeDate2", "" + spklOvertimeDate2.getText().toString());
                    param.put("description2", "" + spklKeterangan2.getText().toString());
                    param.put("startTime2", "" + spklStartTime2.getText().toString());
                    param.put("finishTime2", "" + spklFinishTime2.getText().toString());
                    param.put("employeeId3", "" + employeeId3);
                    param.put("overtimeDate3", "" + spklOvertimeDate3.getText().toString());
                    param.put("description3", "" + spklKeterangan3.getText().toString());
                    param.put("startTime3", "" + spklStartTime3.getText().toString());
                    param.put("finishTime3", "" + spklFinishTime3.getText().toString());
                    param.put("employeeId4", "" + employeeId4);
                    param.put("overtimeDate4", "" + spklOvertimeDate4.getText().toString());
                    param.put("description4", "" + spklKeterangan4.getText().toString());
                    param.put("startTime4", "" + spklStartTime4.getText().toString());
                    param.put("finishTime4", "" + spklFinishTime4.getText().toString());
                    param.put("employeeId5", "" + employeeId5);
                    param.put("overtimeDate5", "" + spklOvertimeDate5.getText().toString());
                    param.put("description5", "" + spklKeterangan5.getText().toString());
                    param.put("startTime5", "" + spklStartTime5.getText().toString());
                    param.put("finishTime5", "" + spklFinishTime5.getText().toString());
                    return param;
                }
            };
            Volley.newRequestQueue(this).add(request);
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, SpklActivity.class);
        startActivityForResult(intent,1);
        finish();
    }
}