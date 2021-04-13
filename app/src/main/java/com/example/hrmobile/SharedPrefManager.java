package com.example.hrmobile;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private Context mContext;

    public static final String KEY_ISLOGGEDIN = "is_logged_in";
    public static final String PREF_NAME  = "ais_pref";
    public static final String KEY_USER_ID  = "user_id";
    public static final String KEY_EMPLOYEE_ID  = "employee_id";
    public static final String KEY_USER_NAME  = "user_name";
    public static final String KEY_FULLNAME  = "fullname";
    public static final String KEY_NICKNAME  = "nickname";
    public static final String KEY_EMPLOYEE_NUMBER = "employee_number";
    public static final String KEY_EMPLOYEE_GRADE_ID = "employee_grade_id";
    public static final String KEY_EMPLOYEE_GRADE_NAME = "employee_grade_name";
    public static final String KEY_JOB_GRADE_ID = "job_grade_id";
    public static final String KEY_JOB_GRADE_NAME = "job_grade_name";
    public static final String KEY_EMPLOYEE_STATUS_ID = "employee_status_id";
    public static final String KEY_EMPLOYEE_STATUS = "employee_status";
    public static final String KEY_WORKING_STATUS = "working_status";
    public static final String KEY_DEPARTMENT_ID = "department_id";
    public static final String KEY_DEPARTMENT_NAME = "department_name";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_PLACE_BIRTHDAY = "place_birthday";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_COMPANY_WORKBASE_ID = "company_workbase_id";
    public static final String KEY_COMPANY_WORKBASE_NAME = "company_workbase_name";
    public static final String KEY_RELIGION_NAME = "religion_name";
    public static final String KEY_MARITAL_STATUS_ID = "marital_status_id";
    public static final String KEY_MARITAL_STATUS_NAME = "marital_status_name";
    public static final String KEY_MOBILE_PHONE = "mobile_phone";
    public static final String KEY_EMAIL = "email1";
    public static final String KEY_EMPLOYEE_FILE_NAME = "employee_file_name";
    public static final String KEY_JOIN_DATE = "join_date";
    public static final String KEY_COME_OUT_DATE = "come_out_date";

    public SharedPrefManager(Context context){
        this.mContext = context;
    }

    public static SharedPrefManager getInstance(Context context){
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        return sharedPrefManager;
    }

    public boolean getIsLogin(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(KEY_ISLOGGEDIN) && sharedPreferences.getBoolean(KEY_ISLOGGEDIN,false);
    }

    public void setIslogin(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISLOGGEDIN, true);

        editor.apply();
    }

    public void setIsLogout(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void setUserId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_ID, string);
        editor.apply();
    }

    public void setEmployeeId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_ID, string);
        editor.apply();
    }

    public void setUserName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_NAME, string);
        editor.apply();
    }

    public void setFullname(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_FULLNAME, string);
        editor.apply();
    }

    public void setNickname(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_NICKNAME, string);
        editor.apply();
    }

    public void setEmployeeNumber(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_NUMBER, string);
        editor.apply();
    }

    public void setEmployeeGradeId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_GRADE_ID, string);
        editor.apply();
    }

    public void setEmployeeGradeName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_GRADE_NAME, string);
        editor.apply();
    }

    public void setJobGradeId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_JOB_GRADE_ID, string);
        editor.apply();
    }

    public void setJobGradeName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_JOB_GRADE_NAME, string);
        editor.apply();
    }

    public void setEmployeeStatusId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_STATUS_ID, string);
        editor.apply();
    }

    public void setEmployeeStatus(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_STATUS, string);
        editor.apply();
    }

    public void setWorkingStatus(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_WORKING_STATUS, string);
        editor.apply();
    }

    public void setDepartmentId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_DEPARTMENT_ID, string);
        editor.apply();
    }

    public void setDepartmentName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_DEPARTMENT_NAME, string);
        editor.apply();
    }

    public void setGender(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_GENDER, string);
        editor.apply();
    }

    public void setPlaceBirthday(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_PLACE_BIRTHDAY, string);
        editor.apply();
    }

    public void setBirthday(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_BIRTHDAY, string);
        editor.apply();
    }

    public void setAddress(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_ADDRESS, string);
        editor.apply();
    }

    public void setMobilePhone(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_MOBILE_PHONE, string);
        editor.apply();
    }

    public void setEmail(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL, string);
        editor.apply();
    }

    public void setEmployeeFileName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMPLOYEE_FILE_NAME, string);
        editor.apply();
    }

    public void setCity(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_CITY, string);
        editor.apply();
    }

    public void setState(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_STATE, string);
        editor.apply();
    }

    public void setCountry(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_COUNTRY, string);
        editor.apply();
    }

    public void setCompanyWorkbaseId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_COMPANY_WORKBASE_ID, string);
        editor.apply();
    }

    public void setCompanyWorkbaseName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_COMPANY_WORKBASE_NAME, string);
        editor.apply();
    }

    public void setReligionName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_RELIGION_NAME, string);
        editor.apply();
    }

    public void setMaritalStatusId(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_MARITAL_STATUS_ID, string);
        editor.apply();
    }

    public void setMaritalStatusName(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_MARITAL_STATUS_NAME, string);
        editor.apply();
    }

    public void setJoinDate(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_JOIN_DATE, string);
        editor.apply();
    }

    public void setComeOutDate(String string){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_COME_OUT_DATE, string);
        editor.apply();
    }

    public String getKeyUserId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID,null);
    }

    public String getKeyEmployeeId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_ID,null);
    }

    public String getKeyUserName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NAME,null);
    }

    public String getKeyFullname() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FULLNAME,null);
    }

    public String getKeyNickname() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NICKNAME,null);
    }

    public String getKeyEmployeeNumber() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_NUMBER,null);
    }

    public String getKeyEmployeeGradeId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_GRADE_ID,null);
    }

    public String getKeyEmployeeGradeName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_GRADE_NAME,null);
    }

    public String getKeyJobGradeId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_JOB_GRADE_ID,null);
    }

    public String getKeyJobGradeName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_JOB_GRADE_NAME,null);
    }

    public String getKeyEmployeeStatusId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_STATUS_ID,null);
    }

    public String getKeyEmployeeStatus() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_STATUS,null);
    }

    public String getKeyWorkingStatus() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WORKING_STATUS,null);
    }

    public String getKeyDepartmentId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DEPARTMENT_ID,null);
    }

    public String getKeyDepartmentName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DEPARTMENT_NAME,null);
    }

    public String getKeyBirthday() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_BIRTHDAY,null);
    }

    public String getKeyPlaceBirthday() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PLACE_BIRTHDAY,null);
    }

    public String getKeyGender() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_GENDER,null);
    }

    public String getKeyAddress() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADDRESS,null);
    }

    public String getKeyCity() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_CITY,null);
    }

    public String getKeyState() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_STATE,null);
    }

    public String getKeyCountry() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_COUNTRY,null);
    }

    public String getKeyCompanyWorkbaseId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_COMPANY_WORKBASE_ID,null);
    }

    public String getKeyCompanyWorkbaseName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_COMPANY_WORKBASE_NAME,null);
    }

    public String getKeyReligionName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_RELIGION_NAME,null);
    }

    public String getKeyMaritalStatusId() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MARITAL_STATUS_ID,null);
    }

    public String getKeyMaritalStatusName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MARITAL_STATUS_NAME,null);
    }

    public String getKeyMobilePhone() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MOBILE_PHONE,null);
    }

    public String getKeyEmail() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);
    }

    public String getKeyEmployeeFileName() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMPLOYEE_FILE_NAME,null);
    }

    public String getKeyJoinDate() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_JOIN_DATE,null);
    }

    public String getKeyComeOutDate() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_COME_OUT_DATE,null);
    }
}
