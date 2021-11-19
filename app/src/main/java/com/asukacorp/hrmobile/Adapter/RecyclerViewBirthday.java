package com.asukacorp.hrmobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.Data.Birthday;
import com.asukacorp.hrmobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewBirthday extends RecyclerView.Adapter<RecyclerViewBirthday.ViewHolder> {
    private final List<Birthday> mValues;
    private Context context;

    public RecyclerViewBirthday(List<Birthday> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public RecyclerViewBirthday.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_birthday, parent, false);
        return new RecyclerViewBirthday.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewBirthday.ViewHolder holder, final int position) {
        holder.textViewName.setText(mValues.get(position).getFullname());
        holder.textViewAge.setText(mValues.get(position).getAge() + " Tahun");
        if (mValues.get(position).getEmployee_file_name().equals("null"))
            holder.imageEmployee.setImageDrawable(ContextCompat.getDrawable(holder.imageEmployee.getContext(), R.drawable.akun));
        else Picasso.get().load(Config.DATA_URL_EMP_PHOTO+mValues.get(position).getEmployee_file_name()).into(holder.imageEmployee);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textViewName;
        public final TextView textViewAge;
        public final CircleImageView imageEmployee;
        public final LinearLayout layoutBirthday;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewAge = (TextView) itemView.findViewById(R.id.textViewAge);
            imageEmployee = (CircleImageView) itemView.findViewById(R.id.imageEmployee);
            layoutBirthday = (LinearLayout) itemView.findViewById(R.id.layoutBirthday);
        }
    }
}
