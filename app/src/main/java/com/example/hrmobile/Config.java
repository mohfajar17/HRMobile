package com.example.hrmobile;

public class Config {
//    public static final String DATA_URL = "http://192.168.43.183/hr_mobile/";   //IP thetering hp
//    public static final String DATA_URL = "http://192.168.88.212/hr_mobile/";   //IP asuka lt 3
//    public static final String DATA_URL = "http://192.168.0.109/hr_mobile/";   //IP home wifi
    public static final String DATA_URL = "https://ais.asukaindonesia.co.id/mobile/";   //IP server

    public static final String DATA_URL_AIS = DATA_URL + "apiHrMobile.php?apicall=";
    public static final String DATA_URL_LOGIN = DATA_URL_AIS +"login";
    public static final String DATA_URL_SIGNUP = DATA_URL_AIS +"signup";
    public static final String DATA_URL_SIGNUP_EMP_LIST = DATA_URL_AIS +"getDataSignup";

    //main menu
    public static final String DATA_URL_LEAVE_MONEY_BOX = DATA_URL_AIS +"employeeLeaveMoneybox";
    public static final String DATA_URL_USER_ENABLE = DATA_URL_AIS +"getIsMobileActive";

    //image url
    public static final String DATA_URL_PHOTO_PROFILE = "https://ais.asukaindonesia.co.id/protected/attachments/employeePhoto/";
    public static final String DATA_URL_IMAGE = "https://ais.asukaindonesia.co.id/protected/attachments/news/";

    //menu agenda
    public static final String DATA_URL_CALENDAR = DATA_URL_AIS +"getHoliday";
    public static final String DATA_URL_CALENDAR_DETAIL = DATA_URL_AIS +"getHolidayDetail";

    //menu menu
    public static final String DATA_URL_SPKL_LIST = DATA_URL_AIS +"getSpkl";
    public static final String DATA_URL_SPKL_DETAIL_LIST = DATA_URL_AIS +"getSpklDetail";
    public static final String DATA_URL_SPKL_CREATE_DATA_LIST = DATA_URL_AIS +"getDataCreateSpkl";
    public static final String DATA_URL_SPKL_CREATE = DATA_URL_AIS +"createSpkl";
    public static final String DATA_URL_CHECK_CLOCK_LIST = DATA_URL_AIS +"getCheckClock";
    public static final String DATA_URL_CUTI_CREATE_DATA_LIST = DATA_URL_AIS +"getDataCreateCuti";
    public static final String DATA_URL_CUTI_CREATE = DATA_URL_AIS +"createCuti";
    public static final String DATA_URL_SLIP_GAJI = DATA_URL_AIS +"getSlipGaji";
    public static final String DATA_URL_SLIP_GAJI_WEB = DATA_URL + "slipGaji.php?employee_id=";
    public static final String DATA_URL_SLIP_GAJI_WEB_COBA = "https://ais.asukaindonesia.co.id/index.php?r=hr/employee/print2&id=";

    //menu profile
    public static final String DATA_URL_DATA_KELUARGA_LIST = DATA_URL_AIS +"getFamily";
    public static final String DATA_URL_RIWAYAT_JABATAN_LIST = DATA_URL_AIS +"getRiwayatJabatan";
    public static final String DATA_URL_RIWAYAT_PENDIDIKAN_LIST = DATA_URL_AIS +"getRiwayatPendidikan";
    public static final String DATA_URL_PENGALAMAN_KERJA_LIST = DATA_URL_AIS +"getPengalamanKerja";
    public static final String DATA_URL_PELATIHAN_LIST = DATA_URL_AIS +"getPelatihan";
    public static final String DATA_URL_FILE_LIST = DATA_URL_AIS +"getFile";
    public static final String DATA_URL_POTONGAN_TUNJANGAN_LIST = DATA_URL_AIS +"getPotonganTunjangan";
    public static final String DATA_URL_CUTI_LIST = DATA_URL_AIS +"getDataCuti";

    //add or update data
    public static final String DATA_URL_UPDATE_PASSWORD = DATA_URL_AIS +"editPassword";
    public static final String DATA_URL_UPDATE_PERSONAL = DATA_URL_AIS +"editPersonalData";
}