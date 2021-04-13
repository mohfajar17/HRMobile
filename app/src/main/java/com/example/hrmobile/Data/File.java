package com.example.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class File implements Parcelable {
    private String category;
    private String file_description;
    private String file_name;
    private String file_location;

    protected File (Parcel in) {
        category = in.readString();
        file_description = in.readString();
        file_name = in.readString();
        file_location = in.readString();
    }

    public File (JSONObject jsonObject){
        try {
            this.category = jsonObject.getString("category");
            this.file_description = jsonObject.getString("file_description");
            this.file_name = jsonObject.getString("file_name");
            this.file_location = jsonObject.getString("file_location");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<File> CREATOR = new Creator<File>() {
        @Override
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(file_description);
        dest.writeString(file_name);
        dest.writeString(file_location);
    }

    public String getCategory() {
        return category;
    }

    public String getFile_description() {
        return file_description;
    }

    public String getFile_name() {
        return file_name;
    }

    public String getFile_location() {
        return file_location;
    }
}