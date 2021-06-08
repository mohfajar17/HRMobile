package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.Cuti;
import com.example.hrmobile.R;

import java.util.List;

public class RecyclerViewCuti extends RecyclerView.Adapter<RecyclerViewCuti.ViewHolder> {
    private final List<Cuti> mValues;
    private Context context;

    public RecyclerViewCuti(List<Cuti> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_data_cuti_list, parent, false);
        return new RecyclerViewCuti.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewCuti.ViewHolder holder, final int position) {
        int nomor = position + 1;
        holder.textNo.setText("" + nomor);
        holder.textLeaveNo.setText(mValues.get(position).getEmployee_leave_number());
        holder.textTglCuti.setText(mValues.get(position).getDate_leave());
        holder.textTglPengajuan.setText(mValues.get(position).getProposed_date());
        holder.textTglKadarluarsa.setText(mValues.get(position).getDate_extended());
        holder.textStatus.setText(mValues.get(position).getStatus());
        holder.textKategoriCuti.setText(mValues.get(position).getLeave_category_name());

        if (Integer.valueOf(mValues.get(position).getIs_approved()) == 1){
            holder.textDisetujui.setText("Ya");
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.cyan));
        } else {
            holder.textDisetujui.setText("Belum Disetujui");
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textNo;
        public final TextView textLeaveNo;
        public final TextView textTglCuti;
        public final TextView textTglPengajuan;
        public final TextView textTglKadarluarsa;
        public final TextView textStatus;
        public final TextView textKategoriCuti;
        public final TextView textDisetujui;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textLeaveNo = (TextView) itemView.findViewById(R.id.textLeaveNo);
            textTglCuti = (TextView) itemView.findViewById(R.id.textTglCuti);
            textTglPengajuan = (TextView) itemView.findViewById(R.id.textTglPengajuan);
            textTglKadarluarsa = (TextView) itemView.findViewById(R.id.textTglKadarluarsa);
            textStatus = (TextView) itemView.findViewById(R.id.textStatus);
            textKategoriCuti = (TextView) itemView.findViewById(R.id.textKategoriCuti);
            textDisetujui = (TextView) itemView.findViewById(R.id.textDisetujui);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
