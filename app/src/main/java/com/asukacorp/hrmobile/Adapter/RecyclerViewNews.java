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

import androidx.recyclerview.widget.RecyclerView;

import com.asukacorp.hrmobile.Config;
import com.asukacorp.hrmobile.Data.News;
import com.asukacorp.hrmobile.Home.NewsDetailActivity;
import com.asukacorp.hrmobile.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewNews extends RecyclerView.Adapter<RecyclerViewNews.ViewHolder> {
    private final List<News> mValues;
    private Context context;

    public RecyclerViewNews(List<News> mValues, Context context) {
        this.mValues = mValues;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home_list, parent, false);
        return new RecyclerViewNews.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewNews.ViewHolder holder, final int position) {
        holder.newsTitle.setText(mValues.get(position).getNews_title());
        holder.webNews.loadData(mValues.get(position).getNews_contents(), "text/html", null);
        Picasso.get().load(Config.DATA_URL_IMAGE+mValues.get(position).getImage_name()).resize(400, 600).into(holder.imageNews);

        if (holder.imageNews.getDrawable() == null) {
            holder.imageNews.setImageResource(R.drawable.no_image);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
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
        public final TextView newsTitle;
        public final ImageView imageNews;
        public final LinearLayout layoutNews;
        public final WebView webNews;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            imageNews = (ImageView) itemView.findViewById(R.id.imageNews);
            layoutNews = (LinearLayout) itemView.findViewById(R.id.layuotNews);
            webNews = (WebView) itemView.findViewById(R.id.webNews);
        }
    }
}
