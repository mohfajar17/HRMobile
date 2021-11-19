package com.asukacorp.hrmobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asukacorp.hrmobile.Data.Spkl;
import com.asukacorp.hrmobile.Menu.Spkl.SpklDetailActivity;
import com.asukacorp.hrmobile.R;

import java.util.List;

public class RecyclerViewSpkl extends RecyclerView.Adapter<RecyclerViewSpkl.ViewHolder> {
    private final List<Spkl> mValues;
    private final Context context;

    public RecyclerViewSpkl(List<Spkl> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_spkl_list, parent, false);
        return new RecyclerViewSpkl.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewSpkl.ViewHolder holder, final int position) {
        holder.pskTextSpklNumber.setText(""+mValues.get(position).getOvertime_workorder_number());
        holder.pskTextDate.setText(""+mValues.get(position).getProposed_date());
        holder.pskTextDescription.setText(""+mValues.get(position).getWork_description());
        holder.pskTextLocation.setText(""+mValues.get(position).getWork_location());
        holder.pskTextJobCode.setText(""+mValues.get(position).getJob_order_id());
        holder.pskTextFile.setText(""+mValues.get(position).getOvertime_file_name());
        holder.pskTextDepartment.setText(""+mValues.get(position).getDepartment_id());
        holder.pskTextApproval1.setText(""+mValues.get(position).getApproval1_by());
        holder.pskTextApproval2.setText(""+mValues.get(position).getApproval2_by());
        holder.pskTextVerifiedBy.setText(""+mValues.get(position).getVerified_by());

        if (position%2==0)
            holder.pskLayoutList.setBackgroundColor(context.getResources().getColor(R.color.white));
        else holder.pskLayoutList.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpklDetailActivity.class);
                intent.putExtra("detail", mValues.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView pskTextSpklNumber;
        public final TextView pskTextDate;
        public final TextView pskTextDescription;
        public final TextView pskTextLocation;
        public final TextView pskTextJobCode;
        public final TextView pskTextFile;
        public final TextView pskTextDepartment;
        public final TextView pskTextApproval1;
        public final TextView pskTextApproval2;
        public final TextView pskTextVerifiedBy;

        public final LinearLayout pskLayoutList;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            pskTextSpklNumber = (TextView) view.findViewById(R.id.pskTextSpklNumber);
            pskTextDate = (TextView) view.findViewById(R.id.pskTextDate);
            pskTextDescription = (TextView) view.findViewById(R.id.pskTextDescription);
            pskTextLocation = (TextView) view.findViewById(R.id.pskTextLocation);
            pskTextJobCode = (TextView) view.findViewById(R.id.pskTextJobCode);
            pskTextFile = (TextView) view.findViewById(R.id.pskTextFile);
            pskTextDepartment = (TextView) view.findViewById(R.id.pskTextDepartment);
            pskTextApproval1 = (TextView) view.findViewById(R.id.pskTextApproval1);
            pskTextApproval2 = (TextView) view.findViewById(R.id.pskTextApproval2);
            pskTextVerifiedBy = (TextView) view.findViewById(R.id.pskTextVerifiedBy);

            pskLayoutList = (LinearLayout) view.findViewById(R.id.pskLayoutList);
        }
    }
}
