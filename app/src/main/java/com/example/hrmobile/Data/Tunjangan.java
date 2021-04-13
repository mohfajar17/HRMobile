package com.example.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Tunjangan implements Parcelable {
    private String employee_grade_allowance_name;
    private String value;
    private String count_as_religious_holiday_allowance;
    private String is_active;

    protected Tunjangan(Parcel in) {
        employee_grade_allowance_name = in.readString();
        value = in.readString();
        count_as_religious_holiday_allowance = in.readString();
        is_active = in.readString();
    }

    public Tunjangan(JSONObject jsonObject){
        try {
            this.employee_grade_allowance_name = jsonObject.getString("employee_grade_allowance_name");
            this.value = jsonObject.getString("value");
            this.count_as_religious_holiday_allowance = jsonObject.getString("count_as_religious_holiday_allowance");
            this.is_active = jsonObject.getString("is_active");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Tunjangan> CREATOR = new Creator<Tunjangan>() {
        @Override
        public Tunjangan createFromParcel(Parcel in) {
            return new Tunjangan(in);
        }

        @Override
        public Tunjangan[] newArray(int size) {
            return new Tunjangan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(employee_grade_allowance_name);
        dest.writeString(value);
        dest.writeString(count_as_religious_holiday_allowance);
        dest.writeString(is_active);
    }

    public String getEmployee_grade_allowance_name() {
        return employee_grade_allowance_name;
    }

    public String getValue() {
        return value;
    }

    public String getCount_as_religious_holiday_allowance() {
        return count_as_religious_holiday_allowance;
    }

    public String getIs_active() {
        return is_active;
    }
}
