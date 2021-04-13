package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.Tunjangan;
import com.example.hrmobile.R;

import java.text.NumberFormat;
import java.util.List;

public class RecyclerViewTunjangan extends RecyclerView.Adapter<RecyclerViewTunjangan.ViewHolder> {
    private final List<Tunjangan> mValues;
    private Context context;

    public RecyclerViewTunjangan(List<Tunjangan> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_tunjangan_list, parent, false);
        return new RecyclerViewTunjangan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewTunjangan.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.textNo.setText("" + nomor);
        holder.textNama.setText(mValues.get(position).getEmployee_grade_allowance_name());
        holder.textNilai.setText(mValues.get(position).getValue());
        holder.textHitungThr.setText(mValues.get(position).getCount_as_religious_holiday_allowance());
        holder.textAktif.setText(mValues.get(position).getIs_active());

        if (position%2==0)
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        else holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textNo;
        public final TextView textNama;
        public final TextView textNilai;
        public final TextView textHitungThr;
        public final TextView textAktif;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textNama = (TextView) itemView.findViewById(R.id.textNama);
            textNilai = (TextView) itemView.findViewById(R.id.textNilai);
            textHitungThr = (TextView) itemView.findViewById(R.id.textHitungThr);
            textAktif = (TextView) itemView.findViewById(R.id.textAktif);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
