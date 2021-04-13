package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.SpklDetail;
import com.example.hrmobile.R;

import java.util.List;

public class RecyclerViewSpklDetail extends RecyclerView.Adapter<RecyclerViewSpklDetail.ViewHolder> {

    private final List<SpklDetail> mValues;
    private Context context;

    public RecyclerViewSpklDetail(List<SpklDetail> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_spkl_detail_list, parent, false);
        return new RecyclerViewSpklDetail.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewSpklDetail.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.spklTextNo.setText("" + nomor);
        holder.spklTextKaryawan.setText(mValues.get(position).getFullname());
        holder.spklTextPosisi.setText(mValues.get(position).getJob_grade_name());
        holder.spklTextTglLembur.setText(mValues.get(position).getOvertime_date());
        holder.spklTextStartTime.setText(mValues.get(position).getStart_time());
        holder.spklTextFinishTime.setText(mValues.get(position).getFinish_time());
        holder.spklTextDescription.setText(mValues.get(position).getDescription());
        holder.spklTextApproval1.setText(mValues.get(position).getApproval_status1());
        holder.spklTextApproval2.setText(mValues.get(position).getApproval_status2());

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
        public final TextView spklTextNo;
        public final TextView spklTextKaryawan;
        public final TextView spklTextPosisi;
        public final TextView spklTextTglLembur;
        public final TextView spklTextStartTime;
        public final TextView spklTextFinishTime;
        public final TextView spklTextDescription;
        public final TextView spklTextApproval1;
        public final TextView spklTextApproval2;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            spklTextNo = (TextView) itemView.findViewById(R.id.spklTextNo);
            spklTextKaryawan = (TextView) itemView.findViewById(R.id.spklTextKaryawan);
            spklTextPosisi = (TextView) itemView.findViewById(R.id.spklTextPosisi);
            spklTextTglLembur = (TextView) itemView.findViewById(R.id.spklTextTglLembur);
            spklTextStartTime = (TextView) itemView.findViewById(R.id.spklTextStartTime);
            spklTextFinishTime = (TextView) itemView.findViewById(R.id.spklTextFinishTime);
            spklTextDescription = (TextView) itemView.findViewById(R.id.spklTextDescription);
            spklTextApproval1 = (TextView) itemView.findViewById(R.id.spklTextApproval1);
            spklTextApproval2 = (TextView) itemView.findViewById(R.id.spklTextApproval2);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}