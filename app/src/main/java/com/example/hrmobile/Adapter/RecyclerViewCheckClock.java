package com.example.hrmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hrmobile.Data.CheckClock;
import com.example.hrmobile.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCheckClock extends RecyclerView.Adapter<RecyclerViewCheckClock.ViewHolder> implements Filterable {

    private final List<CheckClock> mValues;
    private final List<CheckClock> values;
    private final Spinner spinnerSearch;
    private Context context;

    public RecyclerViewCheckClock(List<CheckClock> mValues, Spinner spinnerSearch, Context context) {
        this.mValues = mValues;
        this.spinnerSearch = spinnerSearch;
        this.context = context;
        values = new ArrayList<>(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_check_clock_list, parent, false);
        return new RecyclerViewCheckClock.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewCheckClock.ViewHolder holder, final int position) {
        holder.textJoNumber.setText(""+mValues.get(position).getJob_order());
        holder.textDailyWages.setText("Rp. "+mValues.get(position).getDaily_wages());
        holder.textCheckClockDate.setText(""+mValues.get(position).getDate_check_clock());
        holder.textDay.setText(""+mValues.get(position).getDay());
        holder.textCheckIn.setText(""+mValues.get(position).getCheck_in());
        holder.textCheckOut.setText(""+mValues.get(position).getCheck_out());
        holder.textStartSPKL.setText(""+mValues.get(position).getStart_time());
        holder.textEndSPKL.setText(""+mValues.get(position).getFinish_time());
        holder.textPanggilanDarurat.setText(""+mValues.get(position).getEmergency_call());
        holder.textNoLunch.setText(""+mValues.get(position).getNo_lunch());
        holder.textKetLibur.setText(""+mValues.get(position).getNote_for_shift());
        holder.textShift.setText(""+mValues.get(position).getShift_category());
        holder.textTypeJamKerja.setText(""+mValues.get(position).getType_work_hour());
        holder.textIjinTerlambat.setText(""+mValues.get(position).getPermission_late());

        if (position%2==0)
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        else holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CheckClock> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.add((CheckClock) values);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CheckClock item : values){
                    if (spinnerSearch.getSelectedItemPosition()==0){
                        if (item.getJob_order().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getDate_check_clock().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getDay().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getEmergency_call().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getNo_lunch().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getNote_for_shift().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getShift_category().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getType_work_hour().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        } else if (item.getPermission_late().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==1){
                        if (item.getJob_order().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==2){
                        if (item.getDate_check_clock().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==3){
                        if (item.getDay().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==4){
                        if (item.getEmergency_call().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==5){
                        if (item.getNo_lunch().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==6){
                        if (item.getNote_for_shift().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==7){
                        if (item.getShift_category().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==8){
                        if (item.getType_work_hour().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    } else if (spinnerSearch.getSelectedItemPosition()==9){
                        if (item.getPermission_late().toLowerCase().contains(filterPattern)){
                            filteredList.add(item);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mValues.clear();
            mValues.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView textJoNumber;
        public final TextView textDailyWages;
        public final TextView textCheckClockDate;
        public final TextView textDay;
        public final TextView textCheckIn;
        public final TextView textCheckOut;
        public final TextView textStartSPKL;
        public final TextView textEndSPKL;
        public final TextView textPanggilanDarurat;
        public final TextView textNoLunch;
        public final TextView textKetLibur;
        public final TextView textShift;
        public final TextView textTypeJamKerja;
        public final TextView textIjinTerlambat;

        public final LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            textJoNumber = (TextView) view.findViewById(R.id.textJoNumber);
            textDailyWages = (TextView) view.findViewById(R.id.textDailyWages);
            textCheckClockDate = (TextView) view.findViewById(R.id.textCheckClockDate);
            textDay = (TextView) view.findViewById(R.id.textDay);
            textCheckIn = (TextView) view.findViewById(R.id.textCheckIn);
            textCheckOut = (TextView) view.findViewById(R.id.textCheckOut);
            textStartSPKL = (TextView) view.findViewById(R.id.textStartSPKL);
            textEndSPKL = (TextView) view.findViewById(R.id.textEndSPKL);
            textPanggilanDarurat = (TextView) view.findViewById(R.id.textPanggilanDarurat);
            textNoLunch = (TextView) view.findViewById(R.id.textNoLunch);
            textKetLibur = (TextView) view.findViewById(R.id.textKetLibur);
            textShift = (TextView) view.findViewById(R.id.textShift);
            textTypeJamKerja = (TextView) view.findViewById(R.id.textTypeJamKerja);
            textIjinTerlambat = (TextView) view.findViewById(R.id.textIjinTerlambat);

            layout = (LinearLayout) view.findViewById(R.id.layout);
        }
    }
}
