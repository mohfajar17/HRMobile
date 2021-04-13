package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.Keluarga;
import com.example.hrmobile.R;

import java.util.List;

public class RecyclerViewKeluarga extends RecyclerView.Adapter<RecyclerViewKeluarga.ViewHolder> {

    private final List<Keluarga> mValues;
    private Context context;

    public RecyclerViewKeluarga(List<Keluarga> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_data_keluarga_list, parent, false);
        return new RecyclerViewKeluarga.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewKeluarga.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.textNo.setText("Keluarga " + nomor);
        holder.textNama.setText(mValues.get(position).getFamily_name());
        holder.textTanggalLahir.setText(mValues.get(position).getBirthday());
        holder.textHubunganKeluarga.setText(mValues.get(position).getFamily_type_name());
        holder.textJenisKelamin.setText(mValues.get(position).getGender());
        holder.textPendidikanTerakhir.setText(mValues.get(position).getLast_education());
        holder.textPekerjaan.setText(mValues.get(position).getJob());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textNo;
        public final TextView textNama;
        public final TextView textTanggalLahir;
        public final TextView textHubunganKeluarga;
        public final TextView textJenisKelamin;
        public final TextView textPendidikanTerakhir;
        public final TextView textPekerjaan;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textNama = (TextView) itemView.findViewById(R.id.textNama);
            textTanggalLahir = (TextView) itemView.findViewById(R.id.textTanggalLahir);
            textHubunganKeluarga = (TextView) itemView.findViewById(R.id.textHubunganKeluarga);
            textJenisKelamin = (TextView) itemView.findViewById(R.id.textJenisKelamin);
            textPendidikanTerakhir = (TextView) itemView.findViewById(R.id.textPendidikanTerakhir);
            textPekerjaan = (TextView) itemView.findViewById(R.id.textPekerjaan);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}