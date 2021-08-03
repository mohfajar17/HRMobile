package com.asukacorp.hrmobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.asukacorp.hrmobile.Data.File;
import com.asukacorp.hrmobile.R;

import java.util.List;

public class RecyclerViewFile extends RecyclerView.Adapter<RecyclerViewFile.ViewHolder> {

    private final List<File> mValues;
    private Context context;

    public RecyclerViewFile(List<File> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_file_list, parent, false);
        return new RecyclerViewFile.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewFile.ViewHolder holder, final int position) {
        int nomor = position + 1;
        holder.textNo.setText("File " + nomor);
        holder.textCategory.setText(mValues.get(position).getCategory());
        holder.textFileDescription.setText(mValues.get(position).getFile_description());
        holder.textFileName.setText(mValues.get(position).getFile_name());
        holder.imageDownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://ais.asukaindonesia.co.id/protected/attachments/emp/"+ mValues.get(position).getFile_location() + "/" + mValues.get(position).getFile_name());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                context.startActivity(launchBrowser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textNo;
        public final TextView textCategory;
        public final TextView textFileDescription;
        public final TextView textFileName;
        public final ImageView imageDownloadFile;

        public final LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            textNo = (TextView) itemView.findViewById(R.id.textNo);
            textCategory = (TextView) itemView.findViewById(R.id.textCategory);
            textFileDescription = (TextView) itemView.findViewById(R.id.textFileDescription);
            textFileName = (TextView) itemView.findViewById(R.id.textFileName);
            imageDownloadFile = (ImageView) itemView.findViewById(R.id.imageDownloadFile);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
        }
    }
}
