package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.Pelatihan;
import com.example.hrmobile.R;

import java.util.List;

public class RecyclerViewPelatihan extends RecyclerView.Adapter<RecyclerViewPelatihan.ViewHolder> {

    private final List<Pelatihan> mValues;
    private Context context;

    public RecyclerViewPelatihan(List<Pelatihan> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_pelatihan_list, parent, false);
        return new RecyclerViewPelatihan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewPelatihan.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.textNo.setText("" + nomor);
        holder.textTglAwal.setText(mValues.get(position).getStart_date());
        holder.textTglAkhir.setText(mValues.get(position).getEnd_date());
        holder.textTrainingName.setText(mValues.get(position).getTraining_name());
        holder.textKeterangan.setText(mValues.get(position).getDescription());
        holder.textPlace.setText(mValues.get(position).getPlace());
        holder.textProvider.setText(mValues.get(position).getProvider());
        holder.textDurationDay.setText(mValues.get(position).getDuration_day());
        holder.textEvaluationDate.setText(mValues.get(position).getEvaluation_date());

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
        public final TextView textTglAwal;
        public final TextView textTglAkhir;
        public final TextView textTrainingName;
        public final TextView textKeterangan;
        public final TextView textPlace;
        public final TextView textProvider;
        public final TextView textDurationDay;
        public final TextView textEvaluationDate;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textTglAwal = (TextView) itemView.findViewById(R.id.textTglAwal);
            textTglAkhir = (TextView) itemView.findViewById(R.id.textTglAkhir);
            textTrainingName = (TextView) itemView.findViewById(R.id.textTrainingName);
            textKeterangan = (TextView) itemView.findViewById(R.id.textKeterangan);
            textPlace = (TextView) itemView.findViewById(R.id.textPlace);
            textProvider = (TextView) itemView.findViewById(R.id.textProvider);
            textDurationDay = (TextView) itemView.findViewById(R.id.textDurationDay);
            textEvaluationDate = (TextView) itemView.findViewById(R.id.textEvaluationDate);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
