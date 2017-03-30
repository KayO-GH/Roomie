package com.roomiegh.roomie.util;

/**
 * Created by Kwadwo Agyapon-Ntra on 30/07/2015.
 */
public class DBUtil {
    public static final String DB_NAME ="roomiedb.db";

    //INSTITUTION TABLE
    public static final String INSTITUTION_TABLE ="institution_tbl";
    public static final String COLUMN_INS_ID ="insID";
    public static final String COLUMN_INS_NAME = "insName";

    //TENANT EDUCATION TABLE
    public static final String TENANT_EDU_TABLE ="tenant_edu_tbl";
   // foreign key:  public static final String COLUMN_INS_ID ="insID";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_PROGRAMME ="programme";
    public static final String COLUMN_REF_NO ="refNo";

    //TENANT GUARDIAN TABLE
    public static final String TENANT_GUARDIAN_TABLE ="tenant_guardian_tbl";
    public static final String COLUMN_GUARD_ID ="guardID";
    public static final String COLUMN_GUARD_F_NAME = "guardFName";
    public static final String COLUMN_GUARD_L_NAME ="guardLName";
    public static final String COLUMN_GUARD_PHONE ="guardPhone";
    public static final String COLUMN_GUARD_PHONE_2 ="guardPhone2";
    public static final String COLUMN_GUARD_OCCUPATION ="guardOccupation";
    public static final String COLUMN_GUARD_ADDRESS ="guardAddress";
   //FOREIGN KEY:  public static final String COLUMN_REF_NO ="refNo";

    //TENANT PERSONAL TABLE
    public static final String TENANT_PERSONAL_TABLE ="tenant_personal_tbl";
    //public static final String COLUMN_REF_NO ="refNo";
    public static final String COLUMN_F_NAME = "fName";
    public static final String COLUMN_L_NAME ="lName";
    public static final String COLUMN_NATIONALITY ="nationality";
    public static final String COLUMN_GENDER ="gender";
    public static final String COLUMN_PHONE ="phone";
    public static final String COLUMN_PHONE_2 ="phone2";
    public static final String COLUMN_EMAIL ="email";
    public static final String COLUMN_PHOTO ="photo";
    public static final String COLUMN_DOB ="dob";

    //SIGNIN TABLE
    public static final String SIGN_IN_TABLE ="sign_in_tbl";
    public static final String COLUMN_SIGN_IN_ID = "signInID";
    //public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_SIGN_IN_TIME = "signInTime";
    public static final String COLUMN_SIGN_OUT_TIME = "signOutTime";


    public static final int DB_VERSION =1;

    //CREATING TABLES USING SQL CREATE SYNTAX
    public static final String CREATE_INSTITUTION_TABLE=
            "CREATE TABLE " + INSTITUTION_TABLE + " (" + COLUMN_INS_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_INS_NAME + " TEXT ) ";

    public static final String CREATE_TENANT_EDU_TABLE=
            "CREATE TABLE " + TENANT_EDU_TABLE + " (" + COLUMN_INS_ID +
                    " INTEGER, " + COLUMN_YEAR + " INTEGER, " +
                    COLUMN_PROGRAMME + " TEXT, " + COLUMN_REF_NO + " INTEGER ) ";

    public static final String CREATE_TENANT_GUARDIAN_TABLE=
            "CREATE TABLE " + TENANT_GUARDIAN_TABLE + " (" + COLUMN_GUARD_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_GUARD_F_NAME +
                    " TEXT, " + COLUMN_GUARD_L_NAME + " TEXT, " + COLUMN_GUARD_PHONE +
                    " TEXT, " + COLUMN_GUARD_PHONE_2 + " TEXT, " + COLUMN_GUARD_OCCUPATION +
                    " TEXT, " + COLUMN_GUARD_ADDRESS + " TEXT, " +
                    COLUMN_REF_NO + " INTEGER ) ";

    public static final String CREATE_TENANT_PERSONAL_TABLE=
            "CREATE TABLE " + TENANT_PERSONAL_TABLE + " ( " + COLUMN_REF_NO +
                    " INTEGER PRIMARY KEY, " + COLUMN_F_NAME + " TEXT, "+
                    COLUMN_L_NAME + " TEXT, "+ COLUMN_NATIONALITY +" TEXT, "+
                    COLUMN_GENDER +" TEXT, "+ COLUMN_PHONE +" TEXT, "+ COLUMN_PHONE_2 +
                    " TEXT, "+COLUMN_EMAIL+" TEXT UNIQUE, "+ COLUMN_PHOTO + " BLOB, "+
                    COLUMN_DOB +" TEXT ) ";

    public static final String CREATE_SIGN_IN_TABLE=
            "CREATE TABLE " + SIGN_IN_TABLE + " (" + COLUMN_SIGN_IN_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " + COLUMN_SIGN_IN_TIME + " TEXT, " +
                    COLUMN_SIGN_OUT_TIME + " TEXT ) ";
}
