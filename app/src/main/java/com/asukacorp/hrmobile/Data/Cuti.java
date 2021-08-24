package com.asukacorp.hrmobile.Data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Cuti implements Parcelable {

    private String employee_leave_number;
    private String start_leave;
    private String date_leave;
    private String date_begin;
    private String date_end;
    private String proposed_date;
    private String date_extended;
    private String work_date;
    private String sisa_cuti;
    private String status;
    private String leave_category_name;
    private String is_approved;
    private String employee;
    private String address_leave;
    private String phone_leave;
    private String subtitute_on_leave;
    private String notes;
    private String approved_by;
    private String approved_date;
    private String approver;
    private String approver_date;
    private String processed_by;
    private String processed_date;

    protected Cuti(Parcel in) {
        employee_leave_number = in.readString();
        start_leave = in.readString();
        date_leave = in.readString();
        date_begin = in.readString();
        date_end = in.readString();
        proposed_date = in.readString();
        date_extended = in.readString();
        work_date = in.readString();
        sisa_cuti = in.readString();
        status = in.readString();
        leave_category_name = in.readString();
        is_approved = in.readString();
        employee = in.readString();
        address_leave = in.readString();
        phone_leave = in.readString();
        subtitute_on_leave = in.readString();
        notes = in.readString();
        approved_by = in.readString();
        approved_date = in.readString();
        approver = in.readString();
        approver_date = in.readString();
        processed_by = in.readString();
        processed_date = in.readString();
    }

    public Cuti(JSONObject jsonObject){
        try {
            this.employee_leave_number = jsonObject.getString("employee_leave_number");
            this.start_leave = jsonObject.getString("start_leave");
            this.date_leave = jsonObject.getString("date_leave");
            this.date_begin = jsonObject.getString("date_begin");
            this.date_end = jsonObject.getString("date_end");
            this.proposed_date = jsonObject.getString("proposed_date");
            this.date_extended = jsonObject.getString("date_extended");
            this.work_date = jsonObject.getString("work_date");
            this.sisa_cuti = jsonObject.getString("sisa_cuti");
            this.status = jsonObject.getString("status");
            this.leave_category_name = jsonObject.getString("leave_category_name");
            this.is_approved = jsonObject.getString("is_approved");
            this.employee = jsonObject.getString("employee");
            this.address_leave = jsonObject.getString("address_leave");
            this.phone_leave = jsonObject.getString("phone_leave");
            this.subtitute_on_leave = jsonObject.getString("subtitute_on_leave");
            this.notes = jsonObject.getString("notes");
            this.approved_by = jsonObject.getString("approved_by");
            this.approved_date = jsonObject.getString("approved_date");
            this.approver = jsonObject.getString("approver");
            this.approver_date = jsonObject.getString("approver_date");
            this.processed_by = jsonObject.getString("processed_by");
            this.processed_date = jsonObject.getString("processed_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Cuti> CREATOR = new Creator<Cuti>() {
        @Override
        public Cuti createFromParcel(Parcel in) {
            return new Cuti(in);
        }

        @Override
        public Cuti[] newArray(int size) {
            return new Cuti[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(employee_leave_number);
        dest.writeString(start_leave);
        dest.writeString(date_leave);
        dest.writeString(date_begin);
        dest.writeString(date_end);
        dest.writeString(proposed_date);
        dest.writeString(date_extended);
        dest.writeString(work_date);
        dest.writeString(sisa_cuti);
        dest.writeString(status);
        dest.writeString(leave_category_name);
        dest.writeString(is_approved);
        dest.writeString(employee);
        dest.writeString(address_leave);
        dest.writeString(phone_leave);
        dest.writeString(subtitute_on_leave);
        dest.writeString(notes);
        dest.writeString(approved_by);
        dest.writeString(approved_date);
        dest.writeString(approver);
        dest.writeString(approver_date);
        dest.writeString(processed_by);
        dest.writeString(processed_date);
    }

    public String getEmployee_leave_number() {
        return employee_leave_number;
    }

    public String getDate_leave() {
        return date_leave;
    }

    public String getProposed_date() {
        return proposed_date;
    }

    public String getDate_extended() {
        return date_extended;
    }

    public String getWork_date() {
        return work_date;
    }

    public String getSisa_cuti() {
        return sisa_cuti;
    }

    public String getStatus() {
        return status;
    }

    public String getLeave_category_name() {
        return leave_category_name;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public String getEmployee() {
        return employee;
    }

    public String getAddress_leave() {
        return address_leave;
    }

    public String getPhone_leave() {
        return phone_leave;
    }

    public String getSubtitute_on_leave() {
        return subtitute_on_leave;
    }

    public String getNotes() {
        return notes;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public String getApproved_date() {
        return approved_date;
    }

    public String getApprover() {
        return approver;
    }

    public String getApprover_date() {
        return approver_date;
    }

    public String getStart_leave() {
        return start_leave;
    }

    public String getProcessed_by() {
        return processed_by;
    }

    public String getProcessed_date() {
        return processed_date;
    }
}
