package com.asukacorp.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Potongan implements Parcelable {
    private String deduction_name;
    private String value;
    private String is_active;
    private String adjustment;
    private String date_begin;
    private String date_end;

    protected Potongan(Parcel in) {
        deduction_name = in.readString();
        value = in.readString();
        is_active = in.readString();
        adjustment = in.readString();
        date_begin = in.readString();
        date_end = in.readString();
    }

    public Potongan(JSONObject jsonObject){
        try {
            this.deduction_name = jsonObject.getString("deduction_name");
            this.value = jsonObject.getString("value");
            this.is_active = jsonObject.getString("is_active");
            this.adjustment = jsonObject.getString("adjustment");
            this.date_begin = jsonObject.getString("date_begin");
            this.date_end = jsonObject.getString("date_end");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Potongan> CREATOR = new Creator<Potongan>() {
        @Override
        public Potongan createFromParcel(Parcel in) {
            return new Potongan(in);
        }

        @Override
        public Potongan[] newArray(int size) {
            return new Potongan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deduction_name);
        dest.writeString(value);
        dest.writeString(is_active);
        dest.writeString(adjustment);
        dest.writeString(date_begin);
        dest.writeString(date_end);
    }

    public String getDeduction_name() {
        return deduction_name;
    }

    public String getValue() {
        return value;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getAdjustment() {
        return adjustment;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public String getDate_end() {
        return date_end;
    }
}
