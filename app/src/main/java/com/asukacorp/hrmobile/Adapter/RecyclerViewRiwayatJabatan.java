package com.asukacorp.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asukacorp.hrmobile.Data.RiwayatJabatan;
import com.asukacorp.hrmobile.R;

import java.util.List;

public class RecyclerViewRiwayatJabatan extends RecyclerView.Adapter<RecyclerViewRiwayatJabatan.ViewHolder> {
    private final List<RiwayatJabatan> mValues;
    private Context context;

    public RecyclerViewRiwayatJabatan(List<RiwayatJabatan> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_riwayat_jabatan_list, parent, false);
        return new RecyclerViewRiwayatJabatan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewRiwayatJabatan.ViewHolder holder, final int position) {
        holder.textDescription.setText(mValues.get(position).getDescription());
        holder.textEmployeeGrade.setText(mValues.get(position).getEmployee_grade_name());
        holder.textIn.setText(mValues.get(position).getStart_date());
        holder.textOut.setText(mValues.get(position).getEnd_date());

        if (position % 2 == 0)
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        else holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textDescription;
        public final TextView textEmployeeGrade;
        public final TextView textIn;
        public final TextView textOut;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textDescription = (TextView) itemView.findViewById(R.id.textDescription);
            textEmployeeGrade = (TextView) itemView.findViewById(R.id.textEmployeeGrade);
            textIn = (TextView) itemView.findViewById(R.id.textIn);
            textOut = (TextView) itemView.findViewById(R.id.textOut);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
