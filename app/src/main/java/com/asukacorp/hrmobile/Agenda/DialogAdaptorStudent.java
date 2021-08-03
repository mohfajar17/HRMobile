package com.asukacorp.hrmobile.Agenda;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asukacorp.hrmobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class DialogAdaptorStudent extends BaseAdapter {
    private Activity context;
    private ArrayList<com.asukacorp.hrmobile.Agenda.Dialogpojo> alCustom;


    public DialogAdaptorStudent(Activity context, ArrayList<com.asukacorp.hrmobile.Agenda.Dialogpojo> alCustom) {
        this.context = context;
        this.alCustom = alCustom;

    }

    @Override
    public int getCount() {
        return alCustom.size();

    }

    @Override
    public Object getItem(int i) {
        return alCustom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        final View listViewItem = inflater.inflate(R.layout.row_addapt, null, true);

        TextView tvTitle=(TextView)listViewItem.findViewById(R.id.tv_name);
        TextView tvSubject=(TextView)listViewItem.findViewById(R.id.tv_type);
        TextView tvDuedate=(TextView)listViewItem.findViewById(R.id.tv_desc);

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM yyyy");

        try {
            Date date = input.parse(alCustom.get(position).getTitles());    // parse input
            tvTitle.setText(output.format(date));                           // format output
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvSubject.setText(alCustom.get(position).getSubjects());
        tvDuedate.setText("Due Date : "+alCustom.get(position).getDuedates());

        return listViewItem;
    }

}

