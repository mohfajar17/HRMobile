package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.Holiday;
import com.example.hrmobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerViewAgenda  extends RecyclerView.Adapter<RecyclerViewAgenda.ViewHolder> {
    private final List<Holiday> mValues;
    private Context context;

    public RecyclerViewAgenda(List<Holiday> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_agenda_list, parent, false);
        return new RecyclerViewAgenda.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAgenda.ViewHolder holder, final int position) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd");
            Date date = inputFormat.parse(mValues.get(position).getHoliday_date());
            holder.textDate.setText(outputFormat.format(date));
        } catch (ParseException e) {
            holder.textDate.setText("-");
            e.printStackTrace();
        }
        holder.textTypeName.setText(mValues.get(position).getHoliday_type_name());
        holder.textName.setText(mValues.get(position).getHoliday_name());

        if (mValues.get(position).getHoliday_type_name().matches("Asuka event reminder"))
            holder.textDate.setTextColor(context.getResources().getColor(R.color.colorAsukaGreen));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textDate;
        public final TextView textTypeName;
        public final TextView textName;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textDate = (TextView) itemView.findViewById(R.id.textDate);
            textTypeName = (TextView) itemView.findViewById(R.id.textTypeName);
            textName = (TextView) itemView.findViewById(R.id.textName);
        }
    }
}
