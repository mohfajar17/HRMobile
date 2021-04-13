package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.Pendidikan;
import com.example.hrmobile.R;

import java.util.List;

public class RecyclerViewPendidikan  extends RecyclerView.Adapter<RecyclerViewPendidikan.ViewHolder> {

    private final List<Pendidikan> mValues;
    private Context context;

    public RecyclerViewPendidikan(List<Pendidikan> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_riwayat_pendidikan_list, parent, false);
        return new RecyclerViewPendidikan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewPendidikan.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.textNo.setText("Pendidikan " + nomor);
        holder.textPendidikan.setText(mValues.get(position).getEducation_name());
        holder.textNamaSekolah.setText(mValues.get(position).getSchool_name());
        holder.textJurusan.setText(mValues.get(position).getMajor());
        holder.textMasuk.setText(mValues.get(position).getEducation_start());
        holder.textLulus.setText(mValues.get(position).getEducation_end());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textNo;
        public final TextView textPendidikan;
        public final TextView textNamaSekolah;
        public final TextView textJurusan;
        public final TextView textMasuk;
        public final TextView textLulus;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textPendidikan = (TextView) itemView.findViewById(R.id.textPendidikan);
            textNamaSekolah = (TextView) itemView.findViewById(R.id.textNamaSekolah);
            textJurusan = (TextView) itemView.findViewById(R.id.textJurusan);
            textMasuk = (TextView) itemView.findViewById(R.id.textMasuk);
            textLulus = (TextView) itemView.findViewById(R.id.textLulus);
        }
    }
}
