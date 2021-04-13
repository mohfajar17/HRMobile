package com.example.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class RiwayatHistory implements Parcelable {
    private String history_date;
    private String status_history;
    private String employee_grade_name;
    private String notes;

    protected RiwayatHistory(Parcel in) {
        history_date = in.readString();
        status_history = in.readString();
        employee_grade_name = in.readString();
        notes = in.readString();
    }

    public RiwayatHistory(JSONObject jsonObject){
        try {
            this.history_date = jsonObject.getString("history_date");
            this.status_history = jsonObject.getString("status_history");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.notes = jsonObject.getString("notes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<RiwayatHistory> CREATOR = new Creator<RiwayatHistory>() {
        @Override
        public RiwayatHistory createFromParcel(Parcel in) {
            return new RiwayatHistory(in);
        }

        @Override
        public RiwayatHistory[] newArray(int size) {
            return new RiwayatHistory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(history_date);
        dest.writeString(status_history);
        dest.writeString(employee_grade_name);
        dest.writeString(notes);
    }

    public String getHistory_date() {
        return history_date;
    }

    public String getStatus_history() {
        return status_history;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getNotes() {
        return notes;
    }
}
