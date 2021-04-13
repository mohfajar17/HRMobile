package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.RiwayatHistory;
import com.example.hrmobile.R;

import java.util.List;

public class RecyclerViewRiwayatHistory extends RecyclerView.Adapter<RecyclerViewRiwayatHistory.ViewHolder>{
    private final List<RiwayatHistory> mValues;
    private Context context;

    public RecyclerViewRiwayatHistory(List<RiwayatHistory> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_riwayat_history_list, parent, false);
        return new RecyclerViewRiwayatHistory.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewRiwayatHistory.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.textNo.setText("" + nomor);
        holder.textTgl.setText(mValues.get(position).getHistory_date());
        holder.textStatus.setText(mValues.get(position).getStatus_history());
        holder.textJenjangKaryawan.setText(mValues.get(position).getEmployee_grade_name());
        holder.textCatatan.setText(mValues.get(position).getNotes());

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
        public final TextView textTgl;
        public final TextView textStatus;
        public final TextView textJenjangKaryawan;
        public final TextView textCatatan;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textTgl = (TextView) itemView.findViewById(R.id.textTgl);
            textStatus = (TextView) itemView.findViewById(R.id.textStatus);
            textJenjangKaryawan = (TextView) itemView.findViewById(R.id.textJenjangKaryawan);
            textCatatan = (TextView) itemView.findViewById(R.id.textCatatan);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
