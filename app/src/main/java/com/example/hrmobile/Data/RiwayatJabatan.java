package com.example.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class RiwayatJabatan implements Parcelable {
    private String description;
    private String employee_grade_name;
    private String start_date;
    private String end_date;

    protected RiwayatJabatan(Parcel in) {
        description = in.readString();
        employee_grade_name = in.readString();
        start_date = in.readString();
        end_date = in.readString();
    }

    public RiwayatJabatan(JSONObject jsonObject){
        try {
            this.description = jsonObject.getString("description");
            this.employee_grade_name = jsonObject.getString("employee_grade_name");
            this.start_date = jsonObject.getString("start_date");
            this.end_date = jsonObject.getString("end_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<RiwayatJabatan> CREATOR = new Creator<RiwayatJabatan>() {
        @Override
        public RiwayatJabatan createFromParcel(Parcel in) {
            return new RiwayatJabatan(in);
        }

        @Override
        public RiwayatJabatan[] newArray(int size) {
            return new RiwayatJabatan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(employee_grade_name);
        dest.writeString(start_date);
        dest.writeString(end_date);
    }

    public String getDescription() {
        return description;
    }

    public String getEmployee_grade_name() {
        return employee_grade_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
