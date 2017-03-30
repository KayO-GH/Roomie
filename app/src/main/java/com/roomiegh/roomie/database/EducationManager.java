package com.roomiegh.roomie.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.roomiegh.roomie.models.Education;
import com.roomiegh.roomie.util.DBUtil;

import java.util.ArrayList;

/**
 * Created by Kwadwo Agyapon-Ntra on 01/08/2015.
 */
public class EducationManager {
    private RoomieDbHelper dBHelper;
    private Context context;
    private SQLiteDatabase db;
    private boolean isOk = false;
    private Education education;
    private ArrayList<Education> allEducations;
    private ContentValues cv;
    private Cursor cr;

    private int iInsID, iYear, iProgramme, iRefNo;

    private String whereConditions;
    private String[] whereArgs;
    private long  rowsEffected;

    private String[] columns = new String[]{
            DBUtil.COLUMN_INS_ID, DBUtil.COLUMN_YEAR,
            DBUtil.COLUMN_PROGRAMME, DBUtil.COLUMN_REF_NO
    };


    public EducationManager(Context context) {
        this.context = context;
        dBHelper = new RoomieDbHelper(context);//creates tables for us
        // education = new Education();
        allEducations = new ArrayList<>();
        cv = new ContentValues();

    }

    private void openForRead(){
        db = dBHelper.getReadableDatabase();
    }

    private void openForWrite(){
        db =dBHelper.getWritableDatabase();
    }

    private void close(){
        db.close();
        dBHelper.close();
    }

    public boolean createEducation(Education education){
        openForWrite();
        cv.put(DBUtil.COLUMN_INS_ID, education.getInsID());
        cv.put(DBUtil.COLUMN_YEAR, education.getYear());
        cv.put(DBUtil.COLUMN_PROGRAMME, education.getProgramme());
        cv.put(DBUtil.COLUMN_REF_NO, education.getRefNo());
        

        rowsEffected = db.insert(DBUtil.TENANT_EDU_TABLE, null, cv);
        if (rowsEffected!=-1){
            isOk=true;
        }
        close();
        return isOk;
    }

    public boolean updateEducation(Education education){
        /*
        * update education_tbl where title=?
        */
        openForWrite();
        whereConditions = DBUtil.COLUMN_REF_NO+"=?";
        whereArgs = new String[]{String.valueOf(education.getRefNo())};

        cv.put(DBUtil.COLUMN_INS_ID, education.getInsID());
        cv.put(DBUtil.COLUMN_YEAR, education.getYear());
        cv.put(DBUtil.COLUMN_PROGRAMME, education.getProgramme());
        cv.put(DBUtil.COLUMN_REF_NO, education.getRefNo());

        rowsEffected = db.update(DBUtil.TENANT_EDU_TABLE, cv, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }

        close();
        return isOk;
    }

    public boolean deleteEducation(Education education){
        openForWrite();
        whereConditions = DBUtil.COLUMN_REF_NO+"=?";
        whereArgs = new String[]{String.valueOf(education.getRefNo())};

        rowsEffected = db.delete(DBUtil.TENANT_EDU_TABLE, whereConditions, whereArgs);
        if (rowsEffected!=0){
            isOk=true;
        }
        close();
        return isOk;
    }

    public Education getEducation(Education education){
        openForRead();
        whereConditions = DBUtil.COLUMN_REF_NO+" like '%" + education.getRefNo() + "%'" ;
        //add whereArgs and use in query

        cr = db.query(DBUtil.TENANT_EDU_TABLE, columns, whereConditions, null, null, null, null);
        iInsID = cr.getColumnIndex(DBUtil.COLUMN_INS_ID);
        iYear = cr.getColumnIndex(DBUtil.COLUMN_YEAR);
        iProgramme = cr.getColumnIndex(DBUtil.COLUMN_PROGRAMME);
        iRefNo = cr.getColumnIndex(DBUtil.COLUMN_REF_NO);

        if (cr!=null){
            cr.moveToFirst();
            this.education = new Education(
                    cr.getInt(iYear),cr.getInt(iRefNo),
                    cr.getInt(iInsID),cr.getString(iProgramme)
            );
        }

        close();
        return this.education;
    }

    public ArrayList<Education>getAllEducations(){
        openForRead();
        cr = db.query(DBUtil.TENANT_EDU_TABLE, columns, null, null, null, null, null);

        iInsID = cr.getColumnIndex(DBUtil.COLUMN_INS_ID);
        iYear = cr.getColumnIndex(DBUtil.COLUMN_YEAR);
        iProgramme = cr.getColumnIndex(DBUtil.COLUMN_PROGRAMME);
        iRefNo = cr.getColumnIndex(DBUtil.COLUMN_REF_NO);

        for (cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
            education = new Education(
                    cr.getInt(iYear),cr.getInt(iRefNo),
                    cr.getInt(iInsID),cr.getString(iProgramme)
            );
            allEducations.add(education);
        }

        close();
        return allEducations;
    }
}
