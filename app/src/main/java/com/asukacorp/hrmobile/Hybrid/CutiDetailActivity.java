package com.asukacorp.hrmobile.Hybrid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asukacorp.hrmobile.Data.Cuti;
import com.asukacorp.hrmobile.R;

public class CutiDetailActivity extends AppCompatActivity {

    private Cuti cutis;

    private TextView textLeaveNo;
    private TextView detailCutiTglPengajuan;
    private TextView detailCutiAlamat;
    private TextView detailCutiPhone;
    private TextView detailCutiStatus;
    private TextView detailCutiTglDisetujui;
    private TextView detailCutiSisa;
    private TextView detailCutiKaryawan;
    private TextView detailCutiKategori;
    private TextView detailCutiTglAwal;
    private TextView detailCutiTglAkhir;
    private TextView detailCutiTglKerja;
    private TextView detailCutiPengganti;
    private TextView detailCutiApproved;
    private TextView detailCutiNotes;
    private TextView detailCutiApprovedBy;
    private TextView detailCutiApprovedDate;
    private TextView detailCutiProcessedBy;
    private TextView detailCutiProcessedDate;
    private TextView detailCutiCreateBy;
    private TextView detailCutiCreateDate;
    private TextView detailCutiModifiedBy;
    private TextView detailCutiModifiedDate;
    private RelativeLayout detailLayoutCuta;
    private ImageView buttonBack;

    private int sisaCuti = 0;
    private ViewGroup.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuti_detail);

        Bundle bundle = getIntent().getExtras();
        cutis = bundle.getParcelable("detail");

        detailLayoutCuta = (RelativeLayout) findViewById(R.id.detailLayoutCuta);
        if (!cutis.getLeave_category_name().matches("Cuti Tahunan") || Integer.valueOf(cutis.getIs_approved())!=1){
            params = detailLayoutCuta.getLayoutParams();
            params.height = 0;
            detailLayoutCuta.setLayoutParams(params);
        }

        textLeaveNo = (TextView) findViewById(R.id.textLeaveNo);
        detailCutiTglPengajuan = (TextView) findViewById(R.id.detailCutiTglPengajuan);
        detailCutiAlamat = (TextView) findViewById(R.id.detailCutiAlamat);
        detailCutiPhone = (TextView) findViewById(R.id.detailCutiPhone);
        detailCutiStatus = (TextView) findViewById(R.id.detailCutiStatus);
        detailCutiTglDisetujui = (TextView) findViewById(R.id.detailCutiTglDisetujui);
        detailCutiSisa = (TextView) findViewById(R.id.detailCutiSisa);
        detailCutiKaryawan = (TextView) findViewById(R.id.detailCutiKaryawan);
        detailCutiKategori = (TextView) findViewById(R.id.detailCutiKategori);
        detailCutiTglAwal = (TextView) findViewById(R.id.detailCutiTglAwal);
        detailCutiTglAkhir = (TextView) findViewById(R.id.detailCutiTglAkhir);
        detailCutiTglKerja = (TextView) findViewById(R.id.detailCutiTglKerja);
        detailCutiPengganti = (TextView) findViewById(R.id.detailCutiPengganti);
        detailCutiApproved = (TextView) findViewById(R.id.detailCutiApproved);
        detailCutiNotes = (TextView) findViewById(R.id.detailCutiNotes);
        detailCutiApprovedBy = (TextView) findViewById(R.id.detailCutiApprovedBy);
        detailCutiApprovedDate = (TextView) findViewById(R.id.detailCutiApprovedDate);
        detailCutiProcessedBy = (TextView) findViewById(R.id.detailCutiProcessedBy);
        detailCutiProcessedDate = (TextView) findViewById(R.id.detailCutiProcessedDate);
        detailCutiCreateBy = (TextView) findViewById(R.id.detailCutiCreateBy);
        detailCutiCreateDate = (TextView) findViewById(R.id.detailCutiCreateDate);
        detailCutiModifiedBy = (TextView) findViewById(R.id.detailCutiModifiedBy);
        detailCutiModifiedDate = (TextView) findViewById(R.id.detailCutiModifiedDate);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);

        textLeaveNo.setText("Cuti Karyawan : " + cutis.getEmployee_leave_number());
        detailCutiTglPengajuan.setText(cutis.getProposed_date());
        detailCutiAlamat.setText(cutis.getAddress_leave());
        detailCutiPhone.setText(cutis.getPhone_leave());
        detailCutiStatus.setText(cutis.getStatus());
        detailCutiTglDisetujui.setText(cutis.getApproved_date());
        if (Integer.valueOf(cutis.getSisa_cuti())>0)
            sisaCuti = Integer.valueOf(cutis.getSisa_cuti());
        detailCutiSisa.setText(sisaCuti + " Hari");
        detailCutiKaryawan.setText(cutis.getEmployee());
        detailCutiKategori.setText(cutis.getLeave_category_name());
        detailCutiTglAwal.setText(cutis.getStart_leave());
        detailCutiTglAkhir.setText(cutis.getDate_leave());
        detailCutiTglKerja.setText(cutis.getWork_date());
        detailCutiPengganti.setText(cutis.getSubtitute_on_leave());
        if (Integer.valueOf(cutis.getIs_approved())==1)
            detailCutiApproved.setText("Ya");
        else if (Integer.valueOf(cutis.getIs_approved())==2)
            detailCutiApproved.setText("Tidak");
        else detailCutiApproved.setText("-");
        detailCutiNotes.setText(cutis.getNotes());
        detailCutiApprovedBy.setText(cutis.getApprover());
        detailCutiApprovedDate.setText(cutis.getApprover_date());
        detailCutiProcessedBy.setText(cutis.getProcessed_by());
        detailCutiProcessedDate.setText(cutis.getProcessed_date());
        detailCutiCreateBy.setText(cutis.getCreated_by());
        detailCutiCreateDate.setText(cutis.getCreated_date());
        detailCutiModifiedBy.setText(cutis.getModified_by());
        detailCutiModifiedDate.setText(cutis.getModified_date());

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}