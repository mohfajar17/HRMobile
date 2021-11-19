package com.asukacorp.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Birthday implements Parcelable {

    private int employee_id;
    private String fullname;
    private String place_birthday;
    private String birthday;
    private String age;
    private String employee_file_name;

    protected Birthday(Parcel in) {
        employee_id = in.readInt();
        fullname = in.readString();
        place_birthday = in.readString();
        birthday = in.readString();
        age = in.readString();
        employee_file_name = in.readString();
    }

    public Birthday(JSONObject jsonObject){
        try {
            this.employee_id = jsonObject.getInt("employee_id");
            this.fullname = jsonObject.getString("fullname");
            this.place_birthday = jsonObject.getString("place_birthday");
            this.birthday = jsonObject.getString("birthday");
            this.age = jsonObject.getString("age");
            this.employee_file_name = jsonObject.getString("employee_file_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Birthday> CREATOR = new Creator<Birthday>() {
        @Override
        public Birthday createFromParcel(Parcel in) {
            return new Birthday(in);
        }

        @Override
        public Birthday[] newArray(int size) {
            return new Birthday[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(employee_id);
        parcel.writeString(fullname);
        parcel.writeString(place_birthday);
        parcel.writeString(birthday);
        parcel.writeString(age);
        parcel.writeString(employee_file_name);
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPlace_birthday() {
        return place_birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getAge() {
        return age;
    }

    public String getEmployee_file_name() {
        return employee_file_name;
    }
}
