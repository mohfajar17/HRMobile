package com.asukacorp.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Keluarga  implements Parcelable {
    private String family_name;
    private String birthday;
    private String family_type_name;
    private String gender;
    private String last_education;
    private String job;

    protected Keluarga (Parcel in) {
        family_name = in.readString();
        birthday = in.readString();
        family_type_name = in.readString();
        gender = in.readString();
        last_education = in.readString();
        job = in.readString();
    }

    public Keluarga (JSONObject jsonObject){
        try {
            this.family_name = jsonObject.getString("family_name");
            this.birthday = jsonObject.getString("birthday");
            this.family_type_name = jsonObject.getString("family_type_name");
            this.gender = jsonObject.getString("gender");
            this.last_education = jsonObject.getString("last_education");
            this.job = jsonObject.getString("job");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Keluarga> CREATOR = new Creator<Keluarga>() {
        @Override
        public Keluarga createFromParcel(Parcel in) {
            return new Keluarga(in);
        }

        @Override
        public Keluarga[] newArray(int size) {
            return new Keluarga[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(family_name);
        dest.writeString(birthday);
        dest.writeString(family_type_name);
        dest.writeString(gender);
        dest.writeString(last_education);
        dest.writeString(job);
    }

    public String getFamily_name() {
        return family_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFamily_type_name() {
        return family_type_name;
    }

    public String getGender() {
        return gender;
    }

    public String getLast_education() {
        return last_education;
    }

    public String getJob() {
        return job;
    }
}
