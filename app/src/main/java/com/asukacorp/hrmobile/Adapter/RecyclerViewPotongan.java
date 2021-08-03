package com.asukacorp.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asukacorp.hrmobile.Data.Potongan;
import com.asukacorp.hrmobile.R;

import java.util.List;

public class RecyclerViewPotongan extends RecyclerView.Adapter<RecyclerViewPotongan.ViewHolder> {
    private final List<Potongan> mValues;
    private Context context;

    public RecyclerViewPotongan(List<Potongan> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_potongan_list, parent, false);
        return new RecyclerViewPotongan.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewPotongan.ViewHolder holder, final int position) {
        int nomor = position+1;
        holder.textNo.setText("" + nomor);
        holder.textPotongan.setText(mValues.get(position).getDeduction_name());
        holder.textNilai.setText(mValues.get(position).getValue());
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
        public final TextView textPotongan;
        public final TextView textNilai;
        public final TextView textAktif;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textPotongan = (TextView) itemView.findViewById(R.id.textPotongan);
            textNilai = (TextView) itemView.findViewById(R.id.textNilai);
            textAktif = (TextView) itemView.findViewById(R.id.textAktif);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
