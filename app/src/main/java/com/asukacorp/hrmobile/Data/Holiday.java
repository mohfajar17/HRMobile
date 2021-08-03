package com.asukacorp.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Holiday implements Parcelable {
    private String holiday_id;
    private String holiday_type_name;
    private String holiday_name;
    private String holiday_date;
    private String holiday_duration;
    private String description;
    private String is_overtime;

    protected Holiday (Parcel in) {
        holiday_id = in.readString();
        holiday_type_name = in.readString();
        holiday_name = in.readString();
        holiday_date = in.readString();
        holiday_duration = in.readString();
        description = in.readString();
        is_overtime = in.readString();
    }

    public Holiday (JSONObject jsonObject){
        try {
            this.holiday_id = jsonObject.getString("holiday_id");
            this.holiday_type_name = jsonObject.getString("holiday_type_name");
            this.holiday_name = jsonObject.getString("holiday_name");
            this.holiday_date = jsonObject.getString("holiday_date");
            this.holiday_duration = jsonObject.getString("holiday_duration");
            this.description = jsonObject.getString("description");
            this.is_overtime = jsonObject.getString("is_overtime");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Holiday> CREATOR = new Creator<Holiday>() {
        @Override
        public Holiday createFromParcel(Parcel in) {
            return new Holiday(in);
        }

        @Override
        public Holiday[] newArray(int size) {
            return new Holiday[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(holiday_id);
        dest.writeString(holiday_type_name);
        dest.writeString(holiday_name);
        dest.writeString(holiday_date);
        dest.writeString(holiday_duration);
        dest.writeString(description);
        dest.writeString(is_overtime);
    }

    public String getHoliday_id() {
        return holiday_id;
    }

    public String getHoliday_type_name() {
        return holiday_type_name;
    }

    public String getHoliday_name() {
        return holiday_name;
    }

    public String getHoliday_date() {
        return holiday_date;
    }

    public String getHoliday_duration() {
        return holiday_duration;
    }

    public String getDescription() {
        return description;
    }

    public String getIs_overtime() {
        return is_overtime;
    }
}
