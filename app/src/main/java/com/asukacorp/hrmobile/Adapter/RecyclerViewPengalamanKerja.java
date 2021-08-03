package com.asukacorp.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asukacorp.hrmobile.Data.PengalamanKerja;
import com.asukacorp.hrmobile.R;

import java.util.List;

public class RecyclerViewPengalamanKerja extends RecyclerView.Adapter<RecyclerViewPengalamanKerja.ViewHolder> {

    private final List<PengalamanKerja> mValues;
    private Context context;

    public RecyclerViewPengalamanKerja(List<PengalamanKerja> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_pengalaman_kerja_list, parent, false);
        return new RecyclerViewPengalamanKerja.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewPengalamanKerja.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.textNo.setText("Pengalaman Kerja " + nomor);
        holder.textTglAwal.setText(mValues.get(position).getStart_date());
        holder.textTglAkhir.setText(mValues.get(position).getEnd_date());
        holder.textKeterangan.setText(mValues.get(position).getExperience_description());
        holder.textPosition.setText(mValues.get(position).getExperience_position());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textNo;
        public final TextView textTglAwal;
        public final TextView textTglAkhir;
        public final TextView textKeterangan;
        public final TextView textPosition;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textTglAwal = (TextView) itemView.findViewById(R.id.textTglAwal);
            textTglAkhir = (TextView) itemView.findViewById(R.id.textTglAkhir);
            textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
            textPosition = (TextView) itemView.findViewById(R.id.textPosition);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
