package com.asukacorp.hrmobile.Hybrid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuti_detail);

        Bundle bundle = getIntent().getExtras();
        cutis = bundle.getParcelable("detail");

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

        textLeaveNo.setText("Cuti Karyawan : " + cutis.getEmployee_leave_number());
        detailCutiTglPengajuan.setText(cutis.getProposed_date());
        detailCutiAlamat.setText(cutis.getAddress_leave());
        detailCutiPhone.setText(cutis.getPhone_leave());
        detailCutiStatus.setText(cutis.getStatus());
        detailCutiTglDisetujui.setText(cutis.getApprover_date());
        detailCutiSisa.setText(cutis.getSisa_cuti() + " Hari");
        detailCutiKaryawan.setText(cutis.getEmployee());
        detailCutiKategori.setText(cutis.getLeave_category_name());
        detailCutiTglAwal.setText(cutis.getStart_leave());
        detailCutiTglAkhir.setText(cutis.getDate_leave());
        detailCutiTglKerja.setText(cutis.getWork_date());
        detailCutiPengganti.setText(cutis.getSubtitute_on_leave());
        if (Integer.valueOf(cutis.getIs_approved())>0)
            detailCutiApproved.setText("Ya");
        else detailCutiApproved.setText("Tidak");
        detailCutiNotes.setText(cutis.getNotes());
        detailCutiApprovedBy.setText(cutis.getApproved_by());
        detailCutiApprovedDate.setText(cutis.getApproved_date());
        detailCutiProcessedBy.setText(cutis.getProcessed_by());
        detailCutiProcessedDate.setText(cutis.getProcessed_date());
    }
}